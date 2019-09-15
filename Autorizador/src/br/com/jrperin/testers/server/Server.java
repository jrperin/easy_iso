package br.com.jrperin.testers.server;



import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Server extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2070948202653179384L;


	public Server(String titulo) {

        super(titulo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
              add(new ServerWindowMain());
              super.setSize(700, 600);
              this.setVisible(true);
        } catch (Exception e) {
              JOptionPane.showMessageDialog(this, e.getMessage(),
                         "Erro no aplicativo: \n", JOptionPane.OK_OPTION);
              e.printStackTrace();
        }
        
  
			
	}
  
    
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e){
    	  
      }
      
      Server server = new Server("TCP/IP SERVER [Tester for ISO messages]");
      //server.pack();
      server.setVisible(true);
    }
}
	
	

