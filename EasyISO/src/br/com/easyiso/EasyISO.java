package br.com.easyiso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import br.com.easyiso.gui.EasyISOGui;
import br.com.easyiso.gui.MsgMastercardGui;
import br.com.easyiso.gui.NovoHexaGui;
import br.com.easyiso.gui.ShowErrorGui;
import br.com.easyiso.gui.actions.EasyIsoActions;
import br.com.jrperin.commoninterfaces.Command;
import br.com.jrperin.commoninterfaces.Observer;
import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters.Codepage;
import br.com.jrperin.tcpip.utils.ThreadSocketRead;
import br.com.jrperin.tcpip.utils.ThreadSocketWrite;


public class EasyISO implements Command{

	private EasyISOGui easyIsoGui = new EasyISOGui(this);
	private File currentDirectory = new File("~");
	
	private Socket socket = null; 
	private ThreadSocketRead  socketReader   = null;
	private ThreadSocketWrite socketWriter  = null;
	private SocketMonitor socketMonitor = null; 
	
	//private String iniFile = ".//EasyISO.ini";
	
	public static void main (String ... arg){
		
		PersistParam.setPersistFileName(new File("./EasyISO.ini").getAbsoluteFile());
		EasyISO eI = new EasyISO();
	}
		
	@Override
	public void execute(Object... arg) {

		int resp = 0;
		
		switch ((EasyIsoActions) arg[0]) {
		
			case NEW:
				try {
					easyIsoGui.newFile();
				} catch (IsoFormatErrorException e) {
					new ShowErrorGui(e.getErrorMessage());
				} catch (NumberFormatException e) {
					new ShowErrorGui(e.getMessage());
				} catch (JDOMException e) {
					new ShowErrorGui(e.getMessage());
				} catch (IOException e) {
					new ShowErrorGui(e.getMessage());
				} catch (ClassNotFoundException e) {
					new ShowErrorGui(e.getMessage());
				} catch (InstantiationException e) {
					new ShowErrorGui(e.getMessage());
				} catch (IllegalAccessException e) {
					new ShowErrorGui(e.getMessage());
				}
				break;
				
			case NEWHEXA:
				//DEBUG
				/*
				JOptionPane.showMessageDialog(null,
					    "New Hexa qtd args:" + arg.length,
					    "Information",
					    JOptionPane.INFORMATION_MESSAGE);
				*/
				
				if (arg.length == 1){
					NovoHexaGui n = new NovoHexaGui(this, "Inserir Área em HEXA", 400, 500);
				} else if (arg.length == 3){
					//DEBUG
					/*
					JOptionPane.showMessageDialog(null,
						    "New Hexa string:" + arg[1] + "\nCodepage = " + arg[2],
						    "Information",
						    JOptionPane.INFORMATION_MESSAGE);
					*/
					try {
						easyIsoGui.newHexaFile(((String) arg[1]), (Codepage) arg[2]);
					} catch (IsoFormatErrorException e) {
						new ShowErrorGui(e.getErrorMessage());
					} catch (NumberFormatException e) {
						new ShowErrorGui(e.getMessage());
					} catch (JDOMException e) {
						new ShowErrorGui(e.getMessage());
					} catch (IOException e) {
						new ShowErrorGui(e.getMessage());
					} catch (ClassNotFoundException e) {
						new ShowErrorGui(e.getMessage());
					} catch (InstantiationException e) {
						new ShowErrorGui(e.getMessage());
					} catch (IllegalAccessException e) {
						new ShowErrorGui(e.getMessage());
					}
					easyIsoGui.repaint();
				}
				break;
				
			case OPEN:
					abrirArquivo();
				break;
				
			case SAVE:
				if (((MsgMastercardGui) arg[1]) != null){		
					salvarArquivo((MsgMastercardGui) arg[1]);
				}				
				break;
				
			case SAVEAS:
				if (((MsgMastercardGui) arg[1]) != null){		
					salvarArquivoComo((MsgMastercardGui) arg[1]);
				}
				break;

			case EXIT:
				fecharJanela();
				
				break;
			case REFRESH_PRINCIPAL:
				easyIsoGui.repaint();
				break;
				
				
			case TCP_OPEN:
				openSocket((String) arg[1], (Integer) arg[2], (Integer) arg[3], (String) arg[4]);
				break;
				
			case TCP_CLOSE:
				closeSocket();
				break;
				
			case TCP_SEND:
				sendSocket((char[]) arg[1], (Codepage) arg[2]);
				break;
			
			default:
				JOptionPane.showMessageDialog(null,
					    "Acao invalida:" + (EasyIsoActions) arg[0],
					    "Information",
					    JOptionPane.INFORMATION_MESSAGE);
				break;
				
		}

	}
	
