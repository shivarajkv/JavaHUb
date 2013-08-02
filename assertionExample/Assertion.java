package com.shivaraj.weeklyAssignment.assertion;

public class Assertion {
	
	
	public static void assertionDemo(int check){
		//checks for the number here... if in valid then asserion error will be thrown
		assert (check >= 0) : "An invalid value";
		System.out.println("An valid value ");
	}
	public static void main(String[] args) {
		
		assertionDemo(-1);
	}

}
