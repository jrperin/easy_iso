package br.com.jrperin.ISO.mastercard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters;
import br.com.jrperin.parameters.Parameters.Codepage;
import br.com.jrperin.uteis.Converter;

public class DEGeneric implements BitStatus{	
	
	// ATRIBUTES -------------------------------------------------------------------------------------------------
	protected String  dataID       = "null";
	protected boolean bitmaped     = false;
	protected boolean status       = false;
	protected int     lenOfLen     = 0;
	protected int     lenData      = 0;       //Real (actual) length of data element
	protected int     lenMax       = 0;       //Maximum length of data element
	protected String  dataType     = "U";     //Type of data element (U= undefined / B=binary / A=Alfabetic 
	                                          //                      N=Numeric / AN=Alfanumeric / ANS=Alfanumeric + Special characters)
	protected char[]  dataEbcdic        ;     //Data Area EBCDIC - Only for input/output when working with Mainframe networks
	protected char[]  dataAscii         ;     //Data Area ASCII  - The system work with that
	protected String  guiOpt       = " ";     //U=Undefined;  M=Mandatory;       C=Conditional;       O=Optional
	                                          //             ME=Mandatory Echo; CE=Conditional Echo; OE=Optional Echo
	protected String  description  = "Generic Data Element";
	
	// CONSTRUTORS -----------------------------------------------------------
	/** 
	* Constructor Generic DE (Data Element)
	* @param NO Parameters  
	* @throws IsoFormatErrorException 
	*/ 
	public DEGeneric() throws IsoFormatErrorException{
		createDE("null", false, 0, 0, "U", " ", "Generic Data Element");
	}

	/** 
	* Constructor Generic DE (Data Element)
	* @param (int) dataID, (boolean) status, (int) lenOfLen, (int) lenMax, (String) dataType, (String) guiOpt, String (description)  
	 * @throws IsoFormatErrorException 
	*/  
	public DEGeneric(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String guiOpt, String description) throws IsoFormatErrorException{
		createDE(dataID, status, lenOfLen, lenMax, dataType, guiOpt, description);
	}

	/** 
	* Constructor Generic DE (Data Element)
	* @param (int) dataID, (boolean) status, (int) lenOfLen, (int) lenMax, (String) dataType,
	*        (String) mandate, String (description), (char[]) dataArea  
	 * @throws IsoFormatErrorException 
	*/  
	public DEGeneric(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String guiOpt, String description, char[] dataArea) throws IsoFormatErrorException{
		createDE(dataID, status, lenOfLen, lenMax,  dataType,  guiOpt, description);
		setData(dataArea);
	}

	/** 
	* Constructor Generic DE (Data Element) - Forcing Character Code Page
	* @param (int) dataID, (boolean) status, (int) lenOfLen, (int) lenMax, (String) dataType,
	*        (String) guiOpt, String (description), (char[]) dataArea, [CodePage] codepage  
	 * @throws IsoFormatErrorException 
	*/  
	public DEGeneric(String dataID, boolean status, int lenOfLen, int lenMax, String dataType, String guiOpt, String description, char[] dataArea, Codepage codepage) throws IsoFormatErrorException{
		createDE(dataID, status, lenOfLen, lenMax,  dataType,  guiOpt, description);
		setData(dataArea, codepage);
	}
	
	// Methods
	public void createDE(String dataID, boolean status, int lenOfLen, int lenMax,  String dataType, String guiOpt, String description) throws IsoFormatErrorException{
		this.setDataID(dataID);
		this.setStatus(status);
		this.setLenOfLen(lenOfLen);
		this.setLenData(0);
		this.setLenMax(lenMax);
		this.setDataType(dataType);
		this.setGuiOption(guiOpt);
		this.setDescription(description);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DEGeneric other = (DEGeneric) obj;
		if (dataID == null) {
			if (other.dataID != null)
				return false;
		} else if (!dataID.equals(other.dataID))
			return false;
		return true;
	}
	
	//--------------- getters -------------------
	
	public char[] getData() {
		return getData(Parameters.systemCodepage);
	}
	
	public String getDataStr(){
		return String.valueOf(getData());
	}
	
	public char[] getData(Codepage codepage) {	
		if (codepage == Codepage.EBCDIC){
			return this.dataEbcdic;
		}else{
			return this.dataAscii;
		}
	}

	public char[] getIsoData(){
		return getIsoData(Parameters.systemCodepage);
	}
	
