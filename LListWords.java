package F28DA_CW1;

import java.util.Iterator;
import java.util.LinkedList;

public class LListWords implements IWords {
	private LinkedList<String> list = new LinkedList<String>();

	/**
	 * adds word to linked list set
	 */
	public void addWord(String word) throws WException {

		if (!wordExists(word)) {
			list.add(word);
		} else {
			throw new WException("duplicate word is: " + word);
		}
	}

	/**
	 * remove word from linked list set
	 */
	@Override
	public void delWord(String word) throws WException {
		if (wordExists(word)) {
			list.remove(word); // remove specific word
		} else {
			throw new WException("word not here");
		}
	}

	/**
	 * checks if word is in list list
	 */
	@Override
	public boolean wordExists(String word) {
		if (list.contains(word)) {
			return true;
		}
		return false;
	}

	/**
	 * returns number of words
	 */
	@Override
	public int nbWords() {
		return list.size();
	}

	/**
	 * returns interator over linked list
	 */
	@Override
	public Iterator<String> allWords() {
		Iterator<String> itr = list.listIterator();
		return itr;

	}

}
