package com.niblvl50.ninja;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.shape.modifier.MoveModifier;
import org.anddev.andengine.entity.shape.modifier.ease.EaseBounceIn;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.view.MotionEvent;

public class MenuActivity extends BaseGameActivity  implements IOnSceneTouchListener
{
	private SpriteBackground mBackground = null;
	public static final int MENU_OPTIONS = 0;
	public static final int MENU_PLAY = 1;
	private Scene menu;
	//Textures
	private TextureRegion menuback;
	private TextureRegion mMenuOptions;
	private TextureRegion mMenuPlay;
	private TextureRegion mTitle;
	
	private int clickedItem;
	
	@Override
	public Engine onLoadEngine() {
		Camera camera = new Camera(0, 0, GameActivity.WORLD_WIDTH, GameActivity.WORLD_HEIGHT);
		EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(GameActivity.WORLD_WIDTH, GameActivity.WORLD_HEIGHT), camera);
		return new Engine(options);
	}

	@Override
	public void onLoadResources() {
		TextureRegionFactory.setAssetBasePath("textures/menu/");		
		Texture ManeBackTexture = new Texture(512, 512, TextureOptions.BILINEAR);
         menuback = TextureRegionFactory.createFromAsset(ManeBackTexture, this,"menu.png",0,0);
         mMenuOptions = TextureRegionFactory.createFromAsset(ManeBackTexture, this, "options.png", 0, 241);
         mMenuPlay = TextureRegionFactory.createFromAsset(ManeBackTexture, this, "play.png", 0, 290);
         mTitle = TextureRegionFactory.createFromAsset(ManeBackTexture, this, "kittysnap.png", 0, 340);
         this.getEngine().getTextureManager().loadTextures(ManeBackTexture);
	}

	@Override
	public Scene onLoadScene() {
		menu = new Scene(1);
		mBackground = new SpriteBackground(1.0f, 1.0f, 1.0f,createParallaxSprite(0.0f,menuback));
		menu.setBackground(mBackground);
		menu.setOnSceneTouchListener(this);	
		float center_x = ((GameActivity.WORLD_WIDTH - this.mMenuPlay.getWidth()) / 2);
		
		MoveModifier moveModifier = new MoveModifier(1.0f, 0,center_x ,120 , 120, EaseBounceIn.getInstance());
		moveModifier.setRemoveWhenFinished(true);
	
		final Sprite menuPlay =  new Sprite(center_x, 120, this.mMenuPlay) {
	            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	                clickedItem = MENU_PLAY;
	                return false;
	            }
	        };
	        menuPlay.addShapeModifier(moveModifier);
	    final Sprite menuOptions =  new Sprite(center_x, 170, this.mMenuOptions) {
	    	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	    			clickedItem = MENU_OPTIONS;
	    			return false;
	            }
	        };
	        moveModifier = new MoveModifier(1.0f, GameActivity.WORLD_WIDTH ,center_x ,170 , 170, EaseBounceIn.getInstance());
	        menuOptions.addShapeModifier(moveModifier);
	        
	        menu.registerTouchArea(menuPlay);
	        menu.registerTouchArea(menuOptions);

			final Sprite menuTitle = new Sprite(((GameActivity.WORLD_WIDTH - this.mTitle.getWidth()) / 2), 30, mTitle);
			
			menu.getTopLayer().addEntity(menuTitle);
	        menu.getTopLayer().addEntity(menuOptions);
	        menu.getTopLayer().addEntity(menuPlay);

		return menu;
	}

	@Override
	public void onLoadComplete() {
	
	}
	
	public void switchActivity(Class <?> cls, boolean finish) {
		Intent gameIntent = new Intent(MenuActivity.this,cls);
		MenuActivity.this.startActivity(gameIntent);		
		if (finish)
			MenuActivity.this.finish();
	}

	private Sprite createParallaxSprite(float y, TextureRegion texture) {
		return new Sprite(0, y, texture.getWidth(), texture.getHeight(),
				texture);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
	      if(pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
	            switch (clickedItem) {
	                case MENU_PLAY:
	                	switchActivity(GameActivity.class, true);
	                    break;
	                case MENU_OPTIONS:
	                	switchActivity(Preference.class, false);
	                    break;
	            }
	        }
	        return false;
	}
	
}
