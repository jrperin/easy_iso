package br.com.easyiso.gui.actions;

import javax.swing.JFrame;

import br.com.easyiso.interfaces.InterForms;
import br.com.jrperin.commoninterfaces.Command;


public class AcFormCancel implements Command{
	JFrame frame = null;
	
	public AcFormCancel(JFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void execute(Object... params) {

		
		frame.dispose();

	}
	
	
}
