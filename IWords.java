
import java.util.Iterator;

/** Interface your set of words has to implement */
public interface IWords {

	/**
	 * Adds a word to the set.
	 * 
	 * @param word
	 *            Word to add.
	 * @throws WException
	 *             If <code>word</code> is already present.
	 **/
	public void addWord(String word) throws WException;
	
	
	/**
	 * Deletes the word from the set.
	 * 
	 * @param word
	 *            Word to delete.
	 * @throws WException
	 *             If the word is not present.
	 */
	public void delWord(String word) throws WException;

	/**
	 * Returns true if a word is present.
	 * 
	 * @param word
	 *            Word being checked.
	 */
	public boolean wordExists(String word);

	/**
	 * Returns the number of words stored in the set.
	 */
	public int nbWords();

	/**
	 * Returns an iterator over all words stored in the set.
	 */
	public Iterator<String> allWords();

}
