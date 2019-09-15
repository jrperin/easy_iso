package strategy.concretas;
    
import javax.swing.text.PlainDocument;  
import filter.DocumentFilterFactory;
import strategy.IDocument;
      
    public class DocUsuario implements IDocument {  
       PlainDocument doc;  
       String er;  
       int tamMax = 0;
       boolean uppercase = false;
       
       
       public DocUsuario(String pEr, int tamMax, boolean uppercase) {  
          this.er = pEr;  
         this.tamMax = tamMax;
         this.uppercase = uppercase;
       }  
      
       @Override  
       public PlainDocument getDocument() {  
          if (doc == null)  
             doc = new PlainDocument();  
          doc.setDocumentFilter(DocumentFilterFactory.criar(er,tamMax, uppercase));  
          return doc;  
       }  
      
    }  