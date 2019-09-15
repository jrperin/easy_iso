package br.com.jrperin.ISO.visa;

import br.com.jrperin.ISO.mastercard.DEGeneric;
import br.com.jrperin.exceptions.IsoErrorCenter;

public class MsgVisa {

	
	private char[] buffer;
	
	private VisaHeader visaHeader;
	private char[] msgType = new char[4];
	private DEGeneric[] DE = new DEGeneric[129];
	
	//Header  hexa -  22 bytes
	//msgType hexa -   2 bytes
	//bitmap 1     -   8 bytes
	//bitmap 2     -   8 bytes
	//------------------ mensagem ISO
	
	
	/*
	 * Tipos de Campos:
	 * UP - unsigned packed (campo pode ter o tamanho expandido e o tamanho em bytes
	 * 		Currency / 
	 */
	
	
	// CONSTRUCTOR ------------------------------------------------
	public MsgVisa(char[] inBuffer) {
		
		/*
		 * Efetua tratamentos de erro para garantir que o Buffer da mensagem tem conteúdo.
		 */
		if (inBuffer == null) {

			new IsoErrorCenter(IsoErrorCenter.ErrorLevel.FATAL,
					           IsoErrorCenter.ErrorType.BAD_ISO_FORMAT,
					           "VISA - Buffer da Mensagem ISO = NULL",
					           this, -1, new Exception());
		}
		try{
			char x = inBuffer[0];
		} catch (Exception e){
			new IsoErrorCenter(IsoErrorCenter.ErrorLevel.FATAL,
			           IsoErrorCenter.ErrorType.BAD_ISO_FORMAT,
			           "VISA - Buffer da Mensagem ISO com tamanho inválido",
			           this, -1, e);
		}
		
		this.buffer = inBuffer; // <--- Incilializa a váriável atributo da classe
		
		/*
		 * Inicializa todos os Data Elementos possíveis da Mensagem ISO
		 */
		//createExpandedDataElements();
		
		/*
		 * Inicializa todos os Data Elementos possíveis da Mensagem ISO
		 */
		//expandISOMessage();
	}
}
