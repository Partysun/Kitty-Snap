package com.niblvl50.ninja.controller;

import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;

import com.niblvl50.ninja.entities.RyuHayabusa;
import com.niblvl50.ninja.eventbus.EventBus;
import com.niblvl50.ninja.eventbus.EventHandler;
import com.niblvl50.ninja.modifiers.JumpModifier;


public class NinjaController implements IController, IAccelerometerListener
{
	private RyuHayabusa object = null;
	
	@Override
	public void registerGameObject(RyuHayabusa object)
	{
		this.object = object;
		EventBus.register(this);
		EventBus.dispatch(this);
	}
	
	@EventHandler
	public void onTouchEvent(TouchEvent event)
	{
		if(event.getAction() == TouchEvent.ACTION_DOWN)
		{
			if(object.canJump())
			{
				object.setAnimationSequence(RyuHayabusa.JUMPING);
				object.canJump(false);
				object.addShapeModifier(new JumpModifier(object.getY()));
			}
		}
	}

	@Override
	public void onAccelerometerChanged(AccelerometerData data)
	{
		object.setVelocityX(data.getY() * object.speed);
	}

}
