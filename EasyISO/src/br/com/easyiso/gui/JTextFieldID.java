package br.com.easyiso.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class JTextFieldID extends JTextField{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID = -1;
	private int lenMax = 0;
	private boolean verifyLenError;
	
	public JTextFieldID(int col){
		super(col);
	}
	
	public JTextFieldID(int col, boolean verifyLenError){
		super(col);
		this.verifyLenError = verifyLenError;
	}
	
	public JTextFieldID(String str){
		super(str);
	}
	
	public JTextFieldID(String str, int col, int lenMax, boolean verifyLenError){
		super(str, col);
		this.verifyLenError = verifyLenError;
		this.lenMax = lenMax;
		this.addKeyListener(new VerifyLenError());
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	
	
	private class VerifyLenError implements KeyListener{


		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			if (verifyLenError && lenMax > 0){
				if (((JTextFieldID) ke.getSource()).getText().length() != lenMax){
					((JTextFieldID) ke.getSource()).setForeground(Color.RED);
				}else{
					((JTextFieldID) ke.getSource()).setForeground(Color.BLACK);
				}
					
			}	
		}

		@Override
		public void keyTyped(KeyEvent ke) {

			
		}
		
	}
	
	
}
