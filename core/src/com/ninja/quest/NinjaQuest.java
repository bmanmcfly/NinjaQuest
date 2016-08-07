package com.ninja.quest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.Vector2;
import com.ninja.quest.Constants.Constants;
//import com.ninja.quest.Fonts.Fonts;

public class NinjaQuest extends Game {
	private SpriteBatch batch;
	public AssetManager assets;
	public FileHandleResolver resolver = new InternalFileHandleResolver();
	public BitmapFont djFont, markerFont ;
//	public Fonts loadFont;

	public static int width = 800, height = 600;


	@Override
	public void create () {
		FreetypeFontLoader.FreeTypeFontLoaderParameter param, parameters;
//		FreeTypeFontGenerator.FreeTypeFontParameter params, parameter;
		param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		parameters = new FreetypeFontLoader.FreeTypeFontLoaderParameter();

//		params = new FreeTypeFontGenerator.FreeTypeFontParameter();
//		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		batch = new SpriteBatch();
		assets = new AssetManager();

//		loadFont = new Fonts();

		assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

//		assets.setLoader(BitmapFont.class, new FreetypeFontLoader(new InternalFileHandleResolver()));

		param.fontFileName = "fonts/DJGROSS.ttf";
		param.fontParameters.size = (int)Math.ceil(Gdx.graphics.getHeight() / 30);
		param.fontParameters.borderWidth = 1.5f;
		param.fontParameters.characters = Constants.FONT_CHARACTERS;
		param.fontParameters.magFilter = Texture.TextureFilter.MipMapNearestNearest;
		assets.load("djFont.ttf", BitmapFont.class, param);
		assets.finishLoading();
		djFont = assets.get("djFont.ttf");

		//Load the fonts here, can use this method to create any other fonts sizes etc here
//		loadFont.fontGenerator("fonts/DJGROSS.ttf");
//		params.characters = Constants.FONT_CHARACTERS;
//		params.size = (int)Math.ceil(Gdx.graphics.getHeight() / 30);
//		params.borderWidth = 1.5f;
//		params.magFilter = Texture.TextureFilter.Nearest;
//		assets.load("fonts/DJGROSS.ttf", FreeTypeFontGenerator.class, );
//		djFont = loadFont.create(params);

		parameters.fontFileName = "fonts/PermanentMarker.ttf";
		parameters.fontParameters.characters = Constants.FONT_CHARACTERS;
		parameters.fontParameters.borderWidth = 1.5f;
		parameters.fontParameters.size = (int)Math.ceil(Gdx.graphics.getHeight() / 30);
		assets.load("PermanentMarker.ttf", BitmapFont.class, parameters);
		assets.finishLoading();
		markerFont = assets.get("PermanentMarker.ttf");

//		loadFont.fontGenerator("fonts/PermanentMarker.ttf");
//		parameter.characters = Constants.FONT_CHARACTERS;
//		parameter.borderWidth = 1.5f;
//		parameter.size = (int)Math.ceil(Gdx.graphics.getHeight() / 30);
//		markerFont = loadFont.create(parameter);

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
