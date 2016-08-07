package com.ninja.quest.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.ninja.quest.Constants.Constants;

/**
 * Created by Bman on 27/02/2016.
 *
 * The Camera uses the basic elements of the orthocamer
 * with functions added for zooming in and out, movement and keeping the camera
 * in the bounds of the map
 */
public class myOrthoCam extends OrthographicCamera {
    float posX, posY;
    public myOrthoCam(){
        super();
    }


    public void displacement(float heroX, float heroY, TiledMap map){
        if (this.position.x < heroX - Gdx.graphics.getWidth() / 30)
            posX = heroX - Gdx.graphics.getWidth() / 10;
        else if (this.position.x > heroX + Gdx.graphics.getWidth() / 10)
            posX = heroX + Gdx.graphics.getWidth() / 10;

        if (this.position.y < heroY - Gdx.graphics.getHeight() / 10)
            posY = heroY - Gdx.graphics.getHeight() / 10;
        else if (this.position.y > heroY + Gdx.graphics.getHeight() / 10)
            posY = heroY + Gdx.graphics.getHeight() / 10;

        this.position.interpolate(new Vector3(posX, posY, 0), 0.45f, Interpolation.fade);

        //Positioning relative to the level map limits
        if(position.x + viewportWidth/2 >((float)(map.getProperties().get("width", Integer.class)* Constants.PixPerTile)))
            position.set((float)(map.getProperties().get("width", Integer.class)* Constants.PixPerTile) - viewportWidth / 2, position.y, 0);
        else if(position.x - viewportWidth / 2 < 0)
            position.set(viewportWidth / 2, position.y, 0);
        if(position.y + viewportHeight/2  > (float) map.getProperties().get("height", Integer.class) * Constants.PixPerTile)
            position.set(position.x, (float) map.getProperties().get("height", Integer.class) * Constants.PixPerTile - viewportHeight / 2, 0);
        else if(position.y - viewportHeight / 2 < 0)
            position.set(position.x, viewportHeight / 2, 0);

        //Zoom-in/Zoom-out
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            viewportWidth *= 1.01f;
            viewportHeight *= 1.01f;
            zoomLimit();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            viewportWidth *= 0.99f;
            viewportHeight *= 0.99f;
            zoomLimit();
        }
    }

    public void zoomLimit(){

        if (viewportWidth > Constants.mapWidth){
            viewportWidth = Constants.mapWidth;
            viewportHeight = viewportWidth * Constants.AspectRatio;
        } else if (viewportWidth < Constants.ScreenWidth / 2) {
            viewportWidth = Constants.ScreenWidth / 2;
            viewportHeight = viewportWidth * Constants.AspectRatio;
        } else if (viewportHeight > Constants.mapHeight) {
            viewportHeight = Constants.mapHeight;
            viewportWidth = viewportHeight / Constants.AspectRatio;
        } else if (viewportHeight < Constants.ScreenHeight / 2) {
            viewportHeight = Constants.ScreenHeight / 2;
            viewportWidth = viewportHeight / Constants.AspectRatio;
        }
    }

}
