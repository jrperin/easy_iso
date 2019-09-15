package br.com.jrperin.ISO.mastercard;

import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters.Codepage;

public class DE003 extends DEGeneric implements BitStatus{
	// CONSTRUCTORS ---------------------------------------------------------------------------------
	/** 
	* Constructor Generic DE (Data Element)
	* @param NO Parameters  
	 * @throws IsoFormatErrorException 
	*/ 
	public DE003() throws IsoFormatErrorException{
		super("null", false, 0, 0, "U", "U", "Generic Data Element");
	}

	/** 
	* Constructor DE003 (Data Element)
	* @param (int) dataID, (boolean) status, (int) lenOfLen, (int) lenMax, (String) dataType, (String) mandate, String (description)  
	 * @throws IsoFormatErrorException 
	*/  
	public DE003(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description) throws IsoFormatErrorException{
		super(dataID, status, lenOfLen, lenMax, dataType, mandate, description);
	}

	/** 
	* Constructor DE003 (Data Element)
	* @param (int) dataID, (boolean) status, (int) lenOfLen, (int) lenMax, (String) dataType,
	*        (String) mandate, String (description), (char[]) dataArea  
	 * @throws IsoFormatErrorException 
	*/  
	public DE003(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description, char[] dataArea) throws IsoFormatErrorException{
		super(dataID, status, lenOfLen, lenMax,  dataType,  mandate, description, dataArea);
	}

	/** 
	* Constructor DE003 (Data Element)
	* @param (int) dataID, (boolean) status, (int) lenOfLen, (int) lenMax, (String) dataType,
	*        (String) mandate, String (description), (char[]) dataArea  
	 * @throws IsoFormatErrorException 
	*/  
	public DE003(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description, char[] dataArea, Codepage codepage) throws IsoFormatErrorException{
		super(dataID, status, lenOfLen, lenMax,  dataType,  mandate, description, dataArea, codepage);
	}
	
	public void setData(char[] data) throws IsoFormatErrorException {
		super.setData(data);
		//this.setSubfields();

	}
	
	public void setData(char[] data, Codepage codepage) throws IsoFormatErrorException {
		super.setData(data, codepage);
		//this.setSubfields();
	}
/*
	private void setSubfields(){
		subField1 = String.copyValueOf(super.getData(), 0, 2);
		subField2 = String.copyValueOf(super.getData(), 2, 2);
		subField3 = String.copyValueOf(super.getData(), 4, 2);
	}
	
	private void validateSubField1(){
		switch (subField1){
			
		}
	};
	
	public String getSubField1(){
		return subField1;
	}
	
	public String getSubField2(){
		return subField2;
	}
	
	public String getSubField3(){
		return subField3;
	}
*/	
	
}
