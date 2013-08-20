package com.quizmania.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Hint implements Serializable{
	
	HashMap<Integer,Character> lettersRevealed;
	public Hint(){
		lettersRevealed = new HashMap<Integer,Character>();
	}
	public Map<Integer, Character> getLettersRevealed() {
		return lettersRevealed;
	}
	public void setLettersRevealed(HashMap<Integer, Character> lettersRevealed) {
		this.lettersRevealed = lettersRevealed;
	}
	public Hint(int letterIndex,char character){
		this();
		lettersRevealed.put(letterIndex, character);		
	}
	
	public char getLetterInIndex(int index){
		if(lettersRevealed.containsKey(index))
			return lettersRevealed.get(index);
		
		return ' ';
	}
	public boolean hasLetterRevealed(int index){
		return lettersRevealed.containsKey(index);
	}

}
