package br.com.easyiso.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdom.JDOMException;
import net.miginfocom.swing.MigLayout;
import br.com.easyiso.factory.ButtonFactory;
import br.com.easyiso.factory.MenuFactory;
import br.com.easyiso.factory.TextAreaFactory;
import br.com.easyiso.factory.TextFieldFactory;
import br.com.easyiso.gui.actions.AcAbrir;
import br.com.easyiso.gui.actions.AcExitApp;
import br.com.easyiso.gui.actions.AcNew;
import br.com.easyiso.gui.actions.AcNewHexa;
import br.com.easyiso.gui.actions.AcSalvar;
import br.com.easyiso.gui.actions.AcSalvarComo;
import br.com.easyiso.gui.actions.EasyIsoActions;
import br.com.easyiso.gui.utils.ScreenUtils;
import br.com.jrperin.ISO.mastercard.MsgInBuffer;
import br.com.jrperin.ISO.mastercard.MsgMastercard;
import br.com.jrperin.commoninterfaces.Command;
import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters;
import br.com.jrperin.parameters.Parameters.Codepage;
import br.com.jrperin.uteis.Converter;
import br.com.jrperin.uteis.LogUtils;

//F0F1F0F0FEE7440188E1E00A0000000000000100F1F6F5F3F9F0F5F9F8F6F0F3F4F4F5F5F0F3F0F0F0F0F0F0F0F0F0F0F0F0F0F0F1F4F5F7F0F0F0F0F0F0F0F0F0F8F1F3F0F0F0F0F0F0F0F0F1F4F5F7F1F2F0F7F1F3F1F5F0F0F5F5F5F7F8F4F8F9F6F1F0F0F0F0F0F0F3F8F0F3F9F5F1F3F0F6F1F2F0F7F1F2F0F7F4F8F1F4F8F1F2F0F6F0F1F1F4F2F9F0F6F0F0F1F2F9F7F0F2F2F0F2F4F4F8F0F4F3F8F0F0F0F0F0F0F0F1F0F0F0F9F8F0F0F2F0F3F0F5F9F9F4D7C1E8D7C1D3405CE2D2E8D7C540404040404040404040F3F5F3F1F4F3F6F9F0F0F1404040D3E4E7F0F2F7E3F8F2F0F2F5F2F4F2F0F7F0F1F0F3F2F1F0F6F1F0F5F0F0F0F0F0F9F8F6F8F4F0F9F8F6F0F2F6F0F0F2F5F1F0F0F0F0F6F6F0F0F4F4F2D3F1F5F2F04040404040F0F0F9D4C3E2F8E8C2E4C2D1F0F3F3F0F1F2F9F3F1F1F1F0F0F5F040D9A48140D18183A48940F6F640829340F4408197
//Converter.fillZerosOnTheLeft(dataID, 3)

public class EasyISOGui extends JFrame implements ActionListener{ 

	Command chamador = null;
	MsgISOFile msgISOFile = new MsgISOFile();
	MsgMastercard msgMC = null;
	JPanel statusBar = null;
	
	//------------------ FABRICAS ----------------------------
	ButtonFactory bFactory = new ButtonFactory();
	MenuFactory   mFactory = new MenuFactory();
	TextAreaFactory tFactory = new TextAreaFactory();

	
	//----------------- OBJETO DA MENSAGEM ISO ---------------
	MsgInBuffer msgInBuffer = null;
	
	//-------------- OBJETOS GRAFICOS INTERNOS ---------------
	JDesktopPane desktop = null;
	List<MsgMastercardGui> filhas = new ArrayList<MsgMastercardGui>();
	JTextArea tMsgHexa = null;
	Font fnt = new Font(Font.MONOSPACED, 12, 14);
	
	JTextArea tObs      = null;
	JTextArea tLog      = null;
	JComboBox cbMsgDefs = null;
	
	//------------- Objetos do TCP/IP ------------------------
	//-----gui------
	JPanel pTcpip     = null;
	//JTextField tTcpip = null;
	JComboBox tTcpip    = null; 
	JTextField tPort  = null;
	JButton btConect  = null;
	JButton btSend    = null;
	JLabel lbStatus   = null;
	JLabel lbTcpip    = null;
	JLabel lbPortN    = null;
	JComboBox cbLen   = null;
	JComboBox cbType  = null;
	ImageIcon connected    = makeIcon("/conectado.png");
	ImageIcon disconnected = makeIcon("/desconectado.png");
	ImageIcon connecting   = makeIcon("/conectando.png");
	
