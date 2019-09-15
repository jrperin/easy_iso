package br.com.jrperin.tcpserver.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

import br.com.easyiso.factory.ButtonFactory;
import br.com.easyiso.gui.utils.ScreenUtils;
import br.com.jrperin.commoninterfaces.Command;
import br.com.jrperin.uteis.Converter;

public class TcpipServerGui extends JFrame implements ActionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextArea   txConsole = null;
	JScrollPane pnCentro  = null;
	
	JPanel      pnRodape  = new JPanel();
	JButton     btSair    = null;
	JButton     btStart   = null;
	JLabel      lbStatus  = null;
	JTextField  txPorta   = null;
	JCheckBox   cbEcho    = null;
	
	JComboBox   cbType    = null;
	JComboBox   cbLen     = null;
	
	
	ImageIcon   stDesconectado = new ImageIcon(this.getClass().getResource("/desconectado.png"));
	ImageIcon   stConectando   = new ImageIcon(this.getClass().getResource("/conectando.png"));
	ImageIcon   stConectado    = new ImageIcon(this.getClass().getResource("/conectado.png"));
	
	ServerSocket server = null;
	Socket       socket = null;
	
	private Command chamador;
	private Font fnt = new Font("Courier", 12, 12);
	
	public TcpipServerGui(String name, Command chamador){
		super(name);
		
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ScreenUtils.setScreenPosition(this, 5, 14, 10, 11, 1);
		
		this.setLayout(new BorderLayout());
		
		this.chamador = chamador;
		
		txConsole = new JTextArea();
		txConsole.setFont(fnt);
		txConsole.setLineWrap(true);
		txConsole.setEditable(false);
		
		
		pnCentro  = new JScrollPane(txConsole);
		pnCentro.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnCentro.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		cbEcho  = new JCheckBox("Responder");
		cbEcho.setToolTipText("Responde a própria mensagem de entrada (ECO).");
		cbEcho.setSelected(false);
		cbEcho.addActionListener(this);
		
		txPorta = new JTextField("3000", 6);

		btStart = (new ButtonFactory()).makeButton("Iniciar", this);
		lbStatus = new JLabel(stDesconectado);
		
		pnRodape.setLayout(new MigLayout("fillx, insets 5"));
		
		String[] strTypes = {"EBCDIC","ASCII" };
		cbType = new JComboBox(strTypes);
        cbType.setEditable(false);
        //cbType.setFont(fnt);
        
        String[] strLen = {"4","6"};
        cbLen = new JComboBox(strLen);
        cbLen.setEditable(false);
        //cbLen.setFont(fnt);
        
        JPanel pnTipo = new JPanel();
        pnTipo.add(new JLabel("Tipo da mensagem:"));
        pnTipo.add(cbType);
        
        JPanel pnLen = new JPanel();
        pnLen.add(new JLabel("Tamanho do header:"));
        pnLen.add(cbLen);
        
        
        pnRodape.add(pnTipo, "spanx 2,push");
        pnRodape.add(pnLen, "spanx 2, wrap");
        
        
		pnRodape.add(new JLabel(" JRPerin Copyright (C) 2012 "), "w 80:180:, push");
		pnRodape.add(cbEcho, "w 100:110:");
		pnRodape.add(lbStatus,"width 15!");
		pnRodape.add(new JLabel("Porta:"), "width 30:40:");
		pnRodape.add(txPorta, "width 40:50:");
		pnRodape.add(btStart, " width 100:100:");
		//pnRodape.add(btSair, "width :100:");
		
		this.setJMenuBar(createMenu());
		
		this.add(pnCentro, BorderLayout.CENTER);
		this.add(pnRodape, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	private JMenuBar createMenu(){
		JMenuBar menubar = new JMenuBar();
		
		JMenu menuArquivo = new JMenu("Arquivo");
		menuArquivo.setMnemonic(KeyEvent.VK_A);
		
		JMenu menuJanela  = new JMenu("Janela");
		menuJanela.setMnemonic(KeyEvent.VK_J);
		
		JMenu menuAjuda  = new JMenu("Ajuda");
		menuAjuda.setMnemonic(KeyEvent.VK_D);
		
		
		JMenuItem sair = new JMenuItem("Sair");
		sair.setMnemonic(KeyEvent.VK_R);
		sair.addActionListener(this);
		
		JMenuItem limparLog = new JMenuItem("Limpar Log");
		limparLog.setMnemonic(KeyEvent.VK_P);
		limparLog.addActionListener(this);
		
		JMenuItem sobre = new JMenuItem("Sobre");
		sobre.setMnemonic(KeyEvent.VK_S);
		sobre.addActionListener(this);
		
		menubar.add(menuArquivo);
		menuArquivo.add(sair);
		
		menubar.add(menuJanela);
		menuJanela.add(limparLog);
		
		menubar.add(menuAjuda);
		menuAjuda.add(sobre);
		return menubar;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equalsIgnoreCase("sair")){
			System.exit(0);
		}

		if(arg0.getActionCommand().equalsIgnoreCase("Limpar Log")){
			this.txConsole.setText("");
		}
		
		if(arg0.getActionCommand().equalsIgnoreCase("sobre")){
			JOptionPane.showMessageDialog(this,
					"Desenvolvido por:\nJoao Roberto Perin - mar/2012\nversao Beta 1" ,
					"Sobre",
					JOptionPane.PLAIN_MESSAGE);
		}

		if (arg0.getSource() == btStart){
			if (arg0.getActionCommand().equalsIgnoreCase("iniciar")){
				
				int len = Integer.parseInt(cbLen.getSelectedItem().toString());
				String tipo = String.valueOf(((cbType.getSelectedItem().toString()).charAt(0)));
				int porta = Integer.parseInt(txPorta.getText());
				
				chamador.execute("startServer", porta, len, tipo);
			} else{
				chamador.execute("stopServer");

			}
		}
		
		if (arg0.getSource() == cbEcho){
			chamador.execute("echo", cbEcho.isSelected());
		}
	}

	public void setConectionStatus (int conStatus){
		switch (conStatus){
		case 0:
			atualizaStatus(stDesconectado, "Iniciar");
			break;
		case 1:
			atualizaStatus(stConectando, "Parar");
			break;
		case 2:
			atualizaStatus(stConectado, "Parar");
			break;
		}


	}

	private void atualizaStatus (ImageIcon icon, String text){
		final ImageIcon icone = icon;
		final String    texto  = text;
		
		lbStatus.setIcon(icone);
		btStart.setText(texto);

	}
	
	public void writeLog(Object ... arg){
		StringBuilder strAux  = new StringBuilder();
		StringBuilder strAux2 = new StringBuilder();
		int len = 0;
		char[] cAux = null;
		
		if(arg[1] instanceof List){
			len = ((List) arg[1]).size();
			 cAux = new char[len];
			int i = 0;
			Iterator<Character> it = ((List)  arg[1]).iterator();
			while(it.hasNext()){
				Character c = it.next();
				strAux.append(c);
				cAux[i++] = c;
			}
		}
		
		strAux2.append(String.valueOf(Converter.expandHexa(cAux)));
		strAux2.append("\n");
		
		Calendar cal = new GregorianCalendar();
		String newText = String.format("\n[%04d/%02d/%02d - %02d:%02d:%02d] %s %s\n%s", 
						 cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
						 cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
						 (String) arg[0],strAux, strAux2.toString());
				
		String textBefore = this.txConsole.getText();
		textBefore +=  newText;
		this.txConsole.setText(textBefore);
	}
	
}
