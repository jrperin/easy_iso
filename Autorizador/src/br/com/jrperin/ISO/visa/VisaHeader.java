package br.com.jrperin.ISO.visa;

public class VisaHeader {
	
	private int    MSG_HEADER_STANDARD  = 22;
	private int    MSG_HEADER_REJECT    = 26;
 	
	private int    intHeaderLen          = 0         ;
	private int    intTotalMessageLength = 0         ;
	
	private char   headerLength                      ;
	private char   headerFormat                      ;
	private char   textFormat                        ;
	private char[] totalMessageLength   = new char[2];
	private char[] destinationID        = new char[3];
	private char[] sourceID             = new char[3];
	private char   roundTripControlInfo              ;
	private char[] base1Flags           = new char[2];
	private char[] messageStatusFlags   = new char[3];
	private char   batchNumber                       ;
	private char[] visaInternational    = new char[3];
	private char   userInfo                          ; // --> até aqui é o Header normal
	
	//------------- daqui para baixo usado apenas para reject message --------------------------
	private char[] bitmap               = new char[2]; // --> usado somente para o reject header
	private char[] rejectDataGroup      = new char[2]; // --> usado somente para o reject header 
	private char[] exceeded; //--> caso  
	private int    exceededLen          = 0;
	
//------------------------------------------
	private boolean rejectHeader = false;
//-------------------------------------------
	
	//CONSTRUCTOR -------------------------------------------------
	public VisaHeader(char[] inBuffer){
		
	}
	
	
	//-------------------------------------------------------------
	
	public char getHeaderLength(){
		return headerLength;
	}
	
	public char getHeaderFormat(){
		return headerFormat;
	}
	
	public char getTextFormat(){
		return textFormat;
	}
	
	public char[] getTotalMessageLength(){
		return totalMessageLength;
	}
	
	public char[] getDestinationID(){
		return destinationID;
	}
	
	public char[] getSourceID() {
		return sourceID;
	}

	public char getRoundTripControlInfo() {
		return roundTripControlInfo;
	}
	
	public char[] getBase1Flags() {
		return base1Flags;
	}
	
	public char[] getMessageStatusFlags() {
		return messageStatusFlags;
	}
	
	public char getBatchNumber() {
		return batchNumber;
	}
	
	public char[] getVisaInternational() {
		return visaInternational;
	}
	
	public char getUserInfo() {
		return userInfo;
	}

	public char[] getBitmap() {
		return bitmap;
	}

	public char[] getRejectDataGroup() {
		return rejectDataGroup;
	}

	
	//----------- Getters atraves dos HeaderFields
	
	public char headerField1(){
		return getHeaderLength();
	}
	
	public char headerField2(){
		return getHeaderFormat();
	}
	
	public char headerField3(){
		return getTextFormat();
	}
	
	public char[] headerField4(){
		return getTotalMessageLength();
	}
	
	public char[] headerField5(){
		return getDestinationID();
	}
	
	public char[] headerField6() {
		return getSourceID();
	}

	public char headerField7() {
		return getRoundTripControlInfo();
	}
	
	public char[] headerField8() {
		return getBase1Flags();
	}
	
	public char[] headerField9() {
		return getMessageStatusFlags();
	}
	
	public char headerField10() {
		return getBatchNumber();
	}
	
	public char[] headerField11() {
		return getVisaInternational();
	}
	
	public char headerField12() {
		return getUserInfo();
	}

	public char[] headerField13() {
		return getBitmap();
	}

	public char[] headerField14() {
		return getRejectDataGroup();
	}
	
	public char[] getExceeded() throws NullPointerException{
		if (exceededLen > 0){
			return this.exceeded;
		}else{
			return null;
		}
	}
	
	public int getExceededLen(){
		return this.exceededLen;
	}
	
	
	//----------------------------------------------
	
	public boolean isRejectHeader() {
		return rejectHeader;
	}
	
}
