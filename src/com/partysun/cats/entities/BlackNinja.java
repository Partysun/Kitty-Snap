package com.partysun.cats.entities;

import com.partysun.cats.Textures;
import com.partysun.cats.collisionhandler.CollisionHandler;
import com.partysun.cats.collisionhandler.ICollidableVisitor;

public class BlackNinja extends GameObject
{

	public BlackNinja()
	{
		super(0, 0, 32, 32, Textures.ryu);
		CollisionHandler.instance().register(this);
	}

	@Override
	public void acceptCollision(ICollidableVisitor visitor)
	{
		visitor.Visit(this);
	}
	
}
