package br.com.jrperin.interfaces;

import br.com.jrperin.ISO.mastercard.MsgInBuffer;

public interface InterForms {

	public void close();
	public void setMsgInBuffer(char[] msgInBuffer);
	public MsgInBuffer getMsgInBuffer();
	public String getTextBox();
	public void setRetorno(boolean retorno);
}
