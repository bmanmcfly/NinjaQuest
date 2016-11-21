package com.ninja.quest.Entities;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.EnumSet;

import static com.ninja.quest.Constants.Constants.CollisionFlags.ENEMY;
import static com.ninja.quest.Constants.Constants.CollisionFlags.LADDER;
import static com.ninja.quest.Constants.Constants.CollisionFlags.PLAYER;

/**
 * Created by Bman on 17/07/2016.
 *
 * The ladders:
 *
 * When the player is collided with the head or feet, pressing up or down puts the player in climbing
 * mode. While climbing terrain collisions are ignored.  If jump off ladder in middle of climb, make an isonladder to show that
 * the player is on the ladder and was climbing in order to ignore those collisions
 *
 * When in climbing mode, the player counts as grounded, but can climb up or down.
 *
 * Pressing jump puts the player in a falling state.
 *
 * If the player reaches the top of the ladder, or the bottom, set the walk path to the one at the top of the ladder, and treat the
 * player as grounded.
 */
public class Ladder extends BaseEntity implements Disposable {
    @Override
    public void dispose() {

    }
    public Ladder(Polygon shape, Vector2 initPos, World world) {
        super(shape, initPos, world);
//        entityIs = Constants.LADDER;
//        collidesWith = Constants.PLAYER;
        entityIsA = LADDER;
        entityCollidesWith = EnumSet.of(PLAYER, ENEMY);
    }

}
