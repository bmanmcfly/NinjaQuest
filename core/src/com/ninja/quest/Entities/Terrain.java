package com.ninja.quest.Entities;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.ninja.quest.Constants.Constants;

import java.util.EnumSet;

import static com.ninja.quest.Constants.Constants.CollisionFlags.ENEMY;
import static com.ninja.quest.Constants.Constants.CollisionFlags.E_BULLET;
import static com.ninja.quest.Constants.Constants.CollisionFlags.PLAYER;
import static com.ninja.quest.Constants.Constants.CollisionFlags.P_BULLET;

/**
 * Created by Bman on 11/07/2016.
 *
 * I realized that it will be easier to turn the terrain into entities
 */
public class Terrain extends BaseEntity {
    public Terrain(Polygon shape, Vector2 initPos, World world) {
        super(shape, initPos, world);
//        entityIs = Constants.TERRAIN;
//        collidesWith = Constants.ALL;
        entityIsA = Constants.CollisionFlags.TERRAIN;
        entityCollidesWith = EnumSet.of(PLAYER, ENEMY, P_BULLET, E_BULLET);
    }
    public void dispose() {

    }
}
