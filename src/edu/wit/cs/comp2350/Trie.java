package edu.wit.cs.comp2350;

import java.util.SortedSet;
import java.util.TreeSet;

public class Trie extends Speller {
    private TrieNode root;
    private boolean sortStrings = false;

    /**
     * Creates a new Trie data structure for use in spell checking
     */
    public Trie() {
        this.root = new TrieNode('\0', false);
    }

    /**
     * Inserts a word into the trie
     *
     * @param s String to add
     */
    @Override
    public void insertWord(String s) {
        s = s.toLowerCase().replaceAll("\\P{Print}", "");
        //for each character
        TrieNode current = this.root;
        for (char b : s.toCharArray()) {
            int pos = Character.getNumericValue(b);
            if (current.children[pos] == null) {
                current.children[pos] = new TrieNode(b, false);
            }
            current = current.children[pos];
        }
        current.isFullWord = true;
    }

    /**
     * Checks if a string is inside the trie
     *
     * @param s The string to check
     * @return True if the word is inside the trie
     */
    @Override
    public boolean contains(String s) {
		// TODO Implement this method
        s = s.toLowerCase().replaceAll("\\P{Print}", "");
        TrieNode current = this.root;
        for (char b : s.toCharArray()) {
            int pos = Character.getNumericValue(b);
            if (current.children[pos] == null) {
                return false;
            }
            current = current.children[pos];
        }
        return current.isFullWord;
    }

    /**
     * Gets suggestions for words
     *
     * @param s The string to check
     * @return Array of possible values
     */
    @Override
    public String[] getSugg(String s) {
        return this.findWords(s.toLowerCase().replaceAll("\\P{Print}", "")).toArray(new String[0]);
    }

    /**
     * A helper method for the recursive findWords method
     *
     * @param s String to check for
     * @return returns an ArrayList of possible suggestions
     */
    private SortedSet<String> findWords(String s) {
        SortedSet<String> suggestions = new TreeSet<>();
        int editDistance = 2;
        findWords(s, suggestions, 0, editDistance);
        return suggestions;
    }

    /**
     * Appends and automatically sorts a list of possible suggestions for a given string s
     *
     * @param s            String to check
     * @param suggestions  The set of current suggestions
     * @param strPos       Current position of
     * @param editDistance the string distance between the modified string and the intended word
     */
    private void findWords(String s, SortedSet<String> suggestions, int strPos, int editDistance) {
        if (strPos == s.length()) {
            if (this.contains(s) && !suggestions.contains(s))
                suggestions.add(s);
            return;
        }
        for (int i = 0; i < 26; i++)
            if (s.charAt(strPos) == 'a' + i)
                findWords(s, suggestions, strPos + 1, editDistance);
            else if (editDistance > 0)
                findWords(s.substring(0, strPos) + (char) ('a' + i) + s.substring(strPos + 1, s.length()),
                        suggestions, strPos + 1, editDistance - 1);
    }

    /**
     * A single node in the trie structure
     */
    private class TrieNode {
        char letter;
        boolean isFullWord;
        TrieNode parent;
        TrieNode[] children;

        /**
         * Creates a new TrieNode
         * @param letter The individual character
         * @param isWord true if all the parent characters make up a word
         */
        TrieNode(char letter, boolean isWord) {
            this.letter = letter;
            this.children = new TrieNode[128]; //ascii range
            this.parent = null;
            this.isFullWord = isWord;
        }
    }

}
