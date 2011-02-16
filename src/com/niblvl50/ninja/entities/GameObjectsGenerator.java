package com.niblvl50.ninja.entities;

import java.util.Arrays;
import java.util.HashMap;

import org.anddev.andengine.util.MathUtils;

import com.niblvl50.ninja.NinjaActivity;
import com.niblvl50.ninja.TempSettingsClass;
import com.partysun.cat.pool.ArtifactPool;

public class GameObjectsGenerator {

	private long spawnArtifactTimer = 0;
	private float delayArtifactSpawn = 0.0f;
	// Скорость падения артефактов.
	private float speed = 50f;
	// Задержка в появлении артефактов.
	private int spawntime = 5;
	// Частота появления бонуса "Щита".
	private int shieldFrequency = 10;
	// Частота появления бонуса "Бонус".
	private int bonusFrequency = 50;
	// Частота появления бонуса "Враг/Наковальня".
	private int enemyFrequency = 30;
		
	public GameObjectsGenerator() {
		super();
	}

	public void spawnNewArtifact()
	{
		speed = TempSettingsClass.getInstance().getSpeedofartifact();
		spawntime = TempSettingsClass.getInstance().getSpeedofspwantime();
		
		final long fireValue = System.currentTimeMillis() - spawnArtifactTimer;

		if (fireValue > delayArtifactSpawn*100 + spawntime) {
			Artifact pArtifact = ArtifactPool.getInstance().obtainPoolItem();
			pArtifact.setPosition(MathUtils.random(0,NinjaActivity.WORLD_WIDTH-pArtifact.getWidth()),-pArtifact.getHeight());
			pArtifact.setVelocityY(speed);
			pArtifact.setArtifactType(getRandomArtifactType());
			spawnArtifactTimer = System.currentTimeMillis();
			this.delayArtifactSpawn = findIslandSpawnDelay();			
		}	
	}
	
	private int getRandomArtifactType() {		
		int dice = MathUtils.random(0,100);		
		
		HashMap<Integer, Integer> freqq = new HashMap<Integer, Integer>();
		shieldFrequency = TempSettingsClass.getInstance().getShieldFreq();
		bonusFrequency = TempSettingsClass.getInstance().getBonusFreq();
		enemyFrequency = TempSettingsClass.getInstance().getEnemyFreq();
		
		freqq.put(shieldFrequency, Artifact.SHIELD);
		freqq.put(bonusFrequency, Artifact.BONUS);
		freqq.put(enemyFrequency, Artifact.ENEMY);		
		
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
}
