package br.com.easyiso.factory;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import strategy.concretas.DocMoeda;
import strategy.concretas.DocNumero;
import strategy.concretas.DocUsuario;
import br.com.easyiso.gui.JTextFieldID;
import br.com.easyiso.gui.utils.JTextFieldLimit;

public class TextFieldFactory {

	public JTextField makeDefaultTextField(String texto, Font fnt){
		JTextField ta = new JTextField(texto);
	    ta.setFont(fnt);
	    return ta;
	}
	
	public JTextField makeDefaultTextField(String texto, Font fnt, int col){
		JTextField ta = new JTextField(texto, col);
	    ta.setFont(fnt);
	    return ta;
	}
	
	
	public JTextFieldID makeDefaultTextField(String texto, Font fnt, int col, int len, boolean verifyLenError, String expRegular, boolean uppercase){
		JTextFieldID ta = new JTextFieldID("", col, len, verifyLenError);
	    ta.setFont(fnt);
	    ta.setDocument(new DocUsuario(expRegular, len, uppercase).getDocument());
	    ta.setText(texto);
	    return ta;
	}

	public JTextFieldID makeNumericTextField(String texto, Font fnt, int col, int len, boolean verifyLenError){
		JTextFieldID tf = new JTextFieldID("", col, len, verifyLenError);
		tf.setFont(fnt);
		tf.setDocument(new DocNumero(len, false).getDocument());
		tf.setText(texto);
		return tf;
	}
	
	public JTextFieldID makeCurrencyTextField(String texto, Font fnt, int col, int len, boolean verifyLenError){
		JTextFieldID tf = new JTextFieldID("", col, len, verifyLenError);
		tf.setFont(fnt);
		tf.setDocument(new DocMoeda(len, false).getDocument());
		tf.setText(texto);
		return tf;
	}
	
}
