package com.shivaraj.training.io;

import java.io.Console;

public class ConsoleExample {
	
	public static void main(String[] args) {
		Console console = System.console();
		
		char[] password = console.readPassword();
		System.out.println("Entered password is ");
		for (char c : password) {
			System.out.print(c);
		}
	}

}
