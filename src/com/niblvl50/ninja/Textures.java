package com.niblvl50.ninja;

import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.BuildableTexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.builder.BlackPawnTextureBuilder;
import org.anddev.andengine.opengl.texture.builder.ITextureBuilder.TextureSourcePackingException;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;

public class Textures
{
	public static BuildableTexture backTexture;
	public static TiledTextureRegion ryu;
	public static TiledTextureRegion artifact;
	public static TextureRegion backgroundLand;
	public static TextureRegion backgroundCloud;
	public static TextureRegion backgroundBushes;
	public static TextureRegion backgroundGrass;
	public static Texture mTexture;
	public static TextureRegion mGameOverTextureRegion;
	public static TextureRegion mPauseTextureRegion;
	public static TextureRegion mRetryTextureRegion;
	public static Texture mFontTexture;
	public static Font mFont;
	public static BuildableTexture artifactTexture;
	
	public static void load(BaseGameActivity activity)
	{
		TextureRegionFactory.setAssetBasePath("textures/");
		
		backTexture = new BuildableTexture(512, 512, TextureOptions.BILINEAR);
		
        ryu = TextureRegionFactory.createTiledFromAsset(backTexture, activity, "cats.png", 4, 1);       
        backgroundLand = TextureRegionFactory.createFromAsset(backTexture, activity, "backgroundLand.png");
        backgroundCloud = TextureRegionFactory.createFromAsset(backTexture, activity, "backgroundSky.png");
        
        artifactTexture = new BuildableTexture(64, 128, TextureOptions.BILINEAR);
        artifact = TextureRegionFactory.createTiledFromAsset(artifactTexture, activity, "artifacts.png", 2, 4);
        
        mTexture = new Texture(512, 256, TextureOptions.BILINEAR);
		mGameOverTextureRegion = TextureRegionFactory.createFromAsset(mTexture, activity, "game_over.png", 0, 0);		
		mRetryTextureRegion = TextureRegionFactory.createFromAsset(mTexture, activity, "retry.png", 0, 50);
		mPauseTextureRegion = TextureRegionFactory.createFromAsset(mTexture, activity, "paused.png", 0, 100);
		
		
    	mFontTexture = new Texture(256, 256,
				TextureOptions.BILINEAR);
    	FontFactory.setAssetBasePath("font/");
		mFont = FontFactory.createFromAsset(mFontTexture, activity, "snap.ttf", 20, true, Color.DKGRAY);

		activity.getEngine().getTextureManager().loadTexture(mFontTexture);
		activity.getEngine().getFontManager().loadFont(mFont);
        try 
        {
            backTexture.build(new BlackPawnTextureBuilder(1));
            artifactTexture.build(new BlackPawnTextureBuilder(1));
        }
        catch(final TextureSourcePackingException e)
        {
            activity.finish();
        }
        activity.getEngine().getTextureManager().loadTextures(backTexture, artifactTexture, mTexture);
	}
}
