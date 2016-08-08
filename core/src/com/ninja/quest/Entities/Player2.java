package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.ninja.quest.Constants.Constants;
import com.ninja.quest.Utils.Input;

import static com.ninja.quest.Constants.Constants.airStates.FALLING;
import static com.ninja.quest.Constants.Constants.airStates.GLIDING;
import static com.ninja.quest.Constants.Constants.airStates.GROUNDED;
import static com.ninja.quest.Constants.Constants.airStates.JUMPING;

/**
 * Created by Bman on 27/02/2016.
 *
 * This is the main player class
 */
public class Player2 implements Disposable{

    public Player2(Polygon shape) {

    }
//    private Vector2 botRight = new Vector2(); //bottom Right of the sprite
//
//    private Vector2 lineStart = new Vector2();
//    private Vector2 lineEnd = new Vector2();
//    private Vector2 nearest = new Vector2();
//
//    private int gndLineStart, gndLineEnd;
//    private Array<Vector2> path = new Array<Vector2>();
//    //Path needs to be cleared each time player goes airborn
//    private float tolerance = 5f;
//
////    private Vector2 groundStart = new Vector2();
////    private Vector2 groundEnd = new Vector2();
//
//    private Vector2 direction = new Vector2();
//    private int ladderIndex;
//
////    private boolean airborn = true;
//    private boolean climbing = false;
//    private boolean jumping = false;
//    private boolean gliding = false;
//    private boolean moveLeft = false, moveRight = false;
//    private boolean blockFoot = false, blockHead = false, blockLeft = false, blockRight = false;
//    private float jumpHeight = 0f;
//    private float moveForce = 0f;
//
//    private Constants.states state = Constants.states.IDLE;
////    private Constants.states prevState;
//    private Constants.airStates airState;
//
////    private Input input;
//
//
//    }

//    public void update(float dt) {
////        prevPos = pos;
//        airState = updateAirState();
//        updateInput();
//
//        //Airstate ONLY impacts the y
//        switch (airState){
//            case JUMPING:
//                //Jumping until the player lets go of the button, or travels high enough, or hits head
//                if (jumping && jumpHeight <= Constants.MAX_JUMP_HEIGHT){
//                    body.jump();
//                    jumpHeight += Constants.JUMP_SPEED * dt;
//                } else {
//                    airState = FALLING;
//                    body.setSpeedY(0);
//                    jumpHeight = 0;
//                }
//                airMove(dt);
//                break;
//            case FALLING:
//                //walking off a ledge, after hitting head, or the result of attacking from gliding
//                //change in Y = (speed.y * dt) + 0.5 * Gravity * dt * dt
//                body.fall(dt);
//                if (gliding){
//                    airState = GLIDING;
//                }
//                airMove(dt);
//                break;
//            case GLIDING:
//                //pressing the jump button while falling, lasts until hitting ground
//                body.glide();
//                airMove(dt);
////                Gdx.app.log("Player2 Update", "GLIDING");
//                break;
//            case GROUNDED:
//                //once a path is loaded, kept until walking off the edge, or jumping
//                walking(dt);
////                Gdx.app.log("Player2 Update", "GROUNDED");
//                if (jumping){
//                    airState = JUMPING;
//                    path = null;
//                    body.jump();
//                }
//                break;
//        }
////        Gdx.app.log("airstate " + airState, "");
////        speed.scl(dt).cpy();
////        Gdx.app.log("Speed", speed.toString());
//        body.updateBody(dt);
////        Gdx.app.log("Speed2", speed.toString());
////        Gdx.app.log("acc: " + acceleration.toString() + " Spd: " + speed.toString(), " pos: " + pos.toString());
//        updateVertices(); //This updates the player polygon
//    }
//
//    public Constants.airStates updateAirState(){
//        if (path == null){
//            if (body.getSpeed().y >= 0) {
//                airState = JUMPING;
//            }
//            if (body.getSpeed().y <= 0 && airState != GLIDING){
//                airState = FALLING;
//            }
//        }else {
//            airState = GROUNDED;
//        }
//        return airState;
//    }
//
//    public void verifyDirection(){
//
//        if (needPrevLine() && !isGroundEdge()){
////            Gdx.app.log("Need prev line", "");
//            getPrevLine();
//        }
//        if (needNextLine() && !isGroundEdge()){
////            Gdx.app.log("Need next line", "");
//            getNextLine();
//        }
//        if (isGroundEdge()){
//            walkOffEdge();
//        }
//
//    }
//
//    public boolean needPrevLine(){
//        return (foot.x < path.get(gndLineStart).x) && body.getSpeed().x <= 0f;
//    }
//
//    public boolean needNextLine(){
//        return foot.x > path.get(gndLineEnd).x && body.getSpeed().x >= 0f;
//    }
//
//    public void getPrevLine(){
//        if (gndLineStart > 0) {
//            gndLineEnd = gndLineStart;
//            gndLineStart--;
//            setDirection();
//        }
//    }
//
//    public void getNextLine(){
//        if (gndLineEnd <= path.size - 2) {
//            gndLineStart = gndLineEnd;
//            gndLineEnd++;
//            setDirection();
//        }
//    }
//
//    public boolean isGroundEdge(){
//        return ((path.get(path.size - 1).x < body.getPos().x) || (path.first().x >= botRight.x));
//    }
//
//    private void walkOffEdge(){
//        path = null;
//        airState = Constants.airStates.FALLING;
//    }
//
//
//    @Override
//    protected float[] initPoly() {
//        float xLeft, xRight, yBot, yTop;
//        float[] vertices;
//        xLeft = 0;
//        xRight = image.getWidth();
//        yBot = 0;
//        yTop = image.getHeight();
//        vertices = new float[]{xRight / 2, yBot, xRight, yTop / 2, xRight / 2, yTop, xLeft, yTop / 2};
//        return vertices;
//    }
//
//    @Override
//    public void airMove(float dt) {
//        int direction = 0;
////        Gdx.app.log("blockLeft" + Boolean.toString(blockLeft), "blockright" + Boolean.toString(blockRight));
//        if (moveLeft && !blockLeft)
//            direction = -1;
//        if (moveRight && !blockRight )
//            direction = 1;
//        if (!moveLeft && !moveRight)
//            direction = 0;
//        body.airSpeed(direction);
//    }
//
//    public void interpolate(float alpha){
////        pos.set(pos.add(speed.cpy().scl(1 - alpha)));
////        updateVertices();
//
//    }
//
//    @Override
//    public void walking(float dt) {
//        //get the direction of movement
//        //check if new line needed
//        verifyDirection();
//        int movement = 0;
//        if (moveLeft && !blockLeft)
//            movement = -1;
//        if (moveRight && !blockRight)
//            movement = +1;
//        body.walk(movement, direction, dt);
////        speed.add(direction.scl(Math.min(moveForce, Constants.MAX_RUN_SPEED)));
//    }
//
//    @Override
//    public void move(Vector2 speed) {
//        body.moveOnce(speed);
//    }
//
//    public boolean isOnLadder() {
////        for (int i = 0; i < GameScreen.ladders.size; i++) {
////            if (GameScreen.ladders.get(i).contains(foot) || GameScreen.ladders.get(i).contains(head)) {
////                if (Input.jumpForce > 0f || Input.jumpForce < 0f) {
////                    ladderIndex = i;
////                    return true;
////                }
////            }
////        }
//        return false;
//    }
//
//    @Override
//    public void climbSpeed(float dt) {
//
//    }
//
//    @Override
//    public void startClimbing() {
//
//    }
//
//
//    public void draw(){
//        image.draw(batch);
//    }
//
//    @Override
//    public void debugDraw(ShapeRenderer shapeRenderer) {
//        shapeRenderer.polygon(body.getShape().getTransformedVertices());
//    }
//

//
//    public void hitHead(){
//        if (body.getSpeed().y > 0) {
//            body.setSpeedY(0f);
//            airState = FALLING;
//        }
////        Gdx.app.log("Hit", "Head");
//    }
//
//    public void setBlockFoot(boolean blocked){
//        blockFoot = blocked;
//    }
//
//    public void setBlockHead(boolean blocked){
//        blockHead = blocked;
//        if (blockHead)
//            hitHead();
//    }
//
//    public void setBlockLeft(boolean blocked){
//        blockLeft = blocked;
//    }
//
//    public void setBlockRight(boolean blocked){
//        blockRight = blocked;
//    }
//
//    public void clearBlocked(){
//        setBlockFoot(false);
//        setBlockLeft(false);
//        setBlockRight(false);
//        setBlockHead(false);
//    }
//
//    @Override
//    public void resolveCollision(Vector2 adjust) {
////        if (body.getSpeed().dot(adjust) <= 0)
//            body.moveOnce(adjust);
//    }
//
//
//    //Getters//////////////////////////////////////////////////////////////////
//
//    public boolean getIsAlive() {
//        return isAlive;
//    }
//
//    public Array<Vector2> getWalkPath(){
//        return path;
//    }
//
//    @Override
//    public Polygon getShape() {
//        return body.getShape();
//    }
//
//    @Override
//    public Vector2 getPos(){
//        return body.getPos();
//    }
//
//    public Vector2 getSpeed() {
//        return body.getSpeed();
//    }
//
//    @Override
//    public boolean isFaceRight() {
//        return faceRight;
//    }
//
//    @Override
//    public Constants.airStates getAirState() {
//        return airState;
//    }
//
//    @Override
//    public Constants.states getState() {
//        return state;
//    }
//
//    public Vector2 getHead(){
//        return head;
//    }
//
//    public Vector2 getFoot(){
//        return foot;
//    }
//
//    @Override
//    public Vector2 getrHand() {
//        return rHand;
//    }
//
//    @Override
//    public Vector2 getlHand() {
//        return lHand;
//    }
//
//    //Setters////////////////////////////////////////////////////////////////
//
////    public void setWalkPath(Array<Vector2> walkPath, int startIndex) {
////        path = walkPath;
////        gndLineStart = startIndex;
////        lineStart.set(walkPath.get(gndLineStart));
////        gndLineEnd = startIndex + 1;
////        lineEnd.set(walkPath.get(gndLineEnd));
////        body.setSpeed(new Vector2(0,0));
////        setDirection();
////    }
//
//    public void setDirection(){
//        Vector2 temp = path.get(gndLineEnd).cpy();
//        direction = temp.sub(path.get(gndLineStart).cpy());
//        direction.nor();
//        foot.set(Intersector.nearestSegmentPoint(path.get(gndLineStart), path.get(gndLineEnd), foot, nearest));
////        Gdx.app.log("pos before", pos.toString());
//        body.setPos(new Vector2(foot.x - image.getWidth() / 2, foot.y));
////        Gdx.app.log("pos after", pos.toString());
//    }
////
////    public void setPos(Vector2 newPos) {
////        body.setPos(newPos);
////    }
////
////    public void setFoot(Vector2 newFoot) {
////        foot.set(newFoot);
//////        body.setPos(foot.x - image.getWidth() / 2, foot.y);
////    }
////
////
////    public void setSpeed(Vector2 newSpeed) {
////        body.setSpeed(newSpeed);
////    }
//
//    @Override
//    public void setDirection(Vector2 dir) {
//        direction.set(dir);
//    }
//
//    @Override
//    public boolean setFacing(boolean faceRight) {
//        return this.faceRight = faceRight;
//    }
//
//    @Override
//    public Constants.airStates setAirState(Constants.airStates air) {
//        airState = air;
//        if (airState == GROUNDED) blockFoot = true;
////        switch (airState){
////            case GROUNDED:
////                blockFoot = true;
////                break;
////            case JUMPING:
////            case FALLING:
////            case GLIDING:
////                break;
////        }
//        return airState;
//    }
//
//    @Override
//    public Constants.states setState(Constants.states action) {
//        return state = action;
//    }

    public void dispose(){

    }

}
