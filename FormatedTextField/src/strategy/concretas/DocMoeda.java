package strategy.concretas;
    
import javax.swing.text.PlainDocument;  

import filter.DocumentFilterFactory;
import strategy.IDocument;
      
    public class DocMoeda implements IDocument {  
       PlainDocument doc;  
      int tamMax = 0;
      boolean uppercase = false;
       
       public DocMoeda(int tamMax, boolean uppercase) {  
           super();  
           this.tamMax = tamMax;
           this.uppercase = uppercase;
        }
       
       @Override  
       public PlainDocument getDocument() {  
          if (doc == null)  
             doc = new PlainDocument();  
          doc.setDocumentFilter(DocumentFilterFactory  
                .criar(DocumentFilterFactory.MOEDA, tamMax, uppercase));  
          return doc;  
       }  
    }  