package com.partysun.cats.entities;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class LevelHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	private static ArrayList<Wave> waves = null;
	Wave temp;
	int count = 0;
	int[] tempInt = null; 
	
	public static ArrayList <Wave> getWaveList() {
		return waves;
	}
/*
	<wave>
    	<enemy type="ship">3</enemy>
    </wave>    
    
     </line>
                <line delay="1000">        	            </line>
	*/
	

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (localName.equals("kitty_snap"))
		{
			waves = new ArrayList<Wave>();
		} else if (localName.equals("wave")) {
			temp = new Wave();
		} else if (localName.equals("line")) {
			String attr = attributes.getValue("delay");
			temp.setDelayLine(Long.parseLong(attr));
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		if (localName.equalsIgnoreCase("line")){
			String [] values = currentValue.split(",");
			int[] line = new int[values.length];
			for(int i=0;i<values.length;i++)
			{
				line[i] = Integer.parseInt(values[i]);
			}
			temp.setLine(line);
			}
			//(Integer.parseInt(currentValue));}
		if (localName.equalsIgnoreCase("wave"))
			waves.add(temp);

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}

	}

  

}
