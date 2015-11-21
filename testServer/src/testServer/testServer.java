package testServer;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class testServer {

	public static void main(String[] args) throws Exception {
//		log.info(new File("log4j2.xml"));
		//testJSON();

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/helloworld", new MyHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
//		log.info("Server Started");
	}
	
}

class MyHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange t) throws IOException {
		try{
			out.println("headers: "+t.getRequestHeaders().entrySet());
//			log.info("Request Recieved");
			InputStream is=t.getRequestBody();
			byte[] buf=new byte[1000];
			String s="";
			int len = 1;
			while(len>0){
				len=is.read(buf);
				if(len>0)s += new String(buf,0,len);
			}
//			if(s.length()>0)addToBuffer(s);
//			new DatabaseUpdater().notifyOfChange();

//			String response = "<jobs><build><number>10</number></build><build><number>20</number></build></jobs>";
			String response = "Hello World";
			t.sendResponseHeaders(200, response.getBytes().length);

			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}catch(Exception e){e.printStackTrace();}
	}
}
