
package br.com.jrperin.uteis;

/**
 * Class to convert ASCII in EBCDIC and EBCDIC to ASCII 
 * @version 1.0
 * @author jrperin
 *
 * Copyright (C) 2011 Joao Roberto Perin.
 */

public class Converter{
	static private int max = 225;                                /* <-- carregado pelo inicializador no final da classe */
	static private char[][] charCode    = new   char[3][max];
	static private String[] caracter    = new String[max]   ;


	/*
	 * =============================== UTILIDADES ================================================================
	 */
	
	/** 
	* Preenche zeros a esquerda de um valor
	* @param intValue = valor a ser tratado
	* @param len = tamanho do String resultado
	* @return String representando o valor com zeros a esquerda
	* @throws -- 
	*/
	static public String fillZerosOnTheLeft(long intValue, int len){
		return fillOnTheLeft(String.valueOf(intValue), len, '0');
	}
	
	static public String fillZerosOnTheRigth(long intValue, int len){
		return fillOnTheRigth(String.valueOf(intValue), len, '0');
	}
	
	static public String fillSpacesOnTheLeft(String text, int len){
		return fillOnTheLeft(text, len, ' ');
	}
	
	static public String fillSpacesOnTheRigth(String text, int len){
		return fillOnTheRigth(text, len, ' ');
	}
	
