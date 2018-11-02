package F28DA_CW1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/** Main class for the Spell-Checker program */
public class SpellChecker {
	/**
	 * Suggests word modifications for a given word and a given word dictionary.
	 * 
	 * @throws WException
	 */
	static public IWords suggestions(String word, IWords dict) throws WException {
		ModWords m = new ModWords();
		IWords list = new HTableWords();

		while (true) {
			String omishChangedWord = m.omish(word, dict, list); // all mods take in the list of words already output
			String insertChangedWord = m.insert(word, dict, list);
			String subChangedWord = m.sub(word, dict, list);
			String reverseChangedWord = m.reversal(word, dict, list);
			try {
				if (subChangedWord != null) {
					list.addWord(subChangedWord);
				} else if (omishChangedWord != null) {
					list.addWord(omishChangedWord);
				} else if (insertChangedWord != null) {
					list.addWord(insertChangedWord);
				} else if (reverseChangedWord != null) {
					list.addWord(reverseChangedWord);
				} else {
					break; // if all modifications are null, break the loop
				}
			} catch (WException e) {
				e.getSuppressed();
			}
		}
		return list;

	}

	/**
	 * Main method for the Spell-Checker program. The program takes two input
	 * filenames in the command line: the word dictionary file and the file
	 * containing the words to spell-check. .
	 * 
	 * 
	 */
	public static void main(String[] args) throws WException {
		if (args.length != 2) {
			System.err.println("Usage: SpellChecker dictionaryFile.txt inputFile.txt ");
			System.exit(1);
		}

		try {

			BufferedInputStream dict, file;
			HTableWords diconary = new HTableWords();
			dict = new BufferedInputStream(new FileInputStream(args[0])); // add diconary to linked list set
			FileWordRead readWords = new FileWordRead(dict);

			long startTime = System.currentTimeMillis();
			while (readWords.hasNextWord()) {
				String nextWord = readWords.nextWord();
				try {
					diconary.addWord(nextWord); // put word in a set
				} catch (WException e) {
					e.getSuppressed();
				}
			}

			long finalTime = System.currentTimeMillis();
			System.out.println("number of words:" + diconary.nbWords());

			dict.close();

			file = new BufferedInputStream(new FileInputStream(args[1])); // open 2nd file to be spell checked
			FileWordRead readWords1 = new FileWordRead(file);

			while (readWords1.hasNextWord()) // if there are words in the file
			{
				String nextWord1 = readWords1.nextWord(); // read the words
				if (diconary.wordExists(nextWord1) == false) // if word is not in the dictionary
				{
					Iterator<String> correctWords = suggestions(nextWord1, diconary).allWords(); // create iterator over
																									// correct words
					System.out.print(nextWord1 + " => "); // user interface
					while (correctWords.hasNext()) { // if there are words left in the list
						System.out.print(correctWords.next() + ", "); // prints out each potential spelling for wrong
																		// word
					}
					System.out.println();
				}
			}
			file.close();
			long runTime = finalTime - startTime;
			System.out.println(runTime + "ms");
		} catch (IOException e) { // catch exceptions caused by file input/output errors
			System.err.println("Missing input file, check your filenames");
			System.exit(1);
		}
	}

}
