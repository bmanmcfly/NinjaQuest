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
 *
 * TODO: Stuff to add
 * TODO: Add ability to slide; if uphill slide to a stop, downhill slide to the flat spot and dampen
 * TODO: Move slower uphill, faster downhill, medium flat
 * TODO: add ability to throw; grounded slow to a stop while throwing
 * TODO: add ability to attack with sword
 * TODO: add glide : glide means speed.y / 2 and constant acceleration
 * TODO: add ability to select items
 */
public class Player extends walkingChar implements Disposable {
//    private SpriteBatch sb;
    private TextureAtlas atlas;
    private TextureAtlas.AtlasRegion region;

    private Vector2 direction = new Vector2();
    private Vector2 deltaPos = new Vector2();
    private float dirX = 0;
    private float dirY = 0;
    private float idleTimer;
    private int frameCount = 0;
    //Controls
    private boolean moveLeft = false, moveRight = false, jumping = false, gliding = false;
    private boolean canClimb = false, climbing = false, attack = false, shooting = false;
    Player(SpriteBatch batch, TextureAtlas atlas, Vector2 initPos, Polygon shape, World world) {
        super(shape, initPos, world);
//        sb = batch;
        this.atlas = atlas;
//        entityIs = Constants.PLAYER;
//        collidesWith = Constants.ENEMY | Constants.E_BULLET | Constants.LADDER | Constants.SENSOR;

        entityIsA = Constants.CollisionFlags.PLAYER;
        entityCollidesWith = Constants.CollisionFlags.ALL;
        airState = Constants.airStates.FALLING;
        state = Constants.states.IDLE;
    }

//        TextureAtlas.AtlasRegion region = atlas.findRegion("Idle_",0);
//        image.setRegion(region);
//        image.setPosition(initPos.x, initPos.y);
//        image.setBounds(initPos.x, initPos.y, Constants.PixPerTile * 1.1f, Constants.PixPerTile * 1.8f);

    void updateInput(){
        moveLeft = Input.left;// && !blockLeft;
        moveRight = Input.right;// && !blockRight;
        jumping = Input.jump;// && !blockHead;
        gliding = Input.climbDown;// && airState == FALLING;
//        climbing = Input.climbUp;
//        climbing = Input.climbDown;
        attack = Input.attack;
        shooting = Input.shoot;

    }

    @Override
    public void update(float dt, Array<BaseEntity> entities) {
        super.update(dt, entities);
        prevPos.set(pos);
        prevAirState = airState;
        prevState = state;
        state = updateState(dt);
        airState = updateAirState();

        updateInput();

        //Checks the player state and input to determine the acceleration for this frame
        calcSpeed(airState, dt);
        collides(entities);
        pos.add(speed);
        shape.setPosition(pos.x, pos.y);
        updateVerts();
    }

    private Constants.airStates updateAirState(){
        /**
         *  Possible airstates:
         *      - Grounded :
         *          * Idle :
         *          * Walking :
         *          * Sliding :
         *      - Airborne :
         *          * Jumping :
         *          * Falling :
         *          * Gliding :
         */
        if (walkPath == null){
            if (speed.y > 0) {
                airState = Constants.airStates.JUMPING;
//                Gdx.app.log("Jumping", "airstate");
            }
            if (speed.y <= 0 && airState != Constants.airStates.GLIDING){
                airState = Constants.airStates.FALLING;
                if (gliding){
                    //start gliding
                    airState = Constants.airStates.GLIDING;
                }
//                Gdx.app.log("airstate", "Falling");
            }
        }else {
            airState = Constants.airStates.GROUNDED;
//            Gdx.app.log("airstate", "Grounded");
        }
        return this.airState;
    }

    private Constants.states updateState(float dt){
        /** States:
         *  CLIMB : If in collision with a ladder and presses climb up or climb down
         *  THROW : Slows to stop creates and projectiles, breaks jump / glide to falling
         *  ATTACK : if grounded slow to a stop, create sword on side facing, breaks jump / glide to falling
         *  IDLE : 2 seconds or so of nothing
         *  WALKING ; Done --  can jump, fall, be hit, slide,
         *  SLIDE : walking flat or downhill at half speed or more, can fall, jump, atk and throw
         *  HIT : fly backwards a half jump backwards, todo when there are enemies around
         *  DYING : save this for dying animation, lose a life and reload at the previous checkpoint
        */
        if (airState == Constants.airStates.GROUNDED) {
            if (dirX == 0) {
                if (idleTimer > 250 && !attack || !jumping) {
                    state = Constants.states.IDLE;
                } else {
                    idleTimer += dt;
                }
            } else {
                state = Constants.states.WALKING;
                idleTimer = 0;
            }
        }
        if (attack) {
            //todo: start an attack timer to know when to end the attack
            // something like if attack and state = attacking and attack timer less than attack time
            state = Constants.states.ATTACK;
        }

        if (shooting) {
            //todo: have a throw timer
            state = Constants.states.THROW;
        }

        return state;
    }

