package br.com.easyiso.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.easyiso.gui.*;
import br.com.jrperin.commoninterfaces.Command;

public class AcSalvar implements ActionListener{


	Command chamador = null;
	EasyISOGui eIso = null;
	
	public AcSalvar(Command chamador, EasyISOGui eIso){
		this.chamador = chamador;
		this.eIso = eIso;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		chamador.execute(EasyIsoActions.SAVE, eIso.getMsgMastercardAtivo() );
		
	}
	


}
