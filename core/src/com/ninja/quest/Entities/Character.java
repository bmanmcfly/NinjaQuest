package com.ninja.quest.Entities;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
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

    //States
    protected Constants.airStates airState;
    protected Constants.states state;

    //Sensor points
    protected Vector2 head = new Vector2();
    protected Vector2 foot = new Vector2();
    protected Vector2 rHand = new Vector2();
    protected Vector2 lHand = new Vector2();
    protected Vector2 botRight = new Vector2();

    protected Character(Polygon shape, Vector2 initPos) {
        super(shape, initPos);
        updateVerts();
    }

    protected void updateVerts(){
        head.set(pos.x + shape.getBoundingRectangle().width / 2, pos.y + shape.getBoundingRectangle().getHeight());
        foot.set(pos.x + shape.getBoundingRectangle().width / 2, pos.y);
        rHand.set(pos.x + shape.getBoundingRectangle().width, pos.y + shape.getBoundingRectangle().height / 2);
        lHand.set(pos.x, pos.y + shape.getBoundingRectangle().height / 2);
        botRight.set(pos.x + shape.getBoundingRectangle().width, pos.y);
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
