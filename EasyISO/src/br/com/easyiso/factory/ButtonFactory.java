package br.com.easyiso.factory;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import br.com.jrperin.commoninterfaces.Command;

public class ButtonFactory  {

	
		public JButton makeButton(String label){
			
			JButton botao = new JButton(label);
			return botao;
		}
		
		public JButton makeButton(String label, Icon icon){
			JButton button = makeButton(label); 
			button.setIcon(icon);
			return button;
			 
		}
		
		public JButton makeButton(String label, Command command){
			JButton button = makeButton(label);
			final Command c = command;
			
			button.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							c.execute();
						}
					}
					);
			return button;
		}
	
		
		public JButton makeButton(String label, Icon icon, Command command){
			JButton button = makeButton(label, command);
			button.setIcon(icon);
			return button;
		}
		
		public JButton makeButton(String label, String iconLocation){
			return makeButton(label, new ImageIcon( iconLocation));
			 
		}
		
		public JButton makeButton(String label, String iconLocation, Command command){
			return makeButton(label, new ImageIcon(iconLocation), command);
		}
		

		
		public JButton makeButton(String label, ActionListener ac){
			JButton button = makeButton(label);
			
			button.addActionListener(ac);
			return button;
		}
		
		public JButton makeButton(String label, Icon icon, ActionListener ac){
			JButton button = makeButton(label, ac);
			button.setIcon(icon);
			return button;
		}
		
		public JButton makeButton(String label, String iconLocation, ActionListener ac){
			JButton button = makeButton(label, new ImageIcon(iconLocation),ac);
			return button;
		}

		public JButton makeButton(String label, Font fnt) {
			JButton botao = new JButton(label);
			botao.setFont(fnt);
			return botao;
			
		}
}
