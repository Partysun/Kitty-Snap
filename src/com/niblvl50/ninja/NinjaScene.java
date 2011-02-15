package com.niblvl50.ninja;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.niblvl50.ninja.controller.NinjaController;
import com.niblvl50.ninja.entities.Artifact;
import com.niblvl50.ninja.entities.GameObjectsGenerator;
import com.niblvl50.ninja.entities.RyuHayabusa;
import com.niblvl50.ninja.eventbus.EventChangeGameStatus;
import com.niblvl50.ninja.eventbus.EntitySpawnedEvent;
import com.niblvl50.ninja.eventbus.EventBus;
import com.niblvl50.ninja.eventbus.EventHandler;
import com.niblvl50.ninja.eventbus.СatсhArtifactEvent;

public class NinjaScene extends Scene
{
	private ParallaxBackground mBackground = null;
	private float parallaxValue = 0.0f;
	private boolean isIslandGenerate = false;
	private GameObjectsGenerator ggenerator;
	private RyuHayabusa ninja = null;

	public NinjaScene()
	{
		super(1);
		EventBus.register(this);
		
		ninja = new RyuHayabusa();
		ninja.attachController(new NinjaController());
		ninja.setPosition(100, 170);
		this.getTopLayer().addEntity(ninja);
		
		mBackground = new ParallaxBackground(1.0f, 1.0f, 1.0f);
		mBackground.addParallaxEntity(new ParallaxEntity(0.0f, this.createParallaxSprite(0.0f, Textures.backgroundLand)));
		mBackground.addParallaxEntity(new ParallaxEntity(0.002f, this.createParallaxSprite(0.0f, Textures.backgroundCloud)));
		//mBackground.addParallaxEntity(new ParallaxEntity(0.0f, this.createParallaxSprite(166.0f, Textures.backgroundGrass)));
		//mBackground.addParallaxEntity(new ParallaxEntity(0.0f, this.createParallaxSprite(140.0f, Textures.backgroundBushes)));
		this.setBackground(mBackground);
		
		// Создаем генератор Артефактов, которые будут падать на игрока.
		ggenerator = new GameObjectsGenerator();
		// Состояние работы генератора.
		isIslandGenerate = true;
	}
	
	@Override
	public void onManagedUpdate(float secondsElapsed)
	{
		super.onManagedUpdate(secondsElapsed);
		
		this.parallaxValue += 60f;
		this.mBackground.setParallaxValue(-parallaxValue);
		if (isIslandGenerate)
			ggenerator.spawnNewArtifact();
	}
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent)
	{
		EventBus.dispatch(pSceneTouchEvent);		
		return true;
	}
	
	private Sprite createParallaxSprite(float y, TextureRegion texture)
	{
		return new Sprite(0, y, texture.getWidth(), texture.getHeight(), texture);
	}
	
	@EventHandler
	public void onGameObjectSpawnedEvent(EntitySpawnedEvent event) {
		this.getTopLayer().addEntity(event.object);		
	}
	
	/*
	/**
	 * Вычисляем какой артифакт упал на голову нашего персонажа.
	 *  -- Положительный эффект бонусы:
	 * 		Щит - одеваем щит и даем защиту от 1 смерти. (Пузырек)
	 * 		СпидХак - увеличивает скорость персонажа. (Ботинок)
	 * 		Замедление времени - замедляет время падения предметов. (Часики песочные)
	 *  -- Отрицательный эффект:
	 *  	Болото - замедляет главного героя. ()
	 *  	На время все что падает становится врагами. (Кукла Вуду)
	 *  	Инвертировать контроллер управления (Бутылка Рома) 	
	 *   
	 * @param catchEvent
	 */

	@EventHandler
	public void onCatchArtifactEvent(СatсhArtifactEvent catchEvent) {
		int curArtifactType = catchEvent.getArtifactType(); 
		
		if (curArtifactType == Artifact.SHIELD){			
			ninja.setShield();		
		}
		
		if (curArtifactType == Artifact.ENEMY){			
			if (ninja.isShield())				
				ninja.killShield();
			else
				ninja.life -= 1;			
		}
		
		EventBus.dispatch(new EventChangeGameStatus(EventChangeGameStatus.PAUSE,this.ninja));
	}
		
 	
		//case Artifact.TYPES.indexOf("SPEEDHACK"): 
			//TODO Action
		//	break;
		//case Artifact.TYPES.indexOf("SLOWTIME"):
		//	//TODO Action
		//	break;
		//case Artifact.TYPES.indexOf("SWAMP"):
		//	//TODO Action
		//	break;
		//case Artifact.TYPES.indexOf("VOODOO"):
		//	//TODO Action
		//	break;
		//case Artifact.TYPES.indexOf("ROME"):
		//	//TODO Action
		//	break;
		
		
	
	
	
}
