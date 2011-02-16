package com.niblvl50.ninja;

import org.anddev.andengine.opengl.font.Font;
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
import android.graphics.Typeface;

public class Textures
{
	public static BuildableTexture buildableTexture;
	public static TiledTextureRegion ryu;
	public static TiledTextureRegion artifact;
	public static TextureRegion backgroundLand;
	public static TextureRegion backgroundCloud;
	public static TextureRegion backgroundBushes;
	public static TextureRegion backgroundGrass;
	public static Texture bTexture;
	public static TextureRegion mControlTextureRegion;
	public static Texture mTexture;
	public static TextureRegion mPausedTextureRegion;
	public static Texture mFontTexture;
	public static Font mFont;
	
	public static void load(BaseGameActivity activity)
	{
		TextureRegionFactory.setAssetBasePath("textures/");
		
		buildableTexture = new BuildableTexture(1024, 512, TextureOptions.DEFAULT);
		
        ryu = TextureRegionFactory.createTiledFromAsset(buildableTexture, activity, "ryu.png", 4, 3);
        artifact = TextureRegionFactory.createTiledFromAsset(buildableTexture, activity, "artifacts.png", 2, 2);
        backgroundLand = TextureRegionFactory.createFromAsset(buildableTexture, activity, "backgroundLand.png");
        backgroundCloud = TextureRegionFactory.createFromAsset(buildableTexture, activity, "backgroundSky.png");
        
        
        mTexture = new Texture(256, 128, TextureOptions.BILINEAR);
		mPausedTextureRegion = TextureRegionFactory.createFromAsset(mTexture, activity, "paused.png", 0, 0);

		bTexture = new Texture(128, 128, TextureOptions.BILINEAR);
		mControlTextureRegion = TextureRegionFactory.createFromAsset(bTexture, activity, "button1.png", 0, 0);
		
    	mFontTexture = new Texture(256, 256,
				TextureOptions.BILINEAR);
		mFont = new Font(mFontTexture, Typeface.create(Typeface.MONOSPACE,
				Typeface.BOLD), 16, true, Color.BLACK);

		activity.getEngine().getTextureManager().loadTexture(mFontTexture);
		activity.getEngine().getFontManager().loadFont(mFont);
        try 
        {
            buildableTexture.build(new BlackPawnTextureBuilder(1));
        }
        catch(final TextureSourcePackingException e)
        {
            activity.finish();
        }
        activity.getEngine().getTextureManager().loadTexture(mTexture);
        activity.getEngine().getTextureManager().loadTextures(buildableTexture, bTexture);
	}
}
