package com.ninja.quest.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.ninja.quest.Constants.Constants;
import com.ninja.quest.Utils.Input;

/**
 * Created by Bman on 8/6/2016.
 *
 * A refactored Player class
 */
public class Player extends Character implements Disposable {
    private SpriteBatch sb;
    private TextureAtlas atlas;
    private Input input;
    private Array<Vector2> path = new Array<Vector2>();
    private TextureAtlas.AtlasRegion region;

    //Controls
    private boolean moveLeft = false, moveRight = false, jumping = false;


    public Player(SpriteBatch batch, TextureAtlas atlas, Vector2 initPos, Polygon shape) {
        super(shape);
        sb = batch;
        this.atlas = atlas;
        this.pos = initPos;
        input = new Input();
    }

//        TextureAtlas.AtlasRegion region = atlas.findRegion("Idle_",0);
//        image.setRegion(region);
//        image.setPosition(initPos.x, initPos.y);
//        image.setBounds(initPos.x, initPos.y, Constants.PixPerTile * 1.1f, Constants.PixPerTile * 1.8f);

    public void updateInput(){
        moveLeft = Input.left;// && !blockLeft;
        moveRight = Input.right;// && !blockRight;
        jumping = Input.jump;// && !blockHead;
//        gliding = Input.jump && airState == FALLING;
    }

    public void update(float dt){
        updateInput();


    }

    @Override
    public void dispose() {

    }
}
