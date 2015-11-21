package org.javabuffer;
import java.io.*;
import java.util.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

import javax.ws.rs.core.MediaType;

import static java.lang.System.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.*;

import com.codesnippets4all.json.parsers.*;
import com.codesnippets4all.json.utils.*;

import com.sun.jersey.api.client.*;

public class JavaBuffer {
	private static volatile LinkedList<String> buffer = new LinkedList<>();
	private static final Logger log = LogManager.getLogger("server_log");
	private static final String DATABASE_PATH = "c:/users/bririche/documents/neo4j/data";
	//private static final String SERVER_URL = "http://rcdn6-vm97-107:7474/db/data/transaction/commit";
	private static final String SERVER_URL = "http://bririche-ws:8000/db/data";

	public static void main(String[] args) throws Exception {
		log.info(new File("log4j2.xml"));
		//testJSON();

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/db/data", new MyHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
		log.info("Server Started");
	}

	public static synchronized void addToBuffer(String a){
		log.info("Adding: "+a.substring(0, Math.min(a.length(), 2000)) + (a.length()>2000?"...("+a.length()+" characters)":""));
		buffer.addLast(a);
	}

	public static synchronized String popFromBuffer(){
		return buffer.pop();
	}

	public static synchronized boolean bufferEmpty(){
		return buffer.size() == 0;
	}

	public static synchronized int bufferSize(){
		return buffer.size();
	}


	public static void testJSON() throws IOException{
		String json = new String(Files.readAllBytes(Paths.get("test.json")));
		try{
			Map<String, Object> jd = JsonParserFactory.getInstance().newJsonParser().parseJson(json);
			log.info(jd);
			int index = 0;
			List statements = (List) jd.get("statements");
			for(Object a:statements){
				log.info(index++ + " :: " + a);
				Map toRead = (Map) a;
				String statement = (String) toRead.get("statement");
				if(toRead.containsKey("parameters")){
					for(Object b:((Map)toRead.get("parameters")).keySet()){
						log.info(b + " :: " + ((Map)toRead.get("parameters")).get(b));
					}
				}
			}
		}catch(Exception e){log.error("Failure to parse json: " + json, e);}
	}

	static class MyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			try{
				out.println("headers: "+t.getRequestHeaders().entrySet());
				log.info("Request Recieved");
				InputStream is=t.getRequestBody();
				byte[] buf=new byte[1000];
				String s="";
				int len = 1;
				while(len>0){
					len=is.read(buf);
					if(len>0)s += new String(buf,0,len);
				}
				if(s.length()>0)addToBuffer(s);
				new DatabaseUpdater().notifyOfChange();

				String response = "<jobs><build><number>10</number></build><build><number>20</number></build></jobs>";
				t.getResponseHeaders().add("Content-Type","application/xml");
				t.sendResponseHeaders(200, response.getBytes().length);

				OutputStream os = t.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}catch(Exception e){e.printStackTrace();}
		}
	}

	static class DatabaseUpdater implements Runnable{
		private static volatile boolean running = false;
		public void notifyOfChange(){
			new Thread(this).start();
		}
		@Override
		public void run() {
			handleRequest();
		}
		private static synchronized void handleRequest(){
			if(running)return;
			running = true;
			while(!bufferEmpty())
				try{restfulPushToNeo4j(popFromBuffer());}catch(Exception e){log.error(e);}
			running = false;
		}
		public static synchronized void restfulPushToNeo4j(String statement){
			log.info("Pushing Statement to Neo4j Server");
			WebResource resource = Client.create().resource(SERVER_URL);
			ClientResponse response = resource.accept("application/json").type(MediaType.APPLICATION_JSON).entity(statement).post(ClientResponse.class);
			log.info("Push Successfull :: " + (bufferSize()) + " requests remaining");
			//log.info(response.getStatus() + " :: " + response.getEntity(String.class));
			response.close();
		}

		public static synchronized void pushToNeo4j(String json){
			log.info("Pushing Statement to Neo4j");
			GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DATABASE_PATH);
			try(Transaction tx = db.beginTx()){
				Map jd = JsonParserFactory.getInstance().newJsonParser().parseJson(json);
				log.info("Parsed JSON: " + jd);
				List statements = (List) jd.get("statements");
				for(Object a:statements){
					Map toRead = (Map) a;
					String statement = (String) toRead.get("statement");
					Map<String, Object> params = new HashMap<>();
					if(toRead.containsKey("parameters")){
						for(Object b:((Map)toRead.get("parameters")).keySet()){
							params.put((String)b, ((Map)toRead.get("parameters")).get(b));
						}
					}
					log.info("Statement Results:\n" + db.execute(statement, params).resultAsString());
				}
				tx.success();
				log.info("Transaction Successful");
			}catch(Exception e){
				log.error("Failure to parse json: " + json, e);
			}
			finally{
				db.shutdown();
			}
		}
	}
}