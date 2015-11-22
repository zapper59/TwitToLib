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
		server.createContext("/helloworld", new MyHandler());
		server.createContext("/receiveData", new MyDataHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
	}

}

class MyDataHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange t) throws IOException {
		try{
		out.println("");
		String data = new Scanner(t.getRequestBody()).useDelimiter("\\A").next();
		out.print(data);
		String ans = getAns(data);
		t.getResponseHeaders().add("Access-Control-Allow-Origin","null"); //Important Line, or failed
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
		try {
			map = TwitterHandler.find(a);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		String ans = TwitterMadLibs.twitTheLibs(map);
		return ans;
	}
}


class MyHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange t) throws IOException {
		String response = "<p>Hello World</p><br><p>How's going</p>";
		t.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
