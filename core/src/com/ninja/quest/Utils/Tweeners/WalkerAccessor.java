package com.ninja.quest.Utils.Tweeners;

import com.ninja.quest.Entities.walkingChar;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Bman on 12/2/2016.
 *
 * A walking character tweener has so many more potential options
 *
 */

public class WalkerAccessor implements TweenAccessor<walkingChar> {

    public static final int SPEED_X = 0;
    public static final int SPEED_Y = 1;
    public static final int SPEED_XY = 2;

    @Override
    public int getValues(walkingChar target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case SPEED_X: returnValues[0] = target.getSpeed().x;
                return 1;
            case SPEED_Y: returnValues[0] = target.getSpeed().y;
                return 1;
            case SPEED_XY:
                returnValues[0] = target.getSpeed().x;
                returnValues[1] = target.getSpeed().y;
                return 2;
            default:
                assert false; return -1;
        }
    }

    @Override
    public void setValues(walkingChar target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case SPEED_X: target.setSpeedX(newValues[0]);
                break;
            case SPEED_Y: target.setSpeedY(newValues[0]);
                break;
            case SPEED_XY: target.setSpeed(newValues[0], newValues[1]);
                break;
            default: assert false; break;
        }
    }

}
