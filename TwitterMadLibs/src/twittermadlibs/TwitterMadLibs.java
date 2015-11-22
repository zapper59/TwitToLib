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
import java.util.TreeSet;


public class TwitterMadLibs {
	
    static WordSet words = new WordSet();

	public static void main(String[] args) {
            args = new String[2];
            args[0] = "english-bidirectional-distsim.tagger";
            args[1] = "";
		if (args.length != 2) {
                System.err.println("usage: java TaggerDemo modelFile fileToTag");
                return;
                }
            MaxentTagger tagger = new MaxentTagger(args[0]);
            List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(getInputString()));
            for (List<HasWord> sentence : sentences) {
              List<TaggedWord> tSentence = tagger.tagSentence(sentence);
              String s = Sentence.listToString(tSentence, false);
              String[] words = s.split(" ");
              for(String word : words)
              {
                  String[] part = word.split("/");
                  addStringToSet(part[0],part[1],1);
              }
            }
            
            words.sortAndCountSets();
  
            String rand = LibsList.randomLib();
            while(rand.contains("<noun>"))
            {
                rand = rand.replaceFirst("<noun>", words.randomNoun());
            }
            while(rand.contains("<plural noun>"))
            {
                rand = rand.replaceFirst("<plural noun>", words.randomPluralNoun());
            }
            while(rand.contains("<verb>"))
            {
                rand = rand.replaceFirst("<verb>", words.randomVerb());
            }
            while(rand.contains("<past verb>"))
            {
                rand = rand.replaceFirst("<past verb>", words.randomPastVerb());
            }
            while(rand.contains("<adverb>"))
            {
                rand = rand.replaceFirst("<adverb>", words.randomAdverb());
            }
            while(rand.contains("<adjective>"))
            {
                rand = rand.replaceFirst("<adjective>", words.randomAdjective());
            }
            while(rand.contains("<number>"))
            {
                rand = rand.replaceFirst("<number>", words.randomNumber());
            }
            System.out.println(rand);
        }
        
        
        public static String getInputString()
        {
            return " Wow. Whoa. That is some group of people. Thousands.\n" +
"\n" +
"So nice, thank you very much. That’s really nice. Thank you. It’s great to be at Trump Tower. It’s great to be in a wonderful city, New York. And it’s an honor to have everybody here. This is beyond anybody’s expectations. There’s been no crowd like this.\n" +
"\n" +
"And, I can tell, some of the candidates, they went in. They didn’t know the air-conditioner didn’t work. They sweated like dogs.\n" +
"\n" +
"(LAUGHTER)\n" +
"\n" +
"They didn’t know the room was too big, because they didn’t have anybody there. How are they going to beat ISIS? I don’t think it’s gonna happen.\n" +
"\n" +
"(APPLAUSE)\n" +
"\n" +
"Our country is in serious trouble. We don’t have victories anymore. We used to have victories, but we don’t have them. When was the last time anybody saw us beating, let’s say, China in a trade deal? They kill us. I beat China all the time. All the time.\n" +
"\n" +
"(APPLAUSE)\n" +
"\n" +
"AUDIENCE MEMBER: We want Trump. We want Trump.\n" +
"\n" +
"TRUMP: When did we beat Japan at anything? They send their cars over by the millions, and what do we do? When was the last time you saw a Chevrolet in Tokyo? It doesn’t exist, folks. They beat us all the time.\n" +
"\n" +
"When do we beat Mexico at the border? They’re laughing at us, at our stupidity. And now they are beating us economically. They are not our friend, believe me. But they’re killing us economically.\n" +
"\n" +
"The U.S. has become a dumping ground for everybody else’s problems.\n" +
"\n" +
"(APPLAUSE)\n" +
"\n" +
"Thank you. It’s true, and these are the best and the finest. When Mexico sends its people, they’re not sending their best. They’re not sending you. They’re not sending you. They’re sending people that have lots of problems, and they’re bringing those problems with us. They’re bringing drugs. They’re bringing crime. They’re rapists. And some, I assume, are good people.\n" +
"\n" +
"But I speak to border guards and they tell us what we’re getting. And it only makes common sense. It only makes common sense. They’re sending us not the right people.\n" +
"\n" +
"It’s coming from more than Mexico. It’s coming from all over South and Latin America, and it’s coming probably — probably — from the Middle East. But we don’t know. Because we have no protection and we have no competence, we don’t know what’s happening. And it’s got to stop and it’s got to stop fast.(APPLAUSE)\n" +
"\n" +
"TRUMP: Islamic terrorism is eating up large portions of the Middle East. They’ve become rich. I’m in competition with them.\n" +
"\n" +
"They just built a hotel in Syria. Can you believe this? They built a hotel. When I have to build a hotel, I pay interest. They don’t have to pay interest, because they took the oil that, when we left Iraq, I said we should’ve taken.\n" +
"\n" +
"So now ISIS has the oil, and what they don’t have, Iran has. And in 19 — and I will tell you this, and I said it very strongly, years ago, I said — and I love the military, and I want to have the strongest military that we’ve ever had, and we need it more now than ever. But I said, “Don’t hit Iraq,” because you’re going to totally destabilize the Middle East. Iran is going to take over the Middle East, Iran and somebody else will get the oil, and it turned out that Iran is now taking over Iraq. Think of it. Iran is taking over Iraq, and they’re taking it over big league.\n" +
"\n" +
"We spent $2 trillion in Iraq, $2 trillion. We lost thousands of lives, thousands in Iraq. We have wounded soldiers, who I love, I love — they’re great — all over the place, thousands and thousands of wounded soldiers.\n" +
"\n" +
"And we have nothing. We can’t even go there. We have nothing. And every time we give Iraq equipment, the first time a bullet goes off in the air, they leave it.\n" +
"\n" +
"Last week, I read 2,300 Humvees — these are big vehicles — were left behind for the enemy. 2,000? You would say maybe two, maybe four? 2,300 sophisticated vehicles, they ran, and the enemy took them.\n" +
"\n" +
"AUDIENCE MEMBER: We need Trump now.\n" +
"\n" +
"TRUMP: You’re right.\n" +
"\n" +
"(APPLAUSE)\n" +
"\n" +
"AUDIENCE MEMBER: We need Trump now.\n" +
"\n" +
"TRUMP: Last quarter, it was just announced our gross domestic product — a sign of strength, right? But not for us. It was below zero. Whoever heard of this? It’s never below zero.\n" +
"\n" +
"Our labor participation rate was the worst since 1978. But think of it, GDP below zero, horrible labor participation rate.\n" +
"\n" +
"And our real unemployment is anywhere from 18 to 20 percent. Don’t believe the 5.6. Don’t believe it.\n" +
"\n" +
"That’s right. A lot of people up there can’t get jobs. They can’t get jobs, because there are no jobs, because China has our jobs and Mexico has our jobs. They all have jobs.\n" +
"\n" +
"But the real number, the real number is anywhere from 18 to 19 and maybe even 21 percent, and nobody talks about it, because it’s a statistic that’s full of nonsense.\n" +
"\n" +
"AUDIENCE MEMBER: We want Trump now.\n" +
"\n" +
"TRUMP: Our enemies are getting stronger and stronger by the way, and we as a country are getting weaker. Even our nuclear arsenal doesn’t work.\n" +
"\n" +
"It came out recently they have equipment that is 30 years old. They don’t know if it worked. And I thought it was horrible when it was broadcast on television, because boy, does that send signals to Putin and all of the other people that look at us and they say, “That is a group of people, and that is a nation that truly has no clue. They don’t know what they’re doing. They don’t know what they’re doing.”\n" +
"\n" +
"AUDIENCE MEMBER: We need Trump now.\n" +
"\n" +
"(APPLAUSE)\n" +
"\n" +
"TRUMP: We have a disaster called the big lie: Obamacare. Obamacare.\n" +
"\n" +
"Yesterday, it came out that costs are going for people up 29, 39, 49, and even 55 percent, and deductibles are through the roof. You have to be hit by a tractor, literally, a tractor, to use it, because the deductibles are so high, it’s virtually useless. It’s virtually useless. It is a disaster.TRUMP: And remember the $5 billion Web site? $5 billion we spent on a Web site, and to this day it doesn’t work. A $5 billion Web site.\n" +
"\n" +
"I have so many Web sites, I have them all over the place. I hire people, they do a Web site. It costs me $3. $5 billion Web site.\n" +
"\n" +
"(APPLAUSE)\n" +
"\n" +
"AUDIENCE: We want Trump. We want Trump. We want Trump. We want Trump.\n" +
"\n" +
"TRUMP: Well, you need somebody, because politicians are all talk, no action. Nothing’s gonna get done. They will not bring us — believe me — to the promised land. They will not.\n" +
"\n" +
"As an example, I’ve been on the circuit making speeches, and I hear my fellow Republicans. And they’re wonderful people. I like them. They all want me to support them. They don’t know how to bring it about. They come up to my office. I’m meeting with three of them in the next week. And they don’t know — “Are you running? Are you not running? Could we have your support? What do we do? How do we do it?”\n" +
"\n" +
"I like them. And I hear their speeches. And they don’t talk jobs and they don’t talk China. When was the last time you heard China is killing us? They’re devaluing their currency to a level that you wouldn’t believe. It makes it impossible for our companies to compete, impossible. They’re killing us.\n" +
"\n" +
"But you don’t hear that from anybody else. You don’t hear it from anybody else. And I watch the speeches.\n" +
"\n" +
"AUDIENCE MEMBER: No more free (inaudible).\n" +
"\n" +
"TRUMP: Thank you.\n" +
"\n" +
"I watch the speeches of these people, and they say the sun will rise, the moon will set, all sorts of wonderful things will happen. And people are saying, “What’s going on? I just want a job. Just get me a job. I don’t need the rhetoric. I want a job.”\n" +
"\n" +
"And that’s what’s happening. And it’s going to get worse, because remember, Obamacare really kicks in in ’16, 2016. Obama is going to be out playing golf. He might be on one of my courses. I would invite him, I actually would say. I have the best courses in the world, so I’d say, you what, if he wants to — I have one right next to the White House, right on the Potomac. If he’d like to play, that’s fine.\n" +
"\n" +
"(APPLAUSE)\n" +
"\n" +
"In fact, I’d love him to leave early and play, that would be a very good thing.\n" +
"\n" +
"(LAUGHTER)\n" +
"\n" +
"But Obamacare kicks in in 2016. Really big league. It is going to be amazingly destructive. Doctors are quitting. I have a friend who’s a doctor, and he said to me the other day, “Donald, I never saw anything like it. I have more accountants than I have nurses. It’s a disaster. My patients are beside themselves. They had a plan that was good. They have no plan now.”\n" +
"\n" +
"We have to repeal Obamacare, and it can be — and — and it can be replaced with something much better for everybody. Let it be for everybody. But much better and much less expensive for people and for the government. And we can do it.\n" +
"\n" +
"(APPLAUSE)\n" +
"\n" +
"AUDIENCE: Trump. Trump. Trump. Trump. Trump. Trump.\n" +
"\n" +
"TRUMP: So I’ve watched the politicians. I’ve dealt with them all my life. If you can’t make a good deal with a politician, then there’s something wrong with you. You’re certainly not very good. And that’s what we have= representing us. They will never make America great again. They don’t even have a chance. They’re controlled fully — they’re controlled fully by the lobbyists, by the donors, and by the special interests, fully.\n" +
"\n" +
"Yes, they control them. Hey, I have lobbyists. I have to tell you. I have lobbyists that can produce anything for me. They’re great. But you know what? it won’t happen. It won’t happen. Because we have to stop doing things for some people, but for this country, it’s destroying our country. We have to stop, and it has to stop now.\n" +
"\n" +
"AUDIENCE: It needs Trump.\n" +
"\n" +
"TRUMP: Now, our country needs — our country needs a truly great leader, and we need a truly great leader now. We need a leader that wrote “The Art of the Deal.”\n" +
"\n" +
"AUDIENCE MEMBER: Yes.\n" +
"\n" +
"TRUMP: We need a leader that can bring back our jobs, can bring back our manufacturing, can bring back our military, can take care of our vets. Our vets have been abandoned.\n" 
;
        }
        
        public static void addStringToSet(String s, String t, int weightM){
            ArrayList<Word> addList = null;
            if(t.equals("RB") || t.equals("RBR") || t.equals("RBS")){
               addList = words.adverbs;
            }
            else if(t.equals("NN") || t.equals("NNP")){
                addList = words.nouns;
            }
            else if(t.equals("NNS") || t.equals("NNPS")){
                addList = words.pluralNouns;
            }
            else if(t.equals("VB") || t.equals("VBG")){
                addList = words.verbs;
            }
            else if(t.equals("VBD") || t.equals("VBN")){
                addList = words.pastVerbs;
            }
            else if(t.equals("JJ") || t.equals("JJR")|| t.equals("JJS")){
                addList = words.adjectives;
            }
            else if(t.equals("CD")){
                addList = words.numbers;
            }
            if(addList != null)
            {
                System.out.println(s);
                if(addList.contains(new Word(s,weightM))) {
                    System.out.println(s);
                    for(Word w : addList)
                    {
                        if(w.word.equals(s))
                            w.add(weightM);
                    }
                }
            else {
                addList.add(new Word(s,weightM));
            }
            
        }
    }
}
