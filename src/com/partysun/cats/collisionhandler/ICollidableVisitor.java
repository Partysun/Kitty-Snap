package com.partysun.cats.collisionhandler;

import com.partysun.cats.entities.Artifact;
import com.partysun.cats.entities.BlackNinja;
import com.partysun.cats.entities.RyuHayabusa;

public interface ICollidableVisitor
{
	public void setObject(ICollidable object);
	
	public void Visit(RyuHayabusa ryu);
	public void Visit(BlackNinja bn);
	public void Visit(Artifact art);
}
