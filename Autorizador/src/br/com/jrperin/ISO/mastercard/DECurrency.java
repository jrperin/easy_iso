
package br.com.jrperin.ISO.mastercard;


import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters;
import br.com.jrperin.parameters.Parameters.Codepage;
import br.com.jrperin.uteis.Converter;

public class DECurrency extends DEGeneric implements BitStatus{
	protected float  currencyValue   ;
	protected long    integerValue ;
	protected String stringValue  ;
	
	/** 
	 * Constructor WITHOUT parameters (will be set do null and zeros)
	 * 
	 * @param None 
	 * @throws IsoFormatErrorException 
	 */  
	public DECurrency() throws IsoFormatErrorException{
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
	public DECurrency(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description) throws IsoFormatErrorException{
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
	public DECurrency(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description, char[] dataArea, Codepage codepage) throws IsoFormatErrorException{
		super(dataID, status, lenOfLen, lenMax, dataType, mandate, description, dataArea, codepage);
		this.setData(dataArea);
	}

	
	public void forceNumberRefresh() throws IsoFormatErrorException{
		setValue(super.getData());
	}
		
	
	//SETTERS ------------------------------------------------------------------------------------------------
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

	/** 
	 * Set the Data Area - Force an Codepage  
	 * Ex.: the data will be interpreted by code page 
	 * @param (char[]) data
	 * @return void 
	 * @throws IsoFormatErrorException 
	 */ 
	public void setData(char[] data, Codepage codepage) throws IsoFormatErrorException{
		super.setData(data, codepage);
		this.setValue(super.getData())
		;
	}
	
	
	/** 
	 * Set the numeric value of Data Element 
	 * the value must have in the default system code page
	 * @param (char[]) array character 
	 * @return void. 
	 * @throws IsoFormatErrorException 
	 */
	public void setValue(char[] data) throws IsoFormatErrorException {
		long longData = 0;
		
		try{
			//integerData = Integer.parseInt(String.valueOf(data));
			longData = Long.parseLong(String.valueOf(data));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			setValue(longData);
		} catch (NumberFormatException nfe) {
			setValue(0);
			throw new IsoFormatErrorException("MASTERCARD - Erro na formatação de valor Numérico [" + String.valueOf(data) + "]",
			           this, this.dataID);
		}
	}
	
	/** 
	 * Set the numeric value of Data Element 
	 * the value will be divided for defaultDecimalDivisor (Ex.: 1000 -> 1000 / 2 --> final value = 10.00
	 * @param (int) value 
	 * @return void. 
	 * @throws IsoFormatErrorException 
	 */  
	protected void setValue(long value) throws IsoFormatErrorException{
		integerValue = value;
		currencyValue   = (integerValue / (float)Parameters.currencyDecimalDivisor);
		stringValue  = Converter.fillZerosOnTheLeft (integerValue, this.lenMax);
		super.setData(stringValue.toCharArray(), Codepage.ASCII);
	}
	
	/** 
	 * Set the numeric value of Data Element in float 
	 * 
	 * @param (float) value 
	 * @return void. 
	 * @throws IsoFormatErrorException 
	 */  
	protected void setValue(float value) throws IsoFormatErrorException{
		currencyValue   = value;
		integerValue = (int) (value * Parameters.currencyDecimalDivisor);
		stringValue  = Converter.fillZerosOnTheLeft (integerValue, this.lenMax);
		
		super.setData(stringValue.toCharArray(), Codepage.ASCII);
	}

	//GETTERS -------------------------------------------------------------------------------------
	public float getCurrencyValue(){
		return currencyValue;
	}
	
	protected long getIntegerValue(){
		return integerValue;
	}
	
	protected String getStringValue(){
		return stringValue;
	}
	
	protected char[] getArrayValue(){
		return stringValue.toCharArray();
	}
	
		
	public String toString(){
		StringBuilder strResult = new StringBuilder(super.toString());
		
		strResult.append("   Value, Currency ....: " + currencyValue  + "\n");
		strResult.append("   Value, Integer .....: " + integerValue   + "\n");
		strResult.append("   Value, String ......: " + stringValue    + "\n");
		
		return strResult.toString();
	}
}

