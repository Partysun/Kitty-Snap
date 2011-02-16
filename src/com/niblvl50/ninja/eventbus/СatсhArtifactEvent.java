package com.niblvl50.ninja.eventbus;

import com.niblvl50.ninja.entities.Artifact;

/**
 * Описывает событие столкновения героя с артифактами падающими ему на голову.
 * @author Zatsepin Yura 
 */
public class СatсhArtifactEvent {

	private Artifact artifact;
	
	public СatсhArtifactEvent(Artifact artifact)
	{		
		this.artifact = artifact;
	}
	
	public int getArtifactScore()
	{	
		int type = getArtifactType();
		if (type == Artifact.ENEMY)
			return -100;
		if (type == Artifact.SHIELD)
			return 10;
		return 20;
	}
	
	public int getArtifactType()
	{
		return this.artifact.getArtifactType();
	}
}
