package br.com.easyiso.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;




import br.com.easyiso.factory.ButtonFactory;
import br.com.easyiso.factory.TextAreaFactory;
import br.com.easyiso.gui.actions.AcFormCancel;
import br.com.easyiso.gui.actions.EasyIsoActions;
import br.com.easyiso.gui.utils.ScreenUtils;
import br.com.easyiso.gui.utils.ScreenUtils.PositionType;
import br.com.jrperin.commoninterfaces.Command;
import br.com.jrperin.parameters.Parameters.Codepage;
import br.com.jrperin.uteis.FormatadorCECI;


public class ShowHexaGui extends JDialog implements ActionListener{
	

	//private JFrame chamador = null;
	private Command chamador = null;
	
	static final int DEFAULT_W = 400;
	static final int DEFAULT_H = 500;

	JTextArea tHexa = null;
	final String tamParteFixa = "Tamanho da area: ";
	String tamParteVariavel = "";
	String textoHexaASCII = null;
	String textoHexaEBCDIC = null;
	
	JScrollPane pPrincipal = null;
	JPanel pBotoes = null;
	
	Dimension btDim = new Dimension(130,30);
	
	Font fnt = new Font(Font.MONOSPACED, 12, 14);
	 
	
	// --------------- FABRICAS --------------------------
	ButtonFactory   bFactory  = new ButtonFactory();
	TextAreaFactory taFactory = new TextAreaFactory();
	
	
	public ShowHexaGui(Command chamador){
		this(null, "Area em Hexadecimal", DEFAULT_W, DEFAULT_H, "", "");
		this.chamador = chamador;
	}
	
	public ShowHexaGui(Command chamador, String name, int x, int y, String textoHexaEBCDIC, String textoHexaASCII){
		super();
		this.setName(name);
		if (x > 0 && y > 0) {
			super.setSize(x, y);
		} else {
			super.setSize (DEFAULT_W, DEFAULT_H);
		}

		this.chamador = chamador;
		this.textoHexaASCII  = textoHexaASCII;
		this.textoHexaEBCDIC = textoHexaEBCDIC;
		
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
		ScreenUtils.setScreenPosition(this, PositionType.MIDDLE_SCREEN);
		
		makePainelSelection();
		makeForm(textoHexaEBCDIC);
		
		this.setResizable(false);
		//this.setExtendedState(JFrame.DO_NOTHING_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setModal(true);
        this.setVisible(true);
	}
		
	private void makeForm(String textoHexa){
		
		tHexa = taFactory.makeDefaultTextArea(textoHexa, fnt);
        
        
		pPrincipal = new JScrollPane(tHexa);
		pPrincipal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		pBotoes = new JPanel();
		pBotoes.setLayout(new MigLayout("fillx, insets 1"));
		//pBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));

		tamParteVariavel = "" + (textoHexaEBCDIC.length() / 2);
		JLabel lbTamanho = new JLabel(" " + tamParteFixa + tamParteVariavel);
		lbTamanho.setForeground(new Color(41, 105, 47));
		pBotoes.add(lbTamanho, "width :100:,grow,push");
		
		//pBotoes.add(bFactory.makeButton("Cancelar", "./icons/cancel.png", new actionCancel()));
		pBotoes.add( bFactory.makeButton("OK", makeIcon("/ok.png"), new actionOK()) , "width :100:");
		
		this.add(pPrincipal, BorderLayout.CENTER);
		this.add(pBotoes, BorderLayout.SOUTH );
		
		
		KeyStroke strokeEsc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		KeyStroke strokeEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		
		pPrincipal.registerKeyboardAction(new actionOK(), strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);
		pPrincipal.registerKeyboardAction(new actionOK(), strokeEnter, JComponent.WHEN_IN_FOCUSED_WINDOW);
		tHexa.registerKeyboardAction(new actionOK(), strokeEnter, JComponent.WHEN_FOCUSED);
		
		
	}
	
	private ImageIcon makeIcon(String pathIcon){
		return new ImageIcon(this.getClass().getResource(pathIcon));
	}
	
	private void makePainelSelection(){
		
		JRadioButton ascii = new JRadioButton("ASCII");
		ascii.setSelected(false);
		ascii.setMnemonic(KeyEvent.VK_A);
		ascii.addActionListener(this);
		
		JRadioButton ebcdic = new JRadioButton("EBCDIC");
		ebcdic.setSelected(true);
		ebcdic.setMnemonic(KeyEvent.VK_E);
		ebcdic.addActionListener(this);
		
		ButtonGroup group = new ButtonGroup();
		group.add(ebcdic);
		group.add(ascii);
		
		JPanel radioPanel = new JPanel(new MigLayout());
		radioPanel.add(new JLabel("Formato dos dados:"));
		radioPanel.add(ebcdic);
		radioPanel.add(ascii);
		this.add(radioPanel, BorderLayout.NORTH);
		
	}
	
	//------------- testando ----------------------------------------
	public static void main(String args[]){
		 
		ShowHexaGui n = new ShowHexaGui(null);
		n.setVisible(true);
	}

	
	public void close() {
		this.dispose();
	}


	public String getTextBox(){
		return tHexa.getText();
	}
	
	
	public void setTextBox(String texto){
		this.tHexa.setText(texto);
	}

	//@Override
	public void actionPerformed(ActionEvent ev) {
		
		String acao = ev.getActionCommand();
		
		/*
		JOptionPane.showMessageDialog(this,
			    ev.getActionCommand(),
			    "Alerta",
			    JOptionPane.WARNING_MESSAGE);
		*/		
		
		if (acao.equalsIgnoreCase("ASCII")){
			tHexa.setText(textoHexaASCII);
			return;
		}
		
		if (acao.equalsIgnoreCase("EBCDIC")){
			tHexa.setText(textoHexaEBCDIC);
			return;
		}
		
		
	}
	
	private class actionOK implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			close();
			//chamador.execute(EasyIsoActions.REFRESH_PRINCIPAL);
		}
		
	}
	
	private class actionCancel implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			close();
			//chamador.execute(EasyIsoActions.REFRESH_PRINCIPAL);
		}
		
	}
}
