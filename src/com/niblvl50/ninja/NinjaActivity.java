package com.niblvl50.ninja;

import org.anddev.andengine.engine.Engine;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;

import com.niblvl50.ninja.collisionhandler.CollisionHandler;
import com.niblvl50.ninja.controller.NinjaController;
import com.niblvl50.ninja.eventbus.EventChangeGameStatus;
import com.niblvl50.ninja.eventbus.EventBus;
import com.niblvl50.ninja.eventbus.EventHandler;
import com.niblvl50.ninja.eventbus.СatсhArtifactEvent;
import com.partysun.cat.pool.ArtifactPool;

public class NinjaActivity extends BaseGameActivity
{
	public static final int WORLD_WIDTH = 400;
	public static final int WORLD_HEIGHT = 240;
	public int Score = 0;
	private ChangeableText scoreText;
	private ChangeableText lifesText;
	private ChangeableText shieldText;
	private CameraScene mPauseScene;
	private Scene mGameScene; 
	
	@Override
	public Engine onLoadEngine()
	{		
		Camera camera = new Camera(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
		EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(WORLD_WIDTH, WORLD_HEIGHT), camera);
		return new Engine(options);
	}

	@Override
	public void onLoadResources()
	{
		Textures.load(this);
		ArtifactPool.getInstance().setArtifactTexture(Textures.artifact);
		// Allocate and register the collision handler
		CollisionHandler.instance().reset();
		EventBus.clear();
		EventBus.register(this);
		this.getEngine().registerUpdateHandler(CollisionHandler.instance());
	}

	@Override
	public Scene onLoadScene()
	{  
		makePauseScene();
		setGameScreenHUD();
		this.mGameScene = new NinjaScene();
		return mGameScene;
	}
	
	/*
	 * Устанавливает интерфейс игрока на главную камеру.
	 * Интерфейс должен будет содержать отчет о текущем кол-ве очков игрока,
	 * о кол-ве жизней игрока и также
	 * кнопки для управления персонажем.
	 * кнопка паузы.
	 *  
	 */
	private void setGameScreenHUD()
	{
		// Create a hud basically a overlay for the camera
		final HUD hud = new HUD();
		final ChangeableText fpsText = new ChangeableText(0, 0, Textures.mFont,
				"Fps: ?", "Fps: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".length());
		hud.getTopLayer().addEntity(fpsText);
		scoreText = new ChangeableText(0, 20, Textures.mFont,
				"Score: 0", "Score: XXXXXXXXXXXXXXXXXX".length());
		hud.getTopLayer().addEntity(scoreText);
		
		lifesText = new ChangeableText(0, 40, Textures.mFont,
				"Lifes: 1", "Score: XXXXXXXXXXXXXXXXXX".length());
		hud.getTopLayer().addEntity(lifesText);
		
		shieldText = new ChangeableText(0, 60, Textures.mFont,
				"Shield: false", "Score: XXXXXXXXXXXXXXXXXX".length());
		hud.getTopLayer().addEntity(shieldText);
		
		// Добавляем кнопку для отладки
		final Sprite upActionSprite = new Sprite(0, WORLD_HEIGHT - Textures.mControlTextureRegion.getHeight(),
				Textures.mControlTextureRegion) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {				
				Intent settingsActivity = new Intent(getBaseContext(),
						Preference.class);
				startActivity(settingsActivity);
				return true;
			};
		};
		hud.getTopLayer().addEntity(upActionSprite);		
		hud.registerTouchArea(upActionSprite);
		//
		
		this.getEngine().getCamera().setHUD(hud);
		// Attach the FPSLogger
		this.getEngine().registerUpdateHandler(new FPSLogger(0.5f) {
			@Override
			protected void onLogFPS() {
				final String fpsString = String.format(
						"FPS: %.2f (MIN: %.0f ms | MAX: %.0f ms)",
						this.mFrames / this.mSecondsElapsed,
						this.mShortestFrame * MILLISECONDSPERSECOND,
						this.mLongestFrame * MILLISECONDSPERSECOND);
				fpsText.setText(fpsString);
			}
		});	
	}
	
	@Override
	public void onLoadComplete()
	{ }
	
	@EventHandler
	public void registerAccelerometerListener(NinjaController controller)
	{
		this.enableAccelerometerSensor(controller);
	}
	
	/**
	 * Вычисляем какой артифакт упал на голову нашего персонажа.
	 * Получаем кол-во очков с артифакта:
	 *     Прибавляем бонусы к общему числу бонусов.
	 * Если этот артифакт Враг - убиваем: 
	 *     Выводим Гейм Овер + reset уровня + кнопку на выход в активити с меню.
	 *     
	 * @param catchEvent
	 */
	@EventHandler
	public void onCatchArtifactEvent(СatсhArtifactEvent catchEvent) {
		Score += catchEvent.getArtifactScore();		
		scoreText.setText("Score: "+Score+"");
		}

	@EventHandler
	public void onChangeGameStatus(EventChangeGameStatus gameStatus) {
		if (gameStatus.getGameState().equals(EventChangeGameStatus.PAUSE)){
			lifesText.setText("Lifes: "+gameStatus.getNinja().life+"");
			shieldText.setText("Shield: "+gameStatus.getNinja().isShield()+"");
			if (gameStatus.getNinja().life<=0){
				//[Выводим Гейм Овер + reset уровня + кнопку на выход в активити с меню.]
				this.mGameScene.setChildScene(this.mPauseScene, false, true, true);
				this.mEngine.stop();
				System.exit(1);
			}
				
		}
	}
	
	private void makePauseScene()
	{
		this.mPauseScene = new CameraScene(1, this.getEngine().getCamera());
		/* Make the 'PAUSED'-label centered on the camera. */
		final float x =  WORLD_WIDTH * 0.5f; //- this.mPausedTextureRegion.getWidth() / 2;
		final float y = WORLD_HEIGHT * 0.5f; //- this.mPausedTextureRegion.getHeight() / 2;
		final Sprite pausedSprite = new Sprite(x, y, Textures.mPausedTextureRegion);
		this.mPauseScene.getTopLayer().addEntity(pausedSprite);
		/* Makes the paused Game look through. */
		this.mPauseScene.setBackgroundEnabled(false);
	} 

}


