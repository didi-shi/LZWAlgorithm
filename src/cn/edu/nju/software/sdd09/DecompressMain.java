package cn.edu.nju.software.sdd09;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DecompressMain {
	public static ArrayList<String> dictionary=new ArrayList<String>();
	public static void main(String args[]) throws IOException{
		DecompressMain main=new DecompressMain();
		main.Decompress(new File("output stream"));
	}
	public void initDictionary(){
		dictionary.clear();
		int i=0;
		for(i=0;i<256;i++){
			dictionary.add(""+(char)i);
		}
	}
	public void Decompress(File in) throws IOException{
		initDictionary();
		FileOutputStream out=new FileOutputStream(new File("I have a dream(Decoded).txt"));
		LZWDecoder decoder=new LZWDecoder(in);
		String entry="";
		String s="";
		while(true){
			int k=decoder.readcode();
			if(k==5000){
				break;
			}else{
				//exception handler
				if(k>=dictionary.size()){
					entry=s+s.charAt(0);
				}else{
					entry=dictionary.get(k);
				}
				//write entry into file
				int size=entry.length();
				byte[] bytes=new byte[size];
				for(int i=0;i<size;i++){
					bytes[i]=(byte) entry.charAt(i);
				}
				out.write(bytes);
				if(s!=""){
					dictionary.add(s+entry.charAt(0));
				}
				s=entry;
			}
		}
		int k=decoder.getlastcode();
		String last=dictionary.get(k);
		int size=last.length();
		byte[] b=new byte[size];
		for(int i=0;i<size;i++){
			b[i]=(byte) last.charAt(i);
		}
		out.write(b);

	}
}
