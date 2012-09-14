package cn.edu.nju.software.sdd09;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class LZWDecoder {
	FileInputStream bf;
	byte[] buff=new byte[3];
	byte[] tmpbuf=new byte[1];
	int index=0;
	int index2=0;
	public LZWDecoder(File in) throws FileNotFoundException{
		bf=new FileInputStream(in);
	}
	public short readcode() throws IOException{
		short ret=0;
		if(index==0){
			//read three bytes and return first short
			int num=0;
			while(num<3){
				int k=bf.read(tmpbuf);
				if(k==-1){
					break;
				}else{
					buff[num]=tmpbuf[0];
					num++;
				}
			}
			if(num==3){
				//read three bytes successfully
				short b=buff[0];
				short c=(short) (((short) (b<<4)) & 0x0fff);
				ret=(short) (c+(short)((byte)(buff[1]>>4)&0x0f));
			}else{
				//only two bytes being read
				return 5000;
			}
			index=1;
		}else{
			//return second short
			index=0;
			short a=buff[1];
			short b=(short) ((short) (a<<8)& 0x0f00);
			short c=buff[2];
			short d=(short) (c & 0x00ff);
			ret=(short) (b+d);
		}
		return ret;
	}
	public short getlastcode(){
		short ret=0;
		short a=buff[0];
		short b=(short) ((short) (a<<4)& 0x0ff0);
		short c=buff[1];
		short d=(short) ((short) (c>>4) & 0x000f);
		ret=(short) (b+d);
		return ret;
	}
}
