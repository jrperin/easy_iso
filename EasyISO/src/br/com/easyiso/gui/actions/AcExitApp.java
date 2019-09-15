package br.com.easyiso.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.com.jrperin.commoninterfaces.Command;

public class AcExitApp implements ActionListener{

		Command whereExecCommand = null;
		
		public AcExitApp (Command whereExecCommand){
				this.whereExecCommand = whereExecCommand;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			whereExecCommand.execute(EasyIsoActions.EXIT);

		}  
        
}
