package cn.edu.nju.software.sdd09;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LZWEncoder {
	FileOutputStream outstream;
	int index=0;
	byte[] outs=new byte[3];
	public LZWEncoder(FileOutputStream out){
		outstream=out;
	}
	public void write(short code) throws IOException{
		if(index==0){
			outs[0]=(byte)(code>>4);
			outs[1]=(byte)(code<<4);
			index=1;
		}else{
			outs[1]=(byte) ((byte)(code>>8)+outs[1]);
			outs[2]=(byte)code;
			outstream.write(outs);
			index=0;
		}
	}
	public void writelast(short code) throws IOException{
		if(index==1){
			write(code);
		}else{
			byte[] codes=new byte[2];
			codes[0]=(byte)(code>>4);
			codes[1]=(byte)(code<<4);
			outstream.write(codes);
		}
	}
}
