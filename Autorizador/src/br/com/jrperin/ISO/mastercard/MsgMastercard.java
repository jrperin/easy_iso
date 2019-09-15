package br.com.jrperin.ISO.mastercard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.JDOMException;
import br.com.jrperin.exceptions.IsoFormatErrorException;
import br.com.jrperin.parameters.Parameters;
import br.com.jrperin.parameters.Parameters.Codepage;

public class MsgMastercard implements Iterator{

	Vector<DEGeneric> de;
	private Vector<BitStatus> globalBitmap = new Vector<BitStatus>();
	
	private DEBitmap[] bitmap  = new DEBitmap[3];
	private int bitmapPos = 0;
	private int globalBitmapPos = 0; 

	private MsgInBuffer msgInBuffer;
	
	private Codepage inputCodepage = Parameters.networkCodepage;
	
	private File msgDefFile = null;
	
	private String type = null;
	public String getType() {
		return type;
	}

	public String getVersion() {
		return version;
	}

	private String version = null;
	
	/* -------- Iterator ---------------------------------------------- */
	private int itPos = 0;
	
	/* -------- CONSTRUCTOR ------------------------------------------- */

	public MsgMastercard(char[] inBuffer) throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		this(inBuffer, Parameters.networkCodepage, new File("default.def"));
	}
	
	public MsgMastercard(char[] inBuffer, Codepage inputCodepage) throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		this(inBuffer, Parameters.networkCodepage, new File("default.def"));
	}
	
	public MsgMastercard(char[] inBuffer, File msgDefFile) throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		this(inBuffer, Parameters.networkCodepage, msgDefFile);
	}
	
	public MsgMastercard(char[] inBuffer, Codepage inputCodepage, File msgDefFile) throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		/* Efetua tratamentos de erro para garantir que o Buffer da mensagem tem conteúdo. */
		if (inBuffer == null) {
			throw new IsoFormatErrorException("MASTERCARD - Buffer da Mensagem ISO = NULL", this, "null");
		}
		try{
		} catch (Exception e){
			throw new IsoFormatErrorException("MASTERCARD - Buffer da Mensagem ISO com tamanho inválido",
			           this, "null");
		}
		
		this.inputCodepage = inputCodepage;
		this.msgInBuffer = new MsgInBuffer(inBuffer); // <--- Incilializa a váriável atributo da classe
		
		this.msgDefFile = msgDefFile;
		
		/* Inicializa todos os Data Elementos possíveis da Mensagem ISO	 */
		createDefaultMessage();
		
		/* Inicializa todos os Data Elementos possíveis da Mensagem ISO	 */
		expandISOMessage(this.inputCodepage);
	}
	
	public MsgMastercard() throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		createDefaultMessage();
	}

	/* -------- CONSTRUCTOR END --------------------------------------- */
	
	private void expandISOMessage(Codepage inputCodepage) throws IsoFormatErrorException{
		DEGeneric deLido = null;
		
		Iterator<DEGeneric> deIterator = de.iterator();
		
		while (deIterator.hasNext()){
			//DEBUG
			//System.out.println(""+ deIterator.next());
			
			deLido = deIterator.next();
			
			if (deLido.isOn()){
				/*	Obs.: os primeiros campos da mensagem nascem como true (msgtype e bitmap1) */
				deLido.uncompressMsgBuffer(msgInBuffer, inputCodepage);
			}
			
			/*  acerta o mapa de Bits */
			if (deLido instanceof DEBitmap){
				
				bitmap[bitmapPos++] = (DEBitmap) deLido;
				int i = 0;
				boolean[] bitStatus = ((DEBitmap) deLido).getBitmap();
				
				/* -- configura o mapa de bits global com o mapa recem achado -- */
				while (i < bitStatus.length && globalBitmapPos < globalBitmap.size()){
					globalBitmap.get(globalBitmapPos++).setStatus(bitStatus[i++]);
				}
				//DEBUG
				//System.out.println("=============================  EXPAND MESSAGE ISO ===============================");
				//System.out.println(this);
				//System.out.println("=============================  EXPAND MESSAGE ISO FIM ===========================");
				
			}
			
		}

	}
	
	public char[] getMsgType() throws IsoFormatErrorException{
		return getMsgType(Parameters.systemCodepage);
	}
	
	public char[] getMsgType(Codepage codepage) throws IsoFormatErrorException {
		DEGeneric deX = findDE("msgType");
		return deX.getData(codepage);
	}
	
	public String getStringMsgType(Codepage codepage) throws IsoFormatErrorException{
		return String.copyValueOf(getMsgType(codepage));
	}
	
	public String getStringMsgType() throws IsoFormatErrorException{
		return String.copyValueOf(getMsgType(Parameters.systemCodepage));
	}
	
	public void setStringMsgType(String msgType) throws IsoFormatErrorException{
		DEGeneric deX = findDE("msgType");
		deX.setData(msgType.toCharArray());
	}
	
	public List<DEGeneric> getArrayDataElementos(){
		return de;
	}
	
	public DEGeneric getDataElemento( String dataID) throws IsoFormatErrorException{
		DEGeneric deX = findDE(dataID);
		return deX;
	}
	
	public void refreshIsoMessage() throws IsoFormatErrorException{
		
		int nMapa = 0;
		int limitMap = 64;
		int id = 1;
		
		bitmap[0].setStatus(true);
		bitmap[1].setStatus(false);
		bitmap[2].setStatus(false);
		
		Iterator<DEGeneric> itDE = de.iterator();
		globalBitmap.clear();
		
		while(itDE.hasNext()){
			DEGeneric deAux = itDE.next();
			if (deAux.isBitmaped()){
				globalBitmap.add((BitStatus) deAux);
			}
		}
		
		
		Iterator<BitStatus> itStatus = globalBitmap.iterator();
		
		while(itStatus.hasNext()){
			
			if (id >= limitMap){
				limitMap += 64;
				nMapa++;
			}
			if (itStatus.next().isOn()){
				bitmap[nMapa].setStatus(true);
				bitmap[nMapa].setBitmap(id, true);
			}else{
				bitmap[nMapa].setBitmap(id, false);
			}
			id++;
			
		}
		
		/* faz o acerto dos bits que ligam os mapas de bits */
		if (bitmap[2].isOn()){
			bitmap[1].setBitmap(1, true);
		}
		if (bitmap[1].isOn()){
			bitmap[0].setBitmap(1, true);
		}
	}
	
	public char[] getIsoMessage(Codepage codepage) throws IsoFormatErrorException{
		
		char[] result = null;

		this.refreshIsoMessage();
		
		ArrayList<Character> retorno = new ArrayList<Character>();
		this.resetIterator();
		
		/* percorre cada objeto da mensagem para pegar o array de caracteres */
		while (this.hasNext()){
			DEGeneric deAux = (DEGeneric) this.next();

			if (deAux.isOn()){
				char[] dataIso = deAux.getIsoData(codepage);
				for (int i = 0; i < dataIso.length; i++){
					retorno.add(dataIso[i]);
				}
			}
		}
		
		Iterator<Character> itIso = retorno.iterator();
		result = new char[retorno.size()];
		int i = 0;
		while (itIso.hasNext()){
			result[i++] = itIso.next();
		}
		
		return result;
	}
	
	public void setDEData(String dataID, String str) throws IsoFormatErrorException{
		setDEData(dataID, str.toCharArray());
	}
	
	public void setDEData(String dataID, char[] data) throws IsoFormatErrorException{
		
		DEGeneric deX = findDE(dataID);
		deX.setData(data);
	}
	
	public void setDEStatus(String dataID, boolean status) throws IsoFormatErrorException{
		DEGeneric deX = findDE(dataID);
		deX.setStatus(status);

	}
	
	public void setMsgDefFile(File newFile){
		this.msgDefFile = newFile;
	}
	
	public boolean isOn (String dataID) throws IsoFormatErrorException{
		
		return findDE(dataID).isOn();		
	}
	
	public String toString(){
		
		StringBuilder strReturn = new StringBuilder("\n");

		Iterator<DEGeneric> i = de.iterator();
		
		while(i.hasNext()){
			strReturn.append("" + i.next() +"\n");
		}
		
		return strReturn.toString();
		
	}

	private DEGeneric findDE(String dataID) throws IsoFormatErrorException{
		Iterator deI = de.iterator();
		
		while (deI.hasNext()){
			DEGeneric deX = (DEGeneric) deI.next();
			if (deX.getDataID().equalsIgnoreCase("msgType")){
				return deX;
			}
		}
		throw new IsoFormatErrorException("Erro ao encontrar o campo " + dataID, this , dataID);
	}
	
	private void createDefaultMessage() throws IsoFormatErrorException, NumberFormatException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		DEMastercardFactory deFactory = new DEMastercardFactory();
		de = deFactory.getNewMsgMastercard(msgDefFile);
		this.type = deFactory.getType();
		this.version = deFactory.getVersion();
		
		
		/* Configura os mapas de Bits em um unico objeto global */
		Iterator <DEGeneric> itDe = de.iterator();
		int nMapa = 0;
		while(itDe.hasNext()){
			DEGeneric deAux = itDe.next();
			
			if (deAux instanceof DEBitmap){
				bitmap[nMapa++] = (DEBitmap) deAux;
			}
			
			if (deAux.isBitmaped()){
				if(deAux instanceof BitStatus){
					globalBitmap.add( (BitStatus) deAux);
				} else {
					throw new IsoFormatErrorException("Elemento deve ter interface BitStatus!!!", deAux, deAux.getDataID());
				}
			}
		}

	}

	public void resetIterator(){
		itPos = 0;
	}
	
	@Override
	public boolean hasNext() {
		if (itPos < de.size()){
			return true;
		} else{
			return false;
		}
	}

	@Override
	public Object next() {
		if (this.hasNext()){
			return de.get(itPos++);
		}else{
			return null;
		}
	}
	
	@Override
	public void remove() {
		//de.remove(itPos++);
	}

}
