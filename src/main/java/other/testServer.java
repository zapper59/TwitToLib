package other;

import static java.lang.System.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;

import twitter4j.TwitterException;
import twittermadlibs.TwitterMadLibs;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class testServer {

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/receiveData", new MyDataHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
		out.println("Server started");
	}

}
/*
 This handler will accept the request of Twitter username, and return result, perherps we don't really need flask do we?"

*/
class MyDataHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange t) throws IOException {
		try{
		out.println("");
		String data = new Scanner(t.getRequestBody()).useDelimiter("\\A").next();
		out.print(data);
		String ans = getAns(data);
		t.getResponseHeaders().add("Access-Control-Allow-Origin","*"); //Important Line, or failed
		if(ans==null)
			t.sendResponseHeaders(500,0);
		else
			t.sendResponseHeaders(200,ans.getBytes().length); //Important line
		OutputStream os = t.getResponseBody();
		os.write(ans.getBytes());
		os.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	public static String getAns(String a){
		Map<String, Integer> map=null;
		String ans=null;
		try {

			map = TwitterHandler.find(a);
			ans = TwitterMadLibs.twitTheLibs(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ans;
	}
}