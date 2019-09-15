package br.com.jrperin.factory;

import java.awt.Font;

import javax.swing.JTextArea;

public class TextAreaFactory {

	public JTextArea makeDefaultTextArea(String texto, Font fnt){
		JTextArea ta = new JTextArea(texto);
		ta.setLineWrap(true);
	    ta.setWrapStyleWord(true);
	    ta.setFont(fnt);
	    return ta;
	}
	
	public JTextArea makeDefaultTextArea(String texto, Font fnt, int row, int col){
		JTextArea ta = new JTextArea(texto, row, col);
		ta.setLineWrap(true);
	    ta.setWrapStyleWord(true);
	    ta.setFont(fnt);
	    return ta;
	}
}
