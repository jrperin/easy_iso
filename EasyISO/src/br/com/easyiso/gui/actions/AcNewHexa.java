package br.com.easyiso.gui.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import br.com.easyiso.gui.EasyISOGui;
import br.com.easyiso.gui.NovoHexaGui;
import br.com.jrperin.commoninterfaces.Command;



public class AcNewHexa implements ActionListener{
	Command chamador = null;
	
	public AcNewHexa(Command chamador){
		this.chamador = chamador;
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		chamador.execute(EasyIsoActions.NEWHEXA);
	}
	


}
