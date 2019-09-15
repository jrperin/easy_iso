package br.com.easyiso.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.jrperin.commoninterfaces.Command;



public class AcNew implements ActionListener{
	Command chamador = null;
	
	public AcNew(Command chamador){
		this.chamador = chamador;
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.chamador.execute(EasyIsoActions.NEW);
		
	}
	


}
