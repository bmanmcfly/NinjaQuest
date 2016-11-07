package com.ninja.quest.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
public class Player extends walkingChar implements Disposable {
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
        airState = updateAirState();

        updateInput();

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
                Gdx.app.log("Jumping", "airstate");
            }
            if (speed.y <= 0 && airState != Constants.airStates.GLIDING){
                airState = Constants.airStates.FALLING;
//                Gdx.app.log("airstate", "Falling");
            }
        }else {
            airState = Constants.airStates.GROUNDED;
//            Gdx.app.log("airstate", "Grounded");
        }
        return this.airState;
    }

    public void calcSpeed(Constants.airStates airState, float dt){
        if (moveLeft){
            dirX = -1;
            if (speed.x < 0)
                facingRight = false;
        }
        if (moveRight){
            dirX = 1;
            if (speed.x > 0)
                facingRight = true;
        }
        if ((moveLeft && moveRight) || (!moveRight && !moveLeft)){
            dirX = 0;
        }
//        Gdx.app.log("calc speed", "");

        //get the movement speed for the player
        switch (airState){
            case JUMPING:
                speed.y += -Constants.gravity * dt;
                if (dirX != 0) {
                    if (speed.x < 2 && speed.x > -2)
                        speed.x += dirX * 3 * dt;
                } else
                    speed.x *= Constants.DAMPING;
                break;
            case FALLING:
                if (dirX != 0) {
                    if (speed.x < 2 && speed.x > -2)
                        speed.x += dirX * 3 * dt;
                } else
                    speed.x *= Constants.DAMPING;
                speed.y += -Constants.gravity * dt;
                break;
            case GROUNDED:
                updateLine(dt, dirX);
                direction.set(calcDirection());
                if (dirX != 0){
                    if (speed.len() < 2){
                        direction.scl(dirX * 2 * dt);
                        speed.add(direction);
                    }
                }
                if (dirX == 0 || (dirX > 0 && speed.x < 0) || (dirX < 0 && speed.x > 0) ){
                    speed.scl(0.8f);
                }
                if (jumping){
                    speed.y = 4f;
                    walkPath = null;
                }
                break;

        }
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
                    Gdx.app.log("Grounded", "now");
                    airState = Constants.airStates.GROUNDED;
                    if (dirX == 0){
                        speed.set(0,0);
                    } else {
                        speed.y = 0;
                        direction.set(calcDirection().scl(speed.len() * dirX));
                        speed.set(direction);
                    }

                }
                break;
            case Constants.LADDER:
                break;
            case Constants.E_BULLET:
                break;
        }
    }


    @Override
    protected void debugDraw(ShapeRenderer sr){
        super.debugDraw(sr);


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
        return this.speed;
    }

    public float getDirX(){
        return dirX;
    }

    public float getDirY(){
        return dirY;
    }

}
