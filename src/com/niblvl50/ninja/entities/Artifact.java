package com.niblvl50.ninja.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.niblvl50.ninja.GameActivity;
import com.niblvl50.ninja.collisionhandler.CollisionHandler;
import com.niblvl50.ninja.collisionhandler.ICollidableVisitor;
import com.niblvl50.ninja.eventbus.EntitySpawnedEvent;
import com.niblvl50.ninja.eventbus.EventBus;
import com.niblvl50.ninja.eventbus.СatсhArtifactEvent;
import com.partysun.cat.pool.ArtifactPool;

public class Artifact extends GameObject {

	public static final int STOP = 0;
	public static final int BONUS = 1;
	public static final int ENEMY = 2;
	public static final int SHIELD = 3;
	public static final int SPEEDHACK = 4;
	public static final int ROM = 5;
	public static final int SLOWTIME = 6;

	private int type = BONUS;
	
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	public Artifact(TiledTextureRegion pTextureRegion) {
		super(0, 0, WIDTH, HEIGHT, pTextureRegion.clone());
		CollisionHandler.instance().register(this);
		EventBus.dispatch(new EntitySpawnedEvent(this));
		this.loadAnimationSequences();		
	}

	private void loadAnimationSequences() {
		final long[] runningDuration = { 300, 300 };

		this.addAnimationSequence(ENEMY, new StopSequence(0));
		this.addAnimationSequence(SHIELD, new AnimationSequence(6, 7,
				runningDuration, true));
		this.addAnimationSequence(SPEEDHACK, new StopSequence(5));
		this.addAnimationSequence(BONUS, new StopSequence(1));
		this.addAnimationSequence(ROM, new AnimationSequence(2, 3,
				runningDuration, true));
		this.addAnimationSequence(SLOWTIME, new StopSequence(4));
	}

	@Override
	public void onPositionChanged() {

		super.onPositionChanged();

		final float y = this.getY();
		if (y > GameActivity.WORLD_HEIGHT)
			ArtifactPool.getInstance().recyclePoolItem(this);
	}

	@Override
	public void acceptCollision(ICollidableVisitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollideWith(RyuHayabusa object) {
		ArtifactPool.getInstance().recyclePoolItem(this);
		EventBus.dispatch(new СatсhArtifactEvent(this));		

	}

	@Override
	public void onCollideWith(BlackNinja object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollideWith(Artifact object) {
		// TODO Auto-generated method stub

	}

	public void setArtifactType(int type) {
		this.type = type;
		this.setAnimationSequence(type);
	}

	public int getArtifactType() {
		return type;
	}
}
