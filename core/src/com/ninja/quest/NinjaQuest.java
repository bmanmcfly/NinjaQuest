package com.ninja.quest;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.ninja.quest.Constants.Constants;
import com.ninja.quest.Utils.SpriteTween;
//import com.ninja.quest.Fonts.Fonts;

public class NinjaQuest extends Game {
	private SpriteBatch batch;
	public AssetManager assets;
	public FileHandleResolver resolver = new InternalFileHandleResolver();
	public BitmapFont djFont, markerFont ;
//	public Fonts loadFont;
	private TweenManager tweenManager = new TweenManager();

	public static int width = 800, height = 600;


	@Override
	public void create () {
		FreetypeFontLoader.FreeTypeFontLoaderParameter param, parameters;
		param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		parameters = new FreetypeFontLoader.FreeTypeFontLoaderParameter();

		Tween.registerAccessor(Sprite.class, new SpriteTween());

		batch = new SpriteBatch();
		assets = new AssetManager();

//		loadFont = new Fonts();

		assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		param.fontFileName = "fonts/DJGROSS.ttf";
		param.fontParameters.size = (int)Math.ceil(Gdx.graphics.getHeight() / 50);
		param.fontParameters.borderWidth = 1.1f;
		param.fontParameters.characters = Constants.FONT_CHARACTERS;
		param.fontParameters.magFilter = Texture.TextureFilter.MipMapNearestNearest;
		assets.load("djFont.ttf", BitmapFont.class, param);
		assets.finishLoading();
		djFont = assets.get("djFont.ttf");

		parameters.fontFileName = "fonts/PermanentMarker.ttf";
		parameters.fontParameters.characters = Constants.FONT_CHARACTERS;
		parameters.fontParameters.borderWidth = 1.1f;
		parameters.fontParameters.size = (int)Math.ceil(Gdx.graphics.getHeight() / 50);
		assets.load("PermanentMarker.ttf", BitmapFont.class, parameters);
		assets.finishLoading();
		markerFont = assets.get("PermanentMarker.ttf");

		this.setScreen(new com.ninja.quest.Screens.GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch(){
		return batch;
	}

}
