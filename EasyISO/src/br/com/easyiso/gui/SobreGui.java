package br.com.easyiso.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import net.miginfocom.swing.MigLayout;



import br.com.easyiso.factory.ButtonFactory;
import br.com.easyiso.factory.TextAreaFactory;
import br.com.easyiso.gui.utils.ScreenUtils;
import br.com.easyiso.gui.utils.ScreenUtils.PositionType;


public class SobreGui extends JDialog implements ActionListener{
	

	private Component chamador = null;
	
	int DEFAULT_W = 400;
	int DEFAULT_H = 500;

	JTextArea tInput = null;
	String tInputFormated = new String();
	
	JScrollPane pPrincipal = null;
	JPanel pBotoes = null;
	
	Dimension btDim = new Dimension(130,30);
	
	Font fnt = new Font(Font.MONOSPACED, 12, 14);
	
	// --------------- FABRICAS --------------------------
	ButtonFactory   bFactory  = new ButtonFactory();
	TextAreaFactory taFactory = new TextAreaFactory();
	
	
	public SobreGui(Component chamador){
		super((JFrame)chamador, "Sobre");
		super.setSize( DEFAULT_W, DEFAULT_H);
		
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
		
		ScreenUtils.setScreenPosition(this, PositionType.MIDDLE_SCREEN);
		
		makeForm();
		this.setLayout(new MigLayout());
		this.chamador = chamador;
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setModal(true);
        this.setVisible(true);
	}
	

		
	private void makeForm(){
		
		pBotoes.add(bFactory.makeButton("OK", "./icons/ok.png", this));
		this.add(pBotoes, BorderLayout.SOUTH );
		
		this.add(new JLabel("Desenvolvido por João R. Perin"), "wrap");
		this.add(new JLabel( "        fevereiro 2012       "));
		
		pBotoes = new JPanel();
		pBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));

	}
	
	public void actionPerformed(ActionEvent ev) {
		String acao = ev.getActionCommand();
		
		if (acao.equalsIgnoreCase("ok")){
			this.dispose();
		}
		
	}
	
	//------------- testando ----------------------------------------
	public static void main(String args[]){
		 
		SobreGui n = new SobreGui(null);
		n.setVisible(true);
	}

}
