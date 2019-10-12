public class ModWords {
	char[] alfabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * corrects a word by changing letters
	 * 
	 * @param word
	 * @param diconary
	 * @param list
	 * @return correctly spelled word, or null
	 */
	public String sub(String word, IWords diconary, IWords list) {
		StringBuilder sb = new StringBuilder(word);
		for (int i = 0; i < sb.length(); i++) {
			char temp = sb.charAt(i); // holds the initial letter
			for (int a = 0; a < alfabet.length; a++) {
				sb.setCharAt(i, alfabet[a]); // each letter changed with one from the alfabet
				String changedWord = sb.toString(); // puts char array into a string
				if (diconary.wordExists(changedWord) && !list.wordExists(changedWord)) {
					return changedWord; // output that word
				} else {
					sb.setCharAt(i, temp);// puts initial letter back
				}
			}
		}
		//this is a pysh test
		return null;
	}

	/**
	 * corrects a word by omitting a letter
	 * 
	 * @param word
	 * @param diconary
	 * @param list
	 * @return correctly spelled word, or null
	 */
	public String omish(String word, IWords diconary, IWords list) { // look at string builder
		StringBuilder sb = new StringBuilder(word);
		for (int i = 0; i < sb.length(); i++) {
			char temp = sb.charAt(i);
			sb.deleteCharAt(i); // deltete the charcater in a spot
			String changedWord = sb.toString();
			if (diconary.wordExists(changedWord) && list.wordExists(changedWord) == false) {
				return changedWord; // if correct output
			} else {
				sb.insert(i, temp); // if not then put it back
			}
		}
		return null;
	}

	/**
	 * corrects a word by insterting letters into the word
	 * 
	 * @param word
	 * @param diconary
	 * @param list
	 * @return correctly spelled word, or null
	 */
	public String insert(String word, IWords diconary, IWords list) {
		StringBuilder sb = new StringBuilder(word);
		for (int i = 0; i < sb.length() + 1; i++) { // move through the word
			for (int a = 0; a < alfabet.length; a++) { // move through alphabet
				sb.insert(i, alfabet[a]); // insert letter into each spot
				String changedWord = sb.toString();
				if (diconary.wordExists(changedWord) && list.wordExists(changedWord) == false) {
					return changedWord; // if word is correct and not already output
				}
				sb.deleteCharAt(i); // once returned delete the added character
			}
		}
		return null;
	}

	/**
	 * corrects a word by reversing letters
	 * 
	 * @param word
	 * @param diconary
	 * @param list
	 * @return correctly spelled word, or null
	 */
	public String reversal(String word, IWords diconary, IWords list) { // swaps 2 adjacent characters
		StringBuilder sb = new StringBuilder(word);
		for (int i = 0; i < sb.length() - 1; i++) {
			char temp = sb.charAt(i); // pull a temp value
			char temp2 = sb.charAt(i + 1); // oull the value after that one
			sb.setCharAt(i + 1, temp); // sb.insert(i+1, temp); set each one to the opposite position
			sb.setCharAt(i, temp2); // sb.insert(i, temp2);
			String changedWord = sb.toString(); // make string builder a string
			if (diconary.wordExists(changedWord) && list.wordExists(changedWord) == false) {
				return changedWord;
			} else {
				sb.setCharAt(i, temp); // else put them back
				sb.setCharAt(i + 1, temp2);
			}
		}
		return null;
	}

}
