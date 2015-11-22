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
public class LibsList {
	public static String randomLib() {
		return libs[(int) (libs.length * Math.random())];
	}
	private static String[] libs = {
			"Dear School Nurse : miss <noun> \n<adjective> <noun> will not be attending school today. He she has come down with a case of <verb> and has horrible <noun> and a <adjective> fever. We have made an appointment with the <adjective> Dr. <noun> who studied for <number> years in <noun> and has <number> degrees in pediatrics. He will send you all the <plural noun> you need. Thank you!\nSincerely\nMrs. <noun>.",
			"We the <plural noun> of the United States, in Order to form a <adverb> perfect Union, establish <noun>, <verb> <adjective> <noun>, <verb> for the common <noun>, <verb> the <adjective> Welfare, and <verb> the Blessings of <noun> to <plural noun> and our <plural noun>, do ordain and establish this Constitution for the <adverb> <plural noun> of America.",
			"A <adjective> time ago in a <noun> far, far away, there lived a very clever Jedi . They were trying very hard to hunt down the new <noun>. He was a man with a double sided <noun> saver. His name was <noun> <noun>. He had an army of <plural noun>. The Jedi Council had to send 2 Jedi's to fight him. When they fought him, one <plural noun> almost died and the other went down a <adjective> hole. However, the jedi that went down the hole caught <noun> on the way and managed to get out of the hole. Next, he was able to <verb> the <noun> out of the jedi that died and cut <noun> <noun> in to two bits! <noun> <noun> was beaten this time. The jedis were very happy to have won this <adjective> battle."};
}
