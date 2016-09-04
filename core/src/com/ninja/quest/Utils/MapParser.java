package com.ninja.quest.Utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ninja.quest.Constants.Constants;
import com.ninja.quest.Entities.Ground;
import com.ninja.quest.Entities.Ladder;
import com.ninja.quest.Entities.Terrain;
import com.ninja.quest.Entities.World;

/**
 * Created by Bman on 27/02/2016.
 *
 * The map parser is to load things like collision layers,
 * patrol routes, spawn points,
 *
 * For future reference, here is how each object is loaded
 * for (mapobject object: map.getlayers().get("objects").getObjects(){
 *     if(object instanceof Rectanglye){
 *         Rectangle rect = ((RectangleObject) object).getRectangle();
 *     }
 *      if (ojbect instanceof CircleMapObject){
 *          Circle circle = ((CircleMapObject) object).getCircle();
 *      }
 *      if (object instanceof EllipseMapObject){
 *          Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
 *
 *      }
 *      if (object instanceof PolylineMapObject){
 *          Polyline line = ((PolylineMapObject) object).getPolyline();
 *          //get transformedvertices() to draw
 *      }
 *      if (object instanceof PolygonMapObject) {
 *          Polygon poly  = ((PolygonMapObject) object).getPolygon();
 *          //get transformed vertices to draw
 *      }
 * }
 */
public class MapParser{
    private TiledMap map;
    private SpriteBatch sb;

    public MapParser(TiledMap map, SpriteBatch sb) {
        this.map = map;
        this.sb = sb;
    }

    public void drawDebugLayer(ShapeRenderer sr){
        for (MapObject object : map.getLayers().get("Objects").getObjects()){
            if (object instanceof PolygonMapObject){
                com.badlogic.gdx.math.Polygon polygon = ((PolygonMapObject) object).getPolygon();
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.polygon(polygon.getTransformedVertices());
                sr.end();
            }
        }
        for (MapObject object : map.getLayers().get("Platforms").getObjects()){
            if (object instanceof PolygonMapObject){
                com.badlogic.gdx.math.Polygon polygon = ((PolygonMapObject) object).getPolygon();
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.polygon(polygon.getTransformedVertices());
                sr.end();
            }
        }
        for (MapObject object : map.getLayers().get("Ladders").getObjects()){
            if (object instanceof PolygonMapObject){
                com.badlogic.gdx.math.Polygon polygon = ((PolygonMapObject) object).getPolygon();
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.polygon(polygon.getTransformedVertices());
                sr.end();
            }
        }
    }


    public Array<Ground> getGround(World world){
        Array<Ground> ground = new Array<Ground>();
        float[] lines;
        Array<Vector2> vertices;

        for (MapObject object: map.getLayers().get("Ground").getObjects()){
            if (object instanceof PolylineMapObject){
                lines = ((PolylineMapObject) object).getPolyline().getTransformedVertices();
                vertices = new Array<Vector2>(lines.length / 2);
                for (int i=0 ; i < lines.length / 2;i++){
                    vertices.add(new Vector2(lines[i * 2], lines[i * 2 + 1]));
                }
                ground.add(new Ground(vertices, lines, world));
            }
        }
        for (MapObject object: map.getLayers().get("PlatformGround").getObjects()){
            if (object instanceof PolylineMapObject){
                lines = ((PolylineMapObject) object).getPolyline().getTransformedVertices();
                vertices = new Array<Vector2>(lines.length / 2);
                for (int i=0 ; i < lines.length / 2;i++){
                    vertices.add(new Vector2(lines[i * 2], lines[i * 2 + 1]));
                }
                ground.add(new Ground(vertices, lines, world));
            }
        }
        return ground;
    }

    public Array<Ladder> getLadders(World world){
        Array<Ladder> loadLadders = new Array<Ladder>();
        for (MapObject object : map.getLayers().get("Ladders").getObjects()){
            if (object instanceof PolygonMapObject){
                Polygon transformedPoly = new Polygon(((PolygonMapObject) object).getPolygon().getTransformedVertices());
                loadLadders.add(new Ladder(transformedPoly, new Vector2(transformedPoly.getX(), transformedPoly.getY()), world));
            }
        }
        return loadLadders;
    }

    public Array<Terrain> collisionPolys(World world) {
        Array<Terrain> loadPoly = new Array<Terrain>();
        for (MapObject object : map.getLayers().get("Objects").getObjects()){
            if (object instanceof PolygonMapObject){
                Polygon transformedPoly = new Polygon(((PolygonMapObject) object).getPolygon().getTransformedVertices());
                loadPoly.add(new Terrain(transformedPoly, new Vector2(transformedPoly.getX(), transformedPoly.getY()), world));
            }
        }
        for (MapObject object : map.getLayers().get("Platforms").getObjects()) {
            if (object instanceof PolygonMapObject) {
                Polygon transformedPoly = new Polygon(((PolygonMapObject) object).getPolygon().getTransformedVertices());
//                GameScreen.tmpTerrain.add(new Terrain(sb, transformedPoly));
                loadPoly.add(new Terrain(transformedPoly, new Vector2(transformedPoly.getX(), transformedPoly.getY()), world));
            }
        }
        return loadPoly;
    }

    public Vector2 heroSpawn(){
        Vector2 heroSpawn = new Vector2();
        for (MapObject spawn : map.getLayers().get("Spawn").getObjects()){
            if (spawn.getName().equals("HeroSpawn")){
                heroSpawn.x = Float.parseFloat(spawn.getProperties().get("x").toString());
                heroSpawn.y = Float.parseFloat(spawn.getProperties().get("y").toString());
//                Gdx.app.log("Hero start", heroSpawn.toString());
            }
        }
        return heroSpawn;
    }

    public Polygon buildHero(Vector2 position){
        Polygon polygon = new Polygon();
        float[] verts = {Constants.PLAYER_WIDTH / 2, 0,
                        Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT * (3f/8f),
                        Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT * (5f/8f),
                        Constants.PLAYER_WIDTH * (5f/8f), Constants.PLAYER_HEIGHT,
                        Constants.PLAYER_WIDTH * (3f/8f), Constants.PLAYER_HEIGHT,
                        0, Constants.PLAYER_HEIGHT * (5f/8f),
                        0, Constants.PLAYER_HEIGHT * (3f/8f)};
        polygon.setVertices(verts);
        polygon.translate(position.x, position.y);
        return polygon;
    }

    public void dispose(){
        map.dispose();
    }

}
