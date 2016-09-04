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

    protected Array<Vector2> walkPath = new Array<Vector2>();
    protected int lineIndex;
    protected Vector2 lineStart = new Vector2();
    protected Vector2 lineEnd = new Vector2();
    protected Vector2 groundStart = new Vector2();
    protected Vector2 groundEnd = new Vector2();

    //States
    protected Constants.airStates airState;
    protected Constants.states state;

    //Sensor points
    protected Vector2 head = new Vector2();
    protected Vector2 foot = new Vector2();
    protected Vector2 rHand = new Vector2();
    protected Vector2 lHand = new Vector2();
    protected Vector2 botRight = new Vector2();

    protected Character(Polygon shape, Vector2 initPos, World world) {
        super(shape, initPos, world);
        walkPath = null;
        updateVerts();
    }

    protected void updateVerts(){
        head.set(pos.x + shape.getBoundingRectangle().width / 2, pos.y + shape.getBoundingRectangle().getHeight());
        foot.set(pos.x + shape.getBoundingRectangle().width / 2, pos.y);
        rHand.set(pos.x + shape.getBoundingRectangle().width, pos.y + shape.getBoundingRectangle().height / 2);
        lHand.set(pos.x, pos.y + shape.getBoundingRectangle().height / 2);
        botRight.set(pos.x + shape.getBoundingRectangle().width, pos.y);
    }

    @Override
    public void update(float delta, Array<BaseEntity> entities){
        super.update(delta, entities);
        updateVerts();
    }

    public int getGround(){
        Array<Ground> ground = world.getGround();
        Array<Vector2> sections;
        if (walkPath == null) {
            for (int j = 0; j < ground.size; j++) {
                sections = ground.get(j).getWalkPath();
                if (foot.x < sections.first().x) continue;
                if (foot.x > sections.get(sections.size - 1).x) continue;
                Gdx.app.log("Foot", "between ground" + j);
                for (int i = 0; i < sections.size - 1; i++) {
                    Vector2 p1 = sections.get(i);
                    Vector2 p2 = sections.get(i + 1);
                    if (p1.x < foot.x && foot.x < p2.x) {
                        Gdx.app.log("Testing", p1.toString() +", "+ foot.toString() + ", "+ p2.toString());
                        if (Intersector.distanceSegmentPoint(p1, p2, foot) < Constants.TOLERANCE) {
                            Gdx.app.log("Found", p1.toString() + ", " + p2.toString() + ", " + foot.toString());
                            walkPath = sections;
                            groundStart = walkPath.first();
                            groundEnd = walkPath.get(walkPath.size - 1);
                            lineStart = p1;
                            lineEnd = p2;
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }

    public void setDirection(){
        Vector2 temp = walkPath.get(lineIndex + 1).cpy();
        direction = temp.sub(walkPath.get(lineIndex).cpy());
        direction.nor();
//        foot.set(Intersector.nearestSegmentPoint(path.get(gndLineStart), path.get(gndLineEnd), foot, nearest));
//        Gdx.app.log("pos before", pos.toString());
//        body.setPos(new Vector2(foot.x - image.getWidth() / 2, foot.y));
//        Gdx.app.log("pos after", pos.toString());
    }

//    protected boolean jumping;
//    protected float jumpHeight;
//    protected boolean faceRight;
//    protected boolean attacking;
//    protected boolean throwing;
//    protected boolean climbing;
//    protected boolean blockLeft;
//    protected boolean blockRight;
//    protected Vector2 direction = new Vector2();
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
//    protected abstract Vector2 getHead();
//    protected abstract Vector2 getFoot();
//    protected abstract Vector2 getrHand();
//    protected abstract Vector2 getlHand();


}
