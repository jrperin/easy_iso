package br.com.easyiso.gui;

import javax.swing.JCheckBox;

public class JCheckBoxID extends JCheckBox{

	private int ID = -1;
	
	public JCheckBoxID(String text, boolean selected, int ID){
		super(text, selected);
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	
}
