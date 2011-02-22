package com.niblvl50.ninja.entities;

import com.niblvl50.ninja.Textures;
import com.niblvl50.ninja.collisionhandler.CollisionHandler;
import com.niblvl50.ninja.collisionhandler.ICollidableVisitor;

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
