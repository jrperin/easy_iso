package br.com.easyiso.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * <p>StatusBar � um componente para trabalhar com barra de status. Muito similar a barra de estatus
 * de navegadores de internet (browsers). � composto basicamente por um <b>String</b> status, que 
 * indica uma mensagem de processamento e uma barra de progress�o (<b>JProgressBar</b>).</p>
 * 
 * <p>Seu principal m�todo � o {@link #setStatus(String, int, boolean)}. Para mais informa��es veja 
 * a documenta��o do m�todo.</p>
 * 
 * @author Filipe Luchini
 * */

@SuppressWarnings("serial")
public class StatusBar extends JPanel {
	
	private JProgressBar progressBar;
	private int progress, loader, delay;
	private JLabel statusText;
	private String defaulStatusText;
	
	
	/**
	 * Construtor padr�o.
	 * */
	public StatusBar(){
		this.defaulStatusText = "Conclu�do!";
		this.initStatusBar(false);
	}
	
	
	/**
	 * Construtor no qual pode ser definido o texto de estado padr�o da barra de status.
	 * @param defaultStatusText : texto de estado padr�o.
	 * */
	public StatusBar(String defaultStatusText){
		this.defaulStatusText = defaultStatusText;
		this.initStatusBar(false);
	}
	
	
	/**
	 * Construtor no qual pode ser definido o texto de estado padr�o da barra de status e se, quando
	 * carregar, vai ser cont�nua ou n�o.
	 * @param defaultStatusText : texto de estado padr�o.
	 * @param indeterminate : deve ser passado <code>true</code> caso voc� queira que a barra de 
	 * progresso seja cont�nua.
	 * */
	public StatusBar(String defaultStatusText, boolean indeterminate){
		this.defaulStatusText = defaultStatusText;
		this.initStatusBar(indeterminate);
	}
		
	
	private void initStatusBar(boolean indeterminate){
		this.delay = 7;
		this.progress = 0;
		this.statusText = new JLabel(defaulStatusText);
		this.progressBar = new JProgressBar();
		if(indeterminate){
			this.progressBar.setIndeterminate(true);
		}	
		this.progressBar.setValue(progress);
		this.progressBar.setVisible(false);
		
		this.setLayout(new BorderLayout());
		this.add(statusText, BorderLayout.WEST);
		this.add(progressBar, BorderLayout.EAST);		
	}
	
	
	/**
	 * <p>Informa qual � o status atual do StatusBar</p>
	 * 
	 * @param status = texto que indica o status corrente de processo.
	 * @param load = valor em % (percentual de 0% a 100%) que equivale o processo.
	 * @param loading = caso seja true ent�o ser� gerado a amina��o de x% a y% da barra de progresso.
	 * Onde x% � o ultimo estado da barra de procresso e y% (<b>load</b>) � at� onde a barra de progresso deve ser
	 * carregada. Caso seja informado 100% no argumento <b>load</b> ent�o a barra de progresso ser�
	 * carregada at� o fim e finalizada com o status padr�o.   
	 * */
	public void setStatus(String status, int load, boolean loading){
		if(load < 0 || load > 100){
			Exception e = new Exception("O valor de procresso (load) deve estar entre 0% e 100%");
			e.printStackTrace();
		}else{
			
			if(load == 100){
				finalizeLoad();
			}else{
				statusText.setText(status);
				progressBar.setValue(progress);
				progressBar.setVisible(true);
				
				if(loading){			
					load(load);
				}else{
					progress = loader;
					progressBar.setValue(progress);
				}				
			}
		}		
	}
	
	
	private void load(int load){
		
		loader = load;
		
		Thread progressive = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = progress; i < loader; i++) {
					
					progressBar.setValue(i);
					
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				progress = loader;
				progressBar.setValue(progress);				
			}
		});
		progressive.start();
	}
	
	/**
	 * <p>Finaliza a prograss�o de barra de progresso e posiciona o status de processo no status
	 * pad�o do {@link StatusBar}.</p>
	 * */
	public void finalizeLoad(){
		
		Thread progressive = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = progress; i < 100; i++) {
					
					progressBar.setValue(i);
					
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				progress = 0;
				progressBar.setValue(progress);
				progressBar.setVisible(false);
				statusText.setText(defaulStatusText);
			}
		});
			
		progressive.start();
	}

	
	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		if(progress < 0 || progress > 100){
			Exception e = new Exception("O valor de procresso (load) deve estar entre 0% e 100%");
			e.printStackTrace();
		}else{
			this.progress = progress;
			if(progress == 100){
				finalizeLoad();
			}
		}
	}

	
	/**
	 * <p>
	 * Define o tempo de carregamento da barra de progresso.
	 * </p>
	 * @param delay : tempo de atraso (em ms).
	 * */
	public void setDelay(int ms){
		delay = ms;
	}
	
	
	public String getStatusText() {
		return statusText.getText();
	}

	public void setStatusText(String statusText) {
		this.statusText.setText(statusText);
	}

	public String getDefaulStatusText() {
		return defaulStatusText;
	}

	public void setDefaulStatusText(String defaulStatusText) {
		this.defaulStatusText = defaulStatusText;
		this.finalizeLoad();
	}
}


