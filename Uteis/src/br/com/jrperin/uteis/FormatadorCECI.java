package br.com.jrperin.uteis;

public class FormatadorCECI {
	
	private String areaComSpaces; 
	private String areaSemSpaces;
	private String areaTcpIp;
	private int    nCaracteres;
	
	
	//-------------------- CONTRUTORES -------------------------------//
	
	public FormatadorCECI(){
		areaComSpaces = "00";
		areaSemSpaces = "00";
		areaTcpIp     = "00";
		nCaracteres   = 0;
	}
	
	public FormatadorCECI(String s){
		this.setArea(s);
	}
	
	//----------------------- METODOS -------------------------------//
	public String getAreaComSpaces(){
		return areaComSpaces;
	}
	
	public String getAreaSemSpaces(){
		return areaSemSpaces;
	}
	
	public String getAreaTcpIp(){
		return this.areaTcpIp;
	}
	
	public int getNCaracteres(){
		return nCaracteres;
	}
	
	
	public void setArea(String s){
		
		StringBuilder strS = new StringBuilder();
		StringBuilder strC = new StringBuilder();
		StringBuilder strT = new StringBuilder();
		
		int cSpace = 0;
		int cLinha = 0;
		int cCaracteres = 0;
		
		
		if (!(s.isEmpty())){
			s = s.toUpperCase();
			for (char c: s.toCharArray()){
				
				if (isCaracterValid (c)){
					strS.append(c);
					strC.append(c);
					strT.append(c);
			
					cSpace++;
					cCaracteres++;
					cLinha++;
				}
				
				if ((cSpace == 8) && (cLinha < 32)){
					strC.append(" ");
					cSpace = 0;
				}
				
				if (cLinha == 32){
					strC.append('\n');
					strS.append('\n');
					cLinha = 0;
					cSpace = 0;
				}
			}
		}
		
		if (strS.length() < 1){
			strC.append("00");
			strS.append("00");
		}
		
		areaComSpaces = strC.toString();
		areaSemSpaces = strS.toString();
		areaTcpIp     = strT.toString();
		nCaracteres   = cCaracteres / 2;
		
	}
	
	private boolean isCaracterValid (char c){
		
		char group[] = {'A','B','C','D','E','F','a','b','c','d','e','f',
		                        '1','2','3','4','5','6','7','8','9','0'};
		boolean retorno = false;
		
		for (char g : group){
			if (c == g){
				retorno = true;
			}
		}
		return retorno;
	}
}
