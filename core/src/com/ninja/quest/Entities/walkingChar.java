package com.ninja.quest.Entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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

public abstract class walkingChar extends BaseEntity {

    //Variables related to Grounded state when not grounded walkpath == null
    protected Array<Vector2> walkPath = new Array<Vector2>();
    protected int lineIndex, lineEndIndex;
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


    protected walkingChar(Polygon shape, Vector2 position, World world) {
        super(shape, position, world);
        walkPath = null;
        ground.addAll(world.getGround());
        updateVerts();
    }

    protected void updateVerts(){
        head.set(pos.x + shape.getBoundingRectangle().width / 2,
                pos.y + shape.getBoundingRectangle().getHeight());
        foot.set(pos.x + shape.getBoundingRectangle().width / 2, pos.y);
        rHand.set(pos.x + shape.getBoundingRectangle().width,
                pos.y + shape.getBoundingRectangle().height / 2);
        lHand.set(pos.x, pos.y + shape.getBoundingRectangle().height / 2);
        botRight.set(pos.x + shape.getBoundingRectangle().width, pos.y);
    }

    @Override
    protected void debugDraw(ShapeRenderer sr){
        super.debugDraw(sr);
        sr.circle(head.x, head.y, 2);
        sr.circle(foot.x, foot.y, 2);
        sr.circle(rHand.x, rHand.y, 2);
        sr.circle(lHand.x, lHand.y, 2);
        sr.circle(botRight.x, botRight.y, 2);
        sr.circle(pos.x, pos.y, 2);
        sr.setColor(Color.LIME);
        sr.circle(lineStart.x, lineStart.y, 3);
        sr.circle(lineEnd.x, lineEnd.y, 3);

    }

    @Override
    public void update(float delta, Array<BaseEntity> entities){
        super.update(delta, entities);
        updateVerts();
    }

    void updateLine(float dt, float dir) {
        boolean prevLine = false;
        boolean nextLine = false;
        /**
         * The way this one will work :
         * - if the player is moving
         * - if the foot + speed this frame will pass the next line point
         * - 2 cases: Ground start or not
         * case1: if the player fully passes the edge, walkoff edge
         * case2:   - get the distance between the foot and the end point
         *          - reduce the speed by that distance
         *          - update to the new line points
         * - regardless of case if the line changes, start.set(walkpath.get(lineIndex) and
         * end.set(walkpath.get(lineindex+1)
         *
         *
         */
        if (dir == 0) return;
        if (dir < 0) {
            if (foot.x + speed.x * dt <= lineStart.x) {
                //lets assume that the y value will be correct find out later if that causes grief
                Gdx.app.log("Get Prev Line ", Integer.toString(lineIndex));
                if (lineIndex > 0) {
                    foot.set(lineStart.x, lineStart.y);
                    updateVerts();
                    lineIndex--;
                    lineEndIndex--;
                    Gdx.app.log("lineIndex " + Integer.toString(lineIndex), "lineEndIndex " + Integer.toString(lineEndIndex));
                    prevLine = true;
                }
                if (lineIndex == 0) {
                    if (botRight.x + speed.x * dt <= groundStart.x) {
                        //walk off edge
                        walkOffEdge();
                    }
                }
            }
        }
        if (dir > 0){
            if (foot.x + speed.x * dt >= lineEnd.x){
                //lets assume that the y value will be correct find out later if that causes grief
                Gdx.app.log("Get Next Line ", Integer.toString(lineIndex));
//                Gdx.app.log("walkpath ", Integer.toString(walkPath.size));
                if (lineEndIndex < walkPath.size - 1){
                    foot.set(lineEnd.x, lineEnd.y);
                    updateVerts();
                    lineIndex++;
                    lineEndIndex++;
                    Gdx.app.log("lineIndex ", Integer.toString(lineIndex));
                    prevLine = true;
                }
                if (lineEndIndex == walkPath.size - 1){
                    if (pos.x + speed.x * dt >= groundEnd.x){
                        walkOffEdge();
                    }
                }
            }
        }
        if (prevLine) {
            float startX = walkPath.get(lineIndex).x;
            float startY = walkPath.get(lineIndex).y;
            float endX = walkPath.get(lineEndIndex).x;
            float endY = walkPath.get(lineEndIndex).y;

            Gdx.app.log(Float.toString(startX) + " " + Float.toString(startY), Float.toString(endX) + " " + Float.toString(endY));
            lineEnd.set(endX, endY);
            lineStart.set(startX, startY);
            Vector2 tmp = calcDirection();
            float spd = speed.len() * dir;
            speed.set(tmp.x * spd, tmp.y * spd);
            Gdx.app.log("linestart " + lineStart.toString(), "lineEnd " + lineEnd.toString());
        }

    }

    public int getGround(){
        /**This one works, be careful of changes*/
        if (walkPath == null) {
            for (int j = 0; j < ground.size; j++) {
                sections.clear();
                sections.addAll(ground.get(j).getWalkPath());
                if (foot.x < sections.first().x) continue;
                if (foot.x > sections.get(sections.size - 1).x) continue;
//                Gdx.app.log("Foot", "between ground " + j);
                for (int i = 0; i < sections.size - 1; i++) {
                    lineStart.set(sections.get(i));
                    lineEnd.set(sections.get(i + 1));
                    if (lineStart.x < foot.x && foot.x < lineEnd.x) {
//                        Gdx.app.log("Testing; p1", p1.toString() +", foot:"+ foot.toString() + ", p2"+ p2.toString());
                        if (Intersector.distanceSegmentPoint(lineStart, lineEnd, foot) < Constants.TOLERANCE) {
//                            Gdx.app.log("Found p1", p1.toString() + ", foot:" + foot.toString() + ", p2" + p2.toString());
                            Intersector.nearestSegmentPoint(lineStart, lineEnd, foot, nearestPoint);
                            pos.set(foot.x - shape.getBoundingRectangle().width / 2, nearestPoint.y);
                            updateVerts();
                            Gdx.app.log("foot: " + foot.toString(), "nearest: " + nearestPoint.toString());
                            walkPath = sections;
                            Gdx.app.log("walkpath ", Integer.toString(walkPath.size));
                            groundStart.set(walkPath.first().x, walkPath.first().y);
                            groundEnd.set(walkPath.get(walkPath.size - 1).x, walkPath.get(walkPath.size - 1).y);
                            lineIndex = i;
                            lineEndIndex = i + 1;
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }

    public Vector2 calcDirection(){
        Vector2 temp = lineEnd.cpy();
        dirVec = temp.sub(lineStart.cpy());
        dirVec.nor();
//        Gdx.app.log("Calc dirVec", dirVec.toString());
        return dirVec;
    }

    public void walkOffEdge(){
        //once the player has walked off the edge
        walkPath = null;
        airState = Constants.airStates.FALLING;
    }

    public Array<Vector2> getWalkPath(){
        return walkPath;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public int getLineEndIndex() {
        return lineEndIndex;
    }

    public Vector2 getLineStart() {
        return lineStart;
    }


    public Vector2 getLineEnd() {
        return lineEnd;
    }

    public Vector2 getGroundStart() {
        return groundStart;
    }

    public Vector2 getGroundEnd() {
        return groundEnd;
    }

    public Vector2 getFoot() {
        return foot;
    }
}
