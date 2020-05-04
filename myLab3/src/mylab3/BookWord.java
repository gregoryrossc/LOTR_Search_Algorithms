
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

import java.util.Objects;

/**
 * * I, Gregory Carroll, 000101968 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * @author Gregory Carroll
 */
    public class BookWord {

        private final String text;
        private int count = 1; //when book is instantiated it starts with 1 count of the word

        public BookWord(String text){
            this.text = text;
        }

        public String getText(){
            return text;
        }

        public int getCount(){
            return count;
        }

        public void incrementCount(){
            count+=1;
        }
        @Override
        public boolean equals(Object wordToCompare){
            if(this == wordToCompare){
                return true;
            }
            if(wordToCompare == null){
                return false;
            }

            if (wordToCompare instanceof BookWord) {
                BookWord test = (BookWord) wordToCompare;
                return (test.text.equals(this.text));
                
                
            }
            else if(wordToCompare instanceof String){
                
                return this.text.equals((String) wordToCompare); 
                
            }

            return false;
        }
        
        //https://www.edureka.co/community/10676/good-hash-function-for-strings
        @Override
        public final int hashCode(){

            int hash = 7;

            for (int i = 0; i < this.text.length(); i++) {
                hash = hash*31 + this.text.charAt(i);
            }
            return hash;
        }

        @Override
        public String toString(){

            String output = "Word: " + text + " Count: " + count;

            return output;
        }


    }


