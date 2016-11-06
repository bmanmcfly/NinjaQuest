package com.ninja.quest.Constants;

import com.badlogic.gdx.Gdx;

/**
 * Created by Bman on 27/02/2016.
 *
 * The constants class holds all the constants that will be used throughout the project
 */
public class Constants {
    //Font constants
    public static final String FONT_CHARACTERS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    //Map and display constants
    public static final int PixPerTile = 32;
    public static final int HorTilesDisplay = 20;
    public static float AspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
    public static float ScreenWidth = PixPerTile * HorTilesDisplay;
    public static float ScreenHeight = ScreenWidth * AspectRatio;
    public static float mapWidth;
    public static float mapHeight;

    //Hero Constants
    public static final float PLAYER_WIDTH = 1.1f * (float)PixPerTile;
    public static final float PLAYER_HEIGHT = 1.8f * (float)PixPerTile;

    //physics constants
    public static final float gravity = 5f; //frames per second
    public static final float MAX_FALL_SPEED = 3f * PixPerTile; //frames per second
    public static final float JUMP_SPEED = 12f * PixPerTile;// * 60;
    public static final float MAX_JUMP_HEIGHT = 20f * PixPerTile;// * 60;
    public static final float TOLERANCE = 2f;
    public static final float RUN_IMPULSE = 1f * PixPerTile;// * 60;
    public static final float MAX_RUN_SPEED = (2f * PixPerTile) / 60; //2 tiles * 60 fps forced
    public static final float DAMPING = 0.25f;


    //Character States
    public enum states{
        CLIMB, SLIDE, THROW, ATTACK, WALKING, IDLE, HIT, DYING
    }

    public enum airStates{
        JUMPING, FALLING, GLIDING, GROUNDED
    }

    //The collision type will be set at the creation of an object and will help sort how
    //collisions will be handled or ignored.
    public static final short NONE = 0;
    public static final short PLAYER = 1;
    public static final short TERRAIN = 2;
    public static final short ENEMY = 4;
    public static final short P_BULLET = 8;
    public static final short E_BULLET = 16;
    public static final short LADDER = 32;
    public static final short SENSOR = 64;
    public static final short ALL = 128;

}
