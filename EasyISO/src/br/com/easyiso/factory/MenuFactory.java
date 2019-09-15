package br.com.easyiso.factory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import br.com.jrperin.commoninterfaces.Command;

public class MenuFactory {

	// ----------------------- MENU FACTORY -----------------------------------------
	
	public JMenu makeMenuItem(JMenu menu, String label){
		menu.add(new JMenuItem(label));
		return menu; 
	}
	
	public JMenu makeMenuItem(JMenu menu, String label, Icon icon){
		JMenuItem mi = new JMenuItem(label);
		mi.setIcon(icon);
		menu.add(mi);
		return menu; 
	}
	
	public JMenu makeMenuItem(JMenu menu, String label, String iconLocation){
		return makeMenuItem(menu, label, new ImageIcon(iconLocation));
	}
	
	
	public JMenu makeMenuItem(JMenu menu, String label, ActionListener ae){
		JMenuItem mi = new JMenuItem(label);
	
		mi.addActionListener(ae);
		menu.add(mi);
		return menu;
	}
	
	public JMenu makeMenuItem(JMenu menu, String label, Command command){
		JMenuItem mi = new JMenuItem(label);
		
		final Command c = command;
		
		mi.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						c.execute();
					}
				}
				);
		menu.add(mi);
		return menu;
	}
	
	public JMenu makeMenuItem(JMenu menu, String label, Icon icon , ActionListener al){
		JMenuItem mi = new JMenuItem(label);

		mi.addActionListener(al);
		mi.setIcon(icon);
		menu.add(mi);
		return menu;
		
	}
	
	public JMenu makeMenuItem(JMenu menu, String label, String iconLocation , ActionListener al){
		return makeMenuItem(menu, label, new ImageIcon(iconLocation), al);
	}
	
	
	
}
