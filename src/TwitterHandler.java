import java.io.*;
import java.util.*;

import static java.lang.System.*;
import twitter4j.*;
//import
public class TwitterHandler {
	public static Map<String,Integer> find(String user) throws TwitterException{
		user = user.replaceAll("^@", "");
		Twitter twitter = TwitterFactory.getSingleton();
//		Status status = twitter.updateStatus("yo dawgs");
//		out.println(status.getText());
		Query q = new Query("from:"+user);
		q.setCount(100);
		QueryResult result = twitter.search(q);
		Map<String,Integer>ans=new HashMap<>();
		out.println(result.getTweets().size());
		for(Status s:result.getTweets()){
			ans.put(s.getText(),s.getRetweetCount());
			out.println(s.getText());
		}
		out.println(ans);
		return ans;
	}

}
