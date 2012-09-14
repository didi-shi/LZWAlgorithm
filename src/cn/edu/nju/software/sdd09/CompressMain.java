package cn.edu.nju.software.sdd09;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CompressMain {
	public static ArrayList<String> dictionary=new ArrayList<String>();
	public static void main(String args[]) throws IOException{
		CompressMain main=new CompressMain();
		FileReader input=new FileReader("I have a dream.txt");
		FileOutputStream out=new FileOutputStream("output stream");
		main.Compress(input,out);
		out.close();
		input.close();
	}
	public void initDictionary(){
		dictionary.clear();
		int i=0;
		for(i=0;i<256;i++){
			dictionary.add(""+(char)i);
		}
	}
	public void Compress(FileReader input,FileOutputStream out){
		initDictionary();
		LZWEncoder encoder=new LZWEncoder(out);
		try{
			BufferedReader bf=new BufferedReader(input);
			int firstsym=bf.read();
			String s=""+(char)firstsym;
			if(firstsym==-1){
				System.err.print("Empty file!");
			}else{
				while(true){
					int tmp=bf.read();
					if(tmp==-1){
						break;
					}else{
						String c=""+(char)tmp;
						if(exist(s+c)){
							s=s+c;
						}else{
							short code=(short) dictionary.indexOf(s);
							encoder.write(code);
							dictionary.add(s+c);
							s=c;
						}
					}
				}
				short lastcode=(short) dictionary.indexOf(s);
				encoder.writelast(lastcode);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean exist(String str){
		boolean ret=false;
		if(dictionary.contains(str)){
			ret=true;
		}
		return ret;
	}
	
	
}
