package com.niblvl50.ninja.entities;

import java.util.Arrays;
import java.util.HashMap;

import org.anddev.andengine.util.MathUtils;

import android.util.Log;

import com.niblvl50.ninja.GameActivity;
import com.niblvl50.ninja.TempSettingsClass;
import com.partysun.cat.pool.ArtifactPool;

public class GameObjectsGenerator {

	private long spawnArtifactTimer = 0;
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
		
	public GameObjectsGenerator() {
		super();
	}

	public void spawnNewArtifact()
	{	
		speed = TempSettingsClass.getInstance().getSpeedofartifact();
		spawntime = TempSettingsClass.getInstance().getSpeedofspwantime();	
		
		
		long start = System.currentTimeMillis() - timer;
		if (start> 1000)
		{
			Log.d("TIMER", "TICK!");
			if (!LINEOFDEATH && !CUSTOMRANDOM){
				int dice = MathUtils.random(0, 100);
			if (dice >= 0 && dice < 5) {
				LINEOFDEATH = true;
			} 
			if (dice >= 5 && dice < 100) {
				CUSTOMRANDOM = true;
				
			}
			count = System.currentTimeMillis();
			}
			
			timer = System.currentTimeMillis();
		}
		
		if (CUSTOMRANDOM)
		{
			if (count+10000 > System.currentTimeMillis())
				CUSTOMRANDOM =false;
			long fireValue = System.currentTimeMillis() - spawnArtifactTimer;
			if (fireValue > delayArtifactSpawn * 100 + spawntime) {
				Artifact pArtifact = ArtifactPool.getInstance()
						.obtainPoolItem();
				pArtifact.setPosition(MathUtils.random(0,
						GameActivity.WORLD_WIDTH - pArtifact.getWidth()),
						-pArtifact.getHeight());
				pArtifact.setVelocityY(speed);
				pArtifact.setArtifactType(getRandomArtifactType());
				spawnArtifactTimer = System.currentTimeMillis();
				this.delayArtifactSpawn = findIslandSpawnDelay();
			}
		}
		
		if (LINEOFDEATH)
		{
			if (count+6000 > System.currentTimeMillis())
				lineOfDeath(true,0);			
		}
		if (count==0)
		count = System.currentTimeMillis();
		
	}
	
	/*
	 * Создает линию смерти - линия смертельных бонусов с двумя пропусками,
	 *  в которых могут стоять бонусы либо они могут быть пустыми.
	 */
	private void lineOfDeath(boolean emptyLine, int typeBonus)
	{
		int countElements = 10;
		int freePlace = MathUtils.random(0,countElements);
		for(int i=0;i<=countElements;i++)
		{
			if (!emptyLine) {
				if (i == freePlace && i == freePlace + 1) {
					createArtifact(i * (Artifact.WIDTH + 6), -Artifact.HEIGHT,
							Artifact.ROM);
				}
			} else {
				continue;
			}				
			createArtifact(i*(Artifact.WIDTH+6),-Artifact.HEIGHT, typeBonus);
		}
		LINEOFDEATH = false;
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
	
	/*private boolean dice(int f)
	{
		int dice = MathUtils.random(0, 100);
		if (dice >= 0 && dice < f) {
	       return true;
		} 
		return false;
	}*/
}
