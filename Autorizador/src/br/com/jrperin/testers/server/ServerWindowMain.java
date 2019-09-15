package br.com.jrperin.testers.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import br.com.jrperin.uteis.Converter;
import br.com.jrperin.uteis.FormatadorCECI;


public class ServerWindowMain extends JPanel implements ActionListener{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5853123589553350986L;
	private final String LB_CONNECTED_LABEL = "Connected";
	private final String LB_DISCONNECTED_LABEL = "Disconnected";
	private final String LB_CONNECTING_LABEL = "Aguardando conexao...";
	
	private final String BT_START_LABEL = "Start";
	private final String BT_STOP_LABEL  = "Stop";
	
	private final String BT_SEND_LABEL  = "      Send      ";
	
	private final String BT_EXIT_LABEL  = "      Exit      "; 
	
	private JLabel lbIpPort    = new JLabel(" Port Number:");
	private JLabel lbStatus    = new JLabel("Disconnected");
	private JLabel lbAreaOut   = new JLabel("Data to Send:");
	private JLabel lbAreaIn    = new JLabel("Data Received:");
	
	private JTextArea tIpPort  = new JTextArea("3000");
	private JTextArea tAreaOut = new JTextArea("F0F1F0F0FEE7440188E1E00A0000000000000100F1F6F5F3F9F0F5F9F8F6F0F3F4F4F5F5F0F3F0F0F0F0F0F0F0F0F0F0F0F0F0F0F1F4F5F7F0F0F0F0F0F0F0F0F0F8F1F3F0F0F0F0F0F0F0F0F1F4F5F7F1F2F0F7F1F3F1F5F0F0F5F5F5F7F8F4F8F9F6F1F0F0F0F0F0F0F3F8F0F3F9F5F1F3F0F6F1F2F0F7F1F2F0F7F4F8F1F4F8F1F2F0F6F0F1F1F4F2F9F0F6F0F0F1F2F9F7F0F2F2F0F2F4F4F8F0F4F3F8F0F0F0F0F0F0F0F1F0F0F0F9F8F0F0F2F0F3F0F5F9F9F4D7C1E8D7C1D3405CE2D2E8D7C540404040404040404040F3F5F3F1F4F3F6F9F0F0F1404040D3E4E7F0F2F7E3F8F2F0F2F5F2F4F2F0F7F0F1F0F3F2F1F0F6F1F0F5F0F0F0F0F0F9F8F6F8F4F0F9F8F6F0F2F6F0F0F2F5F1F0F0F0F0F6F6F0F0F4F4F2D3F1F5F2F04040404040F0F0F9D4C3E2F8E8C2E4C2D1F0F3F3F0F1F2F9F3F1F1F1F0F0F5F040D9A48140D18183A48940F6F640829340F4408197");
	private JTextArea tAreaIn  = new JTextArea("");
	
	private JScrollPane scAreaOut;
	private JScrollPane scAreaIn;
	
	private JButton btStart;
	private JButton btSend;
	
	private JButton btExit;
	
	private JPanel pTop; private JPanel pTopLeft; private JPanel pTopRight;
	private JPanel pMiddle;
	private JPanel pDataOut;
	private JPanel pDataIn;
	private JPanel pBotton;
	
	ServerSocket     server;
	Socket           socket;
	OutputStream     out   ;
	DataOutputStream dos   ;
	
	WaitConnection   waitC;
	
	
	
	
	//Constructor ----------------------------------------------------------
	
