package br.com.jrperin.interfaces;

public interface Command {

	public Object execute(Object ... params);
	// O item 0 de params pode ser utilizado como Acao a ser tomada e os demais como parametros.
}
