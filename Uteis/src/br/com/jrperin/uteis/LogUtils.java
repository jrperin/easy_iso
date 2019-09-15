package br.com.jrperin.uteis;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class LogUtils {
	
	public static String getLogDataHora(){
	Calendar cal = new GregorianCalendar();
	String returnText = String.format("[%04d/%02d/%02d - %02d:%02d:%02d]", 
					 cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
					 cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
	return returnText;

	}
}