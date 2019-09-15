package br.com.jrperin.ISO.mastercard;

import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters.Codepage;
import br.com.jrperin.uteis.Converter;

public class DENumeric extends DEGeneric implements BitStatus{

	private int    integerValue ;
	private String stringValue  ;
	
	/** 
	 * Constructor WITHOUT parameters (will be set do null and zeros)
	 * 
	 * @param None 
	 * @throws IsoFormatErrorException 
	 */  
	public DENumeric() throws IsoFormatErrorException{
		super();
		this.setValue(0);
	}
	
	/** 
	 * Constructor initialize MOST values excluding DATA AREA
	 * 
	 * @param dataID (int), status (boolean), lenOfLen (int), lenMax (int), dataType (String), mandate (String), description (String)
	 *  (the values will be set to zeros)  
	 * @throws IsoFormatErrorException 
	 */  
	public DENumeric(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description) throws IsoFormatErrorException{
		super(dataID, status, lenOfLen, lenMax, dataType, mandate, description);
		this.setValue(0);
	}

	/** 
	 * Constructor to initialize ALL values
	 * 
	 * @param ebcdicArray is sequence of characters in ebcdic to be converted in an Ascii sequence 
	 * @return Ascii array codes. 
	 * @throws IsoFormatErrorException 
	 */  
	public DENumeric(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description, char[] dataArea) throws IsoFormatErrorException{
		super(dataID, status, lenOfLen, lenMax, dataType, mandate, description, dataArea);
		this.setData(dataArea);
	}

	
	/** 
	 * Constructor to initialize ALL values
	 * 
	 * @param ebcdicArray is sequence of characters in ebcdic to be converted in an Ascii sequence 
	 * @return Ascii array codes. 
	 * @throws IsoFormatErrorException 
	 */  
	public DENumeric(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description, char[] dataArea, Codepage codepage) throws IsoFormatErrorException{
		super(dataID, status, lenOfLen, lenMax, dataType, mandate, description, dataArea, codepage);
		this.setData(dataArea, codepage);
	}

	
	public void forceNumberRefresh() throws NumberFormatException, IsoFormatErrorException{
		setValue(super.getData());
	}
		
	
	//GETTERS & SETTERS ------------------------------------------------------------------------------------------------
	/** 
	 * Set the Data Area - Will use the default code page from the class Parameters  
	 * Ex.: If a charArray has passed and the default code page = EBCDIC the 
	 * @param (char[]) data
	 * @return void 
	 * @throws IsoFormatErrorException 
	 */  
	public void setData(char[] data) throws IsoFormatErrorException{
		super.setData(data);
		this.setValue(super.getData());		
	}

	public void setData(String data) throws IsoFormatErrorException{
		if (data == null){
			data = "0";
		}
		this.setData(data.toCharArray());
	}
	
	/** 
	 * Set the Data Area - Force an Codepage  
	 * Ex.: the data will be interpreted by code page 
	 * @param (char[]) data
	 * @return void 
	 * @throws IsoFormatErrorException 
	 */ 
	public void setData(char[] data, Codepage codepage) throws IsoFormatErrorException{
		super.setData(data, codepage);
		this.setValue(super.getData());
	}
	
	
	/** 
	 * Set the numeric value of Data Element 
	 * the value must have in the default system code page
	 * @param (char[]) array character 
	 * @return void. 
	 * @throws IsoFormatErrorException 
	 * @throws NumberFormatException 
	 */
	public void setValue(char[] data) throws NumberFormatException, IsoFormatErrorException{
		setValue(Integer.parseInt(String.valueOf(data)));
	}
	
	/** 
	 * Set the numeric value of Data Element 
	 * the value will be divided for defaultDecimalDivisor (Ex.: 1000 -> 1000 / 2 --> final value = 10.00
	 * @param (int) value 
	 * @return void. 
	 * @throws IsoFormatErrorException 
	 */  
	protected void setValue(int value) throws IsoFormatErrorException{
		integerValue = value;
		
		stringValue  = Converter.fillZerosOnTheLeft (value, this.lenMax);
		super.setData(stringValue.toCharArray(), Codepage.ASCII);
	}

	
	public String toString(){
		StringBuilder strResult = new StringBuilder(super.toString());
		
		strResult.append("   Value, Integer .....: " + integerValue   + "\n");
		strResult.append("   Value, String ......: " + stringValue    + "\n");
		
		return strResult.toString();
	}
	
}
