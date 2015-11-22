package testServer;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;

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

	public void handle(HttpExchange t) throws IOException {
		try{
		out.println("");		
		String data = new Scanner(t.getRequestBody()).useDelimiter("\\A").next();
		out.print(data);
		t.getResponseHeaders().add("Access-Control-Allow-Origin","null"); //Important Line, or failed
		t.sendResponseHeaders(200,data.getBytes().length); //Important line
		OutputStream os = t.getResponseBody();
		os.write(data.getBytes());
		os.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}


class MyHandler implements HttpHandler {

	public void handle(HttpExchange t) throws IOException {
		String response = "<p>Hello World</p><br><p>How's going</p>";
		t.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
