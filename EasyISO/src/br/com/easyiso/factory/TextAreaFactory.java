package br.com.easyiso.factory;

import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class TextAreaFactory {

	public JTextArea makeDefaultTextArea(String texto, Font fnt){
		JTextArea ta = new JTextArea(texto);
		ta = turnOffTAB(ta);
		ta.setLineWrap(true);
	    ta.setWrapStyleWord(true);
	    ta.setFont(fnt);
	    return ta;
	}
	
	public JTextArea makeDefaultTextArea(String texto, Font fnt, int row, int col){
		JTextArea ta = new JTextArea(texto, row, col);
		ta = turnOffTAB(ta);
		ta.setLineWrap(true);
	    ta.setWrapStyleWord(true);
	    ta.setFont(fnt);
	    return ta;
	}
	
	public JTextArea turnOffTAB(JTextArea textArea){
	  //Componente JTextArea  
	  JTextArea ta = textArea;  
	  
	  //Criando o set para o metodo setFocusTraversalKeys() - tab  
	  Set tab = new HashSet (1);   
	    
	  //Modificando o KeyStroke para avancar  
	  tab.add(KeyStroke.getKeyStroke("TAB"));   
	  
	  //Criando o set para o metodo setFocusTraversalKeys() - shifttab  
	  Set shifttab = new HashSet (1);  
	  
	  //Modificando o KeyStroke para retroceder  
	  shifttab.add(KeyStroke.getKeyStroke("shift TAB"));  
	  
	  //Modificando as propriedades de foco do componente textArea  
	  ta.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,tab);  
	  ta.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,shifttab);
	return ta;  
	}
}
