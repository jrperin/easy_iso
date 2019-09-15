package br.com.jrperin.ISO.mastercard;



/**
 * Class Parameters - All default variables of system must be here 
 * @version 1.0
 * @author jrperin
 *
 * Copyright (C) 2011 Joao Roberto Perin.
 */
public class Parameters {

	
	/* ----------------------------------------
	 *   Parameters to be used on all system 
	 * ----------------------------------------
	 */
	
	// CHARACTER CODE PAGE 
	public static enum Codepage {ASCII, EBCDIC};

	
	//Attributes -------------------------------------------------
	public static Codepage networkCodepage        ; // <- mapa de caracteres que a Mensagem ISO vem
	public static Codepage systemCodepage         ; // <- mapa de caracteres que o SISTEMA usa (se instalado no UNIX por exemplo = ASCII
	public static      int defaultDecimalDivisor  ;
	public static      int currencyDecimalDivisor ; // <- numero de casas decimais para tratamento financeiro
	public static String   systemName             ;
	
	
	//Initializing ... -------------------------------------------
	static{
		systemCodepage         = Codepage.ASCII;
		networkCodepage        = Codepage.EBCDIC;
		defaultDecimalDivisor  = 100;
		currencyDecimalDivisor = 100;
		systemName             = "AUTORIZATION SYSTEM";
	}
	
	
	
	//Methods -----------------------------------------------------------------------------------------------------	
	/** 
	 * Default System Code Page - This is used to turn the default Character Code Page between ASCII or EBCDIC (Mainframe EBCDIC) 
	 * 
	 * @param CodePage (Ex.: Parameters.Codepage.EBCDIC or ...ASCII) 
	 * @return void
	 */  
	public static void setSystemCodePage(Codepage c){
		systemCodepage = c;
	}
	
	public static Codepage getSystemCodePage(){
		return systemCodepage;
	}
	
	
	/** 
	 * Network Code Page - Has used to define what is the code page from the input transactions 
	 * 
	 * @param CodePage (Ex.: Parameters.Codepage.EBCDIC or ...ASCII) 
	 * @return void
	 */  
	public static void setNetworkCodePage(Codepage c){
		networkCodepage = c;
	}
	public static Codepage getNetworkCodepage(){
		return networkCodepage;
	}
	
	
	/** 
	 * Default Decimal Divisor - This is used to calculate the float values from ISO values 
	 * (Ex.: ISO VALUE = 000000002000 Divided for defaultDecimalDivisor = 20.00
	 * @param divisor (int) - Must be multiple of 10 (ex.: 1 ; 10 ; 100; ...) 
	 * @return void
	 */  
	public static void setDefaultDecimalDivisor(int divisor){
		defaultDecimalDivisor = divisor;
	}
	
	public static int getDefaultDecimalDivisor(){
		return defaultDecimalDivisor;
	}
	
	/** 
	 * Default Decimal Divisor - This is used to calculate the float values from ISO values 
	 * (Ex.: ISO VALUE = 000000002000 Divided for defaultDecimalDivisor = 20.00
	 * @param divisor (int) - Must be multiple of 10 (ex.: 1 ; 10 ; 100; ...) 
	 * @return void
	 */  
	public static void setCurrencyDecimalDivisor(int divisor){
		currencyDecimalDivisor = divisor;
	}
	
	public static int getCurrencytDecimalDivisor(){
		return currencyDecimalDivisor;
	}
	
}
