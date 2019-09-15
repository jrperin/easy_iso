package br.com.jrperin.ISO.mastercard;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import br.com.jrperin.exceptions.IsoFormatErrorException;


public class DEMastercardFactory {
	//Map<Integer,DEGeneric> de = new HashMap<Integer, DEGeneric>();	
	File arquivo = null;
	String msgType = "";
	String msgVersion = "";
	
	public Vector getNewMsgMastercard(File msgDefinition) throws JDOMException, IOException, 
											ClassNotFoundException, InstantiationException, 
											IllegalAccessException, NumberFormatException, 
											IsoFormatErrorException{
		
		//Map<String,DEGeneric> deMap = new Hashtable<String, DEGeneric>();
		Vector deMap = new Vector();
		
		this.arquivo = msgDefinition;

		SAXBuilder sb = new SAXBuilder();

		Document d = sb.build(arquivo);

		Element msgDef = d.getRootElement();
		//System.out.println(msgDef.getAttribute("msgType") + " " + msgDef.getAttribute("version"));
		Element msgType = msgDef.getChild("msgType");
		this.msgType = msgType.getAttributeValue("type");
		this.msgVersion = msgType.getAttributeValue("version");
		//DEBUG
		//System.out.println(msgType.getAttributeValue("type") + " vers�o: " + msgType.getAttributeValue("version"));
		
		List deArray = msgType.getChildren();
		Iterator i = deArray.iterator();
		while (i.hasNext()){
			
			
			Element deX = (Element) i.next();
			Class classe2Load = ClassLoader.getSystemClassLoader().loadClass(deX.getAttributeValue("class"));
			DEGeneric de = (DEGeneric) classe2Load.newInstance();
			
			String id = deX.getAttributeValue("id");
			
			de.setDataID(id);
			de.setStatus(Boolean.parseBoolean(deX.getAttributeValue("iniStatus")));
			de.setLenOfLen(Integer.parseInt(deX.getAttributeValue("lenOfLen")));
			de.setLenMax(Integer.parseInt(deX.getAttributeValue("lenMax")));
			de.setDataType(deX.getAttributeValue("dataType"));
			de.setGuiOption(deX.getAttributeValue("guiOpt"));
			de.setBitmaped(Boolean.parseBoolean(deX.getAttributeValue("bitmaped")));
			de.setDescription(deX.getAttributeValue("description"));
			deMap.add(de);
			
		}
		return deMap;


	}

public String getType(){
	return this.msgType;
}

public String getVersion(){
	return this.msgVersion;
}
	
	
	
	
	
