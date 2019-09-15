package br.com.jrperin.ISO.mastercard;

import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters;
import br.com.jrperin.parameters.Parameters.Codepage;
import br.com.jrperin.uteis.Converter;

public class DEBitmap extends DEGeneric implements BitStatus{

	private boolean[] bitmap = new boolean[64]; 
	
	//int offSet = 1;
	
	
	public DEBitmap() throws IsoFormatErrorException{
		super();
		setDataID(dataID);
	}
	
	public DEBitmap(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description) throws IsoFormatErrorException{
		super(dataID, status, lenOfLen, lenMax, dataType, mandate, description);
		super.setData(Converter.getBitmap(this.bitmap));
		//setDataID(dataID);
	}
	
	public DEBitmap(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String mandate, String description, char[] dataArea, Codepage codepage) throws IsoFormatErrorException{
		super(dataID, status, lenOfLen, lenMax, dataType, mandate, description, dataArea, codepage);
		setBitmap(dataArea);
		setData(Converter.getBitmap(this.bitmap));
		//setDataID(dataID);
	}
	
	// METHODS -----------------------------------------------------------------------------------
	private void setBitmap(char[] charArray){
		this.bitmap = Converter.getArrayBitmap(charArray);
		//setData(Converter.getBitmap(this.bitmap));
	}
	
	public void setBitmap(int id, boolean newStatus) throws IsoFormatErrorException{
		
		if (id < 1 | id > 192) {
			throw new IsoFormatErrorException("Bitmap a ser configurado fora do range [1 a 192 != " + id + "]", this, this.dataID);
		} 
		
		int offSet = 1;
		if (id > 64 & id <= 128){
			offSet = 65; 
		}else if (id > 128 & id <= 192){  /* contemplando o 3.o mapa de Bits */
			offSet = 129;
		}
		
		this.bitmap[id - offSet] = newStatus;
		setData(Converter.getBitmap(this.bitmap));
	}
	
	public void setDataID(String dataID) throws IsoFormatErrorException{
		super.setDataID(dataID);
	}
	
	public boolean[] getBitmap(){
		return this.bitmap;
	}
	
	public boolean getBitStatus(int id) throws IsoFormatErrorException{
		if (id < 1 | id > 192) {
			throw new IsoFormatErrorException("Bitmap a ser configurado fora do range [1 a 192 != " + id + "]", this, this.dataID);
		} 
		
		int offSet = 1;
		if (id > 64 & id <= 128){
			offSet = 65; 
		}else if (id > 128 & id <= 192){  /* contemplando o 3.o mapa de Bits */
			offSet = 129;
		}

		return this.bitmap[id - offSet];
	}
	
	public void setData(char[] data) throws IsoFormatErrorException{
		super.setData(data, Parameters.systemCodepage);
		//this.setBitmap(data);
	}
	
	public void setData(char[] data, Codepage codepage) throws IsoFormatErrorException{
		super.setData(data, codepage);
		this.setBitmap(data);
	}
	
	public char[] getData(){
		return Converter.getBitmap(this.getBitmap());
	}
	
	/**
	 * Essa rotina foi sobrescrita, pois um objeto do tipo binário nao pode sofrer alteracao
	 */
	public char[] getIsoData(Codepage c){
		
		String tam = "";
		char[] tamFinal = null;
		char[] resp = null;
		
		char[] data = getData();
		
		//DEBUG
		//System.out.println("\n" + this);
		
		if (this.lenOfLen > 0 ){
			tam = Converter.fillZerosOnTheLeft(this.getLenData(), this.getLenOfLen());
			tamFinal  = tam.toCharArray();
			resp = new char[tamFinal.length + data.length];
			int i = 0;
			for (int x = 0; x < tamFinal.length; x++){
				resp[i++] = tamFinal[x];
			}
			for (int x = 0; x < data.length; x++){
				resp[i++] = data[x];
			}
			return resp;
			
		}else{
			return data;
		}
		
		

	}
	
	public String toString(){
		StringBuilder strResult = new StringBuilder(super.toString());
	
		int offSet = 1;
		
		strResult.append("  ------- DEs status -------\n");
		int i = 0;
		
		for (boolean b : bitmap){
			strResult.append("  " + ("000000" + (i + offSet)).substring(("000000"+ (i + offSet)).length() - 3, ("000000" + (i + offSet)).length()) +
					            "....................: " + (b ? "1 *" : "0")      + "\n");
			i++;
		}
		return strResult.toString();
	}
	
}
