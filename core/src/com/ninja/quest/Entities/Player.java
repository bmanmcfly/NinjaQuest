package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
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
        acceleration.set(0,-Constants.gravity * dt);
        if (moveLeft) {
            if (airState.equals(Constants.airStates.GROUNDED)) {
                verifyDirection();
                acceleration.add(direction.cpy().scl(-Constants.RUN_IMPULSE * dt));
            } else {
                acceleration.add(-Constants.RUN_IMPULSE * dt, 0);
            }
        }
        if (moveRight) {
            if (airState.equals(Constants.airStates.GROUNDED)){
                verifyDirection();
                acceleration.add(direction.cpy().scl(Constants.RUN_IMPULSE * dt));
            } else {
                acceleration.add(Constants.RUN_IMPULSE * dt, 0);
            }
        }

        if (jumping) {
            acceleration.add(0, 10);
            walkPath = null;
        }

        if (gliding) {
            acceleration.add(0, -1);
        }

        //have to cap the speed at a max
        speed.add(acceleration.cpy().scl(dt));
//        speed.clamp(-Constants.MAX_RUN_SPEED, Constants.MAX_RUN_SPEED);
        if (!moveLeft && !moveRight && !jumping && !gliding) {
//            acceleration.set(0,-Constants.gravity);
            speed.interpolate(new Vector2(0, speed.y), 0.25f, Interpolation.fade);
        }

        //will reset the speed to prevent tunneling.
        collides(entities);

        pos.add(speed);
        shape.setPosition(pos.x, pos.y);
    }

    public Constants.airStates updateAirState(){
        if (walkPath == null){
            if (speed.y >= 0) {
                airState = Constants.airStates.JUMPING;
            }
            if (speed.y <= 0 && airState != Constants.airStates.GLIDING){
                airState = Constants.airStates.FALLING;
            }
        }else {
            airState = Constants.airStates.GROUNDED;
        }
        return airState;
    }

    public void getAcceleration(Constants.airStates airState){

    }

    public void collisionResponse(BaseEntity other){
        switch (other.entityIs){
            case Constants.TERRAIN:
                Vector2 adjustment;
                adjustment = MTV.normal.scl(MTV.depth);
//                Gdx.app.log("Adjustment", adjustment.toString());
//                Gdx.app.log("Speed", speed.toString());
                if (adjustment.cpy().dot(speed) < 0){
//                    Gdx.app.log("Dot", "Less than 0");
                    speed.sub(adjustment.scl(-1));
                }
                int groundIndex = getGround();
                if (groundIndex!= -1){
                    airState = Constants.airStates.GROUNDED;
                    direction.set(setDirection());
                }
                break;
            case Constants.LADDER:
                break;
            case Constants.E_BULLET:
                break;

        }
    }

    public void move(Vector2 direction){

    }


//    public void setDirection
//    public Vector2 getFoot(){ return foot; }
//    public Vector2 getHead(){ return head; }
//    public Vector2 getPos(){ return pos;}

    @Override
    public void dispose() {

    }

}
