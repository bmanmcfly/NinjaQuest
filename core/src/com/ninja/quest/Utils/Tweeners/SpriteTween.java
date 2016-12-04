package com.ninja.quest.Utils.Tweeners;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Bman on 11/15/2016.
 *
 * This is a sample accessor for the tween engine
 */

public class SpriteTween implements TweenAccessor<Sprite> {

    public static final int POS_XY = 1;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch(tweenType) {
            case POS_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2; // ** one case for each object - returned one as only 1 value is being changed **//
            default: assert false; return -1;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType){
            case POS_XY:
                target.setPosition(newValues[0], newValues[1]);
                break;
            default:
                assert false; break;
        }
    }
}
