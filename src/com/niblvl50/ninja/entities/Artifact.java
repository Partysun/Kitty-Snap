package com.niblvl50.ninja.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.niblvl50.ninja.NinjaActivity;
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

	private int type = BONUS;

	public Artifact(TiledTextureRegion pTextureRegion) {
		super(0, 0, 28, 28, pTextureRegion.clone());
		CollisionHandler.instance().register(this);
		EventBus.dispatch(new EntitySpawnedEvent(this));
		this.loadAnimationSequences();		
	}

	private void loadAnimationSequences() {
		final long[] runningDuration = { 100, 100 };

		this.addAnimationSequence(STOP, new StopSequence(0));
		this.addAnimationSequence(BONUS, new AnimationSequence(0, 1,
				runningDuration, true));
		this.addAnimationSequence(ENEMY, new AnimationSequence(2, 3,
				runningDuration, true));
		this.addAnimationSequence(SHIELD, new StopSequence(0));
	}

	@Override
	public void onPositionChanged() {

		super.onPositionChanged();

		final float y = this.getY();
		if (y > NinjaActivity.WORLD_HEIGHT)
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
