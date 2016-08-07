package com.ninja.quest.Utils;

/**
 * Created by Bman on 20/03/2016.
 *
 * The projection class gets the
 */
public class Projection {
    protected double min;
    protected double max;

    public Projection(double min, double max){
        this.min = min;
        this.max = max;
    }

    public boolean overlap(Projection p2){
        boolean temp = false;

        if(min > p2.max || max < p2.min)
            temp=true;

        return temp;
    }
}
