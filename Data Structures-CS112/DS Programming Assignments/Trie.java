package structures;

import java.util.ArrayList;

/**
 * This class implements a compressed trie. Each node of the tree is a CompressedTrieNode, with fields for
 * indexes, first child and sibling.
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	/**
	 * Words indexed by this trie.
	 */
	ArrayList<String> words;
	
	/**
	 * Root node of this trie.
	 */
	TrieNode root;
	
	/**
	 * Initializes a compressed trie with words to be indexed, and root node set to
	 * null fields.
	 * 
	 * @param words
	 */
	public Trie() {
		root = new TrieNode(null, null, null);
		words = new ArrayList<String>();
	}
	
	/**
	 * Inserts a word into this trie. Converts to lower case before adding.
	 * The word is first added to the words array list, then inserted into the trie.
	 * 
	 * @param word Word to be inserted.
	 */
	public void insertWord(String word) {
		/** COMPLETE THIS METHOD **/
			
			word = word.toLowerCase();
			words.add(word);
			//System.out.print(words);
			TrieNode ptr = root.firstChild;
			TrieNode prev = null;
			//System.out.print(ptr);
			int r = 0;
			short newStartIndex =0;
			while (ptr != null) {
				//System.out.print(ptr);
				//System.out.print(words);
				
				String temp = words.get(ptr.substr.wordIndex);
				short x = ptr.substr.startIndex;
				x= (short) (x - 1);
				
				while (x <= ptr.substr.endIndex){
					//System.out.print(ptr.substr.startIndex);
					String substring = temp.substring(ptr.substr.startIndex, x+2);
					String substring2 = word.substring(ptr.substr.startIndex, x+2);
					if (substring.equals(substring2)) {
						x++;
					} else {
						break;
					}
				}
				
				
				//when going down
				if (x >= ptr.substr.startIndex){ //enters this!
					//System.out.print(word);
					//firstChild being null means that sibling is null too
					int y = x+1;
					newStartIndex = (short) y;
					if (ptr.firstChild == null && ptr.sibling == null) {
						//System.out.print(ptr);
						//firstChild stuff
						ptr.firstChild = new TrieNode(null, null, null);
						TrieNode child = ptr.firstChild;
						//int y = x+1;
						//newStartIndex = (short) y;
						child.substr = new Indexes(ptr.substr.wordIndex, newStartIndex, ptr.substr.endIndex);

						//sibling stuff for "word"
						child.sibling  = new TrieNode(null, null, null);
						TrieNode sib = child.sibling;
						int intLength = word.length()-1;
						short shortLength = (short) intLength;
						sib.substr = new Indexes(words.indexOf(word), newStartIndex, shortLength);
						
						ptr.substr.endIndex = x;
						
						
						break;
		
					} else {
							if (x <ptr.substr.endIndex) {
								//System.out.print(ptr);
								TrieNode child = new TrieNode(null, null, null);
								//int y = x+1;
								//newStartIndex = (short) y;
								child.substr = new Indexes(ptr.substr.wordIndex, newStartIndex, ptr.substr.endIndex);
								child.firstChild = ptr.firstChild;
								ptr.firstChild = child;
								ptr.substr.endIndex = x;
								child.sibling = new TrieNode(null, null, null);
								TrieNode sib = child.sibling;
								int intLength = word.length()-1;
								short shortLength = (short) intLength;
								sib.substr = new Indexes(words.indexOf(word), newStartIndex, shortLength);
								
								break;
							} else {
								//if (x == ptr.substr.endIndex) {
								ptr = ptr.firstChild;
								prev = ptr;
								//you added this break statement
								
								//System.out.print(ptr);
							}
							//System.out.print(word);
							//break;
						//System.out.print(word);
						
						
					}			
				} 
				
				//end of while going down

				else {
					
					prev = ptr;
					ptr = ptr.sibling;
					
					r = 2;
				}
				
				
				
			}
			
			//when going right or when there is no root.firstChild
			if (ptr == null) {
				if (root.firstChild == null) {
					int length = word.length()-1;
					short leng = (short) length;
					ptr = new TrieNode(null, null, null);
					ptr.substr = new Indexes(words.indexOf(word),(short) 0, leng);
					root.firstChild = ptr;
					//System.out.print(ptr);
				} else {
					int length = word.length()-1;
					short leng = (short) length;
					TrieNode ptr1 = new TrieNode(null, null, null);
					ptr1.substr = new Indexes(words.indexOf(word),newStartIndex, leng);
					//System.out.print(ptr1);
					if (r==1) {
						//prev.firstChild = ptr1;
					} else if (r==2) {
						prev.sibling = ptr1;
					}	
				}
				
				
			}
	}
	
	
	/**
	 * Given a string prefix, returns its "completion list", i.e. all the words in the trie
	 * that start with this prefix. For instance, if the tree had the words bear, bull, stock, and bell,
	 * the completion list for prefix "b" would be bear, bull, and bell; for prefix "be" would be
	 * bear and bell; and for prefix "bell" would be bell. (The last example shows that a prefix can be
	 * an entire word.) The order of returned words DOES NOT MATTER. So, if the list contains bear and
	 * bell, the returned list can be either [bear,bell] or [bell,bear]
	 * 
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all words in tree that start with the prefix, order of words in list does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */

	public ArrayList<String> completionList(String prefix) {
		/** COMPLETE THIS METHOD **/
		
		ArrayList<String> completion = new ArrayList<String>();
		
		//span the sibling set of root.firstChild;
		TrieNode ptr = root.firstChild;
		completion  = getWords(ptr, prefix);
		
		return completion;
		
	}
	
	
	private ArrayList<String> getWords(TrieNode ptr, String prefix) {
		ArrayList<String> gettinWords = new ArrayList<String>();
		int endIndex = ptr.substr.endIndex;
		int prefixLength = prefix.length();
		//sweep through the siblings
		while (ptr != null) {
			int counter = 0;
			String idword = words.get(ptr.substr.wordIndex);
			int idlength = idword.length();
			//System.out.print(idword);
			if (idword.length() >= prefixLength) {
				String substring = idword.substring(0, prefixLength);
				if (substring.equals(prefix)){
					if (ptr.firstChild == null) {
						//System.out.print(idword);
						gettinWords.add(idword);
						
					} else {
						gettinWords.addAll(getWords(ptr.firstChild, prefix));
						counter = 1;
					}
				}
			}
			
			if (ptr.firstChild != null) {
				if (counter == 0) {
					gettinWords.addAll(getWords(ptr.firstChild, prefix));
				}
			}
			
			
			ptr = ptr.sibling;
		}
		
		return gettinWords;
		
	}
	
	
	public void print() {
		print(root, 1, words);
	}
	
	private static void print(TrieNode root, int indent, ArrayList<String> words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			System.out.println("      " + words.get(root.substr.wordIndex));
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		System.out.println("(" + root.substr + ")");
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
