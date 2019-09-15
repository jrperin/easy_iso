package br.com.jrperin.exceptions;

import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.jrperin.parameters.Parameters;

public class IsoErrorCenter extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8676537050104554507L;



	//public enum Action     {LOG, ISO_RETURN            };
	public enum ErrorLevel {INFO, WARNING, ERROR, FATAL  };
	public enum ErrorType  {GENERIC, BAD_ISO_FORMAT      }; 
	Calendar cal = new GregorianCalendar();
	
	public IsoErrorCenter(ErrorLevel errLevel, ErrorType errType, String errDescr, Object o, int errDataID) {
		//errDataID é o campo da Mensagem ISO que está com problema se não identificado = -1 (número um negativo)
		switch(errType){
			case BAD_ISO_FORMAT: 
				processBad_Iso_Format(0, errDataID);
				processGeneric(errLevel, errType, errDescr, o, errDataID);
				break;
	
			default:
				processGeneric(errLevel, errType, errDescr, o, errDataID);
				break;
		}	
		
		switch(errLevel){
			case ERROR:
			case FATAL:
					throw new RuntimeException(errDescr);
			default:
				break;
			
		}
	}

	public IsoErrorCenter(ErrorLevel errLevel, ErrorType errType, String errDescr, Object o, int errDataID, Exception e) {
		//errDataID é o campo da Mensagem ISO que está com problema se não identificado = -1 (número um negativo)
		this(errLevel, errType, errDescr, o, errDataID);
		throw new RuntimeException(errDescr, e);
	}

	private void processBad_Iso_Format(Object o, int errDataID){
		if(errDataID > 0){
			System.out.println("IMPLEMENTAR AQUI O RETORNO PARA A BANDEIRA DE MAL FORMATAÇÃO DO CAMPO DE39 = 30 / DE44 com o campo com erro!");
		}
	}
	
	
	
	private void processGeneric(ErrorLevel errLevel, ErrorType errType, String errDescr, Object o, int errDataID){
		System.out.printf("\n%s\n%04d/%02d/%02d - %02d:%02d:%02d -->  %s:%s: Data ID: [%03d] - Description: [%s] \n%s\n%s\n",
				"----- " + Parameters.systemName + " -----",
	            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
	            cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
	            errType, errLevel,
	            errDataID, errDescr, o.getClass(), o.toString());
	}
	
}
