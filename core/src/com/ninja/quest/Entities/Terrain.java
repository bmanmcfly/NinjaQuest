package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by Bman on 11/07/2016.
 *
 * I realized that it will be easier to turn the terrain into entities
 */
public class Terrain extends BaseEntity {
    boolean isTouched;

    public Terrain(Polygon shape) {
        super(shape);
    }

    public void setTouched(boolean touched){
        isTouched = touched;
    }


    public void dispose() {

    }
}
