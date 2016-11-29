package com.ninja.quest.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Bman on 27/02/2016.
 *
 * The input class
 * DONE: part 1 -- make this the full controls for the player
 * TODO: part 2 -- determine if the app is being played on pc or android and send to the appropriate class
 * todo: part 2 will allow the controls to be determined at runtime, will just have to create equivalents to
 * TODO: add code to flip the boolean switches from touch / mouse controls this will allow more to show off
 * flip the boolean flags
 *
 * **Change - Instead of rolling my own input processor, change things to use a libgdx version **
 */
public class Input implements InputProcessor {

    public static boolean left = false;
    public static boolean right = false;
    public static boolean jump = false;
    public static boolean attack = false;
    public static boolean shoot = false;
    public static boolean climbUp = false;
    public static boolean climbDown = false;

    public Input(){}

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.LEFT:
                left = true;
                break;
            case com.badlogic.gdx.Input.Keys.RIGHT:
                right = true;
                break;
            case com.badlogic.gdx.Input.Keys.UP:
                climbUp = true;
                break;
            case com.badlogic.gdx.Input.Keys.DOWN:
                climbDown = true;
                break;
            case com.badlogic.gdx.Input.Keys.SPACE:
                jump = true;
                break;
            case com.badlogic.gdx.Input.Keys.A:
                attack = true;
                break;
            case com.badlogic.gdx.Input.Keys.S:
                shoot = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.LEFT:
                left = false;
                break;
            case com.badlogic.gdx.Input.Keys.RIGHT:
                right = false;
                break;
            case com.badlogic.gdx.Input.Keys.UP:
                jump = false;
                climbUp = false;
                break;
            case com.badlogic.gdx.Input.Keys.SPACE:
                jump = false;
                break;
            case com.badlogic.gdx.Input.Keys.DOWN:
                climbDown = false;
                break;
            case com.badlogic.gdx.Input.Keys.A:
                attack = false;
                break;
            case com.badlogic.gdx.Input.Keys.S:
                shoot = false;
                break;
            case com.badlogic.gdx.Input.Keys.ESCAPE:
                Gdx.app.exit();
                break;
        }
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link com.badlogic.gdx.Input.Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link com.badlogic.gdx.Input.Buttons#LEFT} on iOS.
     *
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     * @param pointer the pointer for the event.
     * @param button  the button   @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     * @param pointer the pointer for the event.  @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
