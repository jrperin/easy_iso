package br.com.easyiso.testes;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class CarregaMsgIso {

	public static void main(String ... args){
		CarregaMsgIso cmi = new CarregaMsgIso();
	}
	
	public CarregaMsgIso(){
		File arquivo = new File("msgISOMastercard.xml");

		SAXBuilder sb = new SAXBuilder();

		Document d = null;

		try {
			d = sb.build(arquivo);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		Element msgDef = d.getRootElement();
		//System.out.println(msgDef.getAttribute("msgType") + " " + msgDef.getAttribute("version"));
		Element msgType = msgDef.getChild("msgType");
		System.out.println(msgType.getAttributeValue("type") + " versão: " + msgType.getAttributeValue("version"));
		List deArray = msgType.getChildren();
		Iterator i = deArray.iterator();
		while (i.hasNext()){
			Element de = (Element) i.next();
			System.out.print("\n" + de.getAttributeValue("id") + " ");
			System.out.print(de.getAttributeValue("status") + " ");
			System.out.print(de.getAttributeValue("lenOfLen") + " ");
			System.out.print(de.getAttributeValue("lenMax") + " ");
			System.out.print(de.getAttributeValue("dataType") + " ");
			System.out.print(de.getAttributeValue("mandate") + " ");
			System.out.print(de.getAttributeValue("description") + " ");
			System.out.print(de.getAttributeValue("class") + " ");
		}


	}

}
