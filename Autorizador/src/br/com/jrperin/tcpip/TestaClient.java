package br.com.jrperin.tcpip;

public class TestaClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		{
			Runnable cli = new Client();
			new Thread(cli).start();
		}

	}

}
