package edu.wit.cs.comp2350;

import java.util.ArrayList;

public class Trie extends Speller {
    private TrieNode root;
    private int editDistance = 2;

    public Trie() {
        this.root = new TrieNode('\0', false);
    }

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

	@Override
	public String[] getSugg(String s) {
        s = s.toLowerCase().replaceAll("\\P{Print}", "");
        ArrayList<String> suggestions = new ArrayList<>();
        ArrayList<String> results = new ArrayList<>();
        findWords(s, suggestions, 0, this.editDistance);
        for (String sugg : suggestions) {
            if (this.contains(sugg))
                results.add(sugg);
        }
        return results.toArray(new String[0]);
    }

    private void findWords(String s, ArrayList<String> suggestions, int strPos, int editDistance) {
        if (strPos == s.length()) {
            if (this.contains(s)) {
                if (!suggestions.contains(s))
                    suggestions.add(s);
            }
            return;
        }
        for (int i = 0; i < 26; i++)
            if (s.charAt(strPos) == 'a' + i)
                findWords(s, suggestions, strPos + 1, editDistance);
            else if (editDistance > 0)
                findWords(s.substring(0, strPos) + (char) ('a' + i) + s.substring(strPos + 1, s.length()),
                        suggestions, strPos + 1, editDistance - 1);
    }

    private class TrieNode {
        char letter;
        boolean isFullWord;
        TrieNode parent;
        TrieNode[] children;

        /**
         * @param letter The individual character
         * @param isWord true if all the parent characters make up a word
         */
        public TrieNode(char letter, boolean isWord) {
            this.letter = letter;
            this.children = new TrieNode[128]; //ascii range
            this.parent = null;
            this.isFullWord = isWord;
        }
    }

}
