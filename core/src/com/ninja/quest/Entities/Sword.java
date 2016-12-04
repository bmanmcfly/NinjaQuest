package com.ninja.quest.Entities;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ninja.quest.Constants.Constants;

import java.util.EnumSet;

import static com.ninja.quest.Constants.Constants.CollisionFlags.BRAKEABLE_OJBECT;
import static com.ninja.quest.Constants.Constants.CollisionFlags.ENEMY;
import static com.ninja.quest.Constants.Constants.CollisionFlags.E_BULLET;
import static com.ninja.quest.Constants.Constants.CollisionFlags.PLAYER;
import static com.ninja.quest.Constants.Constants.CollisionFlags.P_BULLET;

/**
 * Created by Bman on 11/20/2016.
 *
 * The sword will be created when the entity attacks, it will emanate from the character in the direction of movement
 *
 *
 * TODO: Make sure the sword gets created and destroyed appropriately
 * todo: a sword will break / cancel other swords and projectiles
 *
 *
 */
public class Sword extends BaseEntity{
    Constants.CollisionFlags creator;
    protected Sword(Polygon shape, Vector2 position, World world, Constants.CollisionFlags Creator) {
        super(shape, position, world);
        creator = Creator;
        entityIsA = Constants.CollisionFlags.SWORD;
        if (Creator.equals(PLAYER))
            entityCollidesWith = EnumSet.of(ENEMY, E_BULLET, BRAKEABLE_OJBECT);
        else if (Creator.equals(ENEMY))
            entityCollidesWith = EnumSet.of(PLAYER, P_BULLET, BRAKEABLE_OJBECT);
    }

    @Override
    protected void collisionResponse(BaseEntity other) {
//        super.collisionResponse(other);
        switch (other.entityIsA){
            case ENEMY:
                break;
            case E_BULLET:
                break;
            case BRAKEABLE_OJBECT:
                break;
            case PLAYER:
                break;
            case P_BULLET:
                break;
        }
    }

    @Override
    public void update(float delta, Array<BaseEntity> entities) {
        super.update(delta, entities);
    }
}
