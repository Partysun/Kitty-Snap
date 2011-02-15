package com.niblvl50.ninja.modifiers;

import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.MoveYModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceShapeModifier;
import org.anddev.andengine.entity.shape.modifier.ease.EaseExponentialOut;
import org.anddev.andengine.util.modifier.IModifier;

import com.niblvl50.ninja.entities.GameObject;
import com.niblvl50.ninja.entities.RyuHayabusa;

public class JumpModifier extends SequenceShapeModifier
{
	private static final float jumpHeight = 30.0f;
	private static final float jumpDurationUp = 0.5f;
	private static final float jumpDurationDown = 0.25f;
	
	public JumpModifier(float y)
	{
		super(	new JumpFinishedListener(), 
				new MoveYModifier(jumpDurationUp, y, y - jumpHeight, EaseExponentialOut.getInstance()), 
				new MoveYModifier(jumpDurationDown, y - jumpHeight, y));
		
		this.setRemoveWhenFinished(true);
	}
	
	private static class JumpFinishedListener implements IShapeModifierListener
	{
		@Override
		public void onModifierFinished(IModifier<IShape> modifier, IShape shape)
		{
			GameObject object = (GameObject)shape;
			object.canJump(true);
			object.setAnimationSequence(RyuHayabusa.RUNNING);
		}
	}
}
