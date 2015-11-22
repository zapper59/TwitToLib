/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twittermadlibs;

/**
 *
 * @author justi
 */
public class Word implements Comparable {
	public String word;
	public int weight;

	public Word(String word, int weight) {
		this.word = word;
		this.weight = weight;
	}

	public int add(int weight) {
		this.weight += weight;
		return this.weight;
	}

	@Override
	public int compareTo(Object t) {
		if (((Word) t).weight == this.weight)
			return 0;
		if (((Word) t).weight > this.weight)
			return ((Word) t).weight;
		return -this.weight;
	}

	@Override
	public boolean equals(Object o) {
		return word.equals(((Word) o).word);
	}

}
