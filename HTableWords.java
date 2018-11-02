package F28DA_CW1;

import java.util.*;

public class HTableWords implements IWords, IHashing, IMonitor {
	private int hashArraySize = 7;
	private String[] hashArray = new String[hashArraySize];
	private float maxLoadFactor;
	private int nbWords = 0;
	private int numOfOperations = 0;
	private int amountOfProbing = 0;

	/**
	 * increases size of hash table array
	 * 
	 * @throws WException
	 */
	public HTableWords() { // sets max load factor to .05
		maxLoadFactor = 0.5f;
	}

	public HTableWords(float maxLF) { // allows to set max LF
		maxLoadFactor = maxLF;
	}

	/**
	 * Increases the size of an array
	 * 
	 * @throws WException
	 */
	private void incArray() throws WException {
		hashArraySize = primeNumber(hashArraySize * 2); // adjusts the size variable
		String tempArray[] = new String[hashArraySize]; // creates an array at least twice the current size(prime)
		int k = 0;
		for (int i = 0; i < hashArray.length; i++) { // take each word from current hashmap and re-hash
			if (k == nbWords()) {
				break; // if we have copied over all the words, break the loop
			} else if (hashArray[i] != null) {
				k++;
				String word = hashArray[i]; // the word from the main array
				hash(tempArray, word); // hash the word onto the temp array
			}
		}
		hashArray = tempArray;
	}

	/**
	 * hashes a word onto a given array
	 * 
	 * @param array
	 * @param word
	 */
	private void hash(String[] array, String word) {

		for (int i = hashIndex(word); i < array.length; i = i + doubleHash(word)) {
			if (array[i] == null) { // if next d.hashing spot empty
				array[i] = word; // add word
				break;
			} else {
				i = toFront(i, word); // move to front, if nessiary
				amountOfProbing++;
			}
		}

	}

	/**
	 * Gives array index for when double hashing goes over the end of array
	 * 
	 * @param index
	 * @param word
	 * @return array index
	 */
	private int toFront(int index, String word) {// returns true
		if (index + doubleHash(word) > hashArraySize) { // only chnage if it will go over the end
			int j = (hashArraySize) - index; // find remaining cells in array
			index = 0 - j;
		}
		return index;
	}

	/**
	 * calculates the integer for double hashing
	 * 
	 * @param word
	 * @return the double hashing value
	 */
	private int doubleHash(String word) {
		int doubleFac = primeNumber(hashArraySize % 5); // will give a smaller but not tiny prime number
		int doubleHash = doubleFac - (giveCode(word) % doubleFac); // double hashing number
		return doubleHash;
	}

	/**
	 * calculates hash value
	 * 
	 * @param word
	 * @return words index within the array
	 */
	private int hashIndex(String word) {
		int hashedWordIndex = giveCode(word) % hashArraySize; // gets the has code and makes it compatible with array
		return hashedWordIndex;
	}

	/**
	 * generates the prime after after the given number
	 * 
	 * @param currentPrime
	 * @return next prime number
	 */
	private int primeNumber(int currentNum) {
		for (int i = currentNum + 1; i > currentNum; i++) { // start on number 1 larger than current number
			boolean isPrimeNumber = true;
			for (int j = 2; j < i; j++) { // dividing
				if (i % j == 0) { // if anything divied its not prime
					isPrimeNumber = false;
					break;
				}
			}
			if (isPrimeNumber) { // if the number is prime then return it.
				return i;
			}
		}
		return currentNum;
	}

	/**
	 * adds a word to the main array
	 */
	@Override
	public void addWord(String word) throws WException {
		if (!wordExists(word)) { // if word does not exist
			nbWords++; // increase number of words counter
			hash(hashArray, word); // hashes word onto global array
			numOfOperations++;
			if (loadFactor() > maxLoadFactor()) {
				incArray(); // increase size of array.
			}
		} else {
			throw new WException("word already added");
		}

	}

	/**
	 * deletes word from main array
	 */
	@Override
	public void delWord(String word) throws WException { // still need to do
		if (wordExists(word)) {

			for (int i = hashIndex(word); i < hashArraySize; i = i + doubleHash(word)) { // move forward by d.hashing
																							// factor
				if (hashArray[i] != null) {
					if (hashArray[i].equals(word)) { // check if its equal
						hashArray[i] = null; // make spot null;
						nbWords--; // reduce counter
						numOfOperations++;
						break;
					}
				} else {
					amountOfProbing++;
					i = toFront(i, word); // else move to front if nessissary
				}
			}
		} else {
			throw new WException("word does not exist ");
		}
	}

	/**
	 * returns true if word is in main array
	 */
	@Override
	public boolean wordExists(String word) {
		Iterator<String> a = allWords();
		while (a.hasNext()) { // use the all words iterator
			String k = a.next();
			if (word.equals(k)) { // if words is equal
				numOfOperations++;
				return true; // return true
			}
		}
		return false;

	}

	@Override
	public int nbWords() {
		return nbWords; // return counter
	}

	/**
	 * returns iterator over all the words in the array
	 */
	@Override
	public Iterator<String> allWords() {
		String[] condensedArray = new String[nbWords()];
		int k = 0; // counter for condesed array index
		for (int j = 0; j < hashArraySize; j++) { // add words to condesed array
			if (hashArray[j] != null) {
				condensedArray[k] = hashArray[j];
				k++;
			}
		}
		Iterator<String> iterator = Arrays.asList(condensedArray).iterator(); // create an iterator over that array
		return iterator;
	}

	@Override
	public int giveCode(String word) { // polynomial accumulation, inspired from stack overflow
		int hashCode = 0;
		for (int i = 0; i < word.length(); i++) {
			hashCode = Math.abs(17 * hashCode + (word.charAt(i))); // this will only return positive numbers
		}
		return (int) hashCode;
	}

	@Override
	public float maxLoadFactor() {
		return (float) maxLoadFactor; // max Authorized load factor
	}

	@Override
	public float loadFactor() {
		float loadFactor = (float) nbWords() / hashArraySize; // number of word divided by
		return (float) loadFactor;
	}

	@Override
	public float averageProbes() {
		float a = (float) numOfOperations / amountOfProbing;
		return a;
	}
}
