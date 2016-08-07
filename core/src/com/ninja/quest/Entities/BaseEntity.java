package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Bman on 8/6/2016.
 *
 * The Gameobject is just the x, y position and a body
 * Also hold a sprite, and whether the object is alive or dead
 */
public abstract class BaseEntity implements Disposable {
    protected boolean isAlive;
    protected Sprite sprite = new Sprite();
    protected Body body;
    protected boolean isTouched = false;

    protected BaseEntity(Polygon shape){
        body = new Body(shape);
    }

    public Body getBody() {
        return body;
    }

    public void debugDraw(ShapeRenderer sr){
        sr.polygon(body.getShape().getTransformedVertices());
    }

    public Sprite getSprite(){
        return sprite;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void setTouched(boolean touched){
        isTouched = touched;
    }

    public boolean getTouched(){ return isTouched; }

}
