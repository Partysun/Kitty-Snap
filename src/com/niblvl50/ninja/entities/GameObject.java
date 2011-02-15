package com.niblvl50.ninja.entities;

import java.util.HashMap;
import java.util.Map;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.niblvl50.ninja.collisionhandler.ICollidable;
import com.niblvl50.ninja.controller.IController;


public abstract class GameObject extends AnimatedSprite implements ICollidable
{
	private float lastXPos = 0;
	private boolean canJump = true;
	
	private Map<Integer, IAnimationSequence> mSequences = new HashMap<Integer, IAnimationSequence>();
	
	public GameObject(float x, float y, TiledTextureRegion texture)
	{
		super(x, y, texture.getWidth(), texture.getHeight(), texture);
	}
	
	public GameObject(float x, float y, int sizex, int sizey, TiledTextureRegion texture)
	{
		super(x, y, sizex, sizey, texture);
	}
	
	@Override
	public void onPositionChanged()
	{
		super.onPositionChanged();
		
		if(lastXPos < this.getX())
			this.flipSprite(false);
		else if(lastXPos > this.getX())
			this.flipSprite(true);
		
		lastXPos = this.getX();
	}
	
	public void setAnimationSequence(int key)
	{
		IAnimationSequence sequence = mSequences.get(key);
		if(sequence == null)
			return;
		
		sequence.runSequence(this);
	}
	
	public void addAnimationSequence(int key, IAnimationSequence sequence)
	{
		mSequences.put(key, sequence);
	}
	
	public void flipSprite(boolean flip)
	{
		this.getTextureRegion().setFlippedHorizontal(flip);
	}
	
	public void attachController(IController controller)
	{
		controller.registerGameObject(this);
	}
	
	public void canJump(boolean jump)
	{
		this.canJump = jump;
	}
	
	public boolean canJump()
	{
		return this.canJump;
	}
	
	@Override
	public void onCollideWith(RyuHayabusa object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollideWith(BlackNinja object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollideWith(Artifact object) {
		// TODO Auto-generated method stub
		
	}


}
