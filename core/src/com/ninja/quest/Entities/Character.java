package com.ninja.quest.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.ninja.quest.Constants.Constants;

/**
 * Created by Bman on 09/07/2016.
 *
 * A character is an entity, that can move around (individual NPCs have AI, Players use input)
 * It can jump
 * state is either airborn, grounded
 * can walk, face left, face right, attack, jump, climb, throw or shoot
 * so, updates,
 */
public abstract class Character extends BaseEntity implements Disposable {

    //Variables related to Grounded state when not grounded walkpath == null
    protected Array<Vector2> walkPath = new Array<Vector2>();
    protected int lineIndex;
    protected Vector2 lineStart = new Vector2();
    protected Vector2 lineEnd = new Vector2();
    protected Vector2 groundStart = new Vector2();
    protected Vector2 groundEnd = new Vector2();
    protected Vector2 nearestPoint = new Vector2();
    protected Array<Ground> ground = new Array<Ground>();
    protected Array<Vector2> sections = new Array<Vector2>();

    //States
    protected Constants.airStates airState;
    protected Constants.states state;

    //Sensor points
    protected Vector2 head = new Vector2();
    protected Vector2 foot = new Vector2();
    protected Vector2 rHand = new Vector2();
    protected Vector2 lHand = new Vector2();
    protected Vector2 botRight = new Vector2();
    //TODO: add sensor to detect what enemies are on the screen, enemies off the screen by more than 1 square will be in a wait state
//    TODO: Fix the way that characters walk on the ground, so that the character walks along the ground
    protected boolean facingRight = true;

    protected Character(Polygon shape, Vector2 initPos, World world) {
        super(shape, initPos, world);
        this.walkPath = null;
        ground = world.getGround();
        updateVerts();
    }

    protected void updateVerts(){
        this.head.set(this.pos.x + this.shape.getBoundingRectangle().width / 2,
                this.pos.y + this.shape.getBoundingRectangle().getHeight());
        this.foot.set(this.pos.x + this.shape.getBoundingRectangle().width / 2, this.pos.y);
        this.rHand.set(this.pos.x + this.shape.getBoundingRectangle().width,
                this.pos.y + this.shape.getBoundingRectangle().height / 2);
        this.lHand.set(this.pos.x, this.pos.y + this.shape.getBoundingRectangle().height / 2);
        this.botRight.set(this.pos.x + this.shape.getBoundingRectangle().width, this.pos.y);
    }

    @Override
    public void update(float delta, Array<BaseEntity> entities){
        super.update(delta, entities);
        updateVerts();
    }

