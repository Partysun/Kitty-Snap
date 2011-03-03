package com.partysun.cats.entities;

import com.partysun.cats.GameActivity;
import com.partysun.cats.TempSettingsClass;
import com.partysun.cats.Textures;
import com.partysun.cats.collisionhandler.CollisionHandler;
import com.partysun.cats.collisionhandler.ICollidableVisitor;
import com.partysun.cats.controller.IController;

public class RyuHayabusa extends GameObject
{
	public static final int STOP = 0;
	public static final int RUNNING = 1;
	public static final int JUMPING = 2;
	public static final int KILLING = 3;
	public int DEFAUL_SPEED = TempSettingsClass.getInstance().getDefaultSpeed();
	public int life = 1;
	private boolean isShield = false;
	public int speed = DEFAUL_SPEED;
	
	public RyuHayabusa()
	{
		super(0, 0, 32, 32, Textures.ryu);
		
		CollisionHandler.instance().register(this);
		
		this.loadAnimationSequences();
		this.setAnimationSequence(RUNNING);
	}

	private void loadAnimationSequences()
	{
		final long[] runningDuration = {100, 100, 100, 100 };
		//final long[] jumpingDuration = { 100, 100, 100, 100 };
		//final long[] killingDuration = { 100, 100, 100, 100 };
		
		this.addAnimationSequence(STOP, new StopSequence(0));
		this.addAnimationSequence(RUNNING, new AnimationSequence(0, 3, runningDuration, true));
		//this.addAnimationSequence(JUMPING, new AnimationSequence(4, 7, jumpingDuration, true));
		//this.addAnimationSequence(KILLING, new AnimationSequence(8, 11, killingDuration, false));
	}
	
	@Override
	public void onPositionChanged()
	{
		super.onPositionChanged();
		
		if(this.mX + this.getWidth() < 0)
			this.mX = GameActivity.WORLD_WIDTH;
		else if(this.mX > GameActivity.WORLD_WIDTH)
			this.mX = 0 - this.getWidth();
	}

	@Override
	public void acceptCollision(ICollidableVisitor visitor)
	{
		visitor.Visit(this);
	}

	public void attachController(IController controller)
	{
		controller.registerGameObject(this);
	}
	
	public void setShield() {
		this.isShield = true;	
	}
	
	public boolean isShield() {
		return this.isShield;	
	}

	public void killShield() {
		this.isShield = false;
	}

}
