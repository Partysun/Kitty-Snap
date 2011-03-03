package com.partysun.cats;

import java.io.IOException;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.modifier.MoveModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.view.RenderSurfaceView;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.modifier.ease.EaseBounceIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;

import com.partysun.cats.collisionhandler.CollisionHandler;
import com.partysun.cats.controller.NinjaController;
import com.partysun.cats.eventbus.EventBus;
import com.partysun.cats.eventbus.EventChangeGameStatus;
import com.partysun.cats.eventbus.EventHandler;
import com.partysun.cats.eventbus.СatсhArtifactEvent;
import com.partysun.cats.pool.ArtifactPool;

public class GameActivity extends BaseGameActivity 
{
	public static final int WORLD_WIDTH = 400;
	public static final int WORLD_HEIGHT = 240;
	public static final int MENU_RETRY = 0;
	public static final int MENU_MENU = 1;
	public int Score = 0;
	private ChangeableText scoreText;
	private Scene mGameScene; 
	private Music mMusic;
	
	private int statusScene = 0;
	
	  protected int getLayoutID() {
          return R.layout.main;
  }

  protected int getRenderSurfaceViewID() {
          return R.id.xml_rendersurfaceview;
  }

  @Override
  protected void onSetContentView() {
          super.setContentView(this.getLayoutID());
          this.mRenderSurfaceView = (RenderSurfaceView) this.findViewById(this.getRenderSurfaceViewID());
          this.mRenderSurfaceView.setEGLConfigChooser(false);
          this.mRenderSurfaceView.setRenderer(this.mEngine);
  }
	
	@Override
	public Engine onLoadEngine()
	{		
		Camera camera = new Camera(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
		EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(WORLD_WIDTH, WORLD_HEIGHT), camera).setNeedsMusic(true);
		return new Engine(options);
	}

