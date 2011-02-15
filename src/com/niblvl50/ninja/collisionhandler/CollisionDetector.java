package com.niblvl50.ninja.collisionhandler;

import org.anddev.andengine.entity.shape.IShape;

import com.niblvl50.ninja.entities.Artifact;
import com.niblvl50.ninja.entities.BlackNinja;
import com.niblvl50.ninja.entities.RyuHayabusa;

public class CollisionDetector implements ICollidableVisitor
{
	private ICollidable object = null;
	
	@Override
	public void setObject(ICollidable object)
	{
		this.object = object;
	}
		
	@Override
	public void Visit(RyuHayabusa ryu)
	{
		if(ryu.collidesWith((IShape) object))
			this.object.onCollideWith(ryu);
	}

	@Override
	public void Visit(BlackNinja bn) {
		if(bn.collidesWith((IShape) object))
			this.object.onCollideWith(bn);
	}

	@Override
	public void Visit(Artifact art) {
		if(art.collidesWith((IShape) object))
			this.object.onCollideWith(art);
	}
	
}
