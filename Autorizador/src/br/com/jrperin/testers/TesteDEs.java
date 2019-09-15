package br.com.jrperin.testers;

import java.io.IOException;
import org.jdom.JDOMException;

import br.com.jrperin.ISO.mastercard.DEBitmap;
import br.com.jrperin.ISO.mastercard.DEGeneric;
import br.com.jrperin.ISO.mastercard.MsgMastercard;
import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters.Codepage;
import br.com.jrperin.uteis.Converter;


public class TesteDEs {
	
	
	public static void main (String args[]) {
		
		//"".toCharArray();
		//null;	
		//convertHexaToCharArray("F0F1F0F0F2380000000000040000000000010000F1F6F1F2F3F4F5F6F7F8F9F0F1F2F3F4F5F6F0F0F9F0F0F0F0F0F0F0F0F0F0F0F1F2F3F4f9f9f9f9f9f9f9f9f9f9F1F2F1F2F1F2F1F0F0F0F0F0F1F1F2F0F0F1F6C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1F0F1F8D1D6C1D640D9D6C2C5D9E3D640D7C5D9C9D5");
		char[] bufferISO =		
		Converter.convertHexaToCharArray("F0F1F0F0FEE7440188E1E00A0000000000000100F1F6F5F3F9F0F5F9F8F6F0F3F4F4F5F5F0F3F0F0F0F0F0F0F0F0F0F0F0F0F0F0F1F4F5F7F0F0F0F0F0F0F0F0F0F8F1F3F0F0F0F0F0F0F0F0F1F4F5F7F1F2F0F7F1F3F1F5F0F0F5F5F5F7F8F4F8F9F6F1F0F0F0F0F0F0F3F8F0F3F9F5F1F3F0F6F1F2F0F7F1F2F0F7F4F8F1F4F8F1F2F0F6F0F1F1F4F2F9F0F6F0F0F1F2F9F7F0F2F2F0F2F4F4F8F0F4F3F8F0F0F0F0F0F0F0F1F0F0F0F9F8F0F0F2F0F3F0F5F9F9F4D7C1E8D7C1D3405C32D2E8D7C540404040404040404040F3F5F3F1F4F3F6F9F0F0F1404040D3E4E7F0F2F7E3F8F2F0F2F5F2F4F2F0F7F0F1F0F3F2F1F0F6F1F0F5F0F0F0F0F0F9F8F6F8F4F0F9F8F6F0F2F6F0F0F2F5F1F0F0F0F0F6F6F0F0F4F4F2D3F1F5F2F04040404040F0F0F9D4C3E2F8E8C2E4C2D1F0F3F3F0F1F2F9F3F1F1F1F0F0F5F040D9A48140D18183A48940F6F640829340F4408197");
		
		/* area abaixo gerada pelo Area Hexadecimal do EasyIso */
		//Converter.convertHexaToCharArray("F0F1F0F0AE48C4016F4544250000000000000100F1F6F5F3F9F0F5F9F8F6F0F3F4F4F5F5F0F3F0F0F0F0F0F0F0F0F0F0F0F0F0F0F1F4F5F7F0F0F0F0F0F0F0F0F0F8F1F3F0F0F0F0F0F0F0F0F1F4F5F7F1F2F0F7F1F3F1F5F0F0F5F5F5F7F8F4F8F9F6F1F0F0F0F0F0F0F3F8F0F3F9F5F1F3F0F6F1F2F0F7F1F2F0F7F4F8F1F4F8F1F2F0F6F0F1F1F4F2F9F0F6F0F0F1F2F9F7F0F2F2F0F2F4F4F8F0F4F3F8F0F0F0F0F0F0F0F1F0F0F0F9F8F0F0F2F0F3F0F5F9F9F4D7C1E8D7C1D3405C32D2E8D7C540404040404040404040F3F5F3F1F4F3F6F9F0F0F1404040D3E4E7F0F2F7E3F8F2F0F2F5F2F4F2F0F7F0F1F0F3F2F1F0F6F1F0F5F0F0F0F0F0F9F8F6F8F4F0F9F8F6F0F2F6F0F0F2F5F1F0F0F0F0F6F6F0F0F4F4F2D3F1F5F2F04040404040F0F0F9D4C3E2F8E8C2E4C2D1F0F3F3F0F1F2F9F3F1F1F1F0F0F5F040D9A48140D18183A48940F6F640829340F4408197");
		//Abaixo, tamanho maior que o permitido bit 02 com tamanho de 26 ao invés de 19(max)
		//Converter.convertHexaToCharArray("F0F1F0F0FEE7440188E1E00A0000000000000100F2F0F5F3F9F0F5F9F8F6F0F3F4F4F5F5F0F3F0F0F0F0F0F0F0F0F0F0F0F0F0F0F1F4F5F7F0F0F0F0F0F0F0F0F0F8F1F3F0F0F0F0F0F0F0F0F1F4F5F7F1F2F0F7F1F3F1F5F0F0F5F5F5F7F8F4F8F9F6F1F0F0F0F0F0F0F3F8F0F3F9F5F1F3F0F6F1F2F0F7F1F2F0F7F4F8F1F4F8F1F2F0F6F0F1F1F4F2F9F0F6F0F0F1F2F9F7F0F2F2F0F2F4F4F8F0F4F3F8F0F0F0F0F0F0F0F1F0F0F0F9F8F0F0F2F0F3F0F5F9F9F4D7C1E8D7C1D3405C32D2E8D7C540404040404040404040F3F5F3F1F4F3F6F9F0F0F1404040D3E4E7F0F2F7E3F8F2F0F2F5F2F4F2F0F7F0F1F0F3F2F1F0F6F1F0F5F0F0F0F0F0F9F8F6F8F4F0F9F8F6F0F2F6F0F0F2F5F1F0F0F0F0F6F6F0F0F4F4F2D3F1F5F2F04040404040F0F0F9D4C3E2F8E8C2E4C2D1F0F3F3F0F1F2F9F3F1F1F1F0F0F5F040D9A48140D18183A48940F6F640829340F4408197");
		
		//Abaixo campo numérico (DE002) com conteúdo nao numerico
		//convertHexaToCharArray("F0F1F0F0FEE7440188E1E00A0000000000000100F1F6F540F9F0F5F9F8F6F0F3F4F4F5F5F0F3F0F0F0F0F0F0F0F0F0F0F0F0F0F0F1F4F5F7F0F0F0F0F0F0F0F0F0F8F1F3F0F0F0F0F0F0F0F0F1F4F5F7F1F2F0F7F1F3F1F5F0F0F5F5F5F7F8F4F8F9F6F1F0F0F0F0F0F0F3F8F0F3F9F5F1F3F0F6F1F2F0F7F1F2F0F7F4F8F1F4F8F1F2F0F6F0F1F1F4F2F9F0F6F0F0F1F2F9F7F0F2F2F0F2F4F4F8F0F4F3F8F0F0F0F0F0F0F0F1F0F0F0F9F8F0F0F2F0F3F0F5F9F9F4D7C1E8D7C1D3405C32D2E8D7C540404040404040404040F3F5F3F1F4F3F6F9F0F0F1404040D3E4E7F0F2F7E3F8F2F0F2F5F2F4F2F0F7F0F1F0F3F2F1F0F6F1F0F5F0F0F0F0F0F9F8F6F8F4F0F9F8F6F0F2F6F0F0F2F5F1F0F0F0F0F6F6F0F0F4F4F2D3F1F5F2F04040404040F0F0F9D4C3E2F8E8C2E4C2D1F0F3F3F0F1F2F9F3F1F1F1F0F0F5F040D9A48140D18183A48940F6F640829340F4408197");
		
		char[] bufferIsoVisa =
		//MENSAGEM VISA
		 Converter.convertHexaToCharArray("16010201054256072112200120004A000000091754000100F664648128F1A0360000000000000004104463053150121011000000000000045850000000045850120721150061000000070115150854110076901000064736632504463053150121011D15085011672025211360F0F3F4F1F2F1F0F7F0F1F1F5F4F2F1F7F6F8F1F5F0F1F0F0F0F0F6F5F8F4F4F0F0F0F24040404040404040C2D6D440C4D9C140E4D440404040404040C3C1D5D6C9D5C8C1E240404040C2D90540404040F2085CE3F1C5E3E2C540098609860EF0F0F0F0F0F8F9F4F6F0F0F0F0F00375000811C000000000000000C500013417650064670580000000020E0040000000000000F1F140F8F4F1");

		
		/* MASTERCARD
		   F0F1F0F0 FEE74401 88E1E00A 00000000 00000100
		   F1F6F5F3 F9F0F5F9 F8F6F0F3 F4F4F5F5 F0F3F0F0
		   F0F0F0F0 F0F0F0F0 F0F0F0F0 F1F4F5F7 F0F0F0F0
		   F0F0F0F0 F0F8F1F3 F0F0F0F0 F0F0F0F0 F1F4F5F7
		   F1F2F0F7 F1F3F1F5 F0F0F5F5 F5F7F8F4 F8F9F6F1
		   F0F0F0F0 F0F0F3F8 F0F3F9F5 F1F3F0F6 F1F2F0F7
		   F1F2F0F7 F4F8F1F4 F8F1F2F0 F6F0F1F1 F4F2F9F0
		   F6F0F0F1 F2F9F7F0 F2F2F0F2 F4F4F8F0 F4F3F8F0
		   F0F0F0F0 F0F0F1F0 F0F0F9F8 F0F0F2F0 F3F0F5F9
		   F9F4D7C1 E8D7C1D3 405C32D2 E8D7C540 40404040
		   40404040 40F3F5F3 F1F4F3F6 F9F0F0F1 404040D3
		   E4E7F0F2 F7E3F8F2 F0F2F5F2 F4F2F0F7 F0F1F0F3
		   F2F1F0F6 F1F0F5F0 F0F0F0F0 F9F8F6F8 F4F0F9F8
		   F6F0F2F6 F0F0F2F5 F1F0F0F0 F0F6F6F0 F0F4F4F2
		   D3F1F5F2 F0404040 4040F0F0 F9D4C3E2 F8E8C2E4
		   C2D1F0F3 F3F0F1F2 F9F3F1F1 F1F0F0F5 F040D9A4
		   8140D181 83A48940 F6F64082 9340F440 8197 
		*/
		
		/* VISA (Tem que retirar o 0100 do início pois é o DV que coloca) ==================================
		   F0F1F0F0 --> COLOCADO PELO DV
		   
		   16     --> Header Length
		   01     --> Header Format
		   02     --> TextFormat
		   0105   --> Total Message Length
		   425607 --> Destination ID
		   211220 --> Source ID
		   01     --> Round-Trip Control Info
		   2000   --> BASE I Flags
		   4A0000 --> Message Status Flags
		   00     --> Batch Number (not used)
		   091754 --> Visa International (Reserved)
		   00     --> User Info
		   
		   0100
		   F6646481 28F1A036 00000000 00000004   <- 1.o e 2.o bitmaps
		    
		   10446305 31501210 11000000 00000004
		   
		   ------------- mensagem 0100 VISA
		   F0F1F0F0
		   16010201 05425607 21122001 20004A00
		   00000917 54000100 F6646481 28F1A036 00000000
		   00000004 10446305 31501210 11000000 00000004
		   58500000 00045850 12072115 00610000 00070115
		   15085411 00769010 00064736 63250446 30531501
		   21011D15 08501167 20252113 60F0F3F4 F1F2F1F0
		   F7F0F1F1 F5F4F2F1 F7F6F8F1 F5F0F1F0 F0F0F0F6
		   F5F8F4F4 F0F0F0F2 40404040 40404040 C2D6D440
		   C4D9C140 E4D44040 40404040 40C3C1D5 D6C9D5C8
		   C1E24040 4040C2D9 05404040 40F2085C E3F1C5E3
		   E2C54009 8609860E F0F0F0F0 F0F8F9F4 F6F0F0F0
		   F0F00375 000811C0 00000000 000000C5 00013417
		   65006467 05800000 00020E00 40000000 000000F1
		   F140F8F4 F1
		   
		 */
		System.out.printf("------------- INICIO DO PROCESSAMENTO  MASTERCARD -- [" + TesteDEs.class + "] --------------\n"     );
		MsgMastercard mcIn = null;
		try {
			mcIn = new MsgMastercard(bufferISO);
		} catch (IsoFormatErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getErrorMessage());
			return;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ANOTACOES:
		// CRIAR UM OBJETO PARA TRATAR CAMPOS DO TIPO FEE, pois o valor é em funcao da primeira parte
		// campo para entender o DE55
		// campo para entender o DE48
		
		
		//System.out.printf("--> valor da transação = %.2f\n", ((DECurrency) mcIn.getDataElemento(String.valueOf(4)).getCurrencyValue());
		System.out.println(mcIn);
		System.out.printf("------------- FIM DO PROCESSAMENTO  MASTERCARD ----- [" + TesteDEs.class + "] --------------\n"     );
		//DEGeneric[] de = mcIn.getDataElementos();
		
		System.out.printf("\n------------- INICIO DO PROCESSAMENTO  VISA -- [" + TesteDEs.class + "] --------------\n"     );
		char[] bitmap1Test = Converter.convertHexaToCharArray("F664648128F1A036");
		DEBitmap bitmap1Visa = null;
		try {
			bitmap1Visa = new DEBitmap();
		} catch (IsoFormatErrorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1.getErrorMessage());
		}
		//bitmap1Visa.setBitmap(bitmap1Test);
		
