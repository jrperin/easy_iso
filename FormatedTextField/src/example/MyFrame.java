package example;
    import java.awt.GridLayout;  
    import javax.swing.JFrame;  
    import javax.swing.JLabel;  
    import javax.swing.JTextField;  
      
   // import camposDeTextoFormatados.JTextField4Numbers;  
    import strategy.IDocument;  
   // import strategy.concretas.DocCriadoAqui;  
    import strategy.concretas.DocLetras;  
import strategy.concretas.DocNumero;
    import strategy.concretas.DocUsuario;
      
    public class MyFrame extends JFrame {  
       private static final long serialVersionUID = 1L;  
      
       /* 
        * Estamos usando o campo de texto diretamente, mas poder�amos 
        * gerar uma nova classe que teria tipos de textos j� definidos   
        * */  
       private JTextField txt1, txt2, txt3;     
         
       /* 
        * Pode ser qualquer um documento para a filtragem! 
        *  
        * */  
       private IDocument doc;  
         
       public MyFrame() {  
          super("Exemplo: F�brica e estrat�gia");  
          setDefaultCloseOperation(EXIT_ON_CLOSE);  
          setSize(400, 150);  
          setLayout(new GridLayout(5,2));  
          setResizable(false);  
          txt1 = new JTextField();  
            
          /* 
           * Aqui est� nossa estrat�gia!! 
           * Quando instanciar o documento voc� ter� o comportamento 
           * de acordo com o tipo!  
           */  
          doc = new DocNumero(3, false);     
            
          txt1.setDocument(doc.getDocument());  
          add(new JLabel("So numeros"));  
          add(txt1);  
            
          doc = new DocLetras(5, false);     
      
          txt2 = new JTextField();  
          txt2.setDocument((doc.getDocument()));  
          add(new JLabel("So letras"));  
          add(txt2);  
      
          //Vou fazer ele escrever s� mai�sculas, para teste  
          doc = new DocUsuario("[^A-Z]", 5, false);  
          txt3 = new JTextField();        
          txt3.setDocument(doc.getDocument());  
          add(new JLabel("Sa Maiusculas"));  
          add(txt3);  
            
          //txt4 = new JTextField();  
          //Essa � classe que eu criei depois de toda a estrutura estar pronta  
          //demonstrando o poder dos padr�es em uso  
          //txt4.setDocument((new DocCriadoAqui()).getDocumentoPlano());  
          //add(new JLabel("S� Min�sculas"));  
          //add(txt4);  
            
          //txt5 = new JTextField4Numbers();  
          //add(new JLabel("Campo j� pronto(S� N�meros)"));  
          //add(txt5);     
       }  
      
       public static void main(String[] args) {  
          new MyFrame().setVisible(true);  
       }  
      
    }  