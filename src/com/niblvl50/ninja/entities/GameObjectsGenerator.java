package com.niblvl50.ninja.entities;

import org.anddev.andengine.util.MathUtils;

import com.niblvl50.ninja.NinjaActivity;
import com.partysun.cat.pool.ArtifactPool;

public class GameObjectsGenerator {

	private long spawnArtifactTimer = 0;
	private float delayArtifactSpawn = 0.0f;
	private float speed = 50f;
		
	public GameObjectsGenerator() {
		super();
	}

	public void spawnNewArtifact()
	{
		final long fireValue = System.currentTimeMillis() - spawnArtifactTimer;

		if (fireValue > delayArtifactSpawn * 800) {
			Artifact pArtifact = ArtifactPool.getInstance().obtainPoolItem();
			pArtifact.setPosition(MathUtils.random(0,NinjaActivity.WORLD_WIDTH-pArtifact.getWidth()),-pArtifact.getHeight());
			pArtifact.setVelocityY(speed);
			pArtifact.setArtifactType(getRandomArtifactType());
			spawnArtifactTimer = System.currentTimeMillis();
			this.delayArtifactSpawn = findIslandSpawnDelay();			
		}	
	}
	
	private int getRandomArtifactType() {		
		return MathUtils.random(1, Artifact.TYPES.size()); 
	}

	private float findIslandSpawnDelay() {
		float pause = MathUtils.random(2,5);	
		return pause;
		}
}
