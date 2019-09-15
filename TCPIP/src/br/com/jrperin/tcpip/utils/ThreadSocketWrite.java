package br.com.jrperin.tcpip.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import br.com.jrperin.commoninterfaces.Command;
import br.com.jrperin.commoninterfaces.Observable;
import br.com.jrperin.commoninterfaces.Observer;
import br.com.jrperin.uteis.Converter;



	
	
public class ThreadSocketWrite implements Observable, Command{
	private List<Observer> observadores = new ArrayList<Observer>();
	private OutputStream writeOutput = null;
	//private DataOutputStream writeOutput = null; <- estava usando esse e nao funcionou
	private int headerLen = 4;
	private String headerType = "E"; //E = EBCDIC / A = ASCII
	
	
	public ThreadSocketWrite(Socket socket, int headerLen, String headerType){
		this.headerLen = headerLen;
		if (headerType.equalsIgnoreCase("E") | headerType.equalsIgnoreCase("A")){
			this.headerType = headerType;
		}else{
			System.out.println("Variavel de inicializa��o da classe ThreadSocketWrite difere de EBCDIC e ASCII.");
			
		}
		try {
			writeOutput = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public void sendData(List data, Integer qtdOfMsgLen, String type) throws IOException{
			execute(data, qtdOfMsgLen, type);

	}

	@Override
	public void addObserver(Observer or) {
		observadores.add(or);
	}

	@Override
	public void removeObserver(Observer or) {
		observadores.remove(or);
	}

	@Override
	public void notifyObservers(Object ... arg) {
		Iterator<Observer> it = observadores.iterator();
		while(it.hasNext()){
			it.next().update(arg);
		}
		
	}

	@Override
	public void execute(Object... arg) {
		
		/*
		 * arg[0] = data to Send
		 */
		
		List data = (List) arg[0];
		

		// Montar o tamanho da mensagem de sa�da antes dos dados
		
		char[] lenAux = new char[headerLen];
		
		String len = Converter.fillZerosOnTheLeft(data.size(), headerLen);
		if (headerType.equalsIgnoreCase("E")){
			//lenAux = Converter.convertAscii2Ebcdic(len.toCharArray());
			lenAux = len.toCharArray();
		}else{
			lenAux = len.toCharArray();
		}
			
		System.out.println("lenAux = " + headerLen);


		byte[] buffer = new byte[data.size() + headerLen];

		
		int i = 0;

		while (i < headerLen){
			buffer[i] = (byte) lenAux[i++];
		}
		
		// Mover a �rea de dados
		
		Iterator<Character> it = data.iterator();
		System.out.println("\n-------- ThreadSocketWrite ------------");
		System.out.println("\nMovendo data 2 buffer (vari�vel b):");
		while(it.hasNext()){
			
			int b = it.next();
			buffer[i++] = (byte) b;

		}
			
		try {
			System.out.println("\nData Area Recebida:");
			it = data.iterator();
			while(it.hasNext()){
				System.out.print("" + it.next() + ";");
			}
		
			System.out.println("\nBuffer de sa�da:");
			for(int i1 = 1; i1 < buffer.length; i1++){
				System.out.print("" + buffer[i1] + ";");
			}
			System.out.println("");
			
			writeOutput.write(buffer);
			writeOutput.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		notifyObservers(data);
		
	}
}
