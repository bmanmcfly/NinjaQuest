package com.ninja.quest.Entities;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Bman on 11/25/2016.
 * This class is the Kunai, which is the basic (and to start ONLY) projectile that the player can throw
 * This one will be a basic body that moves straight along the direction the player is facing until it leaves the
 * screen or collides with something else
 *
 * TODO: Make the kunai poolable, to prevent memory issues
 * TODO: The kunai is just one throwable item.
 * TODO: The kunai is created and destroyed on collision with terrain or enemy, or enemy sword
 *
 */
public class Kunai extends BaseEntity{
    protected Kunai(Polygon shape, Vector2 position, World world) {
        super(shape, position, world);
    }
    
}
