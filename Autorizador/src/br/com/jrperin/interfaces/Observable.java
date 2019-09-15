package br.com.jrperin.interfaces;

public interface Observable {

		public void addObserver(Observer or);
		public void removeObserver(Observer or);
		public void notifyObservers();
}
