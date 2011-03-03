package com.partysun.cats.entities;

import java.util.ArrayList;

/**
 * Класс описывает сущности волны объектов.
 * Волна состоит из некоторого количества линий объектов.
 * Каждая линия имеет свою задержку которая оисывается списком
 * delayLine.
 * @author yura
 *
 */
public class Wave {

	private ArrayList<Long> delayLine = new ArrayList<Long>();
	private ArrayList<int[]> line = new ArrayList<int[]>();
	private long delaySample = 1000;
	
	public Wave(ArrayList<Long> dl, ArrayList<int[]> line)
	{
		this.delayLine = dl;
		this.line =line;
	}
	
	@Override
	public String toString() {
		return display();
	}
	
	public Wave(ArrayList<int[]> line)
	{
		this.line =line;
		this.delayLine.add(new Long(2300));
		for(int i=1;i<line.size();i++)
		{
			this.delayLine.add(delaySample);
		}
	}
	
	public Wave()
	{
	}
	
	public int size(){
		return delayLine.size();
	}

	public Long getDelayLine(int i) {
		return delayLine.get(i);
	}

	public void setDelayLine(Long c) {
		this.delayLine.add(c);
	}

	public int[] getLine(int i) {
		return line.get(i);
	}

	public void setLine(int[] t) {
		this.line.add(t);
	}
	
	public String display(){
		String temp = "";
		for(int i=0;i<delayLine.size();i++){
			temp += (i+1)+". "+delayLine.get(i)+" "+((int[])line.get(i))[0]+"\n";
		}
		return temp;
	}

}