	/*
	public DEMastercardFactory() throws IsoFormatErrorException{
		
		
		int i = -1;
		de.put(i, new DEGeneric (i++, false, 0,   4,   "N", "U", "Message Type"                         ));  //-01 MessageType
	
		de.put(i, new DEBitmap  (i++, false, 0,   8,   "B", "U", "Primary Bit Map"                      ));  //000 -> Primeiro Bitmap 
		de.put(i, new DEBitmap  (i++, false, 0,   8,   "B", "U", "Secondary Bit Map"                    ));  //001 -> Segundo  Bitmap
		de.put(i, new DEGeneric (i++, false, 2,  19,   "N", "U", "Primary Account Number (PAN)"         ));  //002
		de.put(i, new DEGeneric (i++, false, 0,   6,   "N", "U", "Processing Code"                      ));  //003 
		de.put(i, new DECurrency(i++, false, 0,  12,   "N", "U", "Amount, Transaction"                  ));  //004 --> OBJETO TRATAMENTO MOEDA?
		de.put(i, new DECurrency(i++, false, 0,  12,   "N", "U", "Amount, Settlement"                   ));  //005 --> OBJETO TRATAMENTO MOEDA?
		de.put(i, new DECurrency(i++, false, 0,  12,   "N", "U", "Amount, Cardholder Billing"           ));  //006 --> OBJETO TRATAMENTO MOEDA?
		de.put(i, new DEGeneric (i++, false, 0,  10,   "N", "U", "Transmission Date/Time UTC"           ));  //007 --> OBJETO TRATAMENTO DATA?
		de.put(i, new DEGeneric (i++, false, 0,   8,   "N", "U", "Amount, Cardholder Billing Fee"       ));  //008
		de.put(i, new DEGeneric (i++, false, 0,   8,   "N", "U", "Convertion Rate, Settlement"          ));  //009
		de.put(i, new DEGeneric (i++, false, 0,   8,   "N", "U", "Convertion Rate, Cardholder Billing"  ));  //010
		de.put(i, new DEGeneric (i++, false, 0,   6,   "N", "U", "System Trace Audit Number (STAM)"     ));  //011
		de.put(i, new DEGeneric (i++, false, 0,   6,   "N", "U", "Time, Local Transaction"              ));  //012
		de.put(i, new DEGeneric (i++, false, 0,   4,   "N", "U", "Date, Local Transaction (MMDD)"       ));  //013 --> OBJETO TRATAMENTO DATA?
		de.put(i, new DEGeneric (i++, false, 0,   4,   "N", "U", "Date, Card Expiration (MMAA)"         ));  //014 --> OBJETO TRATAMENTO DATA?
		de.put(i, new DEGeneric (i++, false, 0,   4,   "N", "U", "Date, Settlement (MMDD)"              ));  //015 --> OBJETO TRATAMENTO DATA?
		de.put(i, new DEGeneric (i++, false, 0,   4,   "N", "U", "Date, Convertion (MMDD)"              ));  //016
		de.put(i, new DEGeneric (i++, false, 0,   4,   "N", "U", "Date, Capture (MMDD)"                 ));  //017
		de.put(i, new DEGeneric (i++, false, 0,   4,   "N", "U", "Merchant Type"                        ));  //018
		de.put(i, new DEGeneric (i++, false, 0,   3,   "N", "U", "Acquiring Inst. Country Code"         ));  //019
		de.put(i, new DEGeneric (i++, false, 0,   3,   "N", "U", "Primary Account Number Country Code"  ));  //020
		de.put(i, new DEGeneric (i++, false, 0,   3,   "N", "U", "Fowarding Institution Country Code"   ));  //021
		de.put(i, new DEGeneric (i++, false, 0,   3,   "N", "U", "POS Entry Mode"                       ));  //022
		de.put(i, new DEGeneric (i++, false, 0,   3,   "N", "U", "Card Sequence Number"                 ));  //023
		de.put(i, new DEGeneric (i++, false, 0,   3,   "N", "U", "Network International ID"             ));  //024
		de.put(i, new DEGeneric (i++, false, 0,   2,   "N", "U", "POS Condition Code"                   ));  //025
		de.put(i, new DEGeneric (i++, false, 0,   2,   "N", "U", "POS PIN Capture Code"                 ));  //026
		de.put(i, new DEGeneric (i++, false, 0,   1,   "N", "U", "Authorization ID Response Length"     ));  //027
		de.put(i, new DEGeneric (i++, false, 0,   9,  "AN", "U", "Amount, Transaction Fee"              ));  //028
		de.put(i, new DEGeneric (i++, false, 0,   9,  "AN", "U", "Amount, Settlement Fee"               ));  //029
		de.put(i, new DEGeneric (i++, false, 0,   9,  "AN", "U", "Amount, Transaction Processing Fee"   ));  //030
		de.put(i, new DEGeneric (i++, false, 0,   9,  "AN", "U", "Amount, Settlement Processing Fee"    ));  //031
		de.put(i, new DEGeneric (i++, false, 2,   6,   "N", "U", "Acquiring Institution ID Code"        ));  //032
		de.put(i, new DEGeneric (i++, false, 2,   6,   "N", "U", "Fowarding Institution ID Code"        ));  //033
		de.put(i, new DEGeneric (i++, false, 2,  28, "ANS", "U", "Primary Account Number, Extended"     ));  //034
		de.put(i, new DEGeneric (i++, false, 2,  37, "ANS", "U", "Track 2 Data"                         ));  //035
		de.put(i, new DEGeneric (i++, false, 2, 104, "ANS", "U", "Track 3 Data"                         ));  //036
		de.put(i, new DEGeneric (i++, false, 0,  12,  "AN", "U", "Retrieval Reference Number"           ));  //037
		de.put(i, new DEGeneric (i++, false, 0,   6, "ANS", "U", "Autorization ID Response"             ));  //038
		de.put(i, new DEGeneric (i++, false, 0,   2,  "AN", "U", "Response Code"                        ));  //039
		de.put(i, new DEGeneric (i++, false, 0,   3,  "AN", "U", "Service Restriction Code"             ));  //040
		de.put(i, new DEGeneric (i++, false, 0,   8, "ANS", "U", "Card Acceptor Terminal ID"            ));  //041
		de.put(i, new DEGeneric (i++, false, 0,  15, "ANS", "U", "Card Acceptor ID Code"                ));  //042
		de.put(i, new DEGeneric (i++, false, 0,  40, "ANS", "U", "Card Acceptor Name / Location"        ));  //043
		de.put(i, new DEGeneric (i++, false, 2,  25, "ANS", "U", "Additional Response Data"             ));  //044
		de.put(i, new DEGeneric (i++, false, 2,  76, "ANS", "U", "Track 1 Data"                         ));  //045
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Expanded Additional Amounts"          ));  //046
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Additional Data - National Use"       ));  //047
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Additional Data - Private Use"        ));  //048
		de.put(i, new DENumeric (i++, false, 0,   3,   "N", "U", "Currency Code, Transaction"           ));  //049
		de.put(i, new DENumeric (i++, false, 0,   3,   "N", "U", "Currency Code, Settlement"            ));  //050
		de.put(i, new DENumeric (i++, false, 0,   3,   "N", "U", "Currency Code, Cardholder Billing"    ));  //051
		de.put(i, new DEGeneric (i++, false, 0,   8,   "B", "U", "Personal ID Number (PIN) Data"        ));  //052
		de.put(i, new DEGeneric (i++, false, 0,  16,   "N", "U", "Security-Related Control Information" ));  //053
		de.put(i, new DEGeneric (i++, false, 3, 120,   "N", "U", "Aditional Amounts"                    ));  //054 Esse campo deve ser multiplo de 20
		de.put(i, new DEGeneric (i++, false, 3, 255,   "B", "U", "Integrated Circuit Card DATA (CHIP)"  ));  //055
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved For ISO Use"                 ));  //056
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved For National Use"            ));  //057
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved For National Use"            ));  //058
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved For National Use"            ));  //059
		de.put(i, new DEGeneric (i++, false, 3,  60, "ANS", "U", "Advice Reason Code"                   ));  //060
		de.put(i, new DEGeneric (i++, false, 3,  26, "ANS", "U", "POS Data"                             ));  //061
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Intermediate Network Facility Data"   ));  //062
		de.put(i, new DEGeneric (i++, false, 3,  50,  "AN", "U", "Network Data"                         ));  //063
		de.put(i, new DEGeneric (i++, false, 0,   8,   "B", "U", "Message Authentication Code"          ));  //064 
// -------------------- segunda parte da mensagem ISO		
		de.put(i, new DEBitmap  (i++, false, 0,   8,   "B", "U", "Bit Map, Extended (Not Used)"         ));  //065
		de.put(i, new DENumeric (i++, false, 0,   1,   "N", "U", "Settlement Code"                      ));  //066
		de.put(i, new DEGeneric (i++, false, 0,   2,   "N", "U", "Extended Payment Code"                ));  //067
		de.put(i, new DEGeneric (i++, false, 0,   3,   "N", "U", "Receiving Institution Country Code"   ));  //068
		de.put(i, new DEGeneric (i++, false, 0,   3,   "N", "U", "Settlement Institution Country Code"  ));  //069
		de.put(i, new DEGeneric (i++, false, 0,   3,   "N", "U", "Network Management Information Code"  ));  //070
		de.put(i, new DEGeneric (i++, false, 0,   4,   "N", "U", "Message Number"                       ));  //071
		de.put(i, new DEGeneric (i++, false, 0,   4,   "N", "U", "Message Number Last"                  ));  //072	
		de.put(i, new DEGeneric (i++, false, 0,   6,   "N", "U", "Date, Action"                         ));  //073
		de.put(i, new DENumeric (i++, false, 0,  10,   "N", "U", "Credits, Number"                      ));  //074
		de.put(i, new DENumeric (i++, false, 0,  10,   "N", "U", "Credits, Reversal Number"             ));  //075
		de.put(i, new DENumeric (i++, false, 0,  10,   "N", "U", "Debits, Number"                       ));  //076
		de.put(i, new DENumeric (i++, false, 0,  10,   "N", "U", "Debits, Reversal Number"              ));  //077
		de.put(i, new DENumeric (i++, false, 0,  10,   "N", "U", "Transfer, Number"                     ));  //078
		de.put(i, new DENumeric (i++, false, 0,  10,   "N", "U", "Transfer, Reversal Number"            ));  //079
		de.put(i, new DENumeric (i++, false, 0,  10,   "N", "U", "Inquires, Number"                     ));  //080
		de.put(i, new DENumeric (i++, false, 0,  10,   "N", "U", "Authorization, Number"                ));  //081
		de.put(i, new DEGeneric (i++, false, 0,  12,   "N", "U", "Credits, Processing Fee Amount"       ));  //082
		de.put(i, new DEGeneric (i++, false, 0,  12,   "N", "U", "Credits, Transaction Fee Amount"      ));  //083
		de.put(i, new DEGeneric (i++, false, 0,  12,   "N", "U", "Debits, Processing Fee Amount"        ));  //084
		de.put(i, new DEGeneric (i++, false, 0,  12,   "N", "U", "Debits, Transaction Fee Amount"       ));  //085
		de.put(i, new DECurrency(i++, false, 0,  16,   "N", "U", "Credits, Amount"                      ));  //086
		de.put(i, new DECurrency(i++, false, 0,  16,   "N", "U", "Credits, Reversal Amount"             ));  //087
		de.put(i, new DEGeneric (i++, false, 0,  16,   "N", "U", "Debits, Amount"                       ));  //088
		de.put(i, new DEGeneric (i++, false, 0,  16,   "N", "U", "Debits, Reversal Amount"              ));  //089
		de.put(i, new DEGeneric (i++, false, 0,  42,   "N", "U", "Original Data Elements"               ));  //090
		de.put(i, new DEGeneric (i++, false, 0,   1,  "AN", "U", "Issuer File Update Code"              ));  //091
		de.put(i, new DEGeneric (i++, false, 0,   2,  "AN", "U", "File Security Code"                   ));  //092
		de.put(i, new DEGeneric (i++, false, 0,   5,  "AN", "U", "Response Indicator"                   ));  //093
		de.put(i, new DEGeneric (i++, false, 0,   7, "ANS", "U", "Service Indicator"                    ));  //094
		de.put(i, new DEGeneric (i++, false, 0,  42,   "N", "U", "Replacement Amounts"                  ));  //095
		de.put(i, new DEGeneric (i++, false, 0,   8,   "N", "U", "Message Security Code"                ));  //096
		de.put(i, new DEGeneric (i++, false, 0,  17,  "AN", "U", "Amount, Net Settlement"               ));  //097
		de.put(i, new DEGeneric (i++, false, 0,  25, "ANS", "U", "Payee"                                ));  //098
		de.put(i, new DEGeneric (i++, false, 2,  11,   "N", "U", "Settlement Institution ID Code"       ));  //099
		de.put(i, new DEGeneric (i++, false, 2,  11,   "N", "U", "Receiving Institution ID Code"        ));  //100
		de.put(i, new DEGeneric (i++, false, 2,  17, "ANS", "U", "File Name"                            ));  //101
		de.put(i, new DEGeneric (i++, false, 2,  28, "ANS", "U", "Account ID 1"                         ));  //102
		de.put(i, new DEGeneric (i++, false, 2,  28,  "AN", "U", "Account ID 2"                         ));  //103
		de.put(i, new DEGeneric (i++, false, 3, 100, "ANS", "U", "Transaction Description"              ));  //104
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for Future Use"              ));  //105
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for Future Use"              ));  //106
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for Future Use"              ));  //107
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for Future Use"              ));  //108
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for Future Use"              ));  //109
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for Future Use"              ));  //110
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for Future Use"              ));  //111
		de.put(i, new DEGeneric (i++, false, 3, 100, "ANS", "U", "Additional Data, National Use"        ));  //112
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for National Use"            ));  //113
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for National Use"            ));  //114
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for National Use"            ));  //115
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for National Use"            ));  //116
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for National Use"            ));  //117
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for National Use"            ));  //118
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Reserved for National Use"            ));  //119
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Record Data"                          ));  //120
		de.put(i, new DEGeneric (i++, false, 3,   6,   "N", "U", "Authorization ID Code"                ));  //121
		de.put(i, new DEGeneric (i++, false, 3, 999, "ANS", "U", "Additional Record Data"               ));  //122
		de.put(i, new DEGeneric (i++, false, 3, 512, "ANS", "U", "Receipt Free Text"                    ));  //123
		de.put(i, new DEGeneric (i++, false, 3, 199, "ANS", "U", "Member Defined Data"                  ));  //124
		de.put(i, new DEGeneric (i++, false, 0,   8,   "B", "U", "New PIN Data"                         ));  //125
		de.put(i, new DEGeneric (i++, false, 3, 100, "ANS", "U", "Private Data"                         ));  //126
		de.put(i, new DEGeneric (i++, false, 3, 100, "ANS", "U", "Private Data"                         ));  //127
		de.put(i, new DEGeneric (i++, false, 2,   8,   "B", "U", "Message Authentication Code"          ));  //128 	
	}
	
	public Map getNewMsgMastercard(){
		return this.de;
	}
	*/
	
}
