package br.com.jrperin.uteis;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

public class ScreenUtils {

	public static enum PositionType{MIDDLE_SCREEN, FULL_SCREEN, HALF_SCREEN, HALF_SCREEN_MIDDLE, QUARTER_SCREEN_MIDDLE};
	
	static int scrWidth;
	static int scrHeight;
	
	public static void setScreenPosition(Window w, PositionType screenType){  
		   Toolkit tk = Toolkit.getDefaultToolkit();   
		   Dimension scrSize = tk.getScreenSize();  
		   switch(screenType){  
		      case MIDDLE_SCREEN:  
		         //this.setSize(this.sWidth,this.sHeight);  
		         scrWidth = scrSize.width - w.getWidth();   
		         scrHeight = scrSize.height - w.getHeight();  
		         w.setLocation(scrWidth / 2, scrHeight / 2);  
		         break;  
		      case FULL_SCREEN:  
		         w.setLocation(0,0);  
		         w.setSize(scrSize.width, scrSize.height);  
		         break;  
		      case HALF_SCREEN:
		    	  w.setLocation(0,0);
		    	  w.setSize(scrSize.width/2, scrSize.height);
		    	 break;
		      case HALF_SCREEN_MIDDLE:
		    	  w.setSize(scrSize.width/2, scrSize.height);
		    	  scrWidth = scrSize.width - w.getWidth();   
			      scrHeight = scrSize.height - w.getHeight();
			      w.setLocation(scrWidth / 2, 0);
		    	 break;
		      case QUARTER_SCREEN_MIDDLE:
		    	  w.setSize((scrSize.width/4) * 3, (scrSize.height/4) * 3);
		    	  scrWidth = scrSize.width - w.getWidth();   
			      scrHeight = scrSize.height - w.getHeight();
			      w.setLocation(scrWidth / 2, scrHeight / 2 );
		    	 break;
		   }  
		}  
	
}
