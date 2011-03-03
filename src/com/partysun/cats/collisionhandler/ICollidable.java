package com.partysun.cats.collisionhandler;

import com.partysun.cats.entities.Artifact;
import com.partysun.cats.entities.BlackNinja;
import com.partysun.cats.entities.RyuHayabusa;

public interface ICollidable
{
	public void acceptCollision(ICollidableVisitor visitor);
	
	public void onCollideWith(RyuHayabusa object);
	public void onCollideWith(BlackNinja object);
	public void onCollideWith(Artifact object);
}
