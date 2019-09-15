package filter;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;  
import javax.swing.text.BadLocationException;  
import javax.swing.text.DocumentFilter;  

public class DocumentFilterComER extends DocumentFilter{  

	private String er;    
	private int limit = 0;
	private boolean toUppercase = false;
	

	public DocumentFilterComER(String pEr){  
		this(pEr, 0, false);  
	}

	public DocumentFilterComER(String pEr, boolean upper){
		this(pEr, 0, upper);
	}

	public DocumentFilterComER(String pEr, int limit){
		this(pEr, limit, false);
	}

	public DocumentFilterComER(String pEr, int limit, boolean upper){
		super();
		this.er = pEr;
		this.limit = limit;
		this.toUppercase = upper;
	
	}

	@Override  
	public void insertString(FilterBypass fb, int offset, String str,  
			AttributeSet attr) throws BadLocationException {
		if (str == null) return;

		if (this.er != null){
			str = str.replaceAll(this.er, "");
		}
		
		if (toUppercase){
			str = str.toUpperCase();
		}

		int thisLen = (fb.getDocument().getLength() + str.length());
	

		if (limit > 0){
			if (thisLen <= limit){
				fb.insertString(offset, str, attr);
			}else {
				if (fb.getDocument().getLength() == limit) return;

				String newStr = str.substring(0, (limit - fb.getDocument().getLength()));
				fb.insertString(offset, newStr, attr);
			}
		} else {
			fb.insertString(offset, str, attr);
		}
	}  

	@Override  
	public void replace(FilterBypass fb, int offset, int length, String str,  
			AttributeSet attr) throws BadLocationException {  

		if (str == null) return;
		
		if (this.er != null){
			str = str.replaceAll(this.er, "");
		}
		
		if (toUppercase){
			str = str.toUpperCase();
		}

		int thisLen = (fb.getDocument().getLength() + str.length());
		if (length > 0){
			thisLen -= length;
		}

		if (limit > 0){
			if (thisLen <= limit){
				fb.replace(offset, length, str, attr);
			}else {
				if (fb.getDocument().getLength() == limit) return;

				String newStr = str.substring(0, (limit - fb.getDocument().getLength()));
				fb.replace(offset, length, newStr, attr);
			}
		} else {
			fb.replace(offset, length, str, attr);
		}
	}  
}  