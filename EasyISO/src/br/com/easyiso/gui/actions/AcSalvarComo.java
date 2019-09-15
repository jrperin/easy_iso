package br.com.easyiso.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.easyiso.gui.*;
import br.com.jrperin.commoninterfaces.Command;

public class AcSalvarComo implements ActionListener{


	Command chamador = null;
	EasyISOGui eIso = null;
	
	public AcSalvarComo(Command chamador, EasyISOGui eIso){
		this.chamador = chamador;
		this.eIso = eIso;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		chamador.execute(EasyIsoActions.SAVEAS, eIso.getMsgMastercardAtivo() );
		
	}
	


}
