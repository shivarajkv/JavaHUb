package com.shivaraj.training.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ByteArrayInputAndOutputStream {
	
	public static void main(String[] args) {
		byte[] byteArray ={5,4,3,2,1};
		
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		System.out.println("Size of buffer before write-->"+arrayOutputStream.size());
		while(arrayInputStream.available()>0){
			int singleItem = arrayInputStream.read();
			System.out.print(singleItem+" ");
			arrayOutputStream.write(singleItem);
		}
		System.out.println();
		System.out.println("Size after write-->"+arrayOutputStream.size());
		
		
		
	}

}
