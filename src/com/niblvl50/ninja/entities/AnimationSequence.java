package com.niblvl50.ninja.entities;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;

public class AnimationSequence implements IAnimationSequence, IAnimationListener
{
	private final int startFrame;
	private final int endFrame;
	private final long[] frameDurations;
	private final boolean loopAnimation;
	
	public AnimationSequence(int start, int stop, long[] durations, boolean loop)
	{
		this.startFrame = start;
		this.endFrame = stop;
		this.frameDurations = durations;
		this.loopAnimation = loop;
		
		if(this.endFrame - this.startFrame < this.frameDurations.length -1)
		{
			throw new RuntimeException("Frame durations are less than the amount of frames!");
		}
	}

	@Override
	public void runSequence(AnimatedSprite sprite)
	{
		if(!loopAnimation)
			sprite.animate(frameDurations, startFrame, endFrame, 0, this);
		else
			sprite.animate(frameDurations, startFrame, endFrame, loopAnimation);
	}

	@Override
	public void onAnimationEnd(AnimatedSprite arg0)
	{
		// TODO Auto-generated method stub
	}

}
