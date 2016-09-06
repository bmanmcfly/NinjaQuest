package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ninja.quest.Constants.Constants;
import com.ninja.quest.Utils.MapParser;

/**
 * Created by Bman on 8/14/2016.
 *
 * The world is simply a container class to hold the various entities and will be responsible for
 * adding and removing entities.
 * TODO: Complete this list; - means todo, * means done
 * Needs to :
 *  * Initialize: this will load all the various entities from the maploader (add a maploader reference)
 *  - Render: Could use this class to go through and call the rendering for the world (might need the spritebatch)
 *  - Debug Render: call the debug render for each class (might need the shape renderer)
 *  - update: call the update for the entities (might need to shift the camera to the world)
 *  - Add: Add entities will be used to add entities to loop through, this will be useful for enemy spawns
 *  - AddALL: This will tie into the initialize method, after each type of entity is loaded just add all
 *  - getEntities: returns the list of all the entitie
 *  - getNumEntities: counts through all the entities
 *  - getNumEntities(filter): will sort through the "entity is" member to count the entities of a given type
 *  - remove: This will remove any item flagged for removal
 *  - clear: This will empty the array
 */
public class World {
    private Array<BaseEntity> entityArray = new Array<BaseEntity>();
    private Array<Terrain> terrainPoly = new Array<Terrain>();
    private Array<Ground> walkPath = new Array<Ground>();
    private Array<Ladder> ladders = new Array<Ladder>();
    private Player player;

    private MapParser mp;

    public World(MapParser parser){
        mp = parser;
    }

    public void init(SpriteBatch batch, TextureAtlas atlas){

        terrainPoly = mp.collisionPolys(this);
        for (BaseEntity e: terrainPoly){
//            Gdx.app.log("Terrain added", "");
            add(e);
        }
        ladders = mp.getLadders(this);
        for (BaseEntity e: ladders){
//            Gdx.app.log("Terrain added", "");
            add(e);
        }
        walkPath = mp.getGround(this);

        Vector2 position = mp.heroSpawn();
        Polygon polygon = mp.buildHero(position);
        player = new Player(batch, atlas, position, polygon, this);
        entityArray.add(player);
    }

    public void add(BaseEntity entity){
        entityArray.add(entity);
    }

    public void update(float delta){
        for (BaseEntity b: entityArray){
            switch (b.entityIs){
                case Constants.PLAYER:
                    b.update(delta, entityArray);
//                    Gdx.app.log("Update", "Player");
                    break;
//                case Constants.E_BULLET:
//                    b.update(delta, entityArray);
//                    Gdx.app.log("Update", "E_Bullet");
//                    break;
//                case Constants.P_BULLET:
//                    b.update(delta, entityArray);
//                    Gdx.app.log("Update", "Player bullet");
//                    break;
//                case Constants.ENEMY:
//                    Gdx.app.log("Update", "Enemy");
//                    b.update(delta, entityArray);
//                    break;
                default:
            }
        }
    }

    public void debugRender(ShapeRenderer sr){
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.GOLDENROD);
        for (BaseEntity b: entityArray){
            b.debugDraw(sr);
        }
        sr.end();
    }

    public void drawBounds(ShapeRenderer sr){
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLUE);
        for(BaseEntity b: entityArray){
                b.boundsDraw(sr);
        }
        sr.end();
    }

    public Player getPlayer(){
        return player;
    }

    public Array<Ground> getGround(){
        return walkPath;
    }

    public void dispose(){
        for (BaseEntity e: entityArray){
            e.dispose();
        }
    }

}