    public int getGround(){
        /**This one works, be careful of changes*/
        if (this.walkPath == null) {
            for (int j = 0; j < ground.size; j++) {
                this.sections = ground.get(j).getWalkPath();
                if (this.foot.x < this.sections.first().x) continue;
                if (this.foot.x > this.sections.get(this.sections.size - 1).x) continue;
//                Gdx.app.log("Foot", "between ground " + j);
                for (int i = 0; i < this.sections.size - 1; i++) {
                    this.lineStart = sections.get(i);
                    this.lineEnd = sections.get(i + 1);
                    if (this.lineStart.x < this.foot.x && this.foot.x < this.lineEnd.x) {
//                        Gdx.app.log("Testing; p1", p1.toString() +", foot:"+ foot.toString() + ", p2"+ p2.toString());
                        if (Intersector.distanceSegmentPoint(this.lineStart, this.lineEnd, this.foot) < Constants.TOLERANCE) {
//                            Gdx.app.log("Found p1", p1.toString() + ", foot:" + foot.toString() + ", p2" + p2.toString());
                            Intersector.nearestSegmentPoint(this.lineStart, this.lineEnd, this.foot, this.nearestPoint);
                            this.pos.set(this.foot.x - this.shape.getBoundingRectangle().width / 2, nearestPoint.y);
                            updateVerts();
                            Gdx.app.log("foot: " + foot.toString(), "nearest: " + nearestPoint.toString());
                            this.walkPath = sections;
                            this.groundStart = walkPath.first();
                            this.groundEnd = this.walkPath.get(this.walkPath.size - 1);
                            this.lineIndex = i;
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }

    public Vector2 calcDirection(){
        Vector2 temp = this.lineEnd.cpy();
        dirVec = temp.sub(this.lineStart.cpy());
        dirVec.nor();
//        Gdx.app.log("Calc dirVec", dirVec.toString());
        return dirVec;
    }

    public Vector2 walkDirection(){
        // TODO: 10/14/2016 fix the walk direction so that the speed gets adjusted to the direction of the ground segment
        // that the player is standing on.
        // if the player has a valid walkpath
        // save the previous direction vector
        // if there is a change to the direction, or if the player walks off the edge
        // then recalculate the direction
        if (this.walkPath != null){
//            if (!isGroundEdge()) {
//                Gdx.app.log("Not ground edge", "");
            if (needNextLine()) {
                if (!isGroundEdge()) {
                    Gdx.app.log("Need next line", "");
                    getNextLine();
                } else {
                    // TODO: 10/16/2016 make sure that the player is actually past the edge
                    walkOffEdge();
                }
            }
            if (needPrevLine()) {
                Gdx.app.log("Need prev line", "");
                getPrevLine();
            }
//            }
            return calcDirection();
        }
//            } else {
//                walkOffEdge();
//                Gdx.app.log("Walked off edge", "");
//
//                return dirVec.set(0,0);
//            }
//
//            return calcDirection();
//        } else
        return dirVec.set(1,0);
    }

    public boolean needPrevLine(){
        if (speed.x < 0){
            if (Intersector.distanceSegmentPoint(this.lineStart, this.lineEnd, this.foot) < Constants.TOLERANCE){
                return true;
            }
        }
        return false;
    }

    public boolean needNextLine(){
        if (speed.x > 0){
            if (this.foot.x > this.lineEnd.x ){
                return true;
            }
        }
        return false;//this.getFoot().x > this.getLineEnd().x && this.speed.x >= 0f;
    }

    public void getPrevLine(){
////  THIS IS WRONG... get previous line
////        1- make sure there IS a previous line
////        2- set the end point to the start point
////        3- reduce the line index by 1
////        4- get the point at the new index and assign it to the linestart
//
//        if (this.getLineStart() != this.getGroundStart()) {
//            this.getLineEnd().set(this.getLineStart());
//            this.setLineIndex(getLineIndex() - 1);
//
////            this.setLineStart(groundStart);
////            this.setLineIndex(getLineIndex() - 1);
//////            setDirection();
//        }
    }

    public void getNextLine(){
        //We know that this is not the edge of the ground at this point
        // TODO: 10/16/2016 make above comment true
        if (this.lineIndex <= this.walkPath.size - 2){
            this.lineStart.set(lineEnd);
            this.lineIndex++;
            this.lineEnd.set(this.walkPath.get(this.lineIndex + 1));
            Gdx.app.log("Got Next Line", Integer.toString(lineIndex));
        }
//        if (this.getLineIndex() + 1 <= this.getWalkPath().size - 2) {
//            this.setLineStart(this.getLineEnd());
//            lineIndex++;
////            setDirection();
//        }
    }

    public boolean isGroundEdge(){
        // TODO: 10/16/2016 figure out if the player is approaching the ground edge for the current platform

//        return ((this.walkPath.get(this.walkPath.size - 1).x < this.pos.x) || (this.walkPath.first().x >= this.botRight.x));
//        return (this.lineStart.equals(this.groundStart) && (this.botRight.x < this.lineStart.x)) ||
//                (this.lineEnd.equals(this.groundEnd) && (this.pos.x > this.groundEnd.x));
        return false;
    }

    public void walkOffEdge(){
        //once the player has walked off the edge
        this.walkPath = null;
        this.airState = Constants.airStates.FALLING;
    }

//    protected boolean jumping;
//    protected float jumpHeight;
//    protected boolean faceRight;
//    protected boolean attacking;
//    protected boolean throwing;
//    protected boolean climbing;
//    protected boolean blockLeft;
//    protected boolean blockRight;
//    protected Vector2 dirVec = new Vector2();
//

//
//    public Character(SpriteBatch batch) {
//        super(batch);
//    }
//
//    public void debugDraw(ShapeRenderer shapeRenderer){
//        shapeRenderer.polygon(body.getShape().getTransformedVertices());
//    }
//
//    public Rectangle getBounds() {
//        return body.getBounds();
//    }
//
//    public boolean isBlockLeft(boolean blocked){
//        return blockLeft = blocked;
//    }
//
//    public boolean isBlockRight(boolean blocked){
//        return blockRight = blocked;
//    }


//    protected abstract float[] initPoly();
//    protected abstract Constants.airStates getAirState();
//    protected abstract Constants.states getState();
//    protected abstract Constants.airStates setAirState(Constants.airStates air);
//    protected abstract Constants.states setState(Constants.states action);
//    protected abstract boolean isFaceRight();
//    protected abstract boolean setFacing(boolean faceRight);
//    protected abstract void airMove(float dt);
//    protected abstract void walking(float dt);
//    protected abstract void move(Vector2 speed);
//    protected abstract boolean isOnLadder();
//    protected abstract void climbSpeed(float dt);
//    protected abstract void startClimbing();
//    protected abstract void setDirection(Vector2 dir);

    public Array<Vector2> getWalkPath(){
        return this.walkPath;
    }

    public int getLineIndex() {
        return this.lineIndex;
    }

//    public void setLineIndex(int lineIndex) {
//        this.lineIndex = lineIndex;
//    }

    public void setLineStart(Vector2 lineStart) {
        this.lineStart = lineStart;
    }

    public void setLineEnd(Vector2 lineEnd) {
        this.lineEnd = lineEnd;
    }

    public Vector2 getLineStart() {
        return this.lineStart;
    }


    public Vector2 getLineEnd() {
        return this.lineEnd;
    }

    public Vector2 getGroundStart() {
        return this.groundStart;
    }

    public Vector2 getGroundEnd() {
        return this.groundEnd;
    }

    public Vector2 getHead() {
        return this.head;
    }

    public void setHead(float x, float y){
        this.head.set(x,y);
    }

    public Vector2 getFoot() {
        return this.foot;
    }

    public void setFoot(float x, float y){
        this.foot.set(x, y);
    }

    public Vector2 getBotRight() {
        return this.botRight;
    }

    public void setBotRight(float x, float y){
        this.botRight.set(x,y);
    }

    public Vector2 getrHand() {
        return this.rHand;
    }

    public void setrHand(float x, float y){
        this.rHand.set(x,y);
    }

    public Vector2 getlHand() {
        return this.lHand;
    }

    public void setlHand(float x, float y){
        this.lHand.set(x,y);
    }
}
