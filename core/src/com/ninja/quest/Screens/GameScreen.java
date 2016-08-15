package com.ninja.quest.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.ninja.quest.Camera.myOrthoCam;
import com.ninja.quest.Constants.Constants;
import com.ninja.quest.Entities.World;
import com.ninja.quest.NinjaQuest;
import com.ninja.quest.Utils.Input;
import com.ninja.quest.Utils.MapParser;
//import com.ninja.quest.Utils.collisionHandler;

/**
 * Created by Bman on 27/02/2016.
 *
 * The main game playing screen
 * This class must :
 * - load the maps
 * - load the enemies
 * - spawn the player2
 * - update
 * - draw
 * - when victory condition met set the next screen
 */
public class GameScreen implements Screen {
    final NinjaQuest game;
    private myOrthoCam cam;
    private TextureAtlas atlas;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRender;
//    private Player player;
    private MapParser parser;
    private ShapeRenderer sr;
    private Input input = new Input();
    private World world;

    private float time = 0.0f;
    private final float step = 1 / 120.0f;
    private final int maxUpdatesPerFrame = 5;
    private float interpolation = 0.0f;


    //These arrays will be used to handle the collision layer
    //public static Array<Polygon> platformPoly = new Array<Polygon>();
//    private static Array<Terrain> terrainPoly = new Array<Terrain>();
//    private static Array<Ground> walkPath = new Array<Ground>();
//    public static Array<Ladder> ladders = new Array<Ladder>();

    public GameScreen(NinjaQuest game) {
        this.game = game;
        Gdx.input.setInputProcessor(input);
        cam = new myOrthoCam();
        cam.setToOrtho(false, Constants.ScreenWidth, Constants.ScreenHeight);
        cam.update();
        game.assets.load("Sprites/Ninjafull.atlas", TextureAtlas.class);
        game.assets.finishLoading();
        atlas = game.assets.get("Sprites/Ninjafull.atlas");
        game.assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        game.assets.load("Maps/testMap.tmx", TiledMap.class);
        game.assets.finishLoading();

        map = game.assets.get("Maps/testMap.tmx");
        parser = new MapParser(map, game.getBatch());

        //Load collision geometry
        world = new World(parser, input);
        world.init(game.getBatch(), atlas);
        mapRender = new OrthogonalTiledMapRenderer(map, game.getBatch());

        Constants.mapWidth = Float.parseFloat(map.getProperties().get("width").toString()) * Constants.PixPerTile;
        Constants.mapHeight = Float.parseFloat(map.getProperties().get("height").toString()) * Constants.PixPerTile;
        sr = new ShapeRenderer();
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

    }

//    public void UpdateCollisions(float delta){
//        int start;
//        boolean collision = false;
//        for (Terrain T : terrainPoly){
//            //Check terrain vs Player2, Enemy, Items
//            Vector2 correction = collider.updateCollision(T.getShape(), player2.getShape());
//            if (correction != null){
//                Gdx.app.log("Correction", correction.toString());
//                if (collider.checkPoint(player2.getFoot(), T.getShape())){
//                    Gdx.app.log("Foot", "passed");
//                    if (player2.getWalkPath() == null) {
////                        Gdx.app.log("Update Collision", "Walk path not null");
//                        for (Ground tmp : walkPath) {
//                            start = collider.prunePath(tmp.getWalkPath(), player2.getFoot());
//                            if (start != -1) {
//                                player2.setWalkPath(tmp.getWalkPath(), start);
//                                player2.setFoot(collider.getNearest());
//                                break;
//                            }
//                        }
//                    }
//                }
//                if (collider.checkPoint(player2.getHead(), T.getShape())){
//                    Gdx.app.log("blockhead", "true");
//                    player2.setBlockHead(true);
//                }
//                if (collider.checkPoint(player2.getlHand(), T.getShape())){
//                    Gdx.app.log("block left", "true");
//                    player2.setBlockLeft(true);
//                }
//                if (collider.checkPoint(player2.getrHand(), T.getShape())){
//                    Gdx.app.log("block right", "true");
//                    player2.setBlockRight(true);
//                }
//                collision = true;
//                player2.resolveCollision(correction);
////                player2.checkBlocked(T.getShape());
//            }
//        }
//        if (!collision){
//            player2.clearBlocked();
////            Gdx.app.log("blocked", "cleared");
//        }
//    }

//    public void UpdateCollisions(float delta){
//        boolean close;
//        for(Terrain T: terrainPoly) {
//            close = collider.pruneCollisions(player.getBody(), T.getBody());
//            T.setTouched(close);
//            if (close){
//                if (collider.getCollision(player.getBody(), T.getBody())) {
////                    Gdx.app.log("Collision", "With Terrain");
//                    collider.resolve(player.getBody(), T.getBody());
//                }
//            }
//        }
//    }

    public void update(float delta){
        int updatesThisFrame = 0;
        if (delta >= 0.25f){
            delta = 0.25f;
        }
        //Get entity data for the previous position
        time += delta;
        while (time > step && updatesThisFrame < maxUpdatesPerFrame){
            world.update(step);
            updatesThisFrame++;
            time -= step;
        }
        interpolation = time / step; //This is the % between the previous frame and the next frame
//        player2.interpolate(time);
//        Gdx.app.log("Updates" + Integer.toString(updatesThisFrame),"interpolation" + Float.toString(interpolation));
        cam.displacement(world.getPlayer().getPos().x, world.getPlayer().getPos().y, map);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        cam.update();

        mapRender.setView(cam);
        sr.setProjectionMatrix(cam.combined);

        world.drawBounds(sr);

        world.debugRender(sr);

        game.getBatch().begin();
//        Vector3 textPos = new Vector3(10, 10,0);
//        cam.unproject(textPos);
        Vector3 markerPos = new Vector3(10, 10, 0);
        cam.unproject(markerPos);
        game.markerFont.draw(game.getBatch(), "FPS: " + Integer.toString(Gdx.graphics.getFramesPerSecond()) /*+
                "\nSpeed: " + player.getBody().getSpeed().toString()*/, markerPos.x, markerPos.y);
//        player2.draw();
        game.getBatch().end();
    }

    /**
     * @param width the width of the window
     * @param height height of the window
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        Constants.AspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        cam.viewportWidth = Constants.ScreenWidth;
        cam.viewportHeight = Constants.ScreenWidth * Constants.AspectRatio;
        cam.zoomLimit();
        cam.update();

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        game.djFont.dispose();
        game.markerFont.dispose();
        game.getBatch().dispose();
        game.assets.dispose();
        game.assets = null;
        atlas.dispose();
        parser.dispose();
        world.dispose();


    }

    public TextureAtlas getAtlas(){return atlas;}

}
