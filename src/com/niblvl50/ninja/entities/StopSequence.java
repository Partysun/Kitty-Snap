package com.niblvl50.ninja.entities;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

public class StopSequence implements IAnimationSequence
{
	private final int stopFrame;
	
	public StopSequence(int frame)
	{
		this.stopFrame = frame;
	}

	@Override
	public void runSequence(AnimatedSprite sprite)
	{
		sprite.stopAnimation(this.stopFrame);
	}

}
