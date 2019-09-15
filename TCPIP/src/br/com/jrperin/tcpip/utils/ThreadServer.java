package br.com.jrperin.tcpip.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import br.com.jrperin.commoninterfaces.Observer;


public class ThreadServer extends Thread implements br.com.jrperin.commoninterfaces.Observable{

	private ArrayList<Observer> observadores = new ArrayList<Observer>();
	private int status = 0;
	private Socket       socket = null;
	private ServerSocket server = null;
	private Boolean stopThread = false;
	public ThreadServer(int port) throws IOException {
		//super(port);	
		server = new ServerSocket(port);
	}

	public ServerSocket getServerSocket(){
		return this.server;
	}
	
	public Socket getSocket(){
		return this.socket;
	}

	public void setStoped(Boolean stop){
		this.stopThread = stop;
	}
	
	@Override
	public void run() {
		
		this.status = 1; //--> aguardando conexao
		
		try {
			this.socket = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true){

			if (stopThread){
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (server.isClosed()){
				this.status = 0;
			}else{
				this.status = 2;
			}
			
			
				
			notifyObservers();
			//DEBUG
			/*
			System.out.println("ThreadServer.java --------- " +
					           "\n server.isClosed....: " + server.isClosed() +
					           "\n server.isBound.....: " + server.isBound()  +
					           "\n ...................."  +
					           "\n socket.isClosed....: " + socket.isClosed() +
					           "\n socket.isBound.....: " + socket.isBound() +
					           "\n socket.isConnected.: " + socket.isConnected() );
			*/
		}
		
	}


	@Override
	public void addObserver(Observer or) {
		this.observadores.add(or);
		
	}

	@Override
	public void removeObserver(Observer or) {
		this.observadores.remove(or);
		
	}

	@Override
	public void notifyObservers(Object ... arg) {
		
		Iterator<Observer> itOb = observadores.iterator();
		while(itOb.hasNext()){
			itOb.next().update(this.status);
		}
		
	}

}
