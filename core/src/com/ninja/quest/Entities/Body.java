package com.ninja.quest.Entities;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ninja.quest.Constants.Constants;

/**
 * Created by Bman on 30/07/2016.
 * This will be used to hold the body values for all entities :
 *
 * - position
 * - shape
 * - bounding rectangle (1 tile larger on all sides for sweeping collisions
 * - speed
 * - acceleration
 * -
 */
class Body {
    private Vector2 pos = new Vector2();
    private Vector2 speed = new Vector2(0,0);
    private Vector2 acceleration = new Vector2();
    private float width, height;
    private Rectangle bounds = new Rectangle();
    private Polygon shape = new Polygon();
    private boolean moveable;

    Body(Polygon shape, boolean canMove){
        this.shape = shape;
        width = shape.getBoundingRectangle().width;
        height = shape.getBoundingRectangle().height;
        bounds = shape.getBoundingRectangle();
        resizeBounds();
        moveable = canMove;
    }

    Body(float[] verts, Vector2 initPos){
        shape.setVertices(verts);
        shape.translate(initPos.x, initPos.y);
        resizeBounds();
    }

    private void resizeBounds(){
        bounds.x -= Constants.PixPerTile;
        bounds.y -= Constants.PixPerTile;
        bounds.width += 2 * Constants.PixPerTile;
        bounds.height += 2 * Constants.PixPerTile;
    }

    public void update(float dt, Vector2 direction){
        if (moveable) {
            move(dt, direction);
            updateBounds();
        }
    }

    public void move(float dt, Vector2 direction){
        speed.set(direction.scl(Constants.MAX_RUN_SPEED * dt));
        pos.add(speed);
        shape.translate(speed.x, speed.y);
    }

    public void resolve(Vector2 direction){
        pos.add(direction);
        shape.translate(direction.x, direction.y);
        updateBounds();
    }

    private void updateBounds(){
        bounds = shape.getBoundingRectangle();
        resizeBounds();
    }

    public void setPos(Vector2 position){
        pos.set(position);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Vector2 getPos(){
        return pos;
    }

    public Vector2 getSpeed(){
        return speed;
    }

    public Polygon getShape(){
        return shape;
    }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public boolean canMove() { return moveable; }

}

//    public void run(Vector2 direction){
//
//    }
//
//    public void walk(int movement, Vector2 direction, float dt){
//        if (movement != 0) {
//            if (speed.len() <= Constants.MAX_RUN_SPEED) {
//                speed.add(direction.cpy().scl(movement * Constants.RUN_IMPULSE * dt));
//            } else {
//                speed.set(direction.cpy().scl(movement * Constants.MAX_RUN_SPEED));
//            }
//        }
//        else
//            speed.scl(Constants.DAMPING);
////            speed.lerp(speed.set(0,0), 0.7f);
//    }
//
//    public void airSpeed(int direction){
//        acceleration.x = direction * Constants.RUN_IMPULSE ;
//        if (Math.abs(speed.x) >= Math.abs(Constants.MAX_RUN_SPEED)) {
////                Gdx.app.log("speed.x max", Float.toString(speed.x));
//            speed.x = direction * Constants.MAX_RUN_SPEED;
//        } else {
////                Gdx.app.log("speed.x accel", Float.toString(speed.x));
//            speed.x += direction * acceleration.x;
//        }
//        if (direction == 0){
//            speed.x = speed.x * Constants.DAMPING;
////            Gdx.app.log("no move", Float.toString(speed.x));
//        }
//    }
//
//    public void jump() {
//        speed.y = Constants.JUMP_SPEED;
//    }
//
//    public void climb(){
//
//    }
//
//    public void fall(float dt){
//        acceleration.set(0, -Constants.gravity).scl(dt);
//        if (Math.abs(speed.y) <= Math.abs(Constants.MAX_FALL_SPEED)){
////                    Gdx.app.log("Speed", Float.toString(speed.y));
//            speed.y += acceleration.y;
////                    Gdx.app.log("Speed", Float.toString(speed.y));
//        }else {
//            speed.y = -Constants.MAX_FALL_SPEED;
////                    Gdx.app.log("Max speed", "");
//        }
//    }
//
//    public void updateBody(float dt){
//        pos.add(speed.cpy().scl(dt));
//    }
//
//    public void moveOnce(Vector2 moveBy){
//        pos.add(moveBy);
//    }
//
//    public void glide(){
//        speed.y = -Constants.MAX_FALL_SPEED / 3;
//    }
//
//    public Vector2 getPos() {
//        return pos;
//    }
//
//    public void setPos(Vector2 pos) {
//        this.pos = pos;
//    }
//
//    public Vector2 getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(Vector2 speed) {
//        this.speed = speed;
//    }
//
//    public void setSpeedY(float y){
//        speed.y = y;
//    }
//
//    public void setSpeedX(float x){
//        speed.x = x;
//    }
//
//    public Vector2 calcSpeed() {
//        return acceleration;
//    }
//
//    public void setAcceleration(Vector2 acceleration) {
//        this.acceleration = acceleration;
//    }



