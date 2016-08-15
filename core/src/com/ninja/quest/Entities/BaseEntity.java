package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
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
    protected Vector2 pos = new Vector2();
    protected Vector2 prevPos = new Vector2();
    protected Vector2 speed = new Vector2();
    protected Vector2 displacement = new Vector2(); //this is the speed * dt
    protected Vector2 acceleration = new Vector2();
    protected Rectangle bounds = new Rectangle();
    protected Polygon shape = new Polygon();
    protected boolean isAlive;
    protected boolean remove = false; //set to true before removing entity

    protected short entityIs;
    protected short collidesWith;

    //image and state
    protected Sprite sprite = new Sprite();
    protected boolean isTouched = false;

    protected BaseEntity(Polygon shape, Vector2 position){
        this.shape = shape;
        bounds = shape.getBoundingRectangle();
        resizeBounds();
//        shape.translate(position.x, position.y);
        pos = position;
        isAlive = true;
    }

    private void resizeBounds(){
        bounds.x -= Constants.PixPerTile;
        bounds.y -= Constants.PixPerTile;
        bounds.width += 2 * Constants.PixPerTile;
        bounds.height += 2 * Constants.PixPerTile;
    }

    public void update(float delta, Array<BaseEntity> entities){

    }

    public void debugDraw(ShapeRenderer sr){
        sr.polygon(shape.getTransformedVertices());
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

    public short whatAmI(){
        return entityIs;
    }

    public boolean getTouched() {
        return isTouched;
    }

    public Vector2 getPos(){
        return pos;
    }

    public void dispose(){

    }

}