	private final String stConectar    = "Conectar";
	private final String stDesconectar = "Desconectar";
	
	int msgSendType = 0; // 0 = EBCDIC ; 1 = ASCII
	
	//--------------- ATRIBUTOS INTERNOS ---------------------
	String txCopyright = ("  Joao R. Perin (beta2 05/2012)");
	int contaNovos = 1;
	int posX = 0;
	int posY = 0;
	
	/*-------------- CONSTRUTOR -------------------------------*/
	public EasyISOGui(Command chamador){
		super("ISO Easy");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//o tamanho sera: x =5/9 e y = 1/1 (a partir do canto esquerdo superior)
		ScreenUtils.setScreenPosition(this, 19, 20, 10, 11, 1);
		
		this.setLayout(new MigLayout());
		
		this.chamador = chamador;
		this.setIconImage(makeIcon("/appicon.png").getImage());
		createMenu();
		desktop = new JDesktopPane();		
		this.add(desktop, "dock center");
		
		this.add(createStatusBar(), "dock south");
		
		this.add(montaPainelDireito(), "width :350:,dock east");
		
		this.add(createToolBar(), "dock north");
		
		desktop.setBackground(new Color(120, 120, 120));

		//setMsgMC();
		if (desktop.getAllFrames().length > 0){
			try {
				(desktop.getAllFrames())[0].setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			
		}
		this.addWindowListener(new OnClose() );
		this.setVisible(true);
	}
	
	/*-------------- FUNCOES ----------------------------------*/
	private void newMsgMastercard() throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		newMsgMastercard(Converter.convertHexaToCharArray("303030300000000000000000"), Parameters.getSystemCodePage());
		
	}

	private void newMsgMastercard(char[] charArray, Codepage inputCodepage) throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		try {
			//msgMC = new MsgMastercard(charArray, inputCodepage, new File("msgISOMastercard.def"));
			//msgMC = new MsgMastercard(charArray, inputCodepage, new File("msgDV2VZ.def"));
			//msgMC = new MsgMastercard(charArray, inputCodepage, new File("msgNoLimit.def"));
			msgMC = new MsgMastercard(charArray, inputCodepage, (File) cbMsgDefs.getSelectedItem());
		} catch (IsoFormatErrorException e) {
			new ShowErrorGui(e.getErrorMessage());
			return;
		}
		
