package br.com.easyiso.gui.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

public class ScreenUtils {

	public static enum PositionType{MIDDLE_SCREEN, FULL_SCREEN, HALF_SCREEN, HALF_SCREEN_MIDDLE, QUARTER_SCREEN, QUARTER_SCREEN_MIDDLE,
		                            ONE_PART_SCREEN, TWO_PART_SCREEN, THREE_PART_SCREEN, FOUR_PART_SCREEN, FIVE};
	
	static int scrWidth;
	static int scrHeight;
	static int winTopX;
	static int winTopY;
	static int winWidth;
	static int winHeight;
	
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
		      case QUARTER_SCREEN:
		    	  w.setSize((scrSize.width/4) * 3, scrSize.height);
		    	  scrWidth = scrSize.width - w.getWidth();   
			      scrHeight = scrSize.height - w.getHeight();
			      w.setLocation(0, 0);
		    	 break;
		      case QUARTER_SCREEN_MIDDLE:
		    	  w.setSize((scrSize.width/4) * 3, (scrSize.height/4) * 3);
		    	  scrWidth = scrSize.width - w.getWidth();   
			      scrHeight = scrSize.height - w.getHeight();
			      w.setLocation(scrWidth / 2, scrHeight / 2 );
		    	 break;
		   }
		    	 
		    
		}
		   
		public static void setScreenPosition(Window w, int partsOfX, int allPartsX, int partsOfY, int allPartsY, int location ){  
				   Toolkit tk = Toolkit.getDefaultToolkit();   
				   Dimension scrSize = tk.getScreenSize();  

				   //para utilizar fracao da tela: allPartsX = 8 e partsOfX = 7 ==> 7/8 da tela ser� o tamanho em X
				   if (partsOfX != 0 && allPartsX != 0 && partsOfY != 0 && allPartsY != 0){
					   
					   
					   winWidth  = (scrSize.width / allPartsX) * partsOfX;
					   winHeight = (scrSize.height / allPartsY) * partsOfY;
					   winTopX = scrSize.width - winWidth;
					   winTopY = scrSize.height - winHeight;
					   
					   w.setSize(winWidth, winHeight );
					   
					   if (location == 0){
						   w.setLocation(0, 0);
					   } else {
						   w.setLocation(winTopX / 2, winTopY / 2);
					   }
				   }
				   

		   
		}  
	
}
