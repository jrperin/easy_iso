package br.com.jrperin.commoninterfaces;

public interface Command {

	//public Object execute(Object ... arg);
	public void execute(Object ... arg);
	// O item 0 de params pode ser utilizado como Acao a ser tomada e os demais como parametros.
}
