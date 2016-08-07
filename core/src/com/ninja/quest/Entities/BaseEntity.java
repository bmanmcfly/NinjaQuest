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
    protected Vector2 pos = new Vector2();
    protected float width, height;
    protected boolean isAlive;
    protected Sprite sprite = new Sprite();
    protected Body body;

    protected BaseEntity(Polygon shape){
        body = new Body(shape);
        pos.set(shape.getOriginX(), shape.getOriginY());
        width = body.getBounds().width;
        height = body.getBounds().height;
    }

    public Body getBody() {
        return body;
    }

    public void debugDraw(ShapeRenderer sr){
        sr.polygon(body.getShape().getTransformedVertices());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }
}
