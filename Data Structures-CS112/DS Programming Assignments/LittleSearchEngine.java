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
			HashMap<String,Occurrence> docHashMap = new HashMap<String,Occurrence>();
			Scanner sc = new Scanner(new File(docFile));
			while (sc.hasNext()) 
			{
				String line = sc.nextLine();
				if (!line.trim().isEmpty() && !(line == null))
				{	
					String[] token = line.split(" "); 
					for (int i = 0; i < token.length; i++)
					{
						String word = getKeyWord(token[i]);
						if (word != null) //
						{
							if (docHashMap.containsKey(word))
							{
								Occurrence temp = docHashMap.get(word);
								temp.frequency++; 
								docHashMap.put(word, temp); 
							}
							else
							{
								Occurrence occ = new Occurrence (docFile, 1); 
								docHashMap.put(word, occ); 
							}
						}		
					}
				}
			}
			return docHashMap; 		
			// COMPLETE THIS METHOD
			// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		}
		
		public static void main(String[] args) throws FileNotFoundException{
			
			LittleSearchEngine engine = new LittleSearchEngine();
			HashMap<String, String> noiseWords;
			noiseWords = engine.noiseWords;
	

			Scanner textScan = new Scanner(new File("noisewords.txt"));
			while(textScan.hasNext()){//fills the noiseword hashmap
				String word = textScan.next();
				noiseWords.put(word, word);
				
				
			}
			
			textScan.close();
			
			
			HashMap<String, Occurrence> keyWords = engine.loadKeyWords("AliceCh1.txt");
		
			HashMap<String, Occurrence> keyWordsTwo = engine.loadKeyWords("WowCh1.txt");
		
			
			
			HashMap<String, ArrayList<Occurrence>> keyWordsIndex = engine.keywordsIndex;
			
			
			
			for(Map.Entry<String, Occurrence> entry : keyWords.entrySet()){//loads keywords and also checks getKeyWord method at the same time
				
				String currWord = entry.getKey();	
				
			}
			
			engine.mergeKeyWords(keyWords);
			engine.mergeKeyWords(keyWordsTwo);
			
			ArrayList<String> topFive = engine.top5search("death", "drawing");
			
			ArrayList<Occurrence> list1 = keyWordsIndex.get("death");
			ArrayList<Occurrence> list2 = keyWordsIndex.get("drawing");
			
			System.out.print("Current list of item one: ");
			for(int i = 0; i <list1.size(); i++){
				
				System.out.print(list1.get(i) + " ");
				
				
			}
			System.out.println();
			System.out.println();
	

			System.out.print("Current list of item two: ");
	

		    for(int i = 0; i <list2.size(); i++){
				
				System.out.print(list2.get(i) + " ");
				
				
			}
			System.out.println();
			System.out.println();
	

			
			System.out.print("Top five of the two: ");
	

			for(int i = 0; i <topFive.size(); i++){
				
				System.out.print(topFive.get(i));
				
				
			}
			
			
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
			Iterator iter = kws.keySet().iterator();
			int count; 
			while(iter.hasNext()) 
			{
				count = 0; 
				String key = (String)iter.next();
				Occurrence occ = kws.get(key);
				
				if (keywordsIndex.containsKey(key))
				{
					ArrayList<Occurrence> arr = keywordsIndex.get(key); 
					arr.add(occ); 
					ArrayList<Integer> result = insertLastOccurrence(arr); //for graders
					ArrayList<Occurrence> updatedOcc = new ArrayList<Occurrence>(); 
					for (int i = 0; i < arr.size()-1; i++)
						updatedOcc.add(arr.get(i)); 
					
					if (updatedOcc.size() == 1)
					{
						if (updatedOcc.get(0).frequency > occ.frequency)
							updatedOcc.add(occ); 	
						else 
							updatedOcc.add(result.get(result.size()-1), occ);
					}
					else if (result.get(result.size()-1) == updatedOcc.size()-1)
					{
						if (occ.frequency <= updatedOcc.get(updatedOcc.size()-1).frequency)
							updatedOcc.add(occ); 	
						else
							updatedOcc.add(result.get(result.size()-1), occ);
					}
					else if (result.get(result.size()-1) == 0)
					{
						if (occ.frequency >= updatedOcc.get(0).frequency)
						{
							count = 1; 
							ArrayList<Occurrence> temp = new ArrayList<Occurrence>(); 
							temp.add(occ); 
							temp.addAll(updatedOcc); 
							keywordsIndex.put(key, temp);
						}
						else
							updatedOcc.add(1, occ);
					}
					else
						updatedOcc.add(result.get(result.size()-1), occ); 
					if (count!=1)
						keywordsIndex.put(key, updatedOcc); 
				}
				else
				{
					ArrayList<Occurrence> arrayins = new ArrayList<Occurrence>();
					arrayins.add(occ); 
					keywordsIndex.put(key, arrayins);
				}
					 
			}
			// COMPLETE THIS METHOD
		}
		
		/**
		 * Given a word, returns it as a keyword if it passes the keyword test,
		 * otherwise returns null. A keyword is any word that, after being stripped of any
		 * trailing punctuation, consists only of alphabetic letters, and is not
		 * a noise word. All words are treated in a case-INsensitive manner.
		 * 
		 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
		 * 
		 * @param word Candidate word
		 * @return Keyword (word without trailing punctuation, LOWER CASE)
		 */
		public String getKeyWord(String word) { 
			String before = "";
			String middle = ""; 
			String after = ""; 
			int i, j, k;
			if (checkWordforPunctuation(word)) 
			{	
				for (i = 0; i < word.length(); i++)
				{
					if (!Character.isLetter(word.charAt(i)))
						break;  
					else
						before = before + word.charAt(i); 
				}
				for (j = i; j < word.length(); j++)
				{
					if (Character.isLetter(word.charAt(j)))
						break; 
					else
						middle = middle + word.charAt(j);
				}
				for (k = j; k < word.length(); k++)
					after = after + word.charAt(k);
				
				if (after.isEmpty())
				{
				if (noiseWords.containsKey(before.toLowerCase()))
						return null;
					else
					{
						if (!before.trim().isEmpty())
							return before.toLowerCase();
						else
							return null; 
					}
				}
				else 
					return null; 
			}
			else
			{
				if (noiseWords.containsKey(word.toLowerCase()))
					return null;
				else
				{
					if (!word.trim().isEmpty())
						return word.toLowerCase();
					else
						return null;  
				}
			}
			
			// COMPLETE THIS METHOD
			// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		}
		private static boolean checkWordforPunctuation(String word)
		{
			for (int i = 0; i < word.length(); i++)
			{
				if (!Character.isLetterOrDigit(word.charAt(i)))
					return true; 
			}
			return false; 
		}
		
		/**
		 * Inserts the last occurrence in the parameter list in the correct position in the
		 * same list, based on ordering occurrences on descending frequencies. The elements
		 * 0..n-2 in the list are already in the correct order. Insertion is done by
		 * first finding the correct spot using binary search, then inserting at that spot.
		 * 
		 * @param occs List of Occurrences
		 * @return Sequence of mid point indexes in the input list checked by the binary search process,
		 *         null if the size of the input list is 1. This returned array list is only used to test
		 *         your code - it is not used elsewhere in the program.
		 */
		public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
			ArrayList<Integer> arrint = new ArrayList<Integer>(); 
			for (int i = 0; i < occs.size()-1; i++)
			{
				arrint.add(occs.get(i).frequency); 
			}
			int value = occs.get(occs.size()-1).frequency; 
			ArrayList<Integer> result = binarySearch(arrint, value, 0, arrint.size()-1); //for graders
			
			
			// COMPLETE THIS METHOD
			// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
			return result;
		}
		
		private ArrayList<Integer> binarySearch(ArrayList<Integer> arr, int key, int imin, int imax)
		{
		  ArrayList<Integer> midpoints = new ArrayList<Integer>(); 
		  while (imax >= imin)
		  {
		      int imid = (imin + imax) / 2;
		      midpoints.add(imid); 
		      if (arr.get(imid) <  key)
		        imax = imid - 1;
		      else if (arr.get(imid) > key )
		        imin = imid + 1;
		      else
		    	  break; 
		  }
		  return midpoints; 
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
			ArrayList<String> result = new ArrayList<String>();
			ArrayList<Occurrence> list1 = keywordsIndex.get(kw1);
			ArrayList<Occurrence> list2 = keywordsIndex.get(kw2);
			int i = 0, j = 0; 
			int count = 0; //top 5
			if (list1 == null && list2 == null)
				return result; 
			else if (list1==null)//list2 is good
			{
				while (j < list2.size() && count < 5)
				{
					result.add(list2.get(j).document); 
					j++; 
					count++; 
				}
				
			}
			else if (list2==null)//list1 is good
			{
				while (i < list1.size() && count < 5)
				{
					result.add(list1.get(i).document); 
					i++; 
					count++; 
				}
			}
		
			else //both are good
			{	
				while ((i < list1.size() || j < list2.size()) && count < 5) //assuming both words are in master hash table
				{
					if (list1.get(i).frequency > list2.get(j).frequency && (!result.contains(list1.get(i).document))) 
					{
						result.add(list1.get(i).document); 
						i++;
						count++; 
					}
					else if (list1.get(i).frequency < list2.get(j).frequency && (!result.contains(list2.get(j).document)))
					{
						result.add(list2.get(j).document); 
						j++;
						count++; 
					}
					else
					{
						if (!result.contains(list1.get(i).document))
						{
							result.add(list1.get(i).document);
							count++; 
							i++;
						}
						else
							i++; 
						if ((!result.contains(list2.get(j).document)))
						{
							if (count < 5)
							{
								result.add(list2.get(j).document); 
								j++;
								count++; 
							}
						}
						else 
							j++; 
						
					}
				}
			}
			
			// COMPLETE THIS METHOD
			// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
			return result;
		}
	}
