/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twittermadlibs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 *
 * @author justi
 */
public class WordSet {
	int[] counts = new int[9];
	public ArrayList<Word> nouns = new ArrayList<Word>();
	public ArrayList<Word> pluralNouns = new ArrayList<Word>();
	public ArrayList<Word> verbs = new ArrayList<Word>();
	public ArrayList<Word> pastVerbs = new ArrayList<Word>();
	public ArrayList<Word> adverbs = new ArrayList<Word>();
	public ArrayList<Word> adjectives = new ArrayList<Word>();
	public ArrayList<Word> numbers = new ArrayList<Word>();
	public ArrayList<Word> properNouns = new ArrayList<Word>();
	public ArrayList<Word> properPluralNouns = new ArrayList<Word>();
	public WordSet words;
	public WordSet(){
		words=this;
	}
	public void sortAndCountSets() {
		Collections.sort(words.numbers);
		for (Word w : words.numbers) {
			counts[6] += w.weight;
		}

		Collections.sort(words.nouns);
		for (Word w : words.nouns) {
			counts[0] += w.weight;
		}

		Collections.sort(words.pluralNouns);
		for (Word w : words.pluralNouns) {
			counts[1] += w.weight;
		}

		Collections.sort(words.verbs);
		for (Word w : words.verbs) {
			counts[2] += w.weight;
		}

		Collections.sort(words.pastVerbs);
		for (Word w : words.pastVerbs) {
			counts[3] += w.weight;
		}

		Collections.sort(words.adverbs);
		for (Word w : words.adverbs) {
			counts[4] += w.weight;
		}

		Collections.sort(words.adjectives);
		for (Word w : words.adjectives) {
			counts[5] += w.weight;
		}
		
		Collections.sort(words.properNouns);
		for (Word w : words.properNouns) {
			counts[7] += w.weight;
		}

		Collections.sort(words.properPluralNouns);
		for (Word w : words.properPluralNouns) {
			counts[8] += w.weight;
		}
	}

	public String randomNoun() {
		if (counts[0] == 0)
			return "random noun";
		int randomInt = (int) (Math.random() * (counts[0]));
		int i = 0;
		randomInt -= nouns.get(i).weight;
		while (i<nouns.size()-1&&randomInt >= 0) {
			i++;
			randomInt -= nouns.get(i).weight;
		}
		return nouns.get(Math.min(i,nouns.size()-1)).word;
	}

	public String randomPluralNoun() {
		if (counts[1] == 0)
			return "plural noun";
		int randomInt = (int) (Math.random() * (counts[1]));
		int i = 0;
		randomInt -= pluralNouns.get(i).weight;
		while (i<pluralNouns.size()-1&&randomInt >= 0) {
			i++;
			randomInt -= pluralNouns.get(i).weight;
		}
		return pluralNouns.get(Math.min(i,pluralNouns.size()-1)).word;
	}

	public String randomVerb() {
		if (counts[2] == 0)
			return "";
		int randomInt = (int) (Math.random() * (counts[2]));
		int i = 0;
		randomInt -= verbs.get(i).weight;
		while (i<verbs.size()-1&&randomInt >= 0) {
			i++;
			randomInt -= verbs.get(i).weight;
		}
		return verbs.get(Math.min(i,verbs.size()-1)).word;
	}

	public String randomPastVerb() {
		if (counts[3] == 0)
			return "";
		int randomInt = (int) (Math.random() * (counts[3]));
		int i = 0;
		randomInt -= pastVerbs.get(i).weight;
		while (i<pastVerbs.size()-1&&randomInt >= 0) {
			i++;
			randomInt -= pastVerbs.get(i).weight;
		}
		return pastVerbs.get(Math.min(i,pastVerbs.size()-1)).word;
	}

	public String randomAdverb() {
		if (counts[4] == 0)
			return "";
		int randomInt = (int) (Math.random() * (counts[4]));
		int i = 0;
		randomInt -= adverbs.get(i).weight;
		while (i<adverbs.size()-1&&randomInt >= 0) {
			i++;
			randomInt -= adverbs.get(i).weight;
		}
		return pluralNouns.get(Math.min(i,pluralNouns.size()-1)).word;
	}

	public String randomAdjective() {
		if (counts[5] == 0)
			return "";
		int randomInt = (int) (Math.random() * (counts[5]));
		int i = 0;
		randomInt -= adjectives.get(i).weight;
		while (i<adjectives.size()-1&&randomInt >= 0) {
			i++;
			randomInt -= adjectives.get(i).weight;
		}
		return adjectives.get(Math.min(i,adjectives.size()-1)).word;
	}

	public String randomNumber() {
		if (counts[6] == 0)
			return "";
		int randomInt = (int) (Math.random() * (counts[6]));
		int i = 0;
		randomInt -= numbers.get(i).weight;
		while (i<numbers.size()-1&&randomInt >= 0) {
			i++;
			randomInt -= numbers.get(i).weight;
		}
		return numbers.get(Math.min(i,numbers.size()-1)).word;
	}
	
	public String randomProperNoun() {
		if (counts[7] == 0)
			return "";
		int randomInt = (int) (Math.random() * (counts[7]));
		int i = 0;
		randomInt -= properNouns.get(i).weight;
		while (i<properNouns.size()-1&&randomInt >= 0) {
			i++;
			randomInt -= properNouns.get(i).weight;
		}
		return properNouns.get(Math.min(i,properNouns.size()-1)).word;
	}

	public String randomProperPluralNoun() {
		if (counts[8] == 0)
			return "";
		int randomInt = (int) (Math.random() * (counts[8]));
		int i = 0;
		randomInt -= properPluralNouns.get(i).weight;
		while (i<properPluralNouns.size()-1&&randomInt >= 0) {
			i++;
			randomInt -= numbers.get(i).weight;
		}
		return properPluralNouns.get(Math.min(i,properPluralNouns.size()-1)).word;
	}
}
