
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class FormataCeci extends JFrame {

 
      /**
	 * 
	 */
	private static final long serialVersionUID = -6490695938168760754L;



	public FormataCeci(String titulo) {

            super(titulo);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            try {
                  add(new JanelaPrincipal());
                  super.setSize(200, 300);
                  this.setVisible(true);
            } catch (Exception e) {

                  JOptionPane.showMessageDialog(this, e.getMessage(),
                             "Mensagem de Erro", JOptionPane.OK_OPTION);
                  e.printStackTrace();
            }
      }

 

      public static void main(String[] args) {

/*  DEBUG -------------- INICIO */
    	 //  Formatador f = new Formatador("00000A08 eb000100 f6646481 08f0a032 00000000\n" +
    	//	  	                         "00000A08 eb000100 f6646481 08f0a032 00000000\n" +
    	//	  	                         "12345678. xsfdsdfsda;^");
    	//  
    	//   System.out.print(f.getAreaSemSpaces());
    	//   System.out.println("\n--------------- output 2 ------------------------");
    	//   System.out.print(f.getAreaComSpaces());
    	//   System.out.println("\n--------------- qtd bytes -----------------------");
    	//   System.out.print("" + f.getNCaracteres());
/*  DEBUG --------------- FIM */
    	   
    	   
            // o UIManager vai pegar a aparencia do sistema:
            try{
                  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e){

            }
            
            FormataCeci fCeci = new FormataCeci("Formata CECI    -    JRPerin (17/nov/2011 - Ver 2.0)");
            fCeci.pack();
            fCeci.setVisible(true);
      }

}