package filter;
public class DocumentFilterFactory {  
   public static final int NUMEROS = 0;  
   public static final int LETRAS  = 1;
   public static final int MOEDA   = 2;
     
   public static DocumentFilterComER criar(int tipo, int tamMax, boolean uppercase){  
      switch (tipo) {  
      case NUMEROS:  
         return new DocumentFilterComER("[^0-9]", tamMax, uppercase);           
      case LETRAS:  
         return new DocumentFilterComER("[^A-Z|^a-z]", tamMax, uppercase);    
      case MOEDA:  
          return new DocumentFilterComER("[^0-9.,+-]", tamMax, uppercase); 
      default:  
         return new DocumentFilterComER("", tamMax, uppercase);           
      }     
   }  
   
   public static DocumentFilterComER criar(String er, int tamMax, boolean uppercase){  
      return new DocumentFilterComER(er, tamMax, uppercase);     
   }  
}