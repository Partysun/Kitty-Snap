package com.partysun.cats.entities;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс описывает сущности волны объектов.
 * Волна состоит из некоторого количества линий объектов.
 * Каждая линия имеет свою задержку которая оисывается списком
 * delayLine.
 * @author yura
 *
 */
public class Wave {

	private ArrayList<Integer> delayLine = new ArrayList<Integer>();
	private ArrayList<Arrays> line = new ArrayList<Arrays>();
	
	public int size(){
		return delayLine.size();
	}

	public int getDelayLine(int i) {
		return delayLine.get(i);
	}

	public void setLine(int c) {
		this.delayLine.add(c);
	}

	public Arrays getLine(int i) {
		return line.get(i);
	}

	public void setType(Arrays t) {
		this.line.add(t);
	}
}
