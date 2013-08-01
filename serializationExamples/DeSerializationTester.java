package com.shivaraj.xstreamExample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeSerializationTester {

	public static void main(String[] args) {
		Citizen citizen=null;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("D:\\serializaedObject.bin");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			citizen = (Citizen)objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(citizen.age);
		System.out.println(citizen.computer.computerName);
		System.out.println(citizen.mobile.number);
		
	}

}
