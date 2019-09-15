package br.com.jrperin.tcpip;
import java.net.*;
import java.io.*;

import br.com.jrperin.ISO.mastercard.MsgMastercard;

public class Client implements Runnable{

	int   firstByte    = 0;
	char unsignedByte = 0;

	Socket client = null;
	InputStream leitorSocket = null;
	InputStreamReader leitorCaracteres = null;
	DataInputStream   leitorInteger = null;
	
	public Client(){

		try{
			//creating the socket to connect to server running on same machine binded on port no 3000
			client = new Socket("localhost",3000);
			System.out.println("[CLIENT] Client connected ");
			
			leitorSocket = client.getInputStream();
			leitorCaracteres = new InputStreamReader(leitorSocket);
			leitorInteger    = new DataInputStream(leitorSocket);
			
			System.out.println("[CLIENT] fim do construtor");
		
		}catch(Exception err){
			System.err.println("* err" + err);
		}

	}

	public void run() {
		int len =0;
		
		System.out.println("Entrei no run");
		
		try{
			System.out.println("Entrei no try");
			
			while (true){
				//System.out.println("Entrei no while(true)");
				len = 0;
				
				if ((len = leitorInteger.readInt()) != 0){
			
					//int len = leitorInteger.readInt();
					System.out.println("Tamanho do dado lido: "+ len);
	
					if (len < 1){ 
						throw new RuntimeException("Buffer de entrada vazio [" + this.getClass() + "]");
					}
	
					char[] inBuffer = new char[len];
			
					for (int i = 0; i < len; i++){   // <--- faz a leitura do buffer
						
						inBuffer[i] = (char) leitorSocket.read();
					}
	/*
					char[] buffer = new char[len];
	
	
					int i = 0;
					for (char x : inBuffer){
						firstByte = (0x000000FF & ((int)x));
						unsignedByte = (char)firstByte;
						buffer[i++] = unsignedByte;
						//System.out.println(""+ i + "[" + unsignedByte +"]");
					}
	*/		
	
					System.out.print("["+ this.getClass()+"] ----------------- INICIO TESTE TCP ---------------");
					MsgMastercard mcIn = new MsgMastercard(inBuffer);
					System.out.print(mcIn);
					System.out.print("["+ this.getClass()+"] ------------------- FIM TESTE TCP ----------------");
			
					System.out.print("\n");
				}
			}
		
		
		}catch (Exception err){
			System.err.println("* err" + err);
			
		}
	

	}
}
