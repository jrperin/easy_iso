package br.com.jrperin.tcpserver;
import java.net.*;
import java.util.List;
import java.io.*;


import br.com.jrperin.commoninterfaces.Command;
import br.com.jrperin.commoninterfaces.Observer;
import br.com.jrperin.tcpserver.gui.TcpipServerGui;
import br.com.jrperin.tcpip.utils.ThreadServer;
import br.com.jrperin.tcpip.utils.ThreadSocketRead;
import br.com.jrperin.tcpip.utils.ThreadSocketWrite;

public class TcpipServer implements Command{
	private ServerSocket server = null;
	private Socket socket = null;
	private ThreadServer threadServer = null;
	private ThreadSocketRead  threadReadSocket = null;
	private ThreadSocketWrite threadWriteSocket = null;
	private Boolean echo = false;
	
	private TcpipServerGui tcpipGui = null;
	
	private int headerLen = 4;
	private String headerType = "E";
	
	private ObservaServerStatus observaServerStatus = new ObservaServerStatus();
	
 	public static void main (String ... arg){
		new TcpipServer();
	}
	
 	public TcpipServer(){
 		tcpipGui = new TcpipServerGui("Servidor TCP/IP - EasyISO Simulator", this);
 	}
 	
	private ServerSocket startServer(int porta, int len, String type, Observer Ob) throws IOException{
		
		this.headerLen = len;
		this.headerType = type;
		
		tcpipGui.setConectionStatus(1); //--> aguardando conexao
		
		//this.port = portNumber;
		/* creating server socket binding at port # 3000 */
		threadServer = new ThreadServer(porta);
		this.server = threadServer.getServerSocket(); 
		server.setReuseAddress(true);
		threadServer.addObserver(observaServerStatus);
		threadServer.start();
		return server;
	}

	private void startReaderSocket(Socket socket ){
		threadReadSocket = new ThreadSocketRead(socket, headerLen, headerType);
		threadReadSocket.addObserver(new ObservaReaderSocket());
		threadReadSocket.start();
	}

	private void startWriterSocket(Socket socket){
		threadWriteSocket = new ThreadSocketWrite(socket, headerLen, headerType);
		threadWriteSocket.addObserver(new ObservaWriterSocket());
	}
	
	private void echoData(List data){
		if(echo){
			threadWriteSocket.execute(data);
		}
	}
	
	private void closeAll(){
		if (socket != null){
			try {
				socket.close();
				socket = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (server != null){
			try {
				server.close();
				server = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		threadServer.setStoped(true);
		threadReadSocket.setStoped(true);
		threadServer = null;
		threadReadSocket = null;
		threadWriteSocket = null;
	}
	
	@Override
	public void execute(Object... arg) {
	
		if (arg[0] instanceof String){
			if (((String) arg[0]).equalsIgnoreCase("startServer")){
				try {
					//arg1 = porta / arg2 = tamanho header / arg3 = tipo (A-Ascii / E-Ebcdic)
					this.startServer((Integer) arg[1], (Integer) arg[2], (String) arg[3], observaServerStatus);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if (((String) arg[0]).equalsIgnoreCase("stopServer")){
				closeAll();
			}
			
			if(((String) arg[0]).equalsIgnoreCase("ECHO")){
				this.echo = (Boolean) arg[1];
			}
		}
		
	}
	
	class ObservaServerStatus implements Observer{

		@Override
		public void update(Object... arg) {
			if(arg[0] instanceof Integer){
				tcpipGui.setConectionStatus((Integer) arg[0]);
				if ((Integer) arg[0] == 2){
					if (socket == null){
						socket = threadServer.getSocket();
						startReaderSocket(socket);
						startWriterSocket(socket);
					}
				}

				//DEBUG
				//System.out.println(" tcp/ip server conexao =" + arg[0]);
			}


		}
		
	}
	

	class ObservaReaderSocket implements Observer{

		@Override
		public void update(Object... arg) {
			if (arg[0] instanceof List){
				tcpipGui.writeLog("Recebido:",arg[0]);
				echoData((List)arg[0]);
			}
			
			if (arg[0] instanceof String){
				if (((String) arg[0]).equalsIgnoreCase("Close")){
					closeAll();
				}
			}
		}
	}
	

	class ObservaWriterSocket implements Observer{

		@Override
		public void update(Object... arg) {
			if (arg[0] instanceof List){
				tcpipGui.writeLog("Enviado.:",arg[0]);
			}
			
		}
		
	}
}