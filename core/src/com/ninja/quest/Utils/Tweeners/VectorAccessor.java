package com.ninja.quest.Utils.Tweeners;

import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Bman on 12/2/2016.
 *
 * The shape accessor will be useful in tweening properties like speed
 */

public class VectorAccessor implements TweenAccessor<Vector2> {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int XY = 2;

    @Override
    public int getValues(Vector2 target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case X: returnValues[0] = target.x;
                return 1;
            case Y: returnValues[0] = target.y;
                return 1;
            case XY:
                returnValues[0] = target.x;
                returnValues[1] = target.y;
                return 2;
            default:
                assert false; return -1;
        }
    }

    @Override
    public void setValues(Vector2 target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case X: target.x = newValues[0];
                break;
            case Y: target.y = newValues[0];
                break;
            case XY: target.set(newValues[0], newValues[1]);
                break;
            default: assert false; break;
        }
    }
}