		//DEBUG
		//System.out.println(msgMC);
		montaFilha();
	}
	
	private File[] getDefFiles(){
		File dir = new File(".");
		FileFilter filtro = new FileFilter(){
							public boolean accept(File pathName){
								return pathName.getAbsolutePath().endsWith(".def");
							}
						};
		return dir.listFiles(filtro);
	}
	
	private JPanel montaPainelDireito(){
		JPanel pDireito = new JPanel();
		pDireito.setLayout(new MigLayout("ins 0"));
		
		
		JPanel pDefMsg = new JPanel();
		pDefMsg.setLayout(new MigLayout("ins 15 0 0 0"));
		
			//String[] ListMsgDefs = {"msgISOMastercard.def", "msgDV2VZ.def", "msgNoLimit.def"};
			JLabel lbMsgDefs = new JLabel("Definicao de Msg ISO:");
		try{
			cbMsgDefs = new JComboBox(getDefFiles());
			cbMsgDefs.setSelectedIndex(0);
		}catch(RuntimeException re){
			JOptionPane.showMessageDialog(this,
				    "Nenhum arquivo de definicao ISO (*.def) encontrado!...\nEncerrando aplicacao...",
				    "Alerta!",
				    JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

		pDefMsg.add(lbMsgDefs, "height 30!, gap unrelated");
		pDefMsg.add(cbMsgDefs, "height 30!, wrap");
		
		
		pDefMsg.add(new JLabel("LOG:"), "height 30!, gap unrelated, push");
		pDefMsg.add(bFactory.makeButton("Limpar Log", new AcLimpaLog()), "right");
		
		JPanel pLog = new JPanel();
		pLog.setLayout(new MigLayout("ins 5"));
		
		TextAreaFactory taf = new TextAreaFactory();
		tObs = taf.makeDefaultTextArea("", fnt);
		tLog = taf.makeDefaultTextArea("", fnt);
		tLog.setFont(new Font("Courier", 12, 12));
		tLog.setEditable(false);
		
		//pObs.add(new JLabel("Observacoes:"), "height :25:, dock north");
		//pObs.add(new JScrollPane(tObs), "dock center");
		
		JPanel pLogTop = new JPanel();
		pLogTop.setLayout(new MigLayout());
		
		//pLogTop.add(new JLabel("LOG:"), "height :25:, push");
		//pLogTop.add(bFactory.makeButton("Limpar Log", new AcLimpaLog()),"split");
		
		//pLog.add(pLogTop, "north");
		pLog.add(new JScrollPane(tLog), "dock center");
		
		//pDireito.add(pDefMsg, "height :60:, width 200 , dock north");
		
		pDireito.add(pLog, "dock center");
		pDireito.add(pDefMsg, "dock north");
		return pDireito;
	}
	
	private void montaFilha() throws IsoFormatErrorException{
		MsgMastercardGui filha = null;
		if (desktop.getAllFrames().length == 0){
			posX = 0;
			posY = 0;
		}
		
		int largFrame = desktop.getWidth() - posY;  
		int altFrame = desktop.getHeight() - posY;  
		
		if (desktop.getAllFrames().length < 10){
			filha = new MsgMastercardGui(msgMC, "Novo" + contaNovos++ + " - Mensagem ISO Mastercard", posX, posY, largFrame, altFrame);
			
			posX += 10;
			posY += 10;
			if (posX > 500) {
				posX = 5;
				posY = 5;
			}
			desktop.add(filha);
			filha.setVisible(true);
			filha.setFirstFocus();

		} else {
			JOptionPane.showMessageDialog(this,
				    "Numero maximo de janelas atingido [10].\nFeche algumas para abrir novas...",
				    "Alerta",
				    JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void organizaFilhas(){
		
		JInternalFrame[] filhas = desktop.getAllFrames();
		if (filhas.length < 1){ return; }
		
		posX = 0;
		posY = 0;
		
		for (int i = filhas.length -1; i >= 0; i--){
			
			int largFrame = desktop.getWidth() - posY;  
			int altFrame = desktop.getHeight() - posY;
			filhas[i].setBounds(posX, posY, largFrame, altFrame);
			
			posX += 10;
			posY += 10;
			if (posX > 500) {
				posX = 5;
				posY = 5;
			}
		}

	}
	
	private void createMenu(){
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		//menuArquivo.setIcon(new ImageIcon("./icons/mastercard.png"));
		
		JMenu menuArquivo = new JMenu("Arquivo");
		mFactory.makeMenuItem(menuArquivo, "Novo <- Area Hexa",makeIcon("/newhexa.png"), new AcNewHexa(chamador));
		menuArquivo.addSeparator();
		mFactory.makeMenuItem(menuArquivo, "Novo",makeIcon("/new.png"), new AcNew(chamador));
		mFactory.makeMenuItem(menuArquivo, "Abrir",makeIcon("/open.png"), new AcAbrir(chamador));
		mFactory.makeMenuItem(menuArquivo, "Salvar",makeIcon("/save.png"), new AcSalvar(chamador, this));
		mFactory.makeMenuItem(menuArquivo, "Salvar Como", new AcSalvarComo(chamador,this));
		menuArquivo.addSeparator();
		mFactory.makeMenuItem(menuArquivo, "Sair",makeIcon("/exit.png"), new AcExitApp(chamador));
		
		JMenu menuJanela = new JMenu("Janelas");
		mFactory.makeMenuItem(menuJanela, "Em Cascata", new AcOrgFilhasCascata());
		//mFactory.makeMenuItem(menuJanela, "Org. Lado a Lado", new AcOrgFilhasLadoALado());
		menuJanela.addSeparator();
		mFactory.makeMenuItem(menuJanela, "Fechar", new AcFecharFilha());
		mFactory.makeMenuItem(menuJanela, "Fechar Todas", new AcFecharTodasFilhas());
		menuJanela.addSeparator();
		mFactory.makeMenuItem(menuJanela, "Limpar LOG", new AcLimpaLog());
		
		JMenu menuHelp = new JMenu("Ajuda");
		mFactory.makeMenuItem(menuHelp, "Sobre", new AcSobre());
		menubar.add(menuArquivo);
		menubar.add(menuJanela);
		menubar.add(menuHelp);
	}

	public File getMsgDefinition(){
		return (File) this.cbMsgDefs.getSelectedItem();
	}
	
	public void setConStatus(int conStatus){
		
		switch (conStatus){
			case 0:
				this.btConect.setText(stConectar);
				this.lbStatus.setIcon(disconnected);
				break;
			case 2:
				this.btConect.setText(stDesconectar);
				this.lbStatus.setIcon(connected);
				break;
		}
		
	}

	public JToolBar createToolBar(){
		
		JToolBar barra = new JToolBar();
		
		barra.add(bFactory.makeButton("", new ImageIcon(getClass().getResource("/newhexa.png")),new AcNewHexa(chamador) ));
		barra.addSeparator();
		barra.add(bFactory.makeButton("", makeIcon("/new.png"), new AcNew(chamador) ));
		barra.add(bFactory.makeButton("",makeIcon("/open.png"), new AcAbrir(chamador)));
		barra.add(bFactory.makeButton("",makeIcon("/save.png"), new AcSalvar(chamador, this)));
		barra.addSeparator();
		barra.add(bFactory.makeButton("Sair",makeIcon("/exit.png"), new AcExitApp(chamador)));

		return barra;
		
	}
	
	public JPanel createStatusBar(){
		
		Font pFnt = new Font(Font.SANS_SERIF, 12, 12);
		statusBar = new JPanel();
		statusBar.setLayout(new MigLayout("insets 5"));
		
		JLabel lbCopyR = new JLabel(txCopyright);
		lbCopyR.setFont(pFnt);
		
		
		String[] strType = {"EBCDIC","ASCII" };
		cbType = new JComboBox(strType);
        cbType.setEditable(false);
        cbType.addActionListener(new AcSendType());
        cbType.setFont(pFnt);
		JPanel pnType = new JPanel();
		pnType.add(new JLabel("Tipo da Msg:"));
		pnType.add(cbType);
		
		
		String[] strLens = {"4","6" };
		cbLen = new JComboBox(strLens);
        cbLen.setEditable(false);
        cbLen.setFont(pFnt);
		JPanel pnLen = new JPanel();
		pnLen.add(new JLabel("Tam. Header:"));
		pnLen.add(cbLen);
        
		
		String[] ipsComuns = {"127.0.0.1","172.10.1.19","192.168.1.106"};
		tTcpip = new JComboBox(ipsComuns);
        tTcpip.setEditable(true);
        tTcpip.setFont(pFnt);
		
        
		//tTcpip= (new TextFieldFactory()).makeDefaultTextField("127.0.0.1", fnt, 20); 
		//---tTcpip= (new TextFieldFactory()).makeDefaultTextField("172.10.1.19", fnt, 20);
		//tPort = (new TextFieldFactory()).makeDefaultTextField("3000", fnt, 8);
		tPort = (new TextFieldFactory()).makeDefaultTextField("3000", fnt, 8);
		btConect = (new ButtonFactory()).makeButton(stConectar, pFnt);

		btSend   = (new ButtonFactory()).makeButton("Enviar", pFnt);
		
		btConect.addActionListener(new AcConectar());
		btSend.addActionListener(new AcSend());
		
		lbStatus = new JLabel(disconnected);
		lbStatus.setFont(pFnt);
		lbTcpip  = new JLabel("   N.o TCP/IP:");
		lbTcpip.setFont(pFnt);
		lbPortN  = new JLabel("Porta:");
		lbPortN.setFont(pFnt);
		
		// --- Cria as Opcoes de ASCII/EBCDIC para enviar a mensagem ------
		
		
		
		/*JRadioButton ascii = new JRadioButton("ASCII");
		ascii.setSelected(false);
		ascii.setMnemonic(KeyEvent.VK_A);
		ascii.addActionListener(new AcSendType());
		
		JRadioButton ebcdic = new JRadioButton("EBCDIC");
		ebcdic.setSelected(true);
		ebcdic.setMnemonic(KeyEvent.VK_E);
		ebcdic.addActionListener(new AcSendType());
		
		ButtonGroup group = new ButtonGroup();
		group.add(ebcdic);
		group.add(ascii);
		
		JPanel radioPanel = new JPanel(new MigLayout());
		radioPanel.add(new JLabel("Formato de envio:"));
		radioPanel.add(ebcdic);
		radioPanel.add(ascii);
		//this.add(radioPanel, BorderLayout.NORTH);
		*/
		
		
		//-------------------------------------------------------------------
		statusBar.add(new JLabel("Teste"),"wrap");
		statusBar.add(lbTcpip);
		statusBar.add(tTcpip);
		statusBar.add(lbPortN);
		statusBar.add(tPort);
		statusBar.add(pnLen);
		statusBar.add(pnType);
		statusBar.add(lbStatus);
		statusBar.add(btConect, "width 100!, height :25:");
		statusBar.add(new JSeparator(SwingConstants.VERTICAL), "width :8:, height :25:");
		//statusBar.add(radioPanel,"width :85:, height :25: ");
		
		statusBar.add(btSend, "width :85:, height :25:, push");
		statusBar.add(lbCopyR, "right");

		return statusBar;
		
	}
	
	private ImageIcon makeIcon(String pathIcon){
		return new ImageIcon(this.getClass().getResource(pathIcon));
	}
	
	public void newHexaFile(String str, Codepage codepage) throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		this.newMsgMastercard(Converter.convertHexaToCharArray(str), codepage);
		this.setEnabled(true);
	}
	
	public void newFile() throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		this.newMsgMastercard();
		this.setEnabled(true);
	}
	
	public MsgISOFile getMsgISOFile() {
		refreshMsgIso();
		return this.msgISOFile;
	}

	public MsgMastercardGui getMsgMastercardAtivo(){
		if ((desktop.getAllFrames()).length > 0){
			return (MsgMastercardGui) (desktop.getAllFrames())[0];
		}
		return null;		
	}
	
	public void writeLog(char[] buffer){
		StringBuilder strAnt = new StringBuilder();
		strAnt.append(this.tLog.getText() + "\n");
		strAnt.append(LogUtils.getLogDataHora() + "\n");
		MsgMastercard msgLog = null;
		try {
			
			Codepage cp = null;
			if (msgSendType == 0){
				cp = Codepage.EBCDIC;
			}else{
				cp = Codepage.ASCII;
			}
			msgLog = new MsgMastercard(buffer, cp, (File) cbMsgDefs.getSelectedItem());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			strAnt.append(Converter.expandHexa(msgLog.getIsoMessage(Parameters.Codepage.EBCDIC)));
		} catch (IsoFormatErrorException e) {
			e.printStackTrace();
		}
		
		strAnt.append("\n");
		strAnt.append(msgLog.toString());
		strAnt.append("\n    --------------  x x x --------------");
		this.tLog.setText(strAnt.toString());
	}
	
	public boolean isFileOpened(File f){
		boolean result = false;
		JInternalFrame iFrame[] = desktop.getAllFrames();
		
		for(JInternalFrame frm : iFrame) {
			
			String str1 = ((MsgMastercardGui) frm).getFileName().getAbsolutePath();
			String str2 = f.getAbsolutePath();
			
			if (str1.equals(str2)){
				result = true;
			}
		}
		return result;
	}
	
	private void refreshMsgIso(){
		this.msgMC = ((MsgMastercardGui) getMsgMastercardAtivo()).getMessageIso();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		JOptionPane.showMessageDialog(desktop,
			    "Beta2: Funcao nao implementada" ,
			    "Informacao",
			    JOptionPane.PLAIN_MESSAGE);
	}
	
	// --> CLASSES INTERNAS <--
	
	private class AcSobre implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(desktop,
				    "Desenvolvido por Joao R. Perin\n 05/2012\nversao Beta 2" ,
				    "Sobre",
				    JOptionPane.PLAIN_MESSAGE);
		}
	}
 
	private class AcFecharFilha implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			MsgMastercardGui filhaAtiva = getMsgMastercardAtivo();
			if (filhaAtiva == null) {return;}
			int intOpcao = JOptionPane.showConfirmDialog(null, "Deseja realmente fechar a janela " + filhaAtiva.getName() + "?", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (intOpcao == 0){
				filhaAtiva.dispose();
			}
		}
		
	}
	
	private class AcFecharTodasFilhas implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JInternalFrame[] filhas =desktop.getAllFrames();
			if (filhas.length < 1) {return;}
			
			int intOpcao = JOptionPane.showConfirmDialog(null, "Deseja realmente fechar todas as janelas ?", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (intOpcao == 0){
				for (int i = 0; i < filhas.length; i++){
					filhas[i].dispose();
				}
			}
		}
		
	}
	
	private class AcOrgFilhasCascata implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0){
			organizaFilhas();
		}
	}

	private class AcOrgFilhasLadoALado implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(desktop,
				    "Beta2: Funcao nao implementada" ,
				    "Informacao",
				    JOptionPane.PLAIN_MESSAGE);
		}
		
	}
	
	private class OnClose extends WindowAdapter{
		public void windowClosing(WindowEvent evt){
			EasyISOGui eg = (EasyISOGui) evt.getSource();
			
			chamador.execute(EasyIsoActions.EXIT);
			
		}
	}

	private class AcConectar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equalsIgnoreCase(stConectar)){

				String servidor = "localhost";
				int porta = 0;
				//servidor = tTcpip.getText();
				servidor = tTcpip.getSelectedItem().toString();
				System.out.println("serviror = " + servidor);
				porta = Integer.parseInt(tPort.getText());
				int headerLen = Integer.parseInt(cbLen.getSelectedItem().toString());
				String headerType = null;
				if (msgSendType == 0) {headerType = "E";} else if (msgSendType == 1){headerType = "A";}
				chamador.execute(EasyIsoActions.TCP_OPEN, servidor, porta, headerLen, headerType );
			}
			
			if (arg0.getActionCommand().equalsIgnoreCase(stDesconectar)){
				chamador.execute(EasyIsoActions.TCP_CLOSE);
			}
		}
		
	}

	private class AcSend implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshMsgIso();
			try {
				if (msgSendType == 0){
					chamador.execute(EasyIsoActions.TCP_SEND, msgMC.getIsoMessage(Codepage.EBCDIC), Codepage.EBCDIC);
				}else if (msgSendType == 1){
					chamador.execute(EasyIsoActions.TCP_SEND, msgMC.getIsoMessage(Codepage.ASCII), Codepage.ASCII);
				}else{
					JOptionPane.showMessageDialog(desktop,
						    "Valor do msgSendType difere de 0 e 1. Valor = " + msgSendType ,
						    "ERRO!!!!",
						    JOptionPane.PLAIN_MESSAGE);
				}
				
			} catch (IsoFormatErrorException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private class AcLimpaLog implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			tLog.setText("");
		}
		
	}
	
	private class AcSendType implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0){
			String acao = arg0.getActionCommand();
			
			/*JOptionPane.showMessageDialog(desktop,
				    "getActionCommand = " + acao ,
				    "msgSendType",
				    JOptionPane.PLAIN_MESSAGE);*/
			
			if (cbType.getSelectedItem().toString().equalsIgnoreCase("EBCDIC")){
				msgSendType = 0;
				Parameters.setNetworkCodePage(Codepage.EBCDIC);
			}
		
			if (cbType.getSelectedItem().toString().equalsIgnoreCase("ASCII")){
				msgSendType = 1;
				Parameters.setNetworkCodePage(Codepage.ASCII);
			}
			
			//if (acao.equalsIgnoreCase("ASCII")){
			//	msgSendType = 1;
			//}
		
			//if (acao.equalsIgnoreCase("EBCDIC")){
			//	msgSendType = 0;
			//}
			
			/*JOptionPane.showMessageDialog(desktop,
				    "Valor do msgSendType = " + msgSendType ,
				    "msgSendType",
				    JOptionPane.PLAIN_MESSAGE);*/
			
			return;
		}
	}
}
