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
		while (rand.contains("<noun>c")) {
			String s = words.randomNoun();
			rand = rand.replaceFirst("<noun>c", "<b>"+s.substring(0, 1).toUpperCase() + s.substring(1)+"</b>");
		}
		while (rand.contains("<plural noun>c")) {
			String s = words.randomPluralNoun();
			rand = rand.replaceFirst("<plural noun>c", "<b>"+s.substring(0, 1).toUpperCase() + s.substring(1)+"</b>");
		}
		while (rand.contains("<verb>c")) {
			String s = words.randomVerb();
			rand = rand.replaceFirst("<verb>c", "<b>"+s.substring(0, 1).toUpperCase() + s.substring(1)+"</b>");
		}
		while (rand.contains("<past verb>c")) {
			String s = words.randomPastVerb();
			rand = rand.replaceFirst("<past verb>c", "<b>"+s.substring(0, 1).toUpperCase() + s.substring(1)+"</b>");
		}
		while (rand.contains("<adverb>c")) {
			String s = words.randomAdverb();
			rand = rand.replaceFirst("<adverb>c", "<b>"+s.substring(0, 1).toUpperCase() + s.substring(1)+"</b>");
		}
		while (rand.contains("<adjective>c")) {
			String s = words.randomAdjective();
			rand = rand.replaceFirst("<adjective>c", "<b>"+s.substring(0, 1).toUpperCase() + s.substring(1)+"</b>");
		}
		while (rand.contains("<number>c")) {
			String s = words.randomNumber();
			rand = rand.replaceFirst("<number>c", "<b>"+s.substring(0, 1).toUpperCase() + s.substring(1)+"</b>");
		}
		while (rand.contains("<proper noun>c")) {
			String s = words.randomProperNoun();
			rand = rand.replaceFirst("<proper noun>c", "<b>"+s.substring(0, 1).toUpperCase() + s.substring(1)+"</b>");
		}
		while (rand.contains("<proper plural noun>c")) {
			String s = words.randomProperPluralNoun();
			rand = rand.replaceFirst("<proper plural noun>c", "<b>"+s.substring(0, 1).toUpperCase() + s.substring(1)+"</b>");
		}
		
		
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
		while (rand.contains("<proper noun>")) {
			rand = rand.replaceFirst("<proper noun>", "<b>"+words.randomProperNoun()+"</b>");
		}
		while (rand.contains("<proper plural noun>")) {
			rand = rand.replaceFirst("<proper plural noun>", "<b>"+words.randomProperPluralNoun()+"</b>");
		}
		String ans="";
		for(String a:rand.split("\n")){
			ans+="<p>"+a+"</p>";
		}
		System.out.println("Request Complete: "+ ans);
		return ans;
	}

	public static void addStringToSet(String s, String t, int weightM, WordSet words) {
		ArrayList<Word> addList = null;
		if (t.equals("RB") || t.equals("RBR") || t.equals("RBS")) {
			addList = words.adverbs;
		} else if (t.equals("NN")) {
			addList = words.nouns;
		} else if (t.equals("NNS")) {
			addList = words.pluralNouns;
		} else if (t.equals("VB") || t.equals("VBG")) {
			addList = words.verbs;
		} else if (t.equals("VBD") || t.equals("VBN")) {
			addList = words.pastVerbs;
		} else if (t.equals("JJ") || t.equals("JJR") || t.equals("JJS")) {
			addList = words.adjectives;
		} else if (t.equals("CD")) {
			addList = words.numbers;
		} else if (t.equals("NNP")) {
			addList = words.properNouns;
			addStringToSet(s, "NN", weightM, words);
		} else if (t.equals("NNPS")) {
			addList = words.properPluralNouns;
			addStringToSet(s, "NNS", weightM, words);
		}
		if (addList != null) {
			if (addList.contains(new Word(s, weightM))) {
				for (Word w : addList) {
					if (w.word.equals(s))
						w.add(weightM);
				}
			} else {
				addList.add(new Word(s, weightM));
			}

		}
	}
}