	public ServerWindowMain() throws Exception{
		
		//Setting the default font
		Font fnt = new Font(Font.MONOSPACED, 12, 14);
		
		this.setLayout(new BorderLayout(10,10));
		
		
		//-------------- Adiciona o painel superior referente ao TCP/IP ------------------------
		pTop = new JPanel();
		pTop.setLayout(new GridLayout(1,0,20,20));


		tIpPort = new JTextArea("3000", 1, 10); 
        tIpPort.setLineWrap(false);
        tIpPort.setWrapStyleWord(true);
        tIpPort.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		tIpPort.setFont(fnt);
		
		btStart = new JButton(BT_START_LABEL);
        btStart.setMnemonic(KeyEvent.VK_S);
        btStart.addActionListener(this);
        Color cl = Color.red;
        lbStatus.setForeground(cl);
		
        pTop.add(lbIpPort);
		pTop.add(tIpPort);
		pTop.add(btStart);
		pTop.add(lbStatus);
		
		
		//--------------- ADICIONA O PAINEL CENTRAL REFERENTE AOS DADOS ------------------
		pDataOut = new JPanel();
		pDataOut.setLayout(new BorderLayout(10,10));
		
		tAreaOut.setLineWrap(true);
        tAreaOut.setWrapStyleWord(true);
        tAreaOut.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        scAreaOut = new JScrollPane(tAreaOut);
        scAreaOut.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
        pDataOut.add(lbAreaOut, BorderLayout.NORTH);
        pDataOut.add(scAreaOut, BorderLayout.CENTER);
        
        pDataIn = new JPanel();
        pDataIn.setLayout(new BorderLayout(10,10));
        
        tAreaIn.setLineWrap(true);
        tAreaIn.setWrapStyleWord(true);
        tAreaIn.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        scAreaIn = new JScrollPane(tAreaIn);
        scAreaIn.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        pDataIn.add(lbAreaIn, BorderLayout.NORTH);
        pDataIn.add(scAreaIn, BorderLayout.CENTER);
        
        pMiddle = new JPanel();
        pMiddle.setLayout(new GridLayout(0,1,10,10));
        pMiddle.add(pDataOut);
        pMiddle.add(pDataIn);
        
		//--------------- ADICIONA O PAINEL DO BOTAO SAIR (BOTTON) -----------------------
		pBotton = new JPanel();
		//pBotton.setLayout(new BorderLayout(10,10));
		pBotton.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 05));
		
		btSend = new JButton(BT_SEND_LABEL);
        btSend.setMnemonic(KeyEvent.VK_E);
        btSend.addActionListener(this);
		
		btExit = new JButton(BT_EXIT_LABEL);
        btExit.setMnemonic(KeyEvent.VK_X);
        btExit.addActionListener(this);
   
        pBotton.add(btSend);
        pBotton.add(btExit);
        
        		
		//------- ADICIONA NO PAINEL PRINCIPAL OS PAINEIS SECUNDARIOS --------------------
		this.add(pTop, BorderLayout.NORTH);
		this.add(pMiddle, BorderLayout.CENTER);
		this.add(pBotton, BorderLayout.SOUTH);
		
	}
	
	
	//--------------------- LISTENERS --------------------------------------
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(BT_EXIT_LABEL)){
			System.exit(1); 
		}
		
		if (e.getActionCommand().equals(BT_START_LABEL)){
			
			System.out.println("getText(): " + btStart.getText() + "\n");
			try{
				
				SwingUtilities.invokeLater(
						new Runnable() {
							public void run(){
								btStart.setText(BT_STOP_LABEL);
								lbStatus.setForeground(new Color(80, 150, 0));
								lbStatus.setText(LB_CONNECTING_LABEL);
								pTop.repaint();
							}
						}
					);

				
				int port = 0;
				port = Integer.parseInt(tIpPort.getText());
				server = new ServerSocket(port);
				
				System.out.println("Server binded at "+((server.getInetAddress()).getLocalHost()).getHostAddress()+":" + port);
				System.out.println("[SERVIDOR] Waiting the Client...");
				
				//ready to accept client request
				//socket =  server.accept();

				waitC = new WaitConnection();
				waitC.start();

				
			} catch (Exception ex){
				System.out.println(ex);
				ex.printStackTrace();
			}
			
		}
		
		if (e.getActionCommand().equals(BT_STOP_LABEL)){
			btStart.setText(BT_START_LABEL);
			lbStatus.setForeground(Color.RED);
			lbStatus.setText(LB_DISCONNECTED_LABEL);
			pTop.repaint();
			
			try{
				if (waitC.isAlive()){
					waitC = null; // <--- PARA INTERRONPER A THREAD
				}
			}catch (Exception e2){
				e2.printStackTrace();
			}
			
			try{
				if (socket != null && socket.isConnected()){
				socket.close();}
				if (server !=null && server.isBound()){
					server.close();
				}
			} catch (Exception ex){
				ex.printStackTrace();
			}
			System.out.println("[SERVER] Conexão encerrada... " + btStart.getText() + "\n");
			//System.exit(1); 
		}
		
		if (e.getActionCommand().equals(BT_SEND_LABEL)){
			
			try{
				//using output stream responsing data ;
		
				FormatadorCECI formatador = new FormatadorCECI(tAreaOut.getText());
				System.out.println(tAreaOut.getText());
				System.out.println(formatador.getAreaTcpIp());
				
				char[] buffer =
						Converter.convertHexaToCharArray(formatador.getAreaTcpIp());
				
				dos.writeInt(buffer.length);
				for (char x : buffer){
					dos.writeByte((byte) x);
					System.out.print("[" + (byte) x + "]");
				}
				
				dos.flush();
				
				System.out.print("Buffer enviado");
	
			}
			catch(Exception err)
			{
				System.err.println("* err" + err);
				err.printStackTrace();
			}
			
		}
		
	}
	
	class WaitConnection extends Thread{
		
		
		public void run(){
			try{
				socket = server.accept();
				//conectado = true;
				
				
				out = socket.getOutputStream();
				dos = new DataOutputStream(out);
				
				SwingUtilities.invokeLater(
						new Runnable() {
							public void run(){
								lbStatus.setForeground(new Color(80, 150, 0));
								lbStatus.setText(LB_CONNECTED_LABEL);
								pTop.repaint();
							}
						}
					);
				
				System.out.println("[SERVER] Passou do Server.accept()");
				
			}catch (Exception e){
				System.out.println("\nAlerta enquanto aguardava a conexao do cliente: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
}
