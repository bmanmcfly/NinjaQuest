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
        //get acceleration - gravity if airborn + (+/-) direction.scl(Constant run impulse)
        //direction : if airborn is 1 or -1, if grounded the direction is the normal of linstart.sub(lineend)
        //if this gets updated every frame then the resulting speed will follow the path, but we also have to update the path

//        if (airState == Constants.airStates.GROUNDED){
//            verifyDirection();
//        } else {
//            direction.set(0,0);
//            if (moveLeft){
//                direction.set(-1,0);
//            }
//            if (moveRight){
//                direction.set(1,0);
//            }
//        }
        acceleration.set(0,-Constants.gravity * dt);
        if (moveLeft) {
            acceleration.add(-Constants.RUN_IMPULSE, 0);
        }
        if (moveRight) {
            acceleration.add(Constants.RUN_IMPULSE, 0);
        }
        if (jumping) {
            acceleration.add(0, 10);
        }
        if (gliding) {
            acceleration.add(0, -1);
        }

        speed.add(acceleration.scl(dt));
        if (!moveLeft && !moveRight && !jumping && !gliding) {
            acceleration.set(0,-Constants.gravity);
            speed.interpolate(new Vector2(0, speed.y), 0.25f, Interpolation.fade);
        }

        //will reset the speed to prevent tunneling.
        collides(entities);

        pos.add(speed);
        shape.setPosition(pos.x, pos.y);
    }
    public void verifyDirection(){

        if (needPrevLine() && !isGroundEdge()){
//            Gdx.app.log("Need prev line", "");
            getPrevLine();
        }
        if (needNextLine() && !isGroundEdge()){
//            Gdx.app.log("Need next line", "");
            getNextLine();
        }
        if (isGroundEdge()){
            walkOffEdge();
        }

    }

    public boolean needPrevLine(){
        return (foot.x < lineStart.x) && speed.x <= 0f;
    }

    public boolean needNextLine(){
        return foot.x > lineEnd.x && speed.x >= 0f;
    }

    public void getPrevLine(){
        if (lineStart != groundStart) {
            groundEnd = groundStart;
            lineIndex--;
            setDirection();
        }
    }

    public void getNextLine(){
        if (lineIndex + 1 <= walkPath.size - 2) {
            groundStart = groundEnd;
            lineIndex++;
            setDirection();
        }
    }

    public boolean isGroundEdge(){
        return ((walkPath.get(walkPath.size - 1).x < pos.x) || (walkPath.first().x >= botRight.x));
    }

    private void walkOffEdge(){
        walkPath = null;
        airState = Constants.airStates.FALLING;
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
