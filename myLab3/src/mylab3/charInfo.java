/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylab3;

/**
 * * I, Gregory Carroll, 000101968 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * @author Gregory Carroll
 */
public class charInfo implements Comparable<charInfo>{

    private String name; //char name
    private int count; //char count
    private int closeToRing; //closeness
    private double closenessFactor; //closeness factor

    public charInfo(String name, int count, int closeToRing, double closenessFactor) {

        this.name = name;
        this.count = count;
        this.closeToRing = closeToRing;
        this.closenessFactor = closenessFactor;

    }

    public String getName() {
        return name;

    }

    public int getCount() {
        return count;
    }

    public int getCloseToRing() {
        return closeToRing;
    }

    public double getClosenessFactor() {
        return closenessFactor;
    }

    /*
    overriding compareTo to sort list in desc order by closeness factor
    */
    @Override
    public int compareTo(charInfo o) {
        
        if( this.closenessFactor == o.getClosenessFactor()){
            return 0;
        }
        if( this.closenessFactor < o.getClosenessFactor()){
            return 1;
        }
        if( this.closenessFactor > o.getClosenessFactor()){
            return -1;
        }

        return 0;
    }

    @Override
    public String toString() {

        return String.format("Name: [%s, %d] Close to Ring %d Closeness Factor: %.4f", name,count, closeToRing,closenessFactor);

    }

}
