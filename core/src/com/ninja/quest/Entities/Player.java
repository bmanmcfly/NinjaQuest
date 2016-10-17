package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.ninja.quest.Constants.Constants;
import com.ninja.quest.Utils.Input;

/**
 * Created by Bman on 8/6/2016.
 *
 * A refactored Player class
 */
public class Player extends Character implements Disposable {
    private SpriteBatch sb;
    private TextureAtlas atlas;
    private TextureAtlas.AtlasRegion region;

    private Vector2 direction = new Vector2();
    private Vector2 deltaPos = new Vector2();
    private float dirX = 0;
    private float dirY = 0;
    private int frameCount = 0;
    //Controls
    private boolean moveLeft = false, moveRight = false, jumping = false, gliding = false;

    public Player(SpriteBatch batch, TextureAtlas atlas, Vector2 initPos, Polygon shape, World world) {
        super(shape, initPos, world);
        sb = batch;
        this.atlas = atlas;
        entityIs = Constants.PLAYER;
        collidesWith = Constants.ENEMY | Constants.E_BULLET | Constants.LADDER | Constants.SENSOR;
        airState = Constants.airStates.FALLING;
    }

//        TextureAtlas.AtlasRegion region = atlas.findRegion("Idle_",0);
//        image.setRegion(region);
//        image.setPosition(initPos.x, initPos.y);
//        image.setBounds(initPos.x, initPos.y, Constants.PixPerTile * 1.1f, Constants.PixPerTile * 1.8f);

    public void updateInput(){
        moveLeft = Input.left;// && !blockLeft;
        moveRight = Input.right;// && !blockRight;
        jumping = Input.jump;// && !blockHead;
        gliding = Input.climbDown;// && airState == FALLING;
    }

    @Override
    public void update(float dt, Array<BaseEntity> entities) {
        super.update(dt, entities);
        prevPos.set(pos);
        updateInput();
        airState = updateAirState();

        //Checks the player state and input to determine the acceleration for this frame
        calcSpeed(airState, dt);
        collides(entities);
        pos.add(speed);
        shape.setPosition(pos.x, pos.y);
    }

    public Constants.airStates updateAirState(){
        if (walkPath == null){
            if (speed.y > 0) {
                airState = Constants.airStates.JUMPING;
//                Gdx.app.log("Jumping", "airstate");
            }
            if (speed.y <= 0 && airState != Constants.airStates.GLIDING){
                airState = Constants.airStates.FALLING;
//                Gdx.app.log("airstate", "Falling");
            }
        }else {
            airState = Constants.airStates.GROUNDED;
//            Gdx.app.log("airstate", "Grounded");
        }
        return airState;
    }

    public void collisionResponse(BaseEntity other){
        switch (other.entityIs){
            case Constants.TERRAIN:
//                Gdx.app.log("Terrain", "Collision response");
                Vector2 adjustment;
                adjustment = MTV.normal.scl(MTV.depth);
                if (adjustment.cpy().dot(speed) < 0){
                    speed.sub(adjustment.scl(-1));
                }
                int groundIndex = getGround();
                if (groundIndex != -1){
                    direction.set(calcDirection());
                    airState = Constants.airStates.GROUNDED;
                    if (dirX == 0) {
                        speed.set(0, 0);
                    }
                    else {
                        speed.y = 0;
                    }
                } else {
                    airState = Constants.airStates.FALLING;
                }
                break;
            case Constants.LADDER:
                break;
            case Constants.E_BULLET:
                break;
        }
    }

    public void calcSpeed(Constants.airStates airState, float dt){
        if (moveLeft){
            dirX = -1;
            facingRight = false;
        }
        if (moveRight){
            dirX = 1;
            facingRight = true;
        }
        if ((moveLeft && moveRight) || (!moveRight && !moveLeft)){
            dirX = 0;
        }
        switch (airState){
            case GLIDING:
                break;
            case JUMPING:
            case FALLING:
                dirY = -1;
                applyAirAccel(dt);
                break;
            case GROUNDED:
                dirY = 0;
                applyGroundAccel(dt);
                break;
        }
    }

    public void applyGroundAccel(float dt){
              //TODO: fix grounded movement
//            //Summary:
//            //*  - get the direction or slope of the ground
//            //*  - check if the player walks off the edge, OR if the player jumps
//            //  - if the speed.len < Max run speed then add the acceleration
//            //      acceleration = direction.scl(dirX * runImpulse * dt)
//            //  - if the speed.len >= maxrunspeed, then the speed vector = direction.scale(dirX * maxrunspeed * dt
        direction.set(walkDirection());
//        Gdx.app.log("direction", direction.toString());
        if (speed.len() < 2)
            speed.mulAdd(direction.scl(dirX), 5 * dt);
        if (dirX == 0)
            speed.scl(0.5f);

    }

    public void applyAirAccel(float dt){

        speed.y += -0.05f;
        if (speed.y <= -3f)
            speed.y = -3f;
        if (dirX == 0) {
            if (Math.abs(speed.x) <= 0.05f){
                speed.x = 0;
            }else {
                speed.x *= 0.8;
            }
        }
        if (dirX != 0 ){
            if (Math.abs(speed.x) <= 5){
                speed.x += dirX * 0.05f;
            } else {
                speed.x = dirX * 4f;
            }
        }
    }

    @Override
    public void dispose() {

    }

    /////////////////////////////////////////////////////////////////////////
    //Getters and Setters
    /////////////////////////////////////////////////////////////////////////

    public Vector2 getDirection() {
        return direction;
    }

    public Vector2 getSpeed(){
//        Gdx.app.log("speed", speed.toString());
        return speed;
    }

    public float getDirX(){
        return dirX;
    }

    public float getDirY(){
        return dirY;
    }

}
