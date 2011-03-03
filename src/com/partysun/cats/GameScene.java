package com.partysun.cats;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.shape.modifier.ScaleModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.modifier.ease.EaseBounceOut;

import com.partysun.cats.controller.NinjaController;
import com.partysun.cats.entities.Artifact;
import com.partysun.cats.entities.GameObjectsGenerator;
import com.partysun.cats.entities.RyuHayabusa;
import com.partysun.cats.eventbus.EntitySpawnedEvent;
import com.partysun.cats.eventbus.EventBus;
import com.partysun.cats.eventbus.EventChangeGameStatus;
import com.partysun.cats.eventbus.EventHandler;
import com.partysun.cats.eventbus.СatсhArtifactEvent;

public class GameScene extends Scene
{
	private ParallaxBackground mBackground = null;
	private float parallaxValue = 0.0f;
	private boolean isIslandGenerate = false;
	private GameObjectsGenerator ggenerator;
	private RyuHayabusa kitty = null;
	private HUD hud;
	private int entityTopIndex = 0;

	public GameScene(HUD hud)
	{
		super(1);
		EventBus.register(this);
		
		this.hud = hud;
		entityTopIndex = hud.getTopLayer().getEntityCount();
		
		kitty = new RyuHayabusa();
		kitty.attachController(new NinjaController());
		kitty.setPosition(100, 170);
		this.getTopLayer().addEntity(kitty);
		
		mBackground = new ParallaxBackground(1.0f, 1.0f, 1.0f);
		mBackground.addParallaxEntity(new ParallaxEntity(0.0f, this.createParallaxSprite(0.0f, Textures.backgroundLand)));
		mBackground.addParallaxEntity(new ParallaxEntity(0.002f, this.createParallaxSprite(0.0f, Textures.backgroundCloud)));
		
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
		
		if (curArtifactType != Artifact.BONUS && curArtifactType != Artifact.ENEMY)
			purgeEffect();
		
		if (curArtifactType == Artifact.SHIELD){			
			kitty.setShield();		
			setEffectInHUD(6);
		}
		
		if (curArtifactType == Artifact.ENEMY){			
			if (kitty.isShield()){	
				Textures.artifact.setCurrentTileIndex(6);
				final TiledSprite effectSprite = new TiledSprite(GameActivity.WORLD_WIDTH-40, 5, 32, 32, Textures.artifact);
				effectSprite.setScale(1.3f);
				float scaleIn = 1.3f;
				effectSprite.addShapeModifier(new ScaleModifier(3, scaleIn, 0f, EaseBounceOut.getInstance()));
				hud.getTopLayer().setEntity(entityTopIndex,effectSprite);
				kitty.killShield();
				}
			else
				kitty.life -= 1;			
		}
		
		if (curArtifactType == Artifact.SPEEDHACK){			
			kitty.speed = 100;		
			setEffectInHUD(5);
		}
		
		if (curArtifactType == Artifact.SLOWTIME){			
			TempSettingsClass.getInstance().setSpeedofartifact(20f);
			setEffectInHUD(4);
		}
		
		if (curArtifactType == Artifact.ROM){			
			kitty.speed = -kitty.speed;
			setEffectInHUD(2);
		}		
		
		EventBus.dispatch(new EventChangeGameStatus(EventChangeGameStatus.PAUSE,this.kitty));
	}
	
	private void setEffectInHUD(int intTile) {
		Textures.artifact.setCurrentTileIndex(intTile);
		final TiledSprite effectSprite = new TiledSprite(GameActivity.WORLD_WIDTH-40, 5, 32, 32, Textures.artifact);
		effectSprite.setScale(0);
		float scaleOut = 1.3f;
		effectSprite.addShapeModifier(new ScaleModifier(3, 1, scaleOut, EaseBounceOut.getInstance()));
		
		hud.getTopLayer().setEntity(entityTopIndex, effectSprite);
	}

	private void purgeEffect()
	{
		TempSettingsClass.getInstance().getPref();
		kitty.killShield();
		kitty.speed = kitty.DEFAUL_SPEED;		
	}

	public void wipe() {
		stopGeneratorObjects();
		purgeEffect();
	}

	public void stopGeneratorObjects() {
		isIslandGenerate = false;			
	}

	public void startGeneratorObjects() {
		isIslandGenerate = true;				
	}
}
