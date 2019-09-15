package br.com.jrperin.ISO.mastercard;

/**
 * Class MsgInBuffer - This Class will be used to give the buffer from network 
 * @version 1.0
 * @author Joao Roberto Perin
 *
 * Copyright (C) 2012 Joao Roberto Perin.
 */
public class MsgInBuffer {
	private char[] buffer;
	private int bufferPos = 0; 
	
	public MsgInBuffer(char[] buffer, int bufferPos){
			this.buffer = buffer;
			this.bufferPos = bufferPos;
	}
	
	public MsgInBuffer(char[] buffer){
		this(buffer, 0);
	}
	
	public MsgInBuffer(){
		char[] buffer = {'0'};
		this.buffer = buffer;
		this.bufferPos = 0;
	}
	
	public void setBuffer(char[] buffer, int bufferPos){
		this.buffer = buffer;
		this.bufferPos = bufferPos;
	}
	
	public void setBuffer(char[] buffer){
		this.buffer = buffer;
	}
	
	
	public void setPos(int bufferPos){
		this.bufferPos = bufferPos;
	}
	
	public char[] getBuffer(){
		return this.buffer;
	}
	
	public int getBufferPos(){
		return this.bufferPos;
	}
	
	 /** 
     * Get a sub buffer 
     * 
     * @param int subBufferLen (tamanho do sub buffer a ser lido)
     * @return char array with sub buffer
     * Obs.: the variable bufferPos (pointer of actual byte) will be moved to next byte after subBuffer
     */  
	public char[] getSubBufferAndMove(int subBufferLen){
		char[] result;
		
		if (subBufferLen > 0){
			result = new char[subBufferLen];
		} else{
			return null;
		}

		for(int i = 0; i < subBufferLen; i ++){
			result[i] = buffer[bufferPos++];
		}


		return result;

	}

}
