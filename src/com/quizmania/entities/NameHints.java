package com.quizmania.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.quizmania.utils.StaticGlobalVariables;

public class NameHints implements Serializable{
	
	HashMap<Integer,Character> lettersRevealed;
	public NameHints(){
		lettersRevealed = new HashMap<Integer,Character>();
	}
	public Map<Integer, Character> getLettersRevealed() {
		return lettersRevealed;
	}
	public void setLettersRevealed(HashMap<Integer, Character> lettersRevealed) {
		this.lettersRevealed = lettersRevealed;
	}
	public NameHints(int letterIndex,char character){
		this();
		lettersRevealed.put(letterIndex, character);		
	}
	
	public char getLetterInIndex(int index){
		if(lettersRevealed.containsKey(index))
			return lettersRevealed.get(index);
		
		return StaticGlobalVariables.BLANK_SPACE;
	}
	@Override
	public String toString() {
		return "NameHints [lettersRevealed=" + lettersRevealed + "]";
	}
	public boolean hasLetterRevealed(int index){
		return lettersRevealed.containsKey(index);
	}

}
