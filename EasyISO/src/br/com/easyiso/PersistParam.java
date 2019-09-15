package br.com.easyiso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class PersistParam {
	
	private static File actualDirectory = new File("~");
	private static File persistFileName = new File("./app.ini");
	private static String tcpIpNumber = new String("localhost");
	private static String portNumber  = new String("");

	public static void setActualDirectory(File f){
		
		if (f.isDirectory()){
			actualDirectory = f;
		}else {
			actualDirectory = f.getParentFile();
		}
		
		savePersistFile();

	}
	
	public static File getActualDirectory() {

		readPersistFile();
		//actualDirectory = new File(dir);
		
		return actualDirectory;
	}

	public static File getPersistFileName() {
		return persistFileName;
	}


	public static void setPersistFileName(File persistFile) {
		File f = null;
		
		try {
			f = persistFile.getCanonicalFile();
		} catch (IOException e1) {
			f = new File(persistFile.getName());
		}
		
		PersistParam.persistFileName = f;
	}


	public static String getTcpIpNumber() {
		return tcpIpNumber;
	}


	public static void setTcpIpNumber(String tcpIpNumber) {
		PersistParam.tcpIpNumber = tcpIpNumber;
	}


	public static String getPortNumber() {
		return portNumber;
	}


	public static void setPortNumber(String portNumber) {
		PersistParam.portNumber = portNumber;
	}
	
	
	private static void savePersistFile(){
		Element appPersistParam = new Element("appPersistParam");
		appPersistParam.setAttribute("version", "1");
		
		Element directory       = new Element("directory");
		directory.setAttribute("lastDirectory", actualDirectory.toString());
		
		Element tcpIp           = new Element("tcpIp");
		tcpIp.setAttribute("tcpIpNumber", tcpIpNumber);
		tcpIp.setAttribute("portNumber", portNumber);
		
		appPersistParam.addContent(directory);
		appPersistParam.addContent(tcpIp);

		Document doc = new Document();
		doc.setRootElement(appPersistParam);
		XMLOutputter xout = new XMLOutputter();
		
		try{
		OutputStream os = new FileOutputStream(persistFileName);
        OutputStreamWriter out = new OutputStreamWriter(os);
                     out.write(xout.outputString(doc));  
                     out.close();  
		}catch(IOException e){  
			e.printStackTrace();
		}
	}
	
	private static void readPersistFile(){
		
		SAXBuilder sb = new SAXBuilder();
		Document d = null;

		try {
			d = sb.build(persistFileName);
		} catch (JDOMException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (Exception e){
			e.printStackTrace();
			return;
		}

		Element appPersistParam = d.getRootElement();

		try{
			actualDirectory = new File(appPersistParam.getChild("directory").getAttributeValue("lastDirectory"));
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			tcpIpNumber     = (appPersistParam.getChild("tcpIp")).getAttributeValue("tcpIpNumber");
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			portNumber      = (appPersistParam.getChild("tcpIp")).getAttributeValue("portNumber");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
