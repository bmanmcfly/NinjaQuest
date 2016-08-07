package com.ninja.quest.Constants;

/**
 * Created by Bman on 27/02/2016.
 * The Enums are to handle various things like states
 */
public class Enums {
    //these are the various states that the character can be in at any given time
    public enum State {
        SPAWN, IDLE, RUN, ATTACK, THROW, JUMP,
        FALL, JUMP_THROW, SLIDE, GLIDE, JUMP_ATTACK, CLIMB, DEAD
    }

    //These are the various attack levels that can be performed by the player will have this level
    //increase based on items picked up
    public enum AtkType {
        WEAK, MEDIUM, STRONG
    }
}
