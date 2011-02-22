package com.niblvl50.ninja.collisionhandler;

import com.niblvl50.ninja.entities.Artifact;
import com.niblvl50.ninja.entities.BlackNinja;
import com.niblvl50.ninja.entities.RyuHayabusa;

public interface ICollidableVisitor
{
	public void setObject(ICollidable object);
	
	public void Visit(RyuHayabusa ryu);
	public void Visit(BlackNinja bn);
	public void Visit(Artifact art);
}
