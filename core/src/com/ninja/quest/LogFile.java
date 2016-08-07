package com.ninja.quest;

/**
 * Created by Bman on 04/03/2016.
 *
 * Going to make this a devlog
 *
 */

/** First step in the log;
*  Ive got the character class templated
 *  then an input class which will be modified to handle all the input
* The map loader will become a full parser
 * game screen is the main game file where the action happens
 * loading screen will implement asset loader
 * constants is a nice location for all global constants
 * Enums will deal with all state handles
 *
 * I will start being more detailed in logging changes so that I won't
 * get myself lost in the code.
*
*/

/**
 * Today I managed to fix the ortho camera so that the camera will be locked into the map
 * Next I adjusted the input to move the character in a way that is locked with the frame rate
 * added the hero spawn into the map parser
 *
 *
 */

/**
* March 7 - Got the asset loader working, literally just had to copy the from the badlogic site
 * will add in asset loading for all resources, and then will move the loading to a dedicated loading
 * screen.
*
* */

/**
 * March 8 - added gravity to start the process of the controls
 *      working on polygon collision detection and if that doesn't work then I will just use
 *      the tiled map settings to set collisions
 * */

/**
 * May 5 - Because the collision detection to this point is only to determine if the player is on the
 *      ground, fixed it now, except will have to modify because it will only trigger the collision when the
 *      foot point enters the polygon
 */


/**
 * In order to have the terrain completed, when the player is grounded, gotta figure out how to load
 * a sequence of points to know which direction the player is moving as the terrain changes angles.
 *
 *
 */

/**
 * Had to do ALOT of refactoring in order to move along the project...
 */

public class LogFile {
}
