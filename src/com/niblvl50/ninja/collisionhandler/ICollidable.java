package com.niblvl50.ninja.collisionhandler;

import com.niblvl50.ninja.entities.Artifact;
import com.niblvl50.ninja.entities.BlackNinja;
import com.niblvl50.ninja.entities.GameObject;
import com.niblvl50.ninja.entities.RyuHayabusa;

public interface ICollidable
{
	public void acceptCollision(ICollidableVisitor visitor);
	
	public void onCollideWith(RyuHayabusa object);
	public void onCollideWith(BlackNinja object);
	public void onCollideWith(Artifact object);
}