	private void fecharJanela(){
		
		int intOpcao = JOptionPane.showConfirmDialog(easyIsoGui, "Deseja sair do programa?", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		/*
		JOptionPane.showOptionDialog(null,   
		        "Deseja Cancelar Importação?",   
		        "Importando Projeto",  
		        0,  
		        JOptionPane.QUESTION_MESSAGE,  
		        null,  
		        botoesDialogo,  
		        botoesDialogo[1]);
		*/
		
		if(intOpcao == 0){
			System.exit(0);
		}
	}
	
	private void abrirArquivo(){
		
		currentDirectory = PersistParam.getActualDirectory();
		
		File arquivo = null;
		JFileChooser jfc = new JFileChooser(currentDirectory);
		jfc.setMultiSelectionEnabled(false);
		
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivos msgiso", "msgiso"));
		
		
		int resp = jfc.showOpenDialog(null);  
		if(resp != JFileChooser.APPROVE_OPTION) return;

		arquivo = jfc.getSelectedFile();
		
		PersistParam.setActualDirectory(arquivo);
		currentDirectory = PersistParam.getActualDirectory();
		
		if (easyIsoGui.isFileOpened(arquivo)){
			JOptionPane.showMessageDialog(null,"Arquivo ja esta aberto","Waring",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		SAXBuilder sb = new SAXBuilder();

		Document d = null;
		try {
			d = sb.build(arquivo);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element msgIso = d.getRootElement();
		if (!msgIso.getAttributeValue("msgType").equalsIgnoreCase("MASTERCARD")){
			JOptionPane.showMessageDialog(null, "Arquivo não é padrão Mastercard: [" + msgIso.getAttributeValue("msgType") + "]");
			return;
		}
		try {
			easyIsoGui.newHexaFile(msgIso.getAttributeValue("dataArea"), Codepage.EBCDIC);
		} catch (IsoFormatErrorException e) {
			new ShowErrorGui(e.getErrorMessage());
		} catch (NumberFormatException e) {
			new ShowErrorGui(e.getMessage());
		} catch (JDOMException e) {
			new ShowErrorGui(e.getMessage());
		} catch (IOException e) {
			new ShowErrorGui(e.getMessage());
		} catch (ClassNotFoundException e) {
			new ShowErrorGui(e.getMessage());
		} catch (InstantiationException e) {
			new ShowErrorGui(e.getMessage());
		} catch (IllegalAccessException e) {
			new ShowErrorGui(e.getMessage());
		}
		(easyIsoGui.getMsgMastercardAtivo()).setFileName(arquivo);
		(easyIsoGui.getMsgMastercardAtivo()).setStatusSalvo(true);
		(easyIsoGui.getMsgMastercardAtivo()).setTitle(arquivo.getAbsolutePath());
		(easyIsoGui.getMsgMastercardAtivo()).setName(arquivo.getAbsolutePath());
		
		//DEBUG
		//System.out.println((easyIsoGui.getMsgMastercardAtivo()).getName());
	}
	
	private void salvarArquivo(MsgMastercardGui msgMcGui){
		if (msgMcGui.getStatusSalvo()){
			
			// ------ AQUI MANDA SALVAR ...
			int retorno = salvarGenerico(msgMcGui.getFileName(), msgMcGui.getStringHexa());
			if (retorno != 0){
				JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo " + (msgMcGui.getFileName()).getAbsolutePath());
			}
		}else{
			salvarArquivoComo(msgMcGui);
		}
	} 
	
	private void salvarArquivoComo(MsgMastercardGui msgMastercardGui){
		try{
		currentDirectory = PersistParam.getActualDirectory();
		}catch (Exception e ){
		}
				
		File arquivo = null;
		JFileChooser jfc = new JFileChooser(currentDirectory);
		//jfc.setAcceptAllFileFilterUsed(false);
		jfc.setMultiSelectionEnabled(false);
		
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivos msgiso", "msgiso"));
		
		
		if (msgMastercardGui == null) return;
        
        int resp = jfc.showSaveDialog(null);  
        if(resp != JFileChooser.APPROVE_OPTION) return;  
        
        arquivo = jfc.getSelectedFile();
        String fName = arquivo.getAbsolutePath();
        String extName = fName.substring(fName.length() - 7, fName.length());
        
        if (!extName.equals(".msgiso")){
        	fName += ".msgiso";
        	arquivo = new File(fName);
        }
        
        PersistParam.setActualDirectory(arquivo);
        currentDirectory = PersistParam.getActualDirectory();
        
        
        // ------ AQUI MANDA SALVAR ...
        int retorno = this.salvarGenerico(arquivo, msgMastercardGui.getStringHexa());

        if (retorno != 0){
        	JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo " + arquivo.getAbsolutePath());
        }

        (easyIsoGui.getMsgMastercardAtivo()).setFileName(arquivo);
        (easyIsoGui.getMsgMastercardAtivo()).setStatusSalvo(true);
        (easyIsoGui.getMsgMastercardAtivo()).setTitle(arquivo.getAbsolutePath());
        (easyIsoGui.getMsgMastercardAtivo()).setName(arquivo.getAbsolutePath());
	}
	
	private int salvarGenerico(File arq, String dados){

		Element msgIso = new Element("msgIso");
		msgIso.setAttribute("msgType", "MASTERCARD");
		msgIso.setAttribute("version", "1");
		msgIso.setAttribute("dataArea", dados);
		
		Document doc = new Document();
		doc.setRootElement(msgIso);
		XMLOutputter xout = new XMLOutputter();
		
		try{
		OutputStream os = new FileOutputStream(arq);
        OutputStreamWriter out = new OutputStreamWriter(os);
                     out.write(xout.outputString(doc));  
                     out.close();  
		}catch(IOException e){  
			//JOptionPane.showMessageDialog(null, e.getMessage());
			return -1;
		}
		
		return 0;
		
	}
	
	private Socket tcpIpConnect(String servidor, int porta) {
			Socket socket = null;
		try {
			socket = new Socket(servidor, porta);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}

	private void closeSocket(){
		try {
			this.socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		socketReader.setStoped(true);
		socketMonitor.setEncerraTask(true);
		
	}
	
	private void openSocket(String server, int porta, int headerLen, String headerType){
		this.socket = tcpIpConnect(server, porta);
		socketMonitor = new SocketMonitor(this.socket);
		socketMonitor.start();
		
		this.socketReader = new ThreadSocketRead(socket, headerLen, headerType);
		
		this.socketReader.addObserver(new ReaderObserver());
		this.socketReader.start();
		
		this.socketWriter = new ThreadSocketWrite(socket, headerLen, headerType);
	}
	
	private void sendSocket(char[] buffer, Codepage codepage){
		String type = null;
		List<Character> argAux = new Vector();
		
		if (codepage == Codepage.ASCII){
			type = "A";
		}else if (codepage == Codepage.EBCDIC){
			type = "E";
		}
		
		try {
			//converte de array para List TCP/IP
			for(int i = 0; i < buffer.length; i++){
				argAux.add(buffer[i]);
			}
			
			this.socketWriter.sendData(argAux, 4, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class ReaderObserver implements Observer{

		@Override
		public void update(Object... arg) {
			if(arg[0] instanceof List){
				List<Character> argX = (List) arg[0];

				char[] buffer = new char[argX.size()];

				Iterator<Character> it = argX.iterator();
				int i = 0;
				while(it.hasNext()){
					buffer[i++] = it.next();
				}

				easyIsoGui.writeLog(buffer);
			}
			
			if(arg[0] instanceof String){
				if(((String) arg[0]).equalsIgnoreCase("close")){
					closeSocket();
				}
			}
		}

	}

	private class SocketMonitor extends Thread{
		Socket socket = null;
		private boolean encerraTask = false;
		
		private void setEncerraTask(boolean b){
			this.encerraTask = b;
		}

		private SocketMonitor(Socket socket){
			this.socket = socket;
		}

		public void run(){
			
			while (true){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (socket == null){
					easyIsoGui.setConStatus(0);
				}else{
					if (socket.isClosed()){
						easyIsoGui.setConStatus(0);
					}else{
						easyIsoGui.setConStatus(2);
					}
				}
				if(encerraTask){
					break;
				}
			}
		}
	}
}
