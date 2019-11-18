package search;

import java.io.*;
import java.util.*;

/**
 * This class encapsulates an occurrence of a keyword in a document. It stores the
 * document name, and the frequency of occurrence in that document. Occurrences are
 * associated with keywords in an index hash table.
 * 
 * @author Sesh Venugopal
 * 
 */
class Occurrence {
	/**
	 * Document in which a keyword occurs.
	 */
	String document;

	/**
	 * The frequency (number of times) the keyword occurs in the above document.
	 */
	int frequency;

	/**
	 * Initializes this occurrence with the given document,frequency pair.
	 * 
	 * @param doc Document name
	 * @param freq Frequency
	 */
	public Occurrence(String doc, int freq) {
		document = doc;
		frequency = freq;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + document + "," + frequency + ")";
	}
}

/**
 * This class builds an index of keywords. Each keyword maps to a set of documents in
 * which it occurs, with frequency of occurrence in each document. Once the index is built,
 * the documents can searched on for keywords.
 *
 */
public class LittleSearchEngine {

	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in descending
	 * order of occurrence frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;

	/**
	 * The hash table of all noise words - mapping is from word to itself.
	 */
	HashMap<String,String> noiseWords;

	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashMap<String,String>(100,2.0f);
	}

	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
			throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.put(word,word);
		}

		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeyWords(docFile);
			mergeKeyWords(kws);
		}

	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeyWords(String docFile) 
			throws FileNotFoundException {
		HashMap<String, Occurrence> hashbrowns = new HashMap <String, Occurrence>();
		Scanner freshScan = new Scanner(new File(docFile));
		while(freshScan.hasNext()){
			String word = getKeyWord(freshScan.next());
			System.out.println(word);
			if(!word.equals(null)){		//Word is not an invalid word for any reason.
				if(hashbrowns.get(word).equals(null)){	// First occurrence of this word in HashMap.
					Occurrence value = new Occurrence(docFile, 0);
					hashbrowns.put(word, value);
				}
				else{	// Word already in HashMap, just increase frequency.
					int tmp = hashbrowns.get(word).frequency+1;
					Occurrence value = new Occurrence(docFile, tmp);
					hashbrowns.put(word, value);
				}
			}
			else{
				continue;
			}
		}
		return hashbrowns;
	}

	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeyWords(HashMap<String,Occurrence> kws) {
		for(String aKeyword : kws.keySet()){
			ArrayList<Occurrence> occs = new ArrayList<Occurrence>();
			
			if(!keywordsIndex.containsKey(aKeyword)){
				occs.add(kws.get(aKeyword));
				keywordsIndex.put(aKeyword, occs);
			}
			else {
				occs.addAll(keywordsIndex.get(aKeyword));
				occs.add(kws.get(aKeyword));
				insertLastOccurrence(occs);
				keywordsIndex.replace(aKeyword, occs);
			}
		}
	}

	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * TRAILING punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyWord(String word) {
		if(wordCheck(word) == true){
			return word;
		}
		else{
			return null;
		}
	}

	private boolean wordCheck(String word){
		word.toLowerCase();
		int lengthOfString = word.length();
		char firstChar = word.charAt(0);
		char lastChar = word.charAt(lengthOfString-1);

		if(word.equals(null)){
			return false;
		}

		if(Character.isLetter(firstChar) == false){	// Word starts with punctuation: Invalid.
			return false;
		}

		if(Character.isLetter(lastChar) == false){	// Ends with punctuation.
			for(int i= lengthOfString-1; i >= 0; i--){	// Gets rid of trailing punctuation.
				char chBefore = word.charAt(i-1);
				if(Character.isLetter(chBefore) == false){	// Is NOT a letter.
					continue;
				}
				word = word.substring(0,i+1);
			}
		}

		for(int j=word.length()-1; j >= 0; j--){	// Checks word without trailing punctuation.
			char ch = word.charAt(j);
			if(Character.isLetter(ch) == false){	//Checking for punctuation in middle of word.
				return false;
			}
			continue;
		}

		if(!noiseWords.get(word).equals(null)){		// Word IS A noise word: Invalid.
			return false;
		}

		return true;
	}

	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * same list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion of the last element
	 * (the one at index n-1) is done by first finding the correct spot using binary search, 
	 * then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		
		ArrayList<Integer> middleIndex = new ArrayList<Integer>();
	
		if(occs == null || occs.size()==1){
			return null;
		}
		
		int lastFreq, left, mid, right;
		lastFreq = occs.get(occs.size()-1).frequency;
		left = 0;
		right = occs.size()-2;
		mid = (left + right)/2;
		middleIndex.add(mid);
		Occurrence toInsert = occs.get(occs.size()-1);
		
		if(right == left){	// Only have two elements in list.
			if(lastFreq < occs.get(0).frequency){
				Occurrence tmp = occs.get(0);
				occs.add(0, toInsert);
				occs.add(tmp);
			}
			else {
				return middleIndex;
			}
		}
		
		while(right != left){
			if(lastFreq > occs.get(mid).frequency){
				right = mid-1;
				if(right == left){
					continue;
				}
				mid = (right - left)/2;
				middleIndex.add(mid);
				continue;
			}
			
			if(lastFreq < occs.get(mid).frequency){
				left = mid+1;
				if(right == left){
					continue;
				}
				mid = (left + right)/2;
				middleIndex.add(mid);
				continue;
			}
			
			if(lastFreq == occs.get(mid).frequency){
				occs.add(toInsert);
			}
		}
		
		middleIndex.add(left);
		
		// Once you reach here, you would have inserted already or you know where you need to insert.
		// Now just check whether to insert left or right of mid.
		if(lastFreq > occs.get(left).frequency){
			occs.add(left, toInsert);
		} else {
			occs.add(left+1, toInsert);
		}
		
		occs.remove(occs.size()-1);
		return middleIndex; 
	}

	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of occurrence frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will appear before doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matching documents, the result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of NAMES of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matching documents,
	 *         the result is null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		
		ArrayList<String> top5docs = new ArrayList<String>();
		kw1 = getKeyWord(kw1);
		kw2 = getKeyWord(kw2);
		
		ArrayList<Occurrence> occs = new ArrayList<Occurrence>();
		if(keywordsIndex.containsKey(kw1)){
			occs.addAll(keywordsIndex.get(kw1));
		}
		
		if(kw1 != null && (kw2 != null) && (!kw2.equals(kw1)) && keywordsIndex.containsKey(kw2)) {
			for(Occurrence oc : keywordsIndex.get(kw2)){
				occs.add(oc);
				insertLastOccurrence(occs);
			}
		}
		
		if(occs != null){
			for(Occurrence oc: occs) {
				if(!top5docs.contains(oc.document)){
					top5docs.add(oc.document);
				}
				if(top5docs.size() == 5){
					break;
				}
			}
		}
		
		return top5docs; 
	}
}