	@Override
	public void onLoadResources()
	{
		MusicFactory.setAssetBasePath("music/");

		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine
					.getMusicManager(), this,
					"birds.ogg");
			this.mMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e("Error", e);
		}

		mMusic.setLooping(true);
		Textures.load(this);
		ArtifactPool.getInstance().setArtifactTexture(Textures.artifact);
		// Allocate and register the collision handler
		CollisionHandler.instance().reset();
		TempSettingsClass.getInstance().setContext(getBaseContext());
		EventBus.clear();
		EventBus.register(this);
		this.getEngine().registerUpdateHandler(CollisionHandler.instance());
	}

	@Override
	public Scene onLoadScene()
	{  
		TempSettingsClass.getInstance().getPref();
		if (!TempSettingsClass.getInstance().isSound())
			mMusic.setVolume(0f);
		this.mGameScene = new GameScene(setGameScreenHUD());
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
	private HUD setGameScreenHUD()
	{
		// Create a hud basically a overlay for the camera
		final HUD hud = new HUD();
		scoreText = new ChangeableText(0, 2, Textures.mFont,
				"Score: 0", "Score: XXXXXXXXXXXXXXXXXX".length());
		hud.getTopLayer().addEntity(scoreText);
		
		this.getEngine().getCamera().setHUD(hud);
		return hud;			
	}
	
	@Override
	public void onLoadComplete()
	{ 		
		this.mMusic.play();
	}
	
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
	
			if (gameStatus.getNinja().life<=0){
				((GameScene) this.mGameScene).wipe();
				destroy();
				this.mGameScene.setChildScene(makeGameOverScene(), false, true, true);		
			}
				
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		destroy();
	}
	
	/*
	 * Записываем в Преференс Текщие очки
	 * Сравниваем текущий с лучшим и который больше записываем
	 * Записываем в Преференс Лучший результат 
	 * Обнуляем Score 
	 */
	private void destroy() {
		
		SharedPreferences.Editor editor = getPreferences(0).edit();
		editor.putInt("highScore", Score);
		editor.commit();
   	  	
		SharedPreferences prefs = getPreferences(0); 
   	  	int highscore = prefs.getInt("bestScore", -1); 
   	  	if (Score>highscore){
   	  		highscore = Score;
   	  		editor.putInt("bestScore", Score);
   	  		editor.commit();   	  		
   	  	}
   	  	
   	  	Score = 0;

		ArtifactPool.getInstance().clear();
		CollisionHandler.instance().reset();
		EventBus.clear();		
	}

	private CameraScene makeGameOverScene()
	{
		statusScene = 1;
		CameraScene mGameOverScene = new CameraScene(1, this.getEngine().getCamera());
		/* Make the 'GameOver'-label centered on the camera. */
		final float x = ((GameActivity.WORLD_WIDTH - Textures.mGameOverTextureRegion.getWidth()) / 2);
		final float y = 50;
		final Sprite pausedSprite = new Sprite(x, y, Textures.mGameOverTextureRegion);
		mGameOverScene.getTopLayer().addEntity(pausedSprite);
		/* Makes the paused Game look through. */
		mGameOverScene.setBackgroundEnabled(false);
		final HUD hud = new HUD();
		float center_x = ((GameActivity.WORLD_WIDTH - Textures.mRetryTextureRegion.getWidth()) / 2);
		MoveModifier moveModifier = new MoveModifier(1.0f, 0,center_x ,120 , 120, EaseBounceIn.getInstance());
		moveModifier.setRemoveWhenFinished(true);
		//final Sprite menuTitle =   new Sprite(((GameActivity.WORLD_WIDTH - this.mTitleTextureRegion.getWidth()) / 2), 20, this.mTitleTextureRegion);
		 final Sprite menuRetry =  new Sprite(center_x, 120, Textures.mRetryTextureRegion) {
	            @Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	            	switchActivity(MenuActivity.class, true);
	                return false;
	            }
	        };
	    menuRetry.addShapeModifier(moveModifier);
	    
	    hud.registerTouchArea(menuRetry);
	    hud.getTopLayer().addEntity(menuRetry);
	    
	    float center_x_text = (GameActivity.WORLD_WIDTH - 200) * 0.5f;
	    
	    //Load Score from preference
	    SharedPreferences prefs = getPreferences(0);
	    int highscore = prefs.getInt("highScore", -1);
	    int bestscore = prefs.getInt("bestScore", -1);
	    
	    hud.getTopLayer().addEntity(new Text(center_x_text, 170, Textures.mFont,"Last Score: "+highscore+""));
	    hud.getTopLayer().addEntity( new Text(center_x_text, 192, Textures.mFont,"Best Score: "+ bestscore+""));
	    
	    this.getEngine().getCamera().setHUD(hud);
	    this.mMusic.pause();
		return mGameOverScene;
	} 
	
	public void switchActivity(Class <?> cls, boolean finish) {
		Intent gameIntent = new Intent(GameActivity.this,cls);
		GameActivity.this.startActivity(gameIntent);		
		if (finish)
			GameActivity.this.finish();
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {		
		if (statusScene !=1){
			if (pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if (this.mEngine.getScene().hasChildScene()) {
				this.mEngine.getScene().back();
				((GameScene) this.mEngine.getScene()).startGeneratorObjects();
				this.mMusic.play();
			} else {		
				pause();
				}			
		}}
		return true;
	}
	
	private void pause()
	{
		statusScene = 2;
		CameraScene mPausedScene = new CameraScene(1, this.getEngine().getCamera());
		/* Make the 'Paused'-label centered on the camera. */
		final float x = ((GameActivity.WORLD_WIDTH - Textures.mPauseTextureRegion.getWidth()) / 2);
		final float y = 50;
		final Sprite pausedSprite = new Sprite(x, y, Textures.mPauseTextureRegion);
		mPausedScene.getTopLayer().addEntity(pausedSprite);
		/* Makes the paused Game look through. */
		mPausedScene.setBackgroundEnabled(false);
		this.mEngine.getScene().setChildScene(mPausedScene, false, true, true);
		((GameScene) this.mEngine.getScene()).stopGeneratorObjects();
		this.mMusic.pause();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (statusScene !=1){pause();}
	}

}

