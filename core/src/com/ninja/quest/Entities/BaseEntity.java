package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
import com.ninja.quest.Constants.Constants;

import java.util.EnumSet;

/**
 * Created by Bman on 8/6/2016.
 *
 * The Gameobject is just the x, y position and a body
 * Also hold a sprite, and whether the object is alive or dead
 *
 * Change - the body idea just complicates the process and so the entity will hold the shape
 */
public abstract class BaseEntity implements Disposable{
    //Physics stuff
    World world;
    protected Intersector.MinimumTranslationVector MTV = new Intersector.MinimumTranslationVector();
    Vector2 pos = new Vector2();
    protected Vector2 prevPos = new Vector2();
    Vector2 speed = new Vector2(0,0);
    protected Vector2 dirVec = new Vector2(0,0);
    private Rectangle bounds = new Rectangle();
    protected Polygon shape = new Polygon();
    private boolean isAlive;
//    protected boolean remove = false; //set to true before removing entity
//    protected Array<Vector2> prePoints = new Array<Vector2>();
//    protected Array<Vector2> otherPoints = new Array<Vector2>();

//    protected short entityIs;
//    short collidesWith;
    protected Constants.CollisionFlags entityIsA;
    protected EnumSet<Constants.CollisionFlags> entityCollidesWith;

    //image and state
    protected Sprite sprite = new Sprite();
    private boolean isTouched = false;
    protected Constants.airStates airState;

    protected BaseEntity(Polygon shape, Vector2 position, World world){
        this.shape = shape;
        this.world = world;
        this.bounds = shape.getBoundingRectangle();
        resizeBounds();
//        shape.translate(position.x, position.y);
        this.pos = position;
        this.isAlive = true;
    }

    public void resizeBounds(){
        this.bounds.x -= Constants.PixPerTile / 2;
        this.bounds.y -= Constants.PixPerTile / 2;
        this.bounds.width += Constants.PixPerTile;
        this.bounds.height += Constants.PixPerTile;
    }

    private void updateBounds(){
        this.bounds = this.shape.getBoundingRectangle();
        resizeBounds();
    }

    public void update(float delta, Array<BaseEntity> entities){
        updateBounds();
    }

    protected void debugDraw(ShapeRenderer sr){
        Vector2 tmp = this.pos.cpy().add(this.speed.cpy().scl(10));
        sr.polygon(this.shape.getTransformedVertices());
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.line(this.pos, tmp);
    }

    void boundsDraw(ShapeRenderer sr){
        if (this.isTouched){
            sr.setColor(Color.RED);
        } else {
            sr.setColor(Color.BLUE);
        }
        sr.box(this.bounds.x, this.bounds.y, 0, this.bounds.getWidth(), this.bounds.getHeight(),0);
    }

    protected void collisionResponse(BaseEntity other){}

    public Array<Vector2> setPolyToVecArray(Polygon polygon){
        Array<Vector2> polyPoints = new Array<Vector2>();
        float[] vertices = polygon.getTransformedVertices();
        int numVertices = polygon.getTransformedVertices().length;
        polyPoints.clear();
        for (int i = 0; i < numVertices / 2; i++){
            float x = vertices[i * 2];
            float y = vertices[i * 2 + 1];
            polyPoints.add(new Vector2(x, y));
        }
        return polyPoints;
    }

    void collides(Array<BaseEntity> things){
        int size = things.size;
//        boolean collision = false;
        Polygon moveShape = this.shape;
        moveShape.translate(this.speed.x, this.speed.y);
        for(int i = 0; i < size; i++) {
            BaseEntity temp = things.get(i);
//            short thisTest = temp.entityIs;
            Constants.CollisionFlags testFlag = temp.entityIsA;
//            if (thisTest == this.entityIs) continue;
            if (testFlag == this.entityIsA) continue;
            // First, get the speed of both entities.  get the dot product between both speeds
//            temp.isTouched = (thisTest & this.collidesWith) == 0 &&
//                    Intersector.overlaps(temp.bounds, this.bounds);
            temp.isTouched = (this.entityCollidesWith.contains(testFlag) &&
                    Intersector.overlaps(temp.bounds, this.bounds));
            if (temp.isTouched) {
//                if (testFlag == LADDER) Gdx.app.log("Test", "Ladder");
                //now, move this to its next position,
                //test for collision with the object
                //if the test passes, add it to the list of collisions
                if (Intersector.overlapConvexPolygons(moveShape, temp.shape, this.MTV)) {
//                    Gdx.app.log("Collision", temp.shape.getBoundingRectangle().x + ", " + temp.shape.getBoundingRectangle().y + " " + this.pos.toString());
                    //Might need to setup some flags here in order to be sure that if the player and ladder, or other cases where
                    //an end contact may be required by testing which two objects are here.
                    this.collisionResponse(temp);
                    temp.collisionResponse(this);
                }
            }
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(entityIsA.name());
//        sb.append("ID").append(Short.toString(this.entityIs));
        sb.append("\nPosition :").append(this.pos);
        sb.append("\nSpeed: ").append(this.speed);
        sb.append("\nis Touched").append(Boolean.toString(this.isTouched));
        return sb.toString();
    }


    public Sprite getSprite(){
        return this.sprite;
    }

    public boolean isAlive(){
        return this.isAlive;
    }

    public boolean getTouched() {
        return this.isTouched;
    }

    public Vector2 getPos(){
        return this.pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getPrevPos() {
        return this.prevPos;
    }

    public Vector2 getSpeed() {
        return this.speed;
    }

    public Vector2 getDirVec() {
        return this.dirVec;
    }

    public void setDirVec(Vector2 dirVec) {
        this.dirVec = dirVec;
    }

    public Polygon getShape() { return this.shape; }

    public void setShape(Polygon shape) {
        this.shape = shape;
    }

//    public short getEntityIs() {
//        return this.entityIs;
//    }


//    public short getCollidesWith() {
//        return this.collidesWith;
//    }
//

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Intersector.MinimumTranslationVector getMTV() {
        return MTV;
    }

    public void setMTV(Intersector.MinimumTranslationVector MTV) {
        this.MTV = MTV;
    }

    public void setPrevPos(Vector2 prevPos) {
        this.prevPos = prevPos;
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public void setSpeed(float speedX, float speedY) {
        this.speed.set(speedX, speedY);
    }

    public void setSpeedX(float speedX) {
        this.speed.x = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speed.y = speedY;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Constants.CollisionFlags getEntityIsA() {
        return entityIsA;
    }

    public void setEntityIsA(Constants.CollisionFlags entityIsA) {
        this.entityIsA = entityIsA;
    }

    public EnumSet<Constants.CollisionFlags> getEntityCollidesWith() {
        return entityCollidesWith;
    }

    public void setEntityCollidesWith(EnumSet<Constants.CollisionFlags> entityCollidesWith) {
        this.entityCollidesWith = entityCollidesWith;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public void setAirState(Constants.airStates airState) {
        this.airState = airState;
    }

    public Constants.airStates getAirState() {
        return this.airState;
    }

    public void dispose(){

    }
}
