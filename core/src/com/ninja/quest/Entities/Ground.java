package com.ninja.quest.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Bman on 12/07/2016.
 *
 * This small class will just hold the information about the walkable paths
 *
 * Each ground object will be one connected length of terrain
 *
 */

//// TODO: 10/13/2016 add the ability to compensate for moveable terrain
//  11/2/2016 in order to do this, will need to make the ground an entitiy whose position will
//    be tied to the polygon, and an update function, then add a debug draw.
//    DONE: ground will not be a visible layer
public class Ground{

    private Array<Vector2> points = new Array<Vector2>();
//    private Vector2 speed = new Vector2();

    public Ground(Array<Vector2> lines, float[] list, World world){
//        float[] verts;
        Vector2 pos = new Vector2();
        points.addAll(lines);
        pos.set(points.first());
//        verts = list;
    }

    public Array<Vector2> getWalkPath(){
        return points;
    }

//
//    public void debugDraw(ShapeRenderer shapeRenderer){
//
//        shapeRenderer.polyline(points.toArray());
//    }


//    private boolean moveable = false;
//
//    public Ground(SpriteBatch batch, Array<Vector2> lines, float[] list) {
//        super(batch);
//        points.addAll(lines);
//        pos.set(points.first());
//        verts = list;
//        speed.set(new Vector2(0,0));
//    }
//
//    @Override
//    public void update(float dt) {
//
//    }
//    @Override
//    public void updateVertices() {
//        //Will only update if it is a moveable one
//    }
//
//
//    @Override
//    public void draw() {
//        //DO NOT DRAW
//    }
//
//    @Override
//    public Vector2 getPos() {
//        return body.getPos();
//    }
//
//    @Override
//    public void resolveCollision(Vector2 adjust) {
//
//    }

}