	static public String fillOnTheLeft(String text, int len, char c){

		if (len < 1){
			return String.valueOf(c);
		}
	
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < len; i++){
			result.append(c);
		}
		result.append(text);
		return result.substring(result.length() - len, result.length());

	}
	
	static public String fillOnTheRigth(String text, int len, char c){

		if (len < 1){
			return String.valueOf(c);
		}
	
		StringBuilder result = new StringBuilder(text);
		for (int i = 0; i < len; i++){
			result.append(c);
		}
		
		return result.substring(result.length() - len, result.length());
	}
	
	
	 /** 
     * get a string from Hexa String 
     * 
     * @param String in hexa representation to will be converted in char array
     * @return char array with characters
     * Ex: String Hexa = F0F0F1F1 Returns: {?},{�},{�},{?}
     *     String Hexa = 30313930 Returns: {0},{1},{9},{0}
     */  
 	static public char[] convertHexaToCharArray(String hexa){
	    StringBuilder hexaAux = new StringBuilder("");
		char[] h1 ; //= hexa.toCharArray();
		int lenNew = 0;
		
		String validHexValues = "01234567890ABCDEFabcdef";
		
		for (int i=0; i< hexa.length(); i++){
			
			if(validHexValues.contains(hexa.substring(i, i+1))) {
			hexaAux.append(hexa.substring(i, i+1)); 
			}
			
		}
		lenNew = hexaAux.length();
		
		if (hexaAux.length() < 2){
			return "0".toCharArray() ;
		}else if ((hexaAux.length() % 2) > 0) { // --> resto da divisao (tem numero impar)
			lenNew--;
		}
		
		h1 = new char[lenNew/2];
		int j = 0;
		for (int i = 0; i < lenNew; i+=2){
			
			String auxStr = "" +  hexa.charAt(i) + hexa.charAt(i + 1);
			h1[j++] = (char) Integer.valueOf(auxStr, 16).intValue();
		}
		
		
		return h1;
	}
	
	static public byte[] convertHexaToByteArray(String hexa){
	    StringBuilder hexaAux = new StringBuilder("");
		byte[] h1 = new byte[1]; //= hexa.toByteArray();
		int lenNew = 0;
		
		String validHexValues = "01234567890ABCDEFabcdef";
		
		for (int i=0; i< hexa.length(); i++){
			
			if(validHexValues.contains(hexa.substring(i, i+1))) {
			hexaAux.append(hexa.substring(i, i+1)); 
			}
			
		}
		lenNew = hexaAux.length();
		
		if (hexaAux.length() < 2){
			h1[0]= 0x0f;
			return h1;
		}else if ((hexaAux.length() % 2) > 0) { // --> resto da divisao (tem numero impar)
			lenNew--;
		}
		
		h1 = new byte[lenNew/2];
		int j = 0;
		for (int i = 0; i < lenNew; i+=2){
			
			String auxStr = "" +  hexa.charAt(i) + hexa.charAt(i + 1);
			h1[j++] = (byte) Integer.valueOf(auxStr, 16).intValue();
		}
		
		
		return h1;
	}
	
	
	static public short[] convertHexaToShortArray(String hexa){
		char[] charBuffer = convertHexaToCharArray(hexa);
		
		short[] shortBuffer = new short[charBuffer.length];
		int i = 0;
		for (char x : charBuffer){
			shortBuffer[i++] = (short) x;
		}
		
		return shortBuffer;
	   /*
		StringBuilder hexaAux = new StringBuilder("");
		short[] h1 = new short[1]; //= hexa.toByteArray();
		int lenNew = 0;
		
		String validHexValues = "01234567890ABCDEFabcdef";
		
		for (int i=0; i< hexa.length(); i++){
			
			if(validHexValues.contains(hexa.substring(i, i+1))) {
			hexaAux.append(hexa.substring(i, i+1)); 
			}
			
		}
		lenNew = hexaAux.length();
		
		if (hexaAux.length() < 2){
			h1[0]= 0x00;
			return h1;
		}else if ((hexaAux.length() % 2) > 0) { // --> resto da divisao (tem numero impar)
			lenNew--;
		}
		
		h1 = new short[lenNew/2];
		int j = 0;
		for (int i = 0; i < lenNew; i+=2){
			
			String auxStr = "" +  hexa.charAt(i) + hexa.charAt(i + 1);
			h1[j++] = (short) Integer.valueOf(auxStr, 16).intValue();
		}
		
		
		return h1;
		*/
	}
	
	/*
	 * ================================= COMPRESSAO/DESCOMPRESSAO HEXADECIMAL =======================================
	 */
	
	 /** 
     * Compress an hexa expanded char Array into a compressed char Array
     * 
     * @param String in hexa representation to will be converted in char array
     * @return char array with characters
     * Ex: String Hexa = 30313930 Returns: {0},{1},{9},{0}
     */  
	static public char[] compressHexa(char[] hexaArray){
		
		if (hexaArray == null){
			throw new RuntimeException("Erro na compress�o do array hexa <<array == null>> [" + Converter.class + ":compressHexa]");
		}
				
		if (hexaArray.length < 2){
			throw new RuntimeException("Erro na compress�o do array hexa <<length < 2>> [" + Converter.class + ":compressHexa]");
		}
		
		if ((hexaArray.length % 2) > 0) { // --> resto da divisao (tem numero impar)
			throw new RuntimeException("Erro na compress�o do array hexa <<Tamanho IMPAR - " + hexaArray.length +
					                   ">> [" + Converter.class + ":compressHexa]");
		}
		
		
	    new StringBuilder("");
		String validHexValues = "01234567890ABCDEFabcdef";
		
		for (int i = 0; i < hexaArray.length; i++){
			
			if( !(validHexValues.contains(String.valueOf(hexaArray[i]))) ) {
				throw new RuntimeException("Erro na compress�o do array hexa <<caracter nao HEXA presente=" + String.valueOf(hexaArray) +
						">> [" + Converter.class + ":compressHexa]");
			}
			
		}
		
		char[] arrayReturn = new char[hexaArray.length/2];
		
		try{
			int j = 0;
			for (int i = 0; i < hexaArray.length; i+=2){
				arrayReturn[j++] = (char) Integer.valueOf(String.valueOf(hexaArray[i]) + String.valueOf(hexaArray[i+1]), 16).intValue();
			}
			return arrayReturn;
		}catch (Exception e){
			throw new RuntimeException("Erro na compress�o do array hexa <<" + e.getMessage() +
					">> [" + Converter.class + ":compressHexa]");
		}
	}
	
	 /** 
     * Expand an charArray to charArray Hexadecimal 
     * 
     * @param char Array to be converted
     * @return char array with hexa representation Ex.: ascii char(A) = char[0]=4 char[2] = 1 (0x41)
     */  
	
	static public char[] expandHexa(char[] charArray){
		if (charArray == null){
			throw new RuntimeException("Erro na descompress�o do array hexa <<array == null>> [" + Converter.class + ":expandHexa]");
		}
		
		if (charArray.length < 1){
			throw new RuntimeException("Erro na descompress�o do array hexa <<length < 1>> [" + Converter.class + ":expandHexa]");
		}
		try{
			char[] hexaReturn = new char[charArray.length * 2];
			int i = 0;	
			for (char x : charArray){
				String x1 = ("000" + Integer.toHexString(x));
				x1 = (x1.substring(x1.length() - 2,x1.length())).toUpperCase();
				hexaReturn[i++] = x1.charAt(0);
				hexaReturn[i++] = x1.charAt(1);
			}
			
			return hexaReturn;
		}catch (Exception e){
			throw new RuntimeException("Erro na descompress�o do array hexa " + e.getMessage() + "[" + Converter.class + ":expandHexa]");
		}
	}
	
	/*
	 * ================================= EBCDIC x ASCII =======================================
	 */

	/** 
	 * Convert Array EBCDIC to ASCII
	 * 
	 * @param ebcdicArray is sequence of characters in ebcdic to be converted in an Ascii sequence 
	 * @return Ascii array codes. 
	 */  
	static public char[] convertEbcdic2Ascii(char ... ebcdicArray){
		char[] asciiOut = new char[] {0x00}; /* if char array is null return low value */
				
		if (ebcdicArray != null && ebcdicArray.length > 0 ){
			char[] charTemp = new char[ebcdicArray.length];
			for (int i = 0; i < ebcdicArray.length; i++){
				charTemp[i] = getAsciiCode(ebcdicArray[i]);
			}
			asciiOut = charTemp;
		}

		return asciiOut;
	}

	/** 
	 * Convert Array ASCII to EBCDIC
	 * 
	 * @param ebcdicCode is a decimal number representing the ebcdic to be converted in ascii 
	 * @return a decimal number with the correspondent ascii code. 
	 */  
	static public char[] convertAscii2Ebcdic(char ... asciiArray){
		char[] ebcdicOut = new char[] {0x00}; /* if char array is null return low value */
		 
		if (asciiArray != null && asciiArray.length > 0){
			char[] charTemp = new char[asciiArray.length];
			for (int i = 0; i < asciiArray.length; i++){
				charTemp[i] = getEbcdicCode(asciiArray[i]);
			}
			ebcdicOut = charTemp;
		}
		return ebcdicOut;
	}

	/** 
	 * give the ASCII code from an EBCDIC code 
	 * 
	 * @param ebcdicCode is a decimal number representing the ebcdic character to be converted in ascii 
	 * @return a character with ascii code. 
	 * Ps.: If the character not found, has returned the input ebcdic code.
	 */  
	static public char getAsciiCode(char ebcdicCode){

		char asciiOut = 63 ; // = "?" //ebcdicCode;

		for(int i = 0; i < charCode[0].length; i++) { 
			if (ebcdicCode == charCode[0][i]){
				asciiOut = charCode[1][i];
				break;
			}
		}
		return asciiOut;
	}

	/** 
	 * give the EBCDIC code from an ASCII code 
	 * 
	 * @param asciiCode is a character number representing the ascii to be converted in ebcdic 
	 * @return a character with the correspondent ebcdic code.
	 * Ps.: If the character not found, has returned the input ascii code.
	 */  
	static public char getEbcdicCode(char asciiCode){
                  
		char ebcdicOut = 111; // = "?" //asciiCode;
                  
		for(int i = 0; i < charCode[1].length; i++ ) { 
			if (asciiCode == charCode[1][i]){
				ebcdicOut = charCode[0][i];
			}
		}

		return ebcdicOut;
	}

	/**
     * Convert 8 characters (bytes) into a 64 bits boolean
     *
     * @param char array with 8 characters (ISO map)
     *
     * @return boolean array with 64 occurs true/false (on/off)
     */
	static public boolean[] getArrayBitmap(char[] charArray){
		
		boolean[] arrayRetorno = new boolean[64];
		char char2Compare =  (char) (1  << 7);
		int bitPos = 0;
		
		if (charArray.length != 8){
			return arrayRetorno;
		}
		
		for (char x : charArray){
			for (int i = 0 ; i < 8; i++){
				arrayRetorno[bitPos++] = ((x & char2Compare) == 0 ? false : true);
				x <<= 1; 
				/* Desloca um bit para a esquerda e compara com  10000000
				 *                                               10111001
				 *                                              ----------
				 *                                               10000000 
				 */
			}
		}
		
		return arrayRetorno;
	}
	
	/**
     * Convert 64 bits boolean into a 8 characters (bytes)
     *
     * @param boolean array with 64 occurs (ISO map - ON/OFF)
     *
     * @return char array with 8 bytes
     */
	static public char[] getBitmap(boolean[] booleanArray){
		char[] arrayRetorno = new char[8];
		int bitPos = 0;
		int bytePos = 0;
		char[] base2 = new char[]  {(char)128, (char)64, (char)32, (char)16, (char)8, (char)4, (char)2, (char)1};
		
		if (booleanArray.length != 64){
			return arrayRetorno;
		}
		
		while (bitPos < 64) {
			arrayRetorno[bytePos] = 0;
			for (int i = 0; i < 8 ; i++){
				if(booleanArray[bitPos++] == true){
					arrayRetorno[bytePos] += base2[i];
				}
			}
			bytePos++;
		}
		
	return arrayRetorno;
	}

	/**
	 * Table Code Mapset Static Constructor 
	 */
	static { 

		/*
		 * Table used to convert EBCDIC into ASCII and ASCII into EBCDIC 
		 * charCode[0] = EBCDIC / charCode [1] = ASCII 
		 */

		int i = 0;

		charCode[0][i] =   0; charCode[1][i]=   0; caracter[i++] = "NUL" ;
		charCode[0][i] =   1; charCode[1][i]=   1; caracter[i++] = "SOH" ;
		charCode[0][i] =   2; charCode[1][i]=   2; caracter[i++] = "STX" ;
		charCode[0][i] =   3; charCode[1][i]=   3; caracter[i++] = "ETX" ;
		charCode[0][i] =   5; charCode[1][i]=   9; caracter[i++] = "HT"  ;
		charCode[0][i] =   7; charCode[1][i]= 127; caracter[i++] = "DEL" ;
		charCode[0][i] =  11; charCode[1][i]=  11; caracter[i++] = "VT"  ;
		charCode[0][i] =  12; charCode[1][i]=  12; caracter[i++] = "FF"  ;
		charCode[0][i] =  13; charCode[1][i]=  13; caracter[i++] = "CR"  ;
		charCode[0][i] =  14; charCode[1][i]=  14; caracter[i++] = "SO"  ;
		charCode[0][i] =  15; charCode[1][i]=  15; caracter[i++] = "OF"  ;
		charCode[0][i] =  16; charCode[1][i]=  16; caracter[i++] = "DLE" ;
		charCode[0][i] =  17; charCode[1][i]=  17; caracter[i++] = "DC1" ;
		charCode[0][i] =  18; charCode[1][i]=  18; caracter[i++] = "DC2" ;
		charCode[0][i] =  19; charCode[1][i]=  19; caracter[i++] = "DC3" ;
		charCode[0][i] =  21; charCode[1][i]=  10; caracter[i++] = "LF"  ;
		charCode[0][i] =  22; charCode[1][i]=  11; caracter[i++] = "BS"  ;
		charCode[0][i] =  24; charCode[1][i]=  24; caracter[i++] = "CAN" ;
		charCode[0][i] =  25; charCode[1][i]=  25; caracter[i++] = "EM"  ;
		charCode[0][i] =  28; charCode[1][i]=  28; caracter[i++] = "FS"  ;
		charCode[0][i] =  29; charCode[1][i]=  29; caracter[i++] = "GS"  ;
		charCode[0][i] =  30; charCode[1][i]=  30; caracter[i++] = "RS"  ;
		charCode[0][i] =  31; charCode[1][i]=  31; caracter[i++] = "US"  ;
		charCode[0][i] =  34; charCode[1][i]=  28; caracter[i++] = "FS"  ;
		charCode[0][i] =  37; charCode[1][i]=  10; caracter[i++] = "LF"  ;
		charCode[0][i] =  38; charCode[1][i]=  23; caracter[i++] = "ETB" ;
		charCode[0][i] =  39; charCode[1][i]=  27; caracter[i++] = "ESC" ;
		charCode[0][i] =  45; charCode[1][i]=   5; caracter[i++] = "ENQ" ;
		charCode[0][i] =  46; charCode[1][i]=   6; caracter[i++] = "ACK" ;
		charCode[0][i] =  47; charCode[1][i]=   7; caracter[i++] = "BEL" ;
		charCode[0][i] =  50; charCode[1][i]=  22; caracter[i++] = "SYN" ;
		charCode[0][i] =  55; charCode[1][i]=   4; caracter[i++] = "EOT" ;
		charCode[0][i] =  60; charCode[1][i]=  20; caracter[i++] = "DC4" ;
		charCode[0][i] =  61; charCode[1][i]=  21; caracter[i++] = "NAK" ;
		charCode[0][i] =  63; charCode[1][i]=  26; caracter[i++] = "SUB" ;
		charCode[0][i] =  64; charCode[1][i]=  32; caracter[i++] = " " ;
		charCode[0][i] =  66; charCode[1][i]= 226; caracter[i++] = "â" ;
		charCode[0][i] =  67; charCode[1][i]= 228; caracter[i++] = "ä" ;
		charCode[0][i] =  68; charCode[1][i]= 224; caracter[i++] = "à" ;
		charCode[0][i] =  69; charCode[1][i]= 225; caracter[i++] = "á" ;
		charCode[0][i] =  70; charCode[1][i]= 227; caracter[i++] = "ã" ;
		charCode[0][i] =  71; charCode[1][i]= 229; caracter[i++] = "å" ;
		charCode[0][i] =  72; charCode[1][i]= 231; caracter[i++] = "ç" ;
		charCode[0][i] =  73; charCode[1][i]= 240; caracter[i++] = "ñ" ;
		charCode[0][i] =  74; charCode[1][i]= 162; caracter[i++] = "¢" ;
		charCode[0][i] =  75; charCode[1][i]=  46; caracter[i++] = "." ;
		charCode[0][i] =  76; charCode[1][i]=  60; caracter[i++] = "<" ;
		charCode[0][i] =  77; charCode[1][i]=  40; caracter[i++] = "(" ;
		charCode[0][i] =  78; charCode[1][i]=  43; caracter[i++] = "0" ;
		charCode[0][i] =  79; charCode[1][i]= 124; caracter[i++] = "|" ;
		charCode[0][i] =  80; charCode[1][i]=  38; caracter[i++] = "&" ;
		charCode[0][i] =  81; charCode[1][i]= 233; caracter[i++] = "é" ;
		charCode[0][i] =  82; charCode[1][i]= 234; caracter[i++] = "ê" ;
		charCode[0][i] =  83; charCode[1][i]= 235; caracter[i++] = "ë" ;
		charCode[0][i] =  84; charCode[1][i]= 232; caracter[i++] = "è" ;
		charCode[0][i] =  85; charCode[1][i]= 237; caracter[i++] = "í" ;
		charCode[0][i] =  86; charCode[1][i]= 238; caracter[i++] = "î" ;
		charCode[0][i] =  87; charCode[1][i]= 239; caracter[i++] = "ï" ;
		charCode[0][i] =  88; charCode[1][i]= 236; caracter[i++] = "ì" ;
		charCode[0][i] =  89; charCode[1][i]= 223; caracter[i++] = "ß" ;
		charCode[0][i] =  90; charCode[1][i]=  33; caracter[i++] = "!" ;
		charCode[0][i] =  91; charCode[1][i]=  36; caracter[i++] = "$" ;
		charCode[0][i] =  92; charCode[1][i]=  42; caracter[i++] = "*" ;
		charCode[0][i] =  93; charCode[1][i]=  41; caracter[i++] = ")" ;
		charCode[0][i] =  94; charCode[1][i]=  59; caracter[i++] = ";" ;
		charCode[0][i] =  95; charCode[1][i]=  94; caracter[i++] = "^" ;
		charCode[0][i] =  96; charCode[1][i]=  45; caracter[i++] = "0" ;
		charCode[0][i] =  97; charCode[1][i]=  47; caracter[i++] = "/" ;
		charCode[0][i] =  98; charCode[1][i]= 194; caracter[i++] = "Â" ;
		charCode[0][i] =  99; charCode[1][i]= 196; caracter[i++] = "Ä" ;
		charCode[0][i] = 100; charCode[1][i]= 192; caracter[i++] = "À" ;
		charCode[0][i] = 101; charCode[1][i]= 193; caracter[i++] = "Á" ;
		charCode[0][i] = 102; charCode[1][i]= 195; caracter[i++] = "Ã" ;
		charCode[0][i] = 103; charCode[1][i]= 197; caracter[i++] = "Å" ;
		charCode[0][i] = 104; charCode[1][i]= 199; caracter[i++] = "Ç" ;
		charCode[0][i] = 105; charCode[1][i]= 209; caracter[i++] = "Ñ" ;
		charCode[0][i] = 106; charCode[1][i]= 166; caracter[i++] = "¦" ;
		charCode[0][i] = 107; charCode[1][i]=  44; caracter[i++] = "0" ;
		charCode[0][i] = 108; charCode[1][i]=  37; caracter[i++] = "%" ;
		charCode[0][i] = 109; charCode[1][i]=  95; caracter[i++] = "_" ;
		charCode[0][i] = 110; charCode[1][i]=  62; caracter[i++] = ">" ;
		charCode[0][i] = 111; charCode[1][i]=  63; caracter[i++] = "?" ;
		charCode[0][i] = 112; charCode[1][i]= 248; caracter[i++] = "ø" ;
		charCode[0][i] = 113; charCode[1][i]= 201; caracter[i++] = "É" ;
		charCode[0][i] = 114; charCode[1][i]= 202; caracter[i++] = "Ê" ;
		charCode[0][i] = 115; charCode[1][i]= 203; caracter[i++] = "Ë" ;
		charCode[0][i] = 116; charCode[1][i]= 200; caracter[i++] = "È" ;
		charCode[0][i] = 117; charCode[1][i]= 205; caracter[i++] = "Í" ;
		charCode[0][i] = 118; charCode[1][i]= 206; caracter[i++] = "Î" ;
		charCode[0][i] = 119; charCode[1][i]= 207; caracter[i++] = "Ï" ;
		charCode[0][i] = 120; charCode[1][i]= 204; caracter[i++] = "Ì" ;
		charCode[0][i] = 121; charCode[1][i]=  95; caracter[i++] = "`" ;
		charCode[0][i] = 122; charCode[1][i]=  58; caracter[i++] = ":" ;
		charCode[0][i] = 123; charCode[1][i]=  35; caracter[i++] = "#" ;
		charCode[0][i] = 124; charCode[1][i]=  64; caracter[i++] = "@" ;
		charCode[0][i] = 125; charCode[1][i]=  39; caracter[i++] = "'" ;
		charCode[0][i] = 126; charCode[1][i]=  61; caracter[i++] = "=" ;
		charCode[0][i] = 127; charCode[1][i]=  34; caracter[i++] = "\"" ;
		charCode[0][i] = 128; charCode[1][i]= 216; caracter[i++] = "Ø" ;
		charCode[0][i] = 129; charCode[1][i]=  97; caracter[i++] = "a" ;
		charCode[0][i] = 130; charCode[1][i]=  98; caracter[i++] = "b" ;
		charCode[0][i] = 131; charCode[1][i]=  99; caracter[i++] = "c" ;
		charCode[0][i] = 132; charCode[1][i]= 100; caracter[i++] = "d" ;
		charCode[0][i] = 133; charCode[1][i]= 101; caracter[i++] = "e" ;
		charCode[0][i] = 134; charCode[1][i]= 102; caracter[i++] = "f" ;
		charCode[0][i] = 135; charCode[1][i]= 103; caracter[i++] = "g" ;
		charCode[0][i] = 136; charCode[1][i]= 104; caracter[i++] = "h" ;
		charCode[0][i] = 137; charCode[1][i]= 105; caracter[i++] = "i" ;
		charCode[0][i] = 138; charCode[1][i]= 171; caracter[i++] = "«" ;
		charCode[0][i] = 139; charCode[1][i]= 187; caracter[i++] = "»" ;
		charCode[0][i] = 140; charCode[1][i]= 240; caracter[i++] = "ð" ;
		charCode[0][i] = 141; charCode[1][i]=  13; caracter[i++] = "ý" ;
		charCode[0][i] = 142; charCode[1][i]= 222; caracter[i++] = "Þ" ;
		charCode[0][i] = 143; charCode[1][i]= 177; caracter[i++] = "±" ;
		charCode[0][i] = 144; charCode[1][i]= 176; caracter[i++] = "°" ;
		charCode[0][i] = 145; charCode[1][i]= 106; caracter[i++] = "j" ;
		charCode[0][i] = 146; charCode[1][i]= 107; caracter[i++] = "k" ;
		charCode[0][i] = 147; charCode[1][i]= 108; caracter[i++] = "l" ;
		charCode[0][i] = 148; charCode[1][i]= 109; caracter[i++] = "m" ;
		charCode[0][i] = 149; charCode[1][i]= 110; caracter[i++] = "n" ;
		charCode[0][i] = 150; charCode[1][i]= 111; caracter[i++] = "o" ;
		charCode[0][i] = 151; charCode[1][i]= 112; caracter[i++] = "p" ;
		charCode[0][i] = 152; charCode[1][i]= 113; caracter[i++] = "q" ;
		charCode[0][i] = 153; charCode[1][i]= 114; caracter[i++] = "r" ;
		charCode[0][i] = 154; charCode[1][i]= 170; caracter[i++] = "ª" ;
		charCode[0][i] = 155; charCode[1][i]= 186; caracter[i++] = "º" ;
		charCode[0][i] = 156; charCode[1][i]= 230; caracter[i++] = "æ" ;
		charCode[0][i] = 157; charCode[1][i]= 184; caracter[i++] = "¸" ;
		charCode[0][i] = 158; charCode[1][i]= 198; caracter[i++] = "Æ" ;
		charCode[0][i] = 159; charCode[1][i]= 164; caracter[i++] = "¤" ;
		charCode[0][i] = 160; charCode[1][i]= 181; caracter[i++] = "µ" ;
		charCode[0][i] = 161; charCode[1][i]= 126; caracter[i++] = "~" ;
		charCode[0][i] = 162; charCode[1][i]= 115; caracter[i++] = "s" ;
		charCode[0][i] = 163; charCode[1][i]= 116; caracter[i++] = "t" ;
		charCode[0][i] = 164; charCode[1][i]= 117; caracter[i++] = "u" ;
		charCode[0][i] = 165; charCode[1][i]= 118; caracter[i++] = "v" ;
		charCode[0][i] = 166; charCode[1][i]= 119; caracter[i++] = "w" ;
		charCode[0][i] = 167; charCode[1][i]= 120; caracter[i++] = "x" ;
		charCode[0][i] = 168; charCode[1][i]= 121; caracter[i++] = "y" ;
		charCode[0][i] = 169; charCode[1][i]= 122; caracter[i++] = "z" ;
		charCode[0][i] = 170; charCode[1][i]= 161; caracter[i++] = "¡" ;
		charCode[0][i] = 171; charCode[1][i]= 191; caracter[i++] = "¿" ;
		charCode[0][i] = 172; charCode[1][i]= 208; caracter[i++] = "Ð" ;
		charCode[0][i] = 173; charCode[1][i]=  91; caracter[i++] = "[" ;
		charCode[0][i] = 174; charCode[1][i]= 254; caracter[i++] = "þ" ;
		charCode[0][i] = 175; charCode[1][i]= 174; caracter[i++] = "®" ;
		charCode[0][i] = 176; charCode[1][i]= 172; caracter[i++] = "¬" ;
		charCode[0][i] = 177; charCode[1][i]= 163; caracter[i++] = "£" ;
		charCode[0][i] = 178; charCode[1][i]= 165; caracter[i++] = "¥" ;
		charCode[0][i] = 179; charCode[1][i]= 149; caracter[i++] = "•" ;
		charCode[0][i] = 180; charCode[1][i]= 169; caracter[i++] = "©" ;
		charCode[0][i] = 181; charCode[1][i]= 167; caracter[i++] = "§" ;
		charCode[0][i] = 182; charCode[1][i]= 182; caracter[i++] = "¶" ;
		charCode[0][i] = 183; charCode[1][i]= 188; caracter[i++] = "¼" ;
		charCode[0][i] = 184; charCode[1][i]= 189; caracter[i++] = "½" ;
		charCode[0][i] = 185; charCode[1][i]= 190; caracter[i++] = "¾" ;
		charCode[0][i] = 186; charCode[1][i]= 221; caracter[i++] = "Ý" ;
		charCode[0][i] = 187; charCode[1][i]= 168; caracter[i++] = "¨" ;
		charCode[0][i] = 188; charCode[1][i]= 175; caracter[i++] = "¯" ;
		charCode[0][i] = 189; charCode[1][i]=  93; caracter[i++] = "]" ;
		charCode[0][i] = 190; charCode[1][i]= 146; caracter[i++] = "’" ;
		charCode[0][i] = 191; charCode[1][i]= 215; caracter[i++] = "×" ;
		charCode[0][i] = 192; charCode[1][i]= 123; caracter[i++] = "{" ;
		charCode[0][i] = 193; charCode[1][i]=  65; caracter[i++] = "A" ;
		charCode[0][i] = 194; charCode[1][i]=  66; caracter[i++] = "B" ;
		charCode[0][i] = 195; charCode[1][i]=  67; caracter[i++] = "C" ;
		charCode[0][i] = 196; charCode[1][i]=  68; caracter[i++] = "D" ;
		charCode[0][i] = 197; charCode[1][i]=  69; caracter[i++] = "E" ;
		charCode[0][i] = 198; charCode[1][i]=  70; caracter[i++] = "F" ;
		charCode[0][i] = 199; charCode[1][i]=  71; caracter[i++] = "G" ;
		charCode[0][i] = 200; charCode[1][i]=  72; caracter[i++] = "H" ;
		charCode[0][i] = 201; charCode[1][i]=  73; caracter[i++] = "I" ;
		charCode[0][i] = 202; charCode[1][i]= 155; caracter[i++] = "–" ;
		charCode[0][i] = 203; charCode[1][i]= 244; caracter[i++] = "ô" ;
		charCode[0][i] = 204; charCode[1][i]= 246; caracter[i++] = "ö" ;
		charCode[0][i] = 205; charCode[1][i]= 242; caracter[i++] = "ò" ;
		charCode[0][i] = 206; charCode[1][i]= 243; caracter[i++] = "ó" ;
		charCode[0][i] = 207; charCode[1][i]= 245; caracter[i++] = "õ" ;
		charCode[0][i] = 208; charCode[1][i]= 125; caracter[i++] = "}" ;
		charCode[0][i] = 209; charCode[1][i]=  74; caracter[i++] = "J" ;
		charCode[0][i] = 210; charCode[1][i]=  75; caracter[i++] = "K" ;
		charCode[0][i] = 211; charCode[1][i]=  76; caracter[i++] = "L" ;
		charCode[0][i] = 212; charCode[1][i]=  77; caracter[i++] = "M" ;
		charCode[0][i] = 213; charCode[1][i]=  78; caracter[i++] = "N" ;
		charCode[0][i] = 214; charCode[1][i]=  79; caracter[i++] = "O" ;
		charCode[0][i] = 215; charCode[1][i]=  80; caracter[i++] = "P" ;
		charCode[0][i] = 216; charCode[1][i]=  81; caracter[i++] = "Q" ;
		charCode[0][i] = 217; charCode[1][i]=  82; caracter[i++] = "R" ;
		charCode[0][i] = 218; charCode[1][i]= 185; caracter[i++] = "¹" ;
		charCode[0][i] = 219; charCode[1][i]= 251; caracter[i++] = "û" ;
		charCode[0][i] = 220; charCode[1][i]= 252; caracter[i++] = "ü" ;
		charCode[0][i] = 221; charCode[1][i]= 249; caracter[i++] = "ù" ;
		charCode[0][i] = 222; charCode[1][i]= 250; caracter[i++] = "ú" ;
		charCode[0][i] = 223; charCode[1][i]= 255; caracter[i++] = "ÿ" ;
		charCode[0][i] = 224; charCode[1][i]=  92; caracter[i++] = "\\" ;
		charCode[0][i] = 225; charCode[1][i]= 247; caracter[i++] = "÷" ;
		charCode[0][i] = 226; charCode[1][i]=  83; caracter[i++] = "S" ;
		charCode[0][i] = 227; charCode[1][i]=  84; caracter[i++] = "T" ;
		charCode[0][i] = 228; charCode[1][i]=  85; caracter[i++] = "U" ;
		charCode[0][i] = 229; charCode[1][i]=  86; caracter[i++] = "V" ;
		charCode[0][i] = 230; charCode[1][i]=  87; caracter[i++] = "W" ;
		charCode[0][i] = 231; charCode[1][i]=  88; caracter[i++] = "X" ;
		charCode[0][i] = 232; charCode[1][i]=  89; caracter[i++] = "Y" ;
		charCode[0][i] = 233; charCode[1][i]=  90; caracter[i++] = "Z" ;
		charCode[0][i] = 234; charCode[1][i]= 178; caracter[i++] = "²" ;
		charCode[0][i] = 235; charCode[1][i]= 212; caracter[i++] = "Ô" ;
		charCode[0][i] = 236; charCode[1][i]= 214; caracter[i++] = "Ö" ;
		charCode[0][i] = 237; charCode[1][i]= 210; caracter[i++] = "Ò" ;
		charCode[0][i] = 238; charCode[1][i]= 211; caracter[i++] = "Ó" ;
		charCode[0][i] = 239; charCode[1][i]= 213; caracter[i++] = "Õ" ;
		charCode[0][i] = 240; charCode[1][i]=  48; caracter[i++] = "0" ;
		charCode[0][i] = 241; charCode[1][i]=  49; caracter[i++] = "1" ;
		charCode[0][i] = 242; charCode[1][i]=  50; caracter[i++] = "2" ;
		charCode[0][i] = 243; charCode[1][i]=  51; caracter[i++] = "3" ;
		charCode[0][i] = 244; charCode[1][i]=  52; caracter[i++] = "4" ;
		charCode[0][i] = 245; charCode[1][i]=  53; caracter[i++] = "5" ;
		charCode[0][i] = 246; charCode[1][i]=  54; caracter[i++] = "6" ;
		charCode[0][i] = 247; charCode[1][i]=  55; caracter[i++] = "7" ;
		charCode[0][i] = 248; charCode[1][i]=  56; caracter[i++] = "8" ;
		charCode[0][i] = 249; charCode[1][i]=  57; caracter[i++] = "9" ;
		charCode[0][i] = 250; charCode[1][i]= 179; caracter[i++] = "³" ;
		charCode[0][i] = 251; charCode[1][i]= 219; caracter[i++] = "Û" ;
		charCode[0][i] = 252; charCode[1][i]= 220; caracter[i++] = "Ü" ;
		charCode[0][i] = 253; charCode[1][i]= 217; caracter[i++] = "Ù" ;
		charCode[0][i] = 254; charCode[1][i]= 218; caracter[i++] = "Ú" ;

		//DEBUG
		//System.out.println("Tamanho do charCode = " + (i) );

	} 

}