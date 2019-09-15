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


public class ShowErrorGui extends JDialog implements ActionListener{
	
	static final int DEFAULT_W = 700;
	static final int DEFAULT_H = 400;

	JTextArea tInput = null;
	String tInputFormated = new String();
	
	JScrollPane pPrincipal = null;
	JPanel pBotoes = null;
	
	Font fnt = new Font(Font.MONOSPACED, 12, 14);
	
	Codepage inputCodepage = Codepage.EBCDIC; 
	
	// --------------- FABRICAS --------------------------
	ButtonFactory   bFactory  = new ButtonFactory();
	TextAreaFactory taFactory = new TextAreaFactory();
	
	public ShowErrorGui(String text){
		this("Erro!", DEFAULT_W, DEFAULT_H, text);	}
	
	public ShowErrorGui(String name, int x, int y, String text){
		this.setName(name);
		
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
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		makeForm();
		
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setModal(true);
		this.pack();
		
		tInput.setText(text);
		
		tInput.requestFocus();
		tInput.setEditable(false);
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

		pBotoes.add(bFactory.makeButton("OK", makeIcon("/ok.png"), new actionOK()) , "split,right, width :100:");
		
		this.add(pPrincipal, BorderLayout.CENTER);
		this.add(pBotoes, BorderLayout.SOUTH );
		
		KeyStroke strokeEsc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		KeyStroke strokeEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		
		pPrincipal.registerKeyboardAction(new actionCancel(), strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);
		pPrincipal.registerKeyboardAction(new actionOK(), strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);
		tInput.registerKeyboardAction(new actionCancel(), strokeEnter, JComponent.WHEN_FOCUSED);
		tInput.registerKeyboardAction(new actionOK(), strokeEnter, JComponent.WHEN_FOCUSED);

	}
	
	private ImageIcon makeIcon(String pathIcon){
		return new ImageIcon(this.getClass().getResource(pathIcon));
	}

	//------------- testando ----------------------------------------
	public static void main(String args[]){
		 
		ShowErrorGui n = new ShowErrorGui("Teste 123");
		n.setVisible(true);
	}

	public void close() {
		this.setVisible(false);
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

	}
	
	private class actionOK implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
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
