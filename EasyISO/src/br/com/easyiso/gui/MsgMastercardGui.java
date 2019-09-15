package br.com.easyiso.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import br.com.easyiso.factory.ButtonFactory;
import br.com.easyiso.factory.TextFieldFactory;
import br.com.easyiso.gui.actions.MsgSalvavel;
import br.com.jrperin.ISO.mastercard.DEBitmap;
import br.com.jrperin.ISO.mastercard.DEGeneric;
import br.com.jrperin.ISO.mastercard.DECurrency;
import br.com.jrperin.ISO.mastercard.MsgMastercard;
import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters;
import br.com.jrperin.parameters.Parameters.Codepage;
import br.com.jrperin.uteis.Converter;
import net.miginfocom.swing.MigLayout;

public class MsgMastercardGui extends JInternalFrame implements ActionListener, MsgSalvavel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MsgMastercard msg           = null;
	JScrollPane painelPrincipal = null;
	JPanel formulario           = null;
	JLabel deLbMsgType          = null;
	JTextField deMsgType        = null;
	JLabel deLbBitmap1          = null;
	JTextField deDataBitmap1    = null;
	JTextField deLenBitmap1     = null;
	JCheckBox chkLigados        = null;
	JInternalFrame chamador     = (JInternalFrame) this;
	
	Vector<JCheckBox>  deChk    = new Vector<JCheckBox>();
	Vector<JTextField> deLen    = new Vector<JTextField>();
	Vector<JTextField> deData   = new Vector<JTextField>();
	Vector<Integer>    deLenLen = new Vector<Integer>();
	Vector<Integer>    deLenMax = new Vector<Integer>();
	

	
	Font fnt = new Font(Font.MONOSPACED, 12, 14);
	int ROW  =  1;
	int COLL =  4;
	int COLD = 40;

	// ---------------- ATRIBUTOS DO ARQUIVO -----------------------------
	boolean foiSalvo = false;
	File nomeArquivo = new File("~");
	
	String strEBCDIC = "";
	String strASCII  = "";
	
	
	public MsgMastercardGui(MsgMastercard msgMastercard, String titulo, int left, int top, int width, int height) throws IsoFormatErrorException{
	
		super(titulo,true, true, true, true);
		this.setName(titulo);

		this.msg = msgMastercard;
		this.update(getGraphics());
		
		this.add(montaBarra(), BorderLayout.NORTH);
		formulario = montaFormulario(false);
		painelPrincipal = new JScrollPane(formulario);
		painelPrincipal.getVerticalScrollBar().setUnitIncrement(50);
		this.add(painelPrincipal, BorderLayout.CENTER);
		
		this.pack();
		
		this.setBounds(left, top, width, height);
		
		this.toFront();
		
	}
	
	public void setFirstFocus(){

		SwingUtilities.invokeLater(new Runnable() {  
			public void run() {  
				if (isVisible()) {
					deData.firstElement().requestFocus();
				}  
			}  
		});
	}
	
	private JPanel montaFormulario(boolean soSelecionados) throws IsoFormatErrorException{

		msg.refreshIsoMessage();
		
		JPanel frm = new JPanel();
		frm.setLayout(new MigLayout());
    	TextFieldFactory tFactory = new TextFieldFactory();

		deChk.clear();
		deLen.clear();
		deData.clear();
		deLenLen.clear();
		deLenMax.clear();
    	
    	// --------------- Montar o formulario percorrendo a mensagem ISO ----------------- 
    	
    	msg.resetIterator();
    	int i = 0;
    	
    	while(msg.hasNext()){
    		DEGeneric DE = (DEGeneric) msg.next();
    		
    		int colLen = 0;
    		
    		/* --- Se o tamanho do campo for superior a COLD, assume COLD --- */
        	if (DE.getLenMax() > COLD){
        		colLen = COLD;
        	} else {
        		colLen = DE.getLenMax();
        	}
    		
    		String DEData = null;
    		if (DE.isOn()){
    			DEData = DE.getDataStr();
    		}
    		
    		String lOL = new String();
    		boolean verifyLenError = true;
    		
    		/* Define o conteudo do campo tamanho */
    		if (DE.getLenOfLen() == 0){
    			lOL = ""+ DE.getLenMax();
    			verifyLenError = true;
    		}else{
    			if (DE.getLenData() == 0){
    				lOL = "" + Converter.fillZerosOnTheLeft(DE.getLenMax(), DE.getLenOfLen());
    			}else{
    				lOL = "" + Converter.fillZerosOnTheLeft(DE.getLenData(), DE.getLenOfLen());
    			}
    			verifyLenError = false;
    		}
    		
    		int id = 0;
    		try{
    			id = Integer.parseInt(DE.getDataID());
    		}catch (NumberFormatException nfe){  
    			/* nao faz nada, fica com -1 mesmo... */ 
    		}
    		
    		String prefixo = "";
    		if (id > 0){
    			prefixo = Converter.fillZerosOnTheLeft(id, 3) + ": ";
    		}
    		
    		JCheckBoxID  cbStatus = new JCheckBoxID(String.valueOf(prefixo + DE.getDescription()), DE.isOn(), i);
    		JTextField tLen = tFactory.makeNumericTextField(lOL, fnt, COLL, DE.getLenOfLen(), false);
    		JTextFieldID tDados = null;
    		
    		// ------- TextFields com tratamento diferente ------------------    		
    		if (DE.getDataType().equalsIgnoreCase("N")){
    			if(DE instanceof DECurrency){
    				tDados = tFactory.makeNumericTextField(DEData, fnt, colLen, DE.getLenMax(), verifyLenError);
    			}else{
    				tDados = tFactory.makeNumericTextField(DEData, fnt, colLen, DE.getLenMax(), verifyLenError) ;
    			}
    		}else if(DE.getDataType().equalsIgnoreCase("AN")){
    			tDados = tFactory.makeDefaultTextField(DEData, fnt, colLen, DE.getLenMax(), verifyLenError,"[^a-z,A-Z,0-9]\\S", false);
    		}else{
    			tDados = tFactory.makeDefaultTextField(DEData, fnt, colLen, DE.getLenMax(), verifyLenError, "", false);
    		}
    		
    		tDados.setID(i); /* Seta o numero interno para achar o cbStatus referente */
    		tDados.addKeyListener(new textAltered());
    		
    		if(DE.getLenOfLen() == 0){
        		tLen.setEditable(false);
        		tLen.setBackground(Color.LIGHT_GRAY);
        		tLen.setFocusable(false);
        	}else{
        		tLen.setEditable(false);
        		tLen.setBackground(Color.WHITE);
        		tLen.setFocusable(false);
        	}
    		
    		if (DE instanceof DEBitmap || (!DE.isBitmaped()) ){
    			tLen.setEditable(false);
    			tLen.setFocusable(false);
    			cbStatus.setEnabled(false);
    			cbStatus.setFocusable(false);
    		}
    		
    		if (DE instanceof DEBitmap){
    			tDados.setEditable(false);
    			tDados.setFocusable(false);
    		}
    		
    		deChk.add(cbStatus);
    		deLen.add(tLen);
    		deData.add(tDados);
    		deLenLen.add(DE.getLenOfLen());
    		deLenMax.add(DE.getLenMax());

    		if (soSelecionados){
    			if (DE.isOn()){
    				frm.add(cbStatus, "width :330:");
    				frm.add(tLen);
    				
    				//if (i != 35 && i != 36){
    					frm.add(tDados,"wrap");
    				//}else{
    				
    					//frm.add(tDados);
    					//frm.add((new ButtonFactory()).makeButton("",makeIcon("/advanced.png")), "wrap");
    				/*
    				 * }
    				 */
    			}	
    		} else {
    			frm.add(cbStatus, "width :330:");
    			frm.add(tLen);
    			///if (i != 35 && i != 36){
    				frm.add(tDados,"wrap");
    			//}else{ 
    				//frm.add(tDados);
    				//frm.add((new ButtonFactory()).makeButton("",makeIcon("/advanced.png")), "wrap");
    			//}

    		}
    		i++;
    	}
    	
    	// ----------- Cria os Demais Campos ------------

    	return frm;
	}

	private void atualizaMsgMastercard() throws IsoFormatErrorException{
		
		/* obs.: A mensagem devera atualizar cada campo individualmente, quando for solicitada a mensagem binaria, 
		 * a propria mensagem ISO devera atualizar os mapas de Bits e devolver o array de caracteres.
		 */

		//DEBUG
		//System.out.println(" Antes de atualizar --------------\n" + msg);
		
		Iterator<JCheckBox> itStatus = deChk.iterator();
		Iterator<JTextField> itData = deData.iterator();
		msg.resetIterator();
		
		while (itData.hasNext() & msg.hasNext() & itStatus.hasNext()){
			Boolean status = itStatus.next().isSelected();
			String  dados  = itData.next().getText();
			DEGeneric de   = (DEGeneric) msg.next();
			
			de.setStatus(status);
			
			if (status){
				de.setData(dados);
			}
		}
				
		this.strASCII  = String.valueOf(Converter.expandHexa(msg.getIsoMessage(Codepage.ASCII)));
		this.strEBCDIC = String.valueOf(Converter.expandHexa(msg.getIsoMessage(Codepage.EBCDIC)));

		//DEBUG
		//System.out.println(" Depois de atualizar --------------\n" + msg);
		
	}

	private JToolBar montaBarra(){
		ButtonFactory btFactory = new ButtonFactory();
		JToolBar barra = new JToolBar();
		barra.setLayout(new MigLayout("fillx,insets 0"));
		chkLigados =  new JCheckBox("Apenas DEs ligados",false );
		chkLigados.setFont(new Font(Font.SANS_SERIF, 10, 12));
		chkLigados.addActionListener(this);
		chkLigados.setToolTipText("Exibe / Omite campos desligados na mensagem");
		
		JButton btHexa = btFactory.makeButton("Area Hexadecimal", "./icons/areahexa.png");
		btHexa.setFont(new Font(Font.SANS_SERIF, 10, 12));
		btHexa.setToolTipText("Mostra a mensagem ISO no formato ASCII ou EBCDIC em HEXADECIMAL");
		btHexa.addActionListener(new showHexa());

		JLabel lbType = new JLabel("Tipo: " + msg.getType() + "    versao: " + msg.getVersion() + " ");
		
		barra.add(chkLigados, "split,left,width 180:");
		barra.add(btHexa, "width :160:");
		barra.add(lbType, "width :120:, dock east");
		return barra;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getSource().equals(chkLigados)){

			try {
				this.atualizaMsgMastercard();
			} catch (IsoFormatErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.remove(painelPrincipal);
			try {
				painelPrincipal = new JScrollPane(montaFormulario(chkLigados.isSelected()));
				painelPrincipal.getVerticalScrollBar().setUnitIncrement(50);
			} catch (IsoFormatErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.add(painelPrincipal);
			this.invalidate();
			this.validate();
			
		}else{
			JOptionPane.showMessageDialog(this,
					"Nao foi o chkLigados que acionou o ActionListener",
					"Alerta",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private class showHexa implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {

			try {
				atualizaMsgMastercard();
			} catch (IsoFormatErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(strEBCDIC.toString());
			ShowHexaGui n = new ShowHexaGui(null, "Area em HEXA", 400, 500, strEBCDIC, strASCII);
		}
		
	}

	private class textAltered implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			final JTextFieldID textID = (JTextFieldID) e.getSource();
			final JTextFieldID lenID = (JTextFieldID) deLen.get(textID.getID());
			final int lOl = deLenLen.get(textID.getID());
	
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					(deChk.get(textID.getID())).setSelected(true);
					
					int len = textID.getText().length();
					lenID.setForeground(Color.BLACK);
					
					if (lOl > 0){
						
						if (len == 0){
							lenID.setText(String.valueOf(Converter.fillZerosOnTheLeft(deLenMax.get(textID.getID()), lOl)));
							lenID.setForeground(Color.RED);
						}else{
							lenID.setText(String.valueOf(Converter.fillZerosOnTheLeft(len, lOl)));
						}
					}else{
						if (len != deLenMax.get(textID.getID())){
							lenID.setForeground(Color.RED);
						}
					}
				}
			});
		}
		
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public File getFileName() {
		return nomeArquivo;
	}
	
	public void setFileName(File f){
		this.nomeArquivo = f;
	}

	@Override
	public String getStringHexa() {
		try {
			atualizaMsgMastercard();
		} catch (IsoFormatErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strEBCDIC.toString();
	}

	@Override
	public boolean getStatusSalvo() {
		return foiSalvo;
	}
	
	public void setStatusSalvo(boolean b){
		this.foiSalvo = b;
	}
	
	public MsgMastercard getMessageIso(){
		try {
			atualizaMsgMastercard();
		} catch (IsoFormatErrorException e) {
			e.printStackTrace();
		}
		return msg;
		
	}
}
