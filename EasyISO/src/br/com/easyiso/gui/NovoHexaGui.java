package br.com.easyiso.gui;

import java.awt.BorderLayout;
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


public class NovoHexaGui extends JDialog implements ActionListener{
	

	//private JFrame chamador = null;
	private Command chamador = null;
	
	static final int DEFAULT_W = 400;
	static final int DEFAULT_H = 500;

	JTextArea tInput = null;
	String tInputFormated = new String();
	
	JScrollPane pPrincipal = null;
	JPanel pBotoes = null;
	
	Dimension btDim = new Dimension(130,30);
	
	Font fnt = new Font(Font.MONOSPACED, 12, 14);
	
	Codepage inputCodepage = Codepage.EBCDIC; 
	
	// --------------- FABRICAS --------------------------
	ButtonFactory   bFactory  = new ButtonFactory();
	TextAreaFactory taFactory = new TextAreaFactory();
	
	
	public NovoHexaGui(Command chamador){
		this(chamador, "Novo", DEFAULT_W, DEFAULT_H);	}
	
	public NovoHexaGui(Command chamador, String name, int x, int y){
		this.setName(name);

		this.chamador = chamador;
		
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
		
		makeForm();
		makePainelSelection();
		
		this.setResizable(false);
		//this.setExtendedState(JFrame.DO_NOTHING_ON_CLOSE);
		//this.setAlwaysOnTop(true);
		this.setModal(true);
		this.pack();
		
		tInput.requestFocus();
		if (x > 0 && y > 0) {
			this.setSize(x, y);
		} else {
			this.setSize (DEFAULT_W, DEFAULT_H);
		}
		ScreenUtils.setScreenPosition(this, PositionType.MIDDLE_SCREEN);
        this.setVisible(true);
        
	}
		
	private void makeForm(){
		
		tInput = taFactory.makeDefaultTextArea("", fnt);
		tInput.setFocusable(true);
		
        
        
		pPrincipal = new JScrollPane(tInput);
		pPrincipal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		pBotoes = new JPanel();
		pBotoes.setLayout(new MigLayout("fillx,insets 0"));

		pBotoes.add(bFactory.makeButton("Cancelar", makeIcon("/cancel.png"), new actionCancel()) , "split,right, width 100!");
		pBotoes.add(bFactory.makeButton("OK", makeIcon("/ok.png"), new actionOK()) , "width 100!");
		
		this.add(pPrincipal, BorderLayout.CENTER);
		this.add(pBotoes, BorderLayout.SOUTH );
		
		
		KeyStroke strokeEsc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		KeyStroke strokeEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		
		pPrincipal.registerKeyboardAction(new actionCancel(), strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);
		pPrincipal.registerKeyboardAction(new actionCancel(), strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);
		tInput.registerKeyboardAction(new actionOK(), strokeEnter, JComponent.WHEN_FOCUSED);
		
		
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
		 
		NovoHexaGui n = new NovoHexaGui(null);
		n.setVisible(true);
	}

	
	public void close() {
		this.dispose();
	}


	public String getTextBox(){
		return tInput.getText();
	}
	
	
	public void setTextBox(String texto){
		this.tInput.setText(texto);
	}
	
	public void setInputFormated(String texto){
		this.tInputFormated = texto;
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
			inputCodepage = Codepage.ASCII;
			return;
		}
		
		if (acao.equalsIgnoreCase("EBCDIC")){
			inputCodepage = Codepage.EBCDIC;
			return;
		}
		
		
	}
	
	private class actionOK implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String texto = tInput.getText();
			if (texto.equals("")){
				//custom title, warning icon
				JOptionPane.showMessageDialog(pPrincipal,
				    "Necessario preencher a caixa de texto.",
				    "Alerta",
				    JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			FormatadorCECI fmt = new FormatadorCECI();
			fmt.setArea(texto);
			
			try{
				chamador.execute(EasyIsoActions.NEWHEXA,  fmt.getAreaTcpIp(), inputCodepage);
			} catch(RuntimeException re) {
				JOptionPane.showMessageDialog(pPrincipal,
					    "Erro ao descompactar a mensagem ISO!!!\n",
					    "ERRO!",
					    JOptionPane.ERROR_MESSAGE);
				re.printStackTrace();
			}
			
			close();
			
		}
		
	}
	
	private class actionCancel implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			close();
		}
		
	}
}
