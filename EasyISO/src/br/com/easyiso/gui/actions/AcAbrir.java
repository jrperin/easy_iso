package br.com.easyiso.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.easyiso.gui.*;
import br.com.jrperin.commoninterfaces.Command;

public class AcAbrir implements ActionListener{


	Command chamador = null;
	
	
	public AcAbrir(Command chamador){
		this.chamador = chamador;
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		chamador.execute(EasyIsoActions.OPEN);
		
	}
	


}
