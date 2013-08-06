package com.shivaraj.training.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

	public static boolean  isComment =false;
	public static boolean isCommentLastLine = false;
	public static boolean isSingleLineComment =false;


	public static boolean isStringLiteral(String content){
		Pattern pattern = Pattern.compile("[\"].*[\"]");
		Matcher matcher = pattern.matcher(content);
		if(matcher.find()){
			return true;
		}
		return false;
	}

	public static boolean  findSingleLineComment(String content){
		Pattern pattern = Pattern.compile("/[\\s]{1,}/.*");
		Matcher matcher = pattern.matcher(content);
		if(matcher.find()){
			isSingleLineComment=true;
			return true;
		}
		return false;
	}
	public static void findCommentLine(String content){

		Pattern pattern = Pattern.compile("/\\*");
		Matcher matcher = pattern.matcher(content);
		Pattern commentEndPattern = Pattern.compile("\\*/");
		Matcher commentEndMatcher = commentEndPattern.matcher(content);
		if(matcher.find()){
			isComment = true;
		}else if(commentEndMatcher.find()){
			isComment = false;
			isCommentLastLine=true;
		}
	}

	public static String putSpaceBetweenSymbols(String content){
		String[] symbolTableOperatorArray = {"=","\\+","-","/","%","\\*","\\++","--","!","==","!=",">",">=","<","<=","\\&&","\\|\\|","\\?:","~","<<",">>",">>>","\\&","\\^","\\|"};
		String[] symbolTableSymbolArray = {"\\(","\\)","\\[","\\]","\\{","\\}",";",":","\\?"};
		for (String string : symbolTableOperatorArray) {
			content = content.replaceAll(string, " "+string+" ");
		}
		for (String string : symbolTableSymbolArray) {
			content = content.replaceAll(string, " "+string+" ");
		}
		return content;
	}

	public static String identifyingTheMethods(String content){
		Pattern pattern = Pattern.compile("[\\w%_]+[\\s]*[(][\\s]*.*[)]");
		Matcher matcher = pattern.matcher(content);
		if(matcher.find()){
			String methodContent = matcher.group();
			String[] splittedMethodContent = methodContent.split("\\(");
			String[] exactMethodName = splittedMethodContent[0].split("[\\s]+");
			String methodName = exactMethodName[exactMethodName.length-1];

			if(!methodName.equalsIgnoreCase("while")&&!methodName.equalsIgnoreCase("if")&&!methodName.equalsIgnoreCase("for")){
				String a = String.valueOf(methodName.charAt(0));
				if(a.matches("[A-Za-z$_]"))
					return methodName;

			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		String lineParsed="";
		int lineCount =0;
		String[] symbolTableKeywordArray = {"for","abstract","new","switch","assert","package","synchronized","boolean","break","byte","case","catch","char","class","native","continue","default","do","if","double","else","enum","extends","final","finally","float","private","implements","import","instanceof","int","short","interface","long","super","this","protected","public","return","try","static","strictfp","while","throw","throws","transient","void","volatile"};
		String[] symbolTableOperatorArray = {"=","+","-","/","%","*","++","--","!","==","!=",">",">=","<","<=","&&","||","?:","~","<<",">>",">>>","&","^","|"};
		List<String> symbolTableKeywordList = Arrays.asList(symbolTableKeywordArray);
		List<String> symbolTableOperatorList = Arrays.asList(symbolTableOperatorArray);
		Pattern pattern = Pattern.compile("[\\w]+[\\s]{1,}([\\w]+)[\\s]{1,}[=]*[\\s]*[\\w]*[\\s]*[;]");

		BufferedReader br = new BufferedReader(new FileReader("D:\\XstreamTester.java"));
		String nextLine= br.readLine();
		while(nextLine!=null){
			nextLine=nextLine.trim();
			lineCount++;
			lineParsed = lineParsed+"\n"+"line "+lineCount+"\n";
			if(nextLine.length()>0){

				findCommentLine(nextLine);
				if(isComment||isCommentLastLine){
					lineParsed= lineParsed+"\t comment line :"+nextLine;
					isCommentLastLine=false;
				}else {
					nextLine=putSpaceBetweenSymbols(nextLine);
					if(isStringLiteral(nextLine)){
						String stringLiteral = nextLine.substring(nextLine.indexOf("\"")+1, nextLine.lastIndexOf("\""));
						lineParsed= lineParsed+"\t String literal:"+ stringLiteral;
						nextLine=nextLine.replaceAll("[\"].*[\"]", "");
					}

					if(findSingleLineComment(nextLine)){
						String commentContent="";
						Pattern patternForCommentLine = Pattern.compile("/[\\s]{1,}/(.*)");
						Matcher matcherForCommentLine = patternForCommentLine.matcher(nextLine);
						if(matcherForCommentLine.find())
							commentContent = matcherForCommentLine.group(1);
						lineParsed= lineParsed+"\t Single Line comment:"+ commentContent ;
						nextLine=nextLine.replaceAll(commentContent, "");
					}
					Matcher matcher = pattern.matcher(nextLine);
					if(matcher.find()){
						lineParsed= lineParsed+"\t identifier:"+ matcher.group(1);
						nextLine=nextLine.replaceAll(matcher.group(1), "");
					}
					String[] lineArray = nextLine.split("[\\s]+");
					for (String string : lineArray) {
						string=string.trim();
						if(symbolTableKeywordList.contains(string)){
							lineParsed= lineParsed+"\t keyword:"+ string;
							nextLine=nextLine.replaceFirst(string, "");
						}else if(symbolTableOperatorList.contains(string)){
							lineParsed= lineParsed+"\t operator:"+ string;
							nextLine=nextLine.replaceAll(string, "");
						}else if(string.length()>0){
							if(string.matches("-?\\d+(\\.\\d+)?")){
								lineParsed=lineParsed+"\tnumeric: "+string;
							}else{
								lineParsed=lineParsed+"\tsymbol: "+string;
							}
						}
					}


				}
			}else if(nextLine.length()==0){
				lineParsed = lineParsed+"\t***blank line***";
			}
			nextLine=br.readLine();
		}
		System.out.println(lineParsed);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("D:\\token.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(lineParsed);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
