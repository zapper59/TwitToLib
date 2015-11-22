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

	public static String twitTheLibs(Map<String,Integer>args){
		WordSet words = new WordSet();
		MaxentTagger tagger = new MaxentTagger("english-bidirectional-distsim.tagger");

		for(Map.Entry<String,Integer>entry:args.entrySet()){
			List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(entry.getKey()));
			out.println(sentences);
			for (List<HasWord> sentence : sentences) {
				List<TaggedWord> tSentence = tagger.tagSentence(sentence);
				String s = Sentence.listToString(tSentence, false);
				String[] wordList = s.split(" ");
				for (String word : wordList) {
					String[] part = word.split("/");
					addStringToSet(part[0], part[1], entry.getValue()+1, words);
				}
			}
		}
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
