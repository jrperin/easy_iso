package br.com.jrperin.uteis;

import javax.swing.ImageIcon;

public class JrpUtils {
	
	public  ImageIcon getIcon(String pathIcon){
		return new ImageIcon(this.getClass().getResource(pathIcon));
	}
	
}
