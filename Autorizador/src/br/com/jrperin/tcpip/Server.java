package br.com.jrperin.tcpip;
import java.net.*;
import java.io.*;

import br.com.jrperin.uteis.Converter;

public class Server
{
	public Server()
	{
		try
		{
			//creating server socket binding at port # 3000
			ServerSocket server=new ServerSocket(3000);
			System.out.println("Server binded at "+((server.getInetAddress()).getLocalHost()).getHostAddress()+":3000");
			System.out.println("[SERVIDOR] Run the Client");
			//ready to accept client request
			Socket socket = server.accept();
			
			//opening the input stream to read data from client connection
			//InputStream in = socket.getInputStream();
			//DataInputStream dis = new DataInputStream(in);
			
			/*
			int len = dis.readInt();
			byte[] data = new byte[len];
			if (len > 0){
				dis.readFully(data, 0, len);
				System.out.print("Dados recebidos:[");
				for (byte b : data){
					System.out.print(b);
				}
				System.out.print("]\n");
			}
			*/
			
			//BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//System.out.println(in.readLine());
			
			//using output stream responsing data 
			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);
			
			
			char[] buffer =
					Converter.convertHexaToCharArray("F0F1F0F0FEE7440188E1E00A0000000000000100F1d5F5F3F9F0F5F9F8F6F0F3F4F4F5F5F0F3F0F0F0F0F0F0F0F0F0F0F0F0F0F0F1F4F5F7F0F0F0F0F0F0F0F0F0F8F1F3F0F0F0F0F0F0F0F0F1F4F5F7F1F2F0F7F1F3F1F5F0F0F5F5F5F7F8F4F8F9F6F1F0F0F0F0F0F0F3F8F0F3F9F5F1F3F0F6F1F2F0F7F1F2F0F7F4F8F1F4F8F1F2F0F6F0F1F1F4F2F9F0F6F0F0F1F2F9F7F0F2F2F0F2F4F4F8F0F4F3F8F0F0F0F0F0F0F0F1F0F0F0F9F8F0F0F2F0F3F0F5F9F9F4D7C1E8D7C1D3405C32D2E8D7C540404040404040404040F3F5F3F1F4F3F6F9F0F0F1404040D3E4E7F0F2F7E3F8F2F0F2F5F2F4F2F0F7F0F1F0F3F2F1F0F6F1F0F5F0F0F0F0F0F9F8F6F8F4F0F9F8F6F0F2F6F0F0F2F5F1F0F0F0F0F6F6F0F0F4F4F2D3F1F5F2F04040404040F0F0F9D4C3E2F8E8C2E4C2D1F0F3F3F0F1F2F9F3F1F1F1F0F0F5F040D9A48140D18183A48940F6F640829340F4408197");
			//byte[] bufferB = new byte[buffer.length];
			
			//int x = 0;
			//for (char c : buffer){
			//	bufferB[x++] = (byte) c;
			//}
			
			dos.writeInt(buffer.length);
			for (char x : buffer){
				dos.writeByte((byte) x);
				System.out.print("[" + (byte) x + "]");
			}
			
			
			//PrintStream out=new PrintStream(socket.getOutputStream());
			System.out.print("Buffer enviado");
			//out.flush();
			//closing the in & out streams
			out.close();
			//in.close();
		}
		catch(Exception err)
		{
			System.err.println("* err" + err);
		}
			
	}
	
	public static void main(String a[])
	{
		new Server();
	}
	
}