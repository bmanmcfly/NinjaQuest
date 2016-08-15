package com.ninja.quest.Entities;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.ninja.quest.Constants.Constants;

/**
 * Created by Bman on 11/07/2016.
 *
 * I realized that it will be easier to turn the terrain into entities
 */
public class Terrain extends BaseEntity {
    public Terrain(Polygon shape, Vector2 initPos) {
        super(shape, initPos);
        entityIs = Constants.TERRAIN;
        collidesWith = Constants.ALL;
    }



    public void dispose() {

    }
}
