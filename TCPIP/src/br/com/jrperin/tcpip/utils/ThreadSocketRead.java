package br.com.jrperin.tcpip.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

//import br.com.easyiso.gui.actions.EasyIsoActions;
import br.com.jrperin.commoninterfaces.Observable;
import br.com.jrperin.commoninterfaces.Observer;
import br.com.jrperin.uteis.Converter;



public class ThreadSocketRead extends Thread implements Observable{
	private Socket socket = null;
	private List<Observer> observadores = new ArrayList<Observer>();
	private boolean stop = false;
	//private BufferedReader readInput = null; 
	//private DataInputStream readInput = null;
	private InputStream readInput = null;
	private String headerType = "A";
	private int headerLen = 4;

	
	public ThreadSocketRead(Socket socket,  int headerLen, String headerType){
		this.socket = socket;
		this.headerType = headerType;
		this.headerLen = headerLen;

		try {
			readInput = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setStoped(boolean stoped){
		this.stop = stoped;
	}

	public void run(){
		int dataLen = -1;
		String lenRecebido = "";

		while(true){
			if (this.stop){
				break;
			}

			if (socket.isInputShutdown()){
				System.out.println("Socket isInputShutdown state: " + socket.isInputShutdown());
				break;

			}

			if (!socket.isBound()){
				System.out.println("Socket isBound state: " + socket.isBound());
				break;
			}

			if (socket.isClosed()){
				System.out.println("Socket isClosed state: " + socket.isClosed());
				break;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			int y = 0;
			try {
				y = readInput.available();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}



			if (y > 0){
				System.out.println("readInput.available = " + y);
				int b1 = 0x00;
				char c1 = 0x00;
				lenRecebido = "";
				for (int x = 0; x < headerLen; x++){
					try {
						b1 =  readInput.read();
						c1 = (char)b1;

						System.out.print("" + c1 +";");

						lenRecebido = lenRecebido + (c1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Erro no readInput do buffer da mensagem ISO.",
								"Erro Receber Mensagem",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				dataLen = 0;

				/*           ACHA O TAMANHO DA MENSAGEM RECEBIDA            */
				if (headerType.equalsIgnoreCase("E")){
					//lenRecebido = String.valueOf(Converter.convertEbcdic2Ascii(lenRecebido.toCharArray()));
				}

				try{
					dataLen = Integer.parseInt(lenRecebido);
				}catch(Exception ne){
					JOptionPane.showMessageDialog(null,
							"Tamanho da Mensagem Recebido Inv�lido: 0x" + String.copyValueOf(Converter.expandHexa(lenRecebido.toCharArray())),
							"Erro Receber Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}

				System.out.println("dataLen = " + dataLen);				
				
				/*           LE O RESTANTE DA MENSAGEM           */
				List<Character> charBuffer =  new Vector<Character>();
				try {
					int i = 0;
					while( i < dataLen){
						b1 =  readInput.read();
						c1 = (char) b1;
						System.out.print("" + c1 +";");

						charBuffer.add((c1));
						i++;
					}

					notifyObservers(charBuffer);
					Iterator it = charBuffer.iterator();
					i = 0;
					System.out.println("IN - charBuffer:\n");
					while(it.hasNext()){
						System.out.print(it.next());
					}
					System.out.println();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao ler o Buffer da Mensagem ISO de entrada.",
							"Erro ao Receber Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					e.printStackTrace();
					//notifyObservers("Close");
					//return;
				}
			}

		}

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
}
