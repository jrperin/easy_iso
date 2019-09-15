package br.com.jrperin.commoninterfaces;


public interface Observable {

		public void addObserver(Observer or);
		public void removeObserver(Observer or);
		public void notifyObservers(Object ... arg);
}
