package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
import com.ninja.quest.Constants.Constants;

/**
 * Created by Bman on 8/6/2016.
 *
 * The Gameobject is just the x, y position and a body
 * Also hold a sprite, and whether the object is alive or dead
 *
 * Change - the body idea just complicates the process and so the entity will hold the shape
 */
public abstract class BaseEntity implements Disposable{
    //Physics stuff
    protected World world;
    protected Intersector.MinimumTranslationVector MTV = new Intersector.MinimumTranslationVector();
    protected Vector2 pos = new Vector2();
    protected Vector2 prevPos = new Vector2();
    protected Vector2 speed = new Vector2(0,0);
    protected Vector2 direction = new Vector2(0,0);
    protected Vector2 acceleration = new Vector2(0,0);
    protected Rectangle bounds = new Rectangle();
    protected Polygon shape = new Polygon();
    protected boolean isAlive;
//    protected boolean remove = false; //set to true before removing entity
//    protected Array<Vector2> prePoints = new Array<Vector2>();
//    protected Array<Vector2> otherPoints = new Array<Vector2>();

    protected short entityIs;
    protected short collidesWith;

    //image and state
    protected Sprite sprite = new Sprite();
    protected boolean isTouched = false;
    protected Constants.airStates airState;

    protected BaseEntity(Polygon shape, Vector2 position, World world){
        this.shape = shape;
        this.world = world;
        bounds = shape.getBoundingRectangle();
        resizeBounds();
//        shape.translate(position.x, position.y);
        pos = position;
        isAlive = true;
    }

    public void resizeBounds(){
        bounds.x -= Constants.PixPerTile / 2;
        bounds.y -= Constants.PixPerTile / 2;
        bounds.width += Constants.PixPerTile;
        bounds.height += Constants.PixPerTile;
    }

    public void updateBounds(){
        bounds = shape.getBoundingRectangle();
        resizeBounds();
    }

    public void update(float delta, Array<BaseEntity> entities){
        updateBounds();
    }

    public void debugDraw(ShapeRenderer sr){
        Vector2 tmp = pos.cpy().add(speed.cpy().scl(5));
        sr.polygon(shape.getTransformedVertices());
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.line(pos, tmp);
    }

    public void boundsDraw(ShapeRenderer sr){
        if (this.isTouched){
            sr.setColor(Color.RED);
        } else {
            sr.setColor(Color.BLUE);
        }
        sr.box(bounds.x, bounds.y, 0, bounds.getWidth(), bounds.getHeight(),0);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public boolean getTouched() {
        return isTouched;
    }

    public Vector2 getPos(){
        return pos;
    }

    public void dispose(){

    }

    protected void collisionResponse(BaseEntity other){}

    public Array<Vector2> setPolyToVecArray(Polygon polygon){
        Array<Vector2> polyPoints = new Array<Vector2>();
        float[] vertices = polygon.getTransformedVertices();
        int numVertices = polygon.getTransformedVertices().length;
        polyPoints.clear();
        for (int i = 0; i < numVertices / 2; i++){
            float x = vertices[i * 2];
            float y = vertices[i * 2 + 1];
            polyPoints.add(new Vector2(x, y));
        }
        return polyPoints;
    }

    public boolean collides(Array<BaseEntity> things){
        int size = things.size;
        boolean collision = false;
        Polygon moveShape = this.shape;
        moveShape.translate(this.speed.x, this.speed.y);
        for(int i = 0; i < size; i++) {
            BaseEntity temp = things.get(i);
            short thisTest = temp.entityIs;
            if (thisTest == this.entityIs) continue;
            // First, get the speed of both entities.  get the dot product between both speeds
            temp.isTouched = (thisTest & this.collidesWith) == 0 &&
                    Intersector.overlaps(temp.bounds, this.bounds);
            if (temp.isTouched /*&& dotProduct <= 0*/) {
//                Gdx.app.log("dot", Float.toString(dotProduct));
                //now, move this to its next position,
                //test for collision with the object
                //if the test passes, add it to the list of collisions
                if (Intersector.overlapConvexPolygons(moveShape, temp.shape, MTV)) {
                    this.collisionResponse(temp);
                    temp.collisionResponse(this);
                    collision = true;
                }
            }
        }
        //if the collision list is still empty, no collisions.
        return collision;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID").append(Short.toString(entityIs));
        sb.append("\nPosition :").append(pos);
        sb.append("\nSpeed: ").append(speed);
        sb.append("\nis Touched").append(Boolean.toString(isTouched));
        return sb.toString();
    }

}
