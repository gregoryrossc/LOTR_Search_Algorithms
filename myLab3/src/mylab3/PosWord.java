/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;

/**
  * I, Gregory Carroll, 000101968 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * @author Gregory Carroll
 */
public class PosWord {

    private int wordPos = 0; //position
    private String word; //the word (ring or a character)
    private String keyWord; //key
    
    public PosWord(int wordPos, String word, String keyWord) {
        this.wordPos = wordPos;
        this.word = word;
        this.keyWord = keyWord;
    }
    
   public int getWordPos(){
       return this.wordPos;
   }
   
   public String getWord(){
       return this.word;
   }
   
   public String getKeyWord(){
       return this.keyWord;
   }
    
    /*
   simple hash algorithm with added int word position 
   to reduce chance of collisions
   */
    @Override
    public final int hashCode() {

        int hash = 7;

        for (int i = 0; i < this.word.length(); i++) {
            hash = hash * 31 + this.word.charAt(i);
        }
        
        return hash + wordPos;
    }

    @Override
    public String toString() {

        String output = "Word: " + word + " Position: " + wordPos;

        return output;
    }

}
