/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twittermadlibs;

import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeSet;

import static java.lang.System.*;
public class TwitterMadLibs {
	static class TaggerRunner implements Runnable{
		public MaxentTagger tagger;
		public Map.Entry<String,Integer>entry;
		public WordSet words;
		public volatile int[]remaining;
		public TaggerRunner(MaxentTagger tag, Map.Entry<String,Integer>e, WordSet w, int[]rem){
			tagger=tag;
			words=w;
			entry=e;
			remaining=rem;
		}
		@Override
		public void run(){
			List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(entry.getKey()));
			out.print(".");
			out.println(sentences);
			for (List<HasWord> sentence : sentences) {
				List<TaggedWord> tSentence = tagger.tagSentence(sentence);
				String s = Sentence.listToString(tSentence, false);
				String[] wordList = s.split(" ");
				for (String word : wordList) {
					String[] part = word.split("/");
					if(remaining[0]>0)
						words.addStringToSet(part[0], part[1], entry.getValue()+1);
				}
			}
			remaining[0]--;
			out.println(remaining[0] + " " + entry.getKey());
			if(remaining[0]==0){
				synchronized(remaining){
					remaining.notifyAll();
				}
			}
		}
	}
	public static MaxentTagger tagger = new MaxentTagger("english-bidirectional-distsim.tagger");
	public static String twitTheLibs(Map<String,Integer>args){
		WordSet words = new WordSet();
		if(args.size()==0)
			return "No Tweets!";
		int []running=new int[]{args.size()};
		for(Map.Entry<String,Integer>entry:args.entrySet()){
			new Thread(new TaggerRunner(tagger,entry,words,running)).start();;
		}
		synchronized(running){
			try {
				running.wait(5000);
			} catch (InterruptedException e) {
				out.println("timeout");
				running[0]=-1;
			}
		}
		out.println();
		System.out.println("Sorting....");
		words.sortAndCountSets();

		String rand = LibsList.randomLib();
		while (rand.contains("<noun>")) {
			rand = rand.replaceFirst("<noun>", "<b>"+words.randomNoun()+"</b>");
		}
		while (rand.contains("<plural noun>")) {
			rand = rand.replaceFirst("<plural noun>", "<b>"+words.randomPluralNoun()+"</b>");
		}
		while (rand.contains("<verb>")) {
			rand = rand.replaceFirst("<verb>", "<b>"+words.randomVerb()+"</b>");
		}
		while (rand.contains("<past verb>")) {
			rand = rand.replaceFirst("<past verb>", "<b>"+words.randomPastVerb()+"</b>");
		}
		while (rand.contains("<adverb>")) {
			rand = rand.replaceFirst("<adverb>", "<b>"+words.randomAdverb()+"</b>");
		}
		while (rand.contains("<adjective>")) {
			rand = rand.replaceFirst("<adjective>", "<b>"+words.randomAdjective()+"</b>");
		}
		while (rand.contains("<number>")) {
			rand = rand.replaceFirst("<number>", "<b>"+words.randomNumber()+"</b>");
		}
		String ans="";
		for(String a:rand.split("\n")){
			ans+="<p>"+a+"</p>";
		}
		System.out.println("Request Complete: "+ ans);
		return ans;
	}
}