    private void calcSpeed(Constants.airStates airState, float dt){
        if (moveLeft){
            dirX = -1;
            if (speed.x < 0 && airState.equals(Constants.airStates.GROUNDED))
                facingRight = false;
        }
        if (moveRight){
            dirX = 1;
            if (speed.x > 0 && airState.equals(Constants.airStates.GROUNDED))
                facingRight = true;
        }
        if ((moveLeft && moveRight) || (!moveRight && !moveLeft)){
            dirX = 0;
        }
//        Gdx.app.log("calc speed", "");

        //get the movement speed for the player
        switch (airState){
            case JUMPING:
            case FALLING:
                /** Vertical motion first */
                // TODO: 12/2/2016 Convert the change in speeds to X and Y to tweens
                if (jumping && jumpHeight < Constants.MAX_JUMP_HEIGHT) {
                    speed.y = Constants.JUMP_IMPULSE;
                    jumpHeight += speed.y * dt;
//                    Gdx.app.log("Jumping", Float.toString(jumpHeight));
                } else {
                    speed.y += -Constants.gravity * dt;
//                    Gdx.app.log("Jumping stopped", Float.toString(jumpHeight));
                }
                if (speed.y < -Constants.MAX_FALL_SPEED)
                    speed.y = -Constants.gravity * dt;
            case GLIDING:
                if (speed.y < -Constants.MAX_FALL_SPEED / 4){

                }
                /** Horizontal motion */
                //DONE: fix the algorithm here
                //this is airborn, when facing the direction moving (facingright and dirx > 0 or opposite) then it will be the normal accelleration
                // when facing right and dirx are opposite each other, limit the speed to half of max and a slower accelleration
                // don't change the xspeed if attacking, if on the ground and attacking start damping the speed
                if (dirX != 0) {
                    /** I planned this one out wrong, if airborn and moving opposite the direction facing,
                     * then cap the run speed to max run speed / 2.
                     * Otherwise, if running in the direction facing, use normal acceleration.
                      */
                    //dirx is positive, right motion, and less than the max speed or
                    //
                    if (dirX > 0 && speed.x > 0 && facingRight
                            || dirX < 0 && speed.x < 0 && !facingRight) {
//                        Gdx.app.log("Moving with direction","");
                        if (Math.abs(speed.x) < Constants.MAX_RUN_SPEED) {
//                            Gdx.app.log("accellerating", "");
                            speed.x += dirX * 3 * dt;
                        } else {
//                            Gdx.app.log("max speed with direction","");
                            speed.x = dirX * Constants.MAX_RUN_SPEED;

                        }
                    } else
//                        Gdx.app.log("Movng against direction", "");
                        if (Math.abs(speed.x) < Constants.MAX_RUN_SPEED / 2) {
//                            Gdx.app.log("accelerating against", "");
                            speed.x += (dirX * 3 * dt) / 5;
                        } else {
//                            Gdx.app.log("Max speed against","");
                            speed.x = dirX * Constants.MAX_RUN_SPEED / 2;
                        }
                } else {
//                    Gdx.app.log("Damping","");
                    speed.x *= Constants.DAMPING;
                }
                break;
            case GROUNDED:
                updateLine(dt, dirX);
                direction.set(calcDirection());
                if (dirX != 0){
                    if (speed.len() < Constants.MAX_RUN_SPEED){
                        direction.scl(dirX * Constants.MAX_RUN_SPEED * dt);
                        speed.add(direction);
                    } else {
                        speed.set(direction.scl(dirX * Constants.MAX_RUN_SPEED));
                    }
                }
                if (dirX == 0 || (dirX > 0 && speed.x < 0) || (dirX < 0 && speed.x > 0
                    || attack || shooting ) ){
                    speed.scl(Constants.SLOW_DAMPING);
                }
                if (jumping){
                    jumpHeight = 0f;
                    speed.y = Constants.JUMP_IMPULSE;
                    walkPath = null;
                }
                break;
            default:
                Gdx.app.log("Invalid", "Airstate");
        }
    }

    public void collisionResponse(BaseEntity other){
        switch (other.entityIsA){
            case TERRAIN:
//                Gdx.app.log("Terrain", "Collision response");
                Vector2 adjustment;
                adjustment = MTV.normal.scl(MTV.depth);
                if (adjustment.cpy().dot(speed) < 0){
                    speed.sub(adjustment.scl(-1));
                }
                int groundIndex = getGround();
                if (groundIndex != -1){
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
            case LADDER:
                Gdx.app.log("Touching ladder", "");

                // TODO: 11/9/2016 make it so the player can climb the ladder
                // To climb a ladder:
                // 1 - make sure the head or foot is within the ladder
                // 2 - if climb up/down is true then state = climbing
                // 3 - while climbing ignore terrain unless at the top or the bottom
                // 4 - if at the bottom or top of the ladder leave climbing state if at the top prevent falling through
                // 5 - can jump to the side, or climbing down jump to drop.


                break;
            case E_BULLET:
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

//    /////////////////////////////////////////////////////////////////////////
//    //Tweening
//    /////////////////////////////////////////////////////////////////////////
//
//
//    @Override
//    public int getValues(Sprite target, int tweenType, float[] returnValues) {
//        switch (tweenType){
//            case :
//                break;
//
//
//        }
//    }
//
//    @Override
//    public void setValues(Sprite target, int tweenType, float[] newValues) {
//
//    }
}