	public char[] getIsoData(Codepage c){
		
		String tam = "";
		char[] tamFinal = null;
		char[] resp = null;
		
		char[] data = getData(c);
		
		//DEBUG
		//System.out.println("\n" + this);
		
		if (this.lenOfLen > 0 ){
			
			tam = Converter.fillZerosOnTheLeft(this.getLenData(), this.getLenOfLen());
			if (c == Codepage.EBCDIC) {
				tamFinal  = Converter.convertAscii2Ebcdic(tam.toCharArray());
			}else{
				tamFinal  = tam.toCharArray();
			}
			
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
	
	public String getDataID() {
		return dataID;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public String getDescription() {
		return description;
	}

	public int getLenData() {
		return lenData;
	}

	public int getLenMax() {
		return lenMax;
	}

	public int getLenOfLen() {
		return lenOfLen;
	}
	
	public String getGuiOption() {
		return guiOpt;
	}
	
	public boolean isBitmaped(){
		return this.bitmaped;
	}
	
	@Override
	public int hashCode() {
		
		
		int id = 1;
		try{
			id = Integer.parseInt(dataID);
		}catch(Exception e){
		}
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataID == null) ? 0 : id);
		return result;
	}
	
	public boolean isOn() {
		return status;
	}
	
	
	//--------------- setters -------------------
	
	public void setData(String data) throws IsoFormatErrorException{
		setData(data.toCharArray());
	}
	
	public void setData(char[] data) throws IsoFormatErrorException {
		setData(data, Parameters.systemCodepage);
	}
	
	protected void setData(char[] data, Codepage c) throws IsoFormatErrorException{
		
		if (this.dataType == "B"){
			dataAscii  = data;
			dataEbcdic = data;
		}else{
			if (c == Codepage.EBCDIC) {
				dataAscii  = Converter.convertEbcdic2Ascii(data);
				dataEbcdic = data;
				this.lenData = dataEbcdic.length;
			}else{
				dataEbcdic = Converter.convertAscii2Ebcdic(data);
				dataAscii  = data;
				this.lenData = dataAscii.length;
			}
		}
		/*
		 * Sempre efetua a validação dos campos
		 */
		validateDataType();
		
	}
	
	public void setDataID(String dataID) throws IsoFormatErrorException {
		this.dataID = dataID;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
		
	public void setDescription(String description) {
		this.description = description;
	}

	public void setLenData(int lenData) {
		this.lenData = lenData;
	}

	public void setLenMax(int lenMax) {
		this.lenMax = lenMax;
	}

	public void setLenOfLen(int lenOfLen) {
		this.lenOfLen = lenOfLen;
	}
	
	public void setGuiOption(String guiOpt) {
		this.guiOpt = guiOpt;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void setBitmaped(boolean status){
		this.bitmaped = status;
	}
	
	public void uncompressMsgBuffer(MsgInBuffer msgInBuffer, Codepage inputCodepage) throws IsoFormatErrorException{
		/*
		 * Verifica se o campo possui tamanho variavel
		 */
		if (this.getLenOfLen() > 0){
			
			//Cria um array temporario para receber o tamanho do tamanho do campo
			char[] isoDataLen = msgInBuffer.getSubBufferAndMove(this.getLenOfLen()); 

			
			//define o tamanho do campo com o que foi lido da mensagem ISO
			int l = 0;
			try{
				if (inputCodepage == Codepage.EBCDIC){
					l = Integer.parseInt(String.valueOf(Converter.convertEbcdic2Ascii(isoDataLen)));
				} else {
					l = Integer.parseInt(String.valueOf(isoDataLen));
				}
			}catch (Exception e){
				
				throw new IsoFormatErrorException("MASTERCARD - Tamanho do Tamanho do Data Elemento com conteúdo não numérico ",
				           						  this, this.dataID);
			}

			
			if (l > this.getLenMax()){
				
				throw new IsoFormatErrorException("MASTERCARD - Tamanho do campo na mensagem ISO (=" + l + ") maior que o máximo especificado (=" + 
				        	this.getLenMax() + ")", this, this.dataID);
			}
			
			this.setLenData(l);
		} else {
			this.setLenData(this.getLenMax());
		}
		
		
		
		/*
		 * Efetua a leitura do campo com o tamanho determinado
		 */

		try{

			this.setData( msgInBuffer.getSubBufferAndMove(this.getLenData()), 
					inputCodepage);
			this.setStatus(true);

		}catch (ArrayIndexOutOfBoundsException e){
			throw new IsoFormatErrorException("MASTERCARD - Mensagem com tamanho menor que o esperado", this, this.dataID);
		}catch (Exception e){
			e.printStackTrace();
			throw new IsoFormatErrorException("MASTERCARD - Erro ao descompactar mensagem ISO", this, this.dataID);
		}
		
	}
	
	public String toString(){
		StringBuilder strResult = new StringBuilder();
		strResult.append("ID.....................: " + dataID  + "\n");
		
		strResult.append("Status.................: " + status      + ((status) ? " *": " ") + "\n");
		strResult.append("Length of Length ......: " + lenOfLen    + "\n");
		strResult.append("Length, data ..........: " + lenData     + "\n");
		strResult.append("Length, Maximum .......: " + lenMax      + "\n");
		strResult.append("Data Element type .....: " + dataType    + "\n");
		strResult.append("Gui Option ............: " + guiOpt      + "\n");
		strResult.append("Description ...........: " + description + "\n");
		strResult.append("Data Area EBCDIC ......: " + ((dataEbcdic == null) ? "NULL" : String.valueOf(dataEbcdic)) + "\n");
		strResult.append("Data Area ASCII .......: " + ((dataAscii  == null) ? "NULL" : String.valueOf(dataAscii )) + "\n");

		return strResult.toString();
	}
		
	private void validateDataType() {
		if (dataType == "N"){
			Pattern pattern = Pattern.compile("\\D");  
            Matcher matcher = pattern.matcher(String.valueOf(dataAscii));  
            if (matcher.find()){
            	throw new RuntimeException("MASTERCARD - Campo especificado como numerico com valor NAO NUMERICO [DE: " + 
                                            this.getDataID() + ":" + String.valueOf(this.getData()) + "]");         	
            }
		}
	}

}
