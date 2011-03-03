package com.partysun.cats.controller;

import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;

import com.partysun.cats.entities.RyuHayabusa;
import com.partysun.cats.eventbus.EventBus;
import com.partysun.cats.eventbus.EventHandler;
import com.partysun.cats.modifiers.JumpModifier;


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
