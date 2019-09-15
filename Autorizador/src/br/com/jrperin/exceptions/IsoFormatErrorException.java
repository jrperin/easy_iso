package br.com.jrperin.exceptions;

import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.jrperin.parameters.Parameters;

public class IsoFormatErrorException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String errorMessage = "";
	String errorDE = "null";
	Calendar cal = new GregorianCalendar();
	
	
	public IsoFormatErrorException(String errorMessage, Object o){
		super(errorMessage);
		setErrorMessage(errorMessage, o, "null");
		errorDE = "null";
	}
	
	public IsoFormatErrorException(String errorMessage, Object o, String dataID){
		super(errorMessage);
		setErrorMessage(errorMessage, o, dataID);
		errorDE = dataID;
	}
	
	/*
	private void setErrorMessage(int dataID, Object obj, String errorMessage){
		Calendar cal = new GregorianCalendar();
		
	
	 *** Poderia ser trocada por uma log de ERRO, ou enviar para uma fila de monitoração.
	
		System.out.printf("%04d/%02d/%02d - %02d:%02d:%02d -->  Data ID (%03d) - Erro: [%s] \n%s \n",
				            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
				            cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
				            dataID, errorMessage, obj.toString());
	}
	 */
	
	private void setErrorMessage(String errDescr, Object o, String dataID){
		
		errorMessage = String.format("%s\n%04d/%02d/%02d - %02d:%02d:%02d --> Data ID: [%s] - Description: [%s] \n%s\n%s\n",
									"----- " + Parameters.systemName + " -----",
									cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
									cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
									dataID, errDescr, o.getClass(), o.toString());
	}


	public String getErrorMessage(){
		return this.errorMessage;
	}
	
	public String getErrorDE(){
		return this.errorDE;
	}
	
}
