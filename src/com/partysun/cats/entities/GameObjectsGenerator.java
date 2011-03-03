package com.partysun.cats.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.anddev.andengine.util.MathUtils;

import android.util.Log;

import com.partysun.cats.TempSettingsClass;
import com.partysun.cats.pool.ArtifactPool;

public class GameObjectsGenerator {

	private long spawnArtifactTimer = 0;
	private long spawnLineTimer = 0;
	private int curWaveLine = 0;
	private Wave curWave = null;
	private float delayArtifactSpawn = 0.0f;
	private long timer = 0;
	// Скорость падения артефактов.
	private float speed = 50f;
	// Задержка в появлении артефактов.
	private int spawntime = 5;
	// Частота появления бонуса "Щита".
	private int shieldFrequency = 5;
	// Частота появления бонуса "Бонус".
	private int bonusFrequency = 50;
	// Частота появления бонуса "Враг/Наковальня".
	private int enemyFrequency = 30;
	// Частота появления бонуса "Ром".
	private int romFrequency = 5;
	// Частота появления бонуса "Замедление времени".
	private int slowFrequency = 5;
	// Частота появления бонуса "Ускорение".
	private int speedFrequency = 5;
	
	private boolean LINEOFDEATH =false;
	private boolean CUSTOMRANDOM =true;
	private long count = 0;
	
	private ArrayList<Wave> waves = new ArrayList<Wave>();
		
	public GameObjectsGenerator() {
		super();
	}

	public void spawnNewArtifact()
	{	
		speed = TempSettingsClass.getInstance().getSpeedofartifact();
		spawntime = TempSettingsClass.getInstance().getSpeedofspwantime();	
		
		//long fireValue = System.currentTimeMillis() - spawnArtifactTimer;
		//	if (fireValue > delayArtifactSpawn * 200 + spawntime) {
				/*Artifact pArtifact = ArtifactPool.getInstance()
						.obtainPoolItem();
				pArtifact.setPosition(MathUtils.random(0,
						GameActivity.WORLD_WIDTH - pArtifact.getWidth()),
						-pArtifact.getHeight());
				pArtifact.setVelocityY(speed);
				pArtifact.setArtifactType(getRandomArtifactType());*/
		if (waves.size()>0)		
		if (curWave == null)
				curWave = waves.get(MathUtils.random(0, waves.size()-1));
		if (createWave())
				curWave = null;
				
				
			//	spawnArtifactTimer = System.currentTimeMillis();
			//	this.delayArtifactSpawn = findIslandSpawnDelay();
			//}
	}
	

	/**
	 * Создает волну на экране.
	 * Возвращает true - если волна закончилась и false - если еще нет.
	 * @param wave
	 */
	private boolean createWave()
	{
		long spawnLineValue = System.currentTimeMillis() - spawnLineTimer;
		if (spawnLineValue > curWave.getDelayLine(curWaveLine) ) {
			//Log.d("COL", curWave.size() + " " + curWaveLine);
			int[] line = curWave.getLine(curWaveLine); 
			for(int i=0;i<line.length;i++)
			{
				if (line[i] == 0)
					continue;
				createArtifact(i * (Artifact.WIDTH + 6), -Artifact.HEIGHT,
						line[i]);
			}
			spawnLineTimer = System.currentTimeMillis();
			++curWaveLine;
			if (curWaveLine==curWave.size())
			{
				curWaveLine = 0;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Создает артифакт на экране в позиции xPos,yPos и типом typeBonus
	 * @param xPos
	 * @param yPos
	 * @param typeBonus
	 */
	private void createArtifact(float xPos, float yPos, int typeBonus)
	{
		Artifact pArtifact = ArtifactPool.getInstance().obtainPoolItem();
		pArtifact.setPosition(xPos,yPos);
		pArtifact.setVelocityY(speed);
		pArtifact.setArtifactType(typeBonus);
	}
	
	private int getRandomArtifactType() {		
		int dice = MathUtils.random(0,100);		
		
		HashMap<Integer, Integer> freqq = new HashMap<Integer, Integer>();
		shieldFrequency = TempSettingsClass.getInstance().getShieldFreq();
		bonusFrequency = TempSettingsClass.getInstance().getBonusFreq();
		enemyFrequency = TempSettingsClass.getInstance().getEnemyFreq();
		romFrequency = TempSettingsClass.getInstance().getRomFreq();
		slowFrequency = TempSettingsClass.getInstance().getSlowFreq();
		speedFrequency = TempSettingsClass.getInstance().getSpeedFreq();
		
		freqq.put(shieldFrequency, Artifact.SHIELD);
		freqq.put(bonusFrequency, Artifact.BONUS);
		freqq.put(enemyFrequency, Artifact.ENEMY);
		freqq.put(romFrequency, Artifact.ROM);
		freqq.put(slowFrequency, Artifact.SLOWTIME);
		freqq.put(speedFrequency, Artifact.SPEEDHACK);
		
		Object [] key = freqq.keySet().toArray();
		
		Arrays.sort(key);
		int last = 0;
		for (int i = 0; i < key.length; i++) {
			
			if (last <= dice && dice < last + (Integer) key[i]) {
				return freqq.get(key[i]);
			}
			last = last + (Integer) key[i];
		}			
		return Artifact.BONUS; 
	}

	private float findIslandSpawnDelay() {
		float pause = MathUtils.random(0,2);	
		return pause;
		}
	
	/**
	 * Создаем волны в коде.
	 * Потом можно будет сделать загрузку волн из файла.
	 * @param arrayList 
	 */
	public void initWaves(ArrayList<Wave> arrayList)
	{
		waves = arrayList;
	/*	String temp = "";
		
		for(int i=0;i<arrayList.size();i++){
			temp += (i+1)+". "+i+" "+((Wave)arrayList.get(i))+"\n";
		}
		Log.d("COL", temp+"");
		
		int[] line = new int[11];
		line[0] = Artifact.STOP;
		line[1] = Artifact.STOP;
		line[2] = Artifact.BONUS;
		line[3] = Artifact.BONUS;
		line[4] = Artifact.STOP;
		line[5] = Artifact.STOP;
		line[6] = Artifact.BONUS;
		line[7] = Artifact.BONUS;
		line[8] = Artifact.STOP;
		line[9] = Artifact.BONUS;
		line[10] = Artifact.STOP;
		
		int[] line2 = new int[11];
		line2[0] = Artifact.STOP;
		line2[1] = Artifact.STOP;
		line2[2] = Artifact.STOP;
		line2[3] = Artifact.STOP;
		line2[4] = Artifact.BONUS;
		line2[5] = Artifact.SHIELD;
		line2[6] = Artifact.STOP;
		line2[7] = Artifact.STOP;
		line2[8] = Artifact.STOP;
		line2[9] = Artifact.STOP;
		line2[10] = Artifact.STOP;
		
		ArrayList<int[]> lines = new ArrayList<int[]>();
		lines.add(line);
		lines.add(line2);
		lines.add(line);
		lines.add(line2);
		
		Wave simpleWave = new Wave(lines);
		
		//Log.d("COL", simpleWave.display()+"");
		waves.add(simpleWave);*/
	}
	
	/*private boolean dice(int f)
	{
		int dice = MathUtils.random(0, 100);
		if (dice >= 0 && dice < f) {
	       return true;
		} 
		return false;
	}*/
}