		try {
			bitmap1Visa.setData(bitmap1Test, Codepage.EBCDIC);
		} catch (IsoFormatErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getErrorMessage());
		}
		bitmap1Visa.setStatus(true);
		bitmap1Visa.setDataType("B");
		System.out.println("Bitmap1Visa: \n" + bitmap1Visa);
		
		char[] bitmap2Test = Converter.convertHexaToCharArray("0000000000000004");
		try {
			DEBitmap bitmap2Visa = new DEBitmap();
		} catch (IsoFormatErrorException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getErrorMessage());
		}
		//bitmap1Visa.setBitmap(bitmap1Test);
		try {
			bitmap1Visa.setData(bitmap2Test, Codepage.EBCDIC);
		} catch (IsoFormatErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getErrorMessage());
		}
		bitmap1Visa.setStatus(true);
		bitmap1Visa.setDataType("B");
		System.out.println("Bitmap2Visa: \n" + bitmap1Visa);
		
		
		System.out.println("Bit 059 VISA: " + String.valueOf(Converter.convertEbcdic2Ascii(Converter.convertHexaToCharArray("F0F0F0F0F0F8F9F4F6F0F0F0F0F0")) ));
		
		
		System.out.printf("------------- FIM DO PROCESSAMENTO  VISA ----- [" + TesteDEs.class + "] --------------\n"     );
		/*
		String aberta =  "16010201054256072112200120004A00000009175400";
		char[] fechada = Converter.compressHexa(aberta.toCharArray());
		System.out.println("String aberta: [" + aberta + "] - String fechada: [" + 
		                    String.valueOf(fechada) + 
		                    "]");
		
		char[] aberta2 = Converter.expandHexa(fechada);
		System.out.println("String fechada: [" + String.valueOf(fechada) + "] - String aberta de novo: [" + 
                String.valueOf(aberta2) + 
                "]");
		System.out.println("String aberta de novo: " + String.valueOf(aberta2));
		*/
		
		
		
		//DEGeneric[] de = new DEGeneric[129];
		
		/*
		 * Tipos de DataElementos:
		 * 		Numerico
		 * 		BitMap
		 * 		Data
		 * 		Financeiro
		 * 		Taxas		
		 */
		
		//DEBUG
		/* ------------------- ERA UTILIZADO PARA TESTES ---------------------------------------------------------
		char[] teste = new char[]{(char)128, (char)0, (char)0, (char)0, (char)0, (char)0, (char)0, (char) 1};
		
		int i = 0;
		
		de[i] = new DEBitmap(i++, false, 0,   8,   "B",  "U", "Primary Bit Map", teste, Parameters.networkCodepage);                         //000 -> Primeiro Bitmap 
		de[i] = new DEBitmap (i++, false, 0,   8,   "B", "U", "Secondary Bit Map",  teste, Parameters.networkCodepage   );                   //001
		de[i] = new DEGeneric(i++, false, 2,  19,   "N", "U", "Primary Account Number");                  //002
		de[i] = new DENumeric(i++, false, 0,   6,   "N", "U", "Processing Code", "003000".toCharArray(), Parameters.networkCodepage );                        //003 S1 n2, S2 n2, S3 n2
		de[i] = new DECurrency(i++, false, 0,  12,   "N", "U", "Amount, Transaction", "000000001234".toCharArray(), Parameters.networkCodepage );                    //004 --> OBJETO TRATAMENTO MOEDA?
		de[i] = new DEGeneric(i++, false, 0,  12,   "N", "U", "Amount, Settlement" );                     //005 --> OBJETO TRATAMENTO MOEDA?
		de[i] = new DEGeneric(i++, false, 0,  12,   "N", "U", "Amount, Cardholder Billing" );             //006 --> OBJETO TRATAMENTO MOEDA?
		de[i] = new DEGeneric(i++, false, 0,  10,   "N", "U", "Transmission Date/Time UTC" );             //007 --> OBJETO TRATAMENTO DATA?
		de[i] = new DEGeneric(i++, false, 0,   8,   "N", "U", "Amount, Cardholder Billing Fee" );         //008
		de[i] = new DEGeneric(i++, false, 0,   8,   "N", "U", "Convertion Rate, Settlement" );            //009
		de[i] = new DEGeneric(i++, false, 0,   8,   "N", "U", "Convertion Rate, Cardholder Billing" );    //010
		de[i] = new DEGeneric(i++, false, 0,   6,   "N", "U", "System Trace Audit Number" );              //011
		de[i] = new DEGeneric(i++, false, 0,   6,   "N", "U", "Time, Local Transaction" );                //012
		de[i] = new DEGeneric(i++, false, 0,   4,   "N", "U", "Date, Local Transaction (MMDD)" );         //013 --> OBJETO TRATAMENTO DATA?
		de[i] = new DEGeneric(i++, false, 0,   4,   "N", "U", "Date, Card Expiration (MMAA)" );           //014 --> OBJETO TRATAMENTO DATA?
		de[i] = new DEGeneric(i++, false, 0,   4,   "N", "U", "Date, Settlement (MMDD)" );                //015 --> OBJETO TRATAMENTO DATA?
		de[i] = new DEGeneric(i++, false, 0,   4,   "N", "U", "Date, Convertion (MMDD)" );                //016
		de[i] = new DEGeneric(i++, false, 0,   4,   "N", "U", "Date, Capture (MMDD)" );                   //017
		de[i] = new DEGeneric(i++, false, 0,   4,   "N", "U", "Merchant Type" );                          //018
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Acquiring Country Code" );                 //019
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Primary Account Number Country Code" );    //020
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Fowarding Institution Country Code");      //021
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "POS Entry Mode" );                         //022
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Card Sequence Number" );                   //023
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Network International ID" );               //024
		de[i] = new DEGeneric(i++, false, 0,   2,   "N", "U", "POS Condition Code" );                     //025
		de[i] = new DEGeneric(i++, false, 0,   2,   "N", "U", "POS PIN Capture Code" );                   //026
		de[i] = new DEGeneric(i++, false, 0,   1,   "N", "U", "Authorization ID Response Length" );       //027
		de[i] = new DEGeneric(i++, false, 0,   9,  "AN", "U", "Amount, Transaction Fee" );                //028
		de[i] = new DEGeneric(i++, false, 0,   9,  "AN", "U", "Amount, Settlement Fee" );                 //029
		de[i] = new DEGeneric(i++, false, 0,   9,  "AN", "U", "Amount, Transaction Processing Fee" );     //030
		de[i] = new DEGeneric(i++, false, 0,   9,  "AN", "U", "Amount, Settlement Processing Fee" );      //031
		de[i] = new DEGeneric(i++, false, 2,   6,   "N", "U", "Acquiring Institution ID Code" );          //032
		de[i] = new DEGeneric(i++, false, 2,   6,   "N", "U", "Fowarding Institution ID Code" );          //033
		de[i] = new DEGeneric(i++, false, 2,  28, "ANS", "U", "Primary Account Number, Extended" );       //034
		de[i] = new DEGeneric(i++, false, 2,  37, "ANS", "U", "Track 2 Data" );                           //035
		de[i] = new DEGeneric(i++, false, 2, 104, "ANS", "U", "Track 3 Data" );                           //036
		de[i] = new DEGeneric(i++, false, 0,  12,  "AN", "U", "Retrieval Reference Number" );             //037
		de[i] = new DEGeneric(i++, false, 0,   6, "ANS", "U", "Autorization ID Response" );               //038
		de[i] = new DEGeneric(i++, false, 0,   2,  "AN", "U", "Response Code" );                          //039
		de[i] = new DEGeneric(i++, false, 0,   3,  "AN", "U", "Service Restriction Code" );               //040
		de[i] = new DEGeneric(i++, false, 0,   8, "ANS", "U", "Card Acceptor Terminal ID" );              //041
		de[i] = new DEGeneric(i++, false, 0,  15, "ANS", "U", "Card Acceptor ID Code" );                  //042
		de[i] = new DEGeneric(i++, false, 0,  40, "ANS", "U", "Card Acceptor Name / Location" );          //043
		de[i] = new DEGeneric(i++, false, 2,  25, "ANS", "U", "Additional Response Data" );               //044
		de[i] = new DEGeneric(i++, false, 2,  76, "ANS", "U", "Track 1 Data" );                           //045
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Expanded Additional Amounts" );            //046
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Additional Data - National Use" );         //047
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Additional Data - Private Use" );          //048
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Currency Code, Transaction" );             //049
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Currency Code, Settlement" );              //050
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Currency Code, Cardholder Billing" );      //051
		de[i] = new DEGeneric(i++, false, 0,   8,   "B", "U", "Personal ID Number (PIN) Data" );          //052
		de[i] = new DEGeneric(i++, false, 0,  16,   "N", "U", "Security-Related Control Information" );   //053
		de[i] = new DEGeneric(i++, false, 3, 120,   "N", "U", "Aditional Amounts" );                      //054 Esse campo deve ser mï¿½ltiplo de 20
		de[i] = new DEGeneric(i++, false, 3, 255,   "B", "U", "Integrated Circuit Card DATA (CHIP)" );    //055
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved For ISO Use" );                   //056
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved For National Use" );              //057
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved For National Use" );              //058
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved For National Use" );              //059
		de[i] = new DEGeneric(i++, false, 3,  60, "ANS", "U", "Advice Reason Code" );                     //060
		de[i] = new DEGeneric(i++, false, 3,  26, "ANS", "U", "POS Data" );                               //061
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Intermediate Network Facility Data" );     //062
		de[i] = new DEGeneric(i++, false, 3,  50,  "AN", "U", "Network Data" );                           //063
		de[i] = new DEGeneric(i++, false, 0,   8,   "B", "U", "Message Authentication Code" );            //064

// --------------------- segunda parte da mensagem ISO		
		de[i] = new DEGeneric(i++, false, 0,   8,   "B", "U", "Bit Map, Extended (Not Used)" );           //065
		de[i] = new DEGeneric(i++, false, 0,   1,   "N", "U", "Settlement Code" );                        //066
		de[i] = new DEGeneric(i++, false, 0,   2,   "N", "U", "Extended Payment Code" );                  //067
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Receiving Institution Country Code" );     //068
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Settlement Institution Country Code" );    //069
		de[i] = new DEGeneric(i++, false, 0,   3,   "N", "U", "Network Management Information Code" );    //070
		de[i] = new DEGeneric(i++, false, 0,   4,   "N", "U", "Message Number" );                         //071
		de[i] = new DEGeneric(i++, false, 0,   4,   "N", "U", "Message Number Last" );                    //072	
		de[i] = new DEGeneric(i++, false, 0,   6,   "N", "U", "Date, Action" );                           //073
		de[i] = new DEGeneric(i++, false, 0,  10,   "N", "U", "Credits, Number" );                        //074
		de[i] = new DEGeneric(i++, false, 0,  10,   "N", "U", "Credits, Reversal Number" );               //075
		de[i] = new DEGeneric(i++, false, 0,  10,   "N", "U", "Debits, Number" );                         //076
		de[i] = new DEGeneric(i++, false, 0,  10,   "N", "U", "Debits, Reversal Number" );                //077
		de[i] = new DEGeneric(i++, false, 0,  10,   "N", "U", "Transfer, Number" );                       //078
		de[i] = new DEGeneric(i++, false, 0,  10,   "N", "U", "Transfer, Reversal Number" );              //079
		de[i] = new DEGeneric(i++, false, 0,  10,   "N", "U", "Inquires, Number" );                       //080
		de[i] = new DEGeneric(i++, false, 0,  10,   "N", "U", "Authorization, Number" );                  //081
		de[i] = new DEGeneric(i++, false, 0,  12,   "N", "U", "Credits, Processing Fee Amount" );         //082
		de[i] = new DEGeneric(i++, false, 0,  12,   "N", "U", "Credits, Transaction Fee Amount" );        //083
		de[i] = new DEGeneric(i++, false, 0,  12,   "N", "U", "Debits, Processing Fee Amount" );          //084
		de[i] = new DEGeneric(i++, false, 0,  12,   "N", "U", "Debits, Transaction Fee Amount" );         //085
		de[i] = new DEGeneric(i++, false, 0,  16,   "N", "U", "Credits, Amount" );                        //086
		de[i] = new DEGeneric(i++, false, 0,  16,   "N", "U", "Credits, Reversal Amount" );               //087
		de[i] = new DEGeneric(i++, false, 0,  16,   "N", "U", "Debits, Amount" );                         //088
		de[i] = new DEGeneric(i++, false, 0,  16,   "N", "U", "Debits, Reversal Amount" );                //089
		de[i] = new DEGeneric(i++, false, 0,  42,   "N", "U", "Original Data Elements" );                 //090
		de[i] = new DEGeneric(i++, false, 0,   1,  "AN", "U", "Issuer File Update Code" );                //091
		de[i] = new DEGeneric(i++, false, 0,   2,  "AN", "U", "File Security Code" );                     //092
		de[i] = new DEGeneric(i++, false, 0,   5,  "AN", "U", "Response Indicator" );                     //093
		de[i] = new DEGeneric(i++, false, 0,   7, "ANS", "U", "Service Indicator" );                      //094
		de[i] = new DEGeneric(i++, false, 0,  42,   "N", "U", "Replacement Amounts" );                    //095
		de[i] = new DEGeneric(i++, false, 0,   8,   "N", "U", "Message Security Code" );                  //096
		de[i] = new DEGeneric(i++, false, 0,  17,  "AN", "U", "Amount, Net Settlement" );                 //097
		de[i] = new DEGeneric(i++, false, 0,  25, "ANS", "U", "Payee" );                                  //098
		de[i] = new DEGeneric(i++, false, 2,  11,   "N", "U", "Settlement Institution ID Code" );         //099
		de[i] = new DEGeneric(i++, false, 2,  11,   "N", "U", "Receiving Institution ID Code" );          //100
		de[i] = new DEGeneric(i++, false, 2,  17, "ANS", "U", "File Name" );                              //101
		de[i] = new DEGeneric(i++, false, 2,  28, "ANS", "U", "Account ID 1" );                           //102
		de[i] = new DEGeneric(i++, false, 2,  28,  "AN", "U", "Account ID 2" );                           //103
		de[i] = new DEGeneric(i++, false, 3, 100, "ANS", "U", "Transaction Description" );                //104
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for Future Use" );                //105
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for Future Use" );                //106
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for Future Use" );                //107
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for Future Use" );                //108
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for Future Use" );                //109
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for Future Use" );                //110
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for Future Use" );                //111
		de[i] = new DEGeneric(i++, false, 3, 100, "ANS", "U", "Additional Data, National Use" );          //112
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for National Use" );              //113
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for National Use" );              //114
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for National Use" );              //115
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for National Use" );              //116
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for National Use" );              //117
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for National Use" );              //118
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Reserved for National Use" );              //119
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Record Data" );                            //120
		de[i] = new DEGeneric(i++, false, 3,   6,   "N", "U", "Authorization ID Code" );                  //121
		de[i] = new DEGeneric(i++, false, 3, 999, "ANS", "U", "Additional Record Data" );                 //122
		de[i] = new DEGeneric(i++, false, 3, 512, "ANS", "U", "Receipt Free Text" );                      //123
		de[i] = new DEGeneric(i++, false, 3, 199, "ANS", "U", "Member Defined Data" );                    //124
		de[i] = new DEGeneric(i++, false, 0,   8,   "B", "U", "New PIN Data" );                           //125
		de[i] = new DEGeneric(i++, false, 3, 100, "ANS", "U", "Private Data" );                           //126
		de[i] = new DEGeneric(i++, false, 3, 100, "ANS", "U", "Private Data" );                           //127
		de[i] = new DEGeneric(i++, false, 2,   8,   "B", "U", "Message Authentication Code" );            //128 
		*/
		
		//DEBUG
		//imprimeDEs(de);
		//imprimeDEsByToString(de);
		//imprimeMensagemISO(mcIn);
		//testaBitmaps(de);
	}
	
	static void imprimeMensagemISO(MsgMastercard mcIn){
		System.out.println("\n----------------- Imprimindo Mensagem ISO (by toString) -------------------\n");
		System.out.println(mcIn.toString());
			
	}
	
	
	static void imprimeDEsByToString(DEGeneric de[]){
		System.out.println("\n------------------- Imprimindo DEs via toString -------------------\n");
		
		for (DEGeneric d : de){
			System.out.println("" + d);
		}
			
	}
	
	static void imprimeDEs(DEGeneric de[]){
		for (DEGeneric d : de){
			System.out.println("" + d.getDataID() + " " +  d.getDescription());
			//System.out.println("---------------------------------------------------------------------");
			if (d instanceof DEBitmap ){
				boolean[] b = ((DEBitmap) d).getBitmap();
				for (boolean x : b){
					System.out.println("" + x );
				}
			}
		}
	}
	
	static void testaBitmaps(DEGeneric de[]) throws IsoFormatErrorException{
		for(int x = 1; x <= 64; x++){
			((DEBitmap)de[0]).setBitmap(x, true);
		}
		for(int x = 1; x <= 64; x++){
			System.out.println("BITMAP 1 (" + x + ") = " + ((DEBitmap)de[0]).getBitStatus(x));
		}
		
		for(int x = 65; x <= 128; x++){
			((DEBitmap)de[1]).setBitmap(x, true);
		}
		for(int x = 1; x <= 64; x++){
			System.out.println("BITMAP 2 (" + x + ") = " + ((DEBitmap)de[1]).getBitStatus(x));
		}
		
		
		
	}
	
	

	
	


	
}
