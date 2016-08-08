package com.ninja.quest.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ninja.quest.Constants.Constants;
import com.ninja.quest.Entities.Body;
import com.ninja.quest.Entities.Ground;

public class collisionHandler {
    private static Intersector.MinimumTranslationVector MTD = new Intersector.MinimumTranslationVector();
    private Vector2 speedA, speedB;
    private Polygon polyA, polyB;
    private Vector2 correction = new Vector2();
    private Array<Vector2> polyArray = new Array<Vector2>();
    public collisionHandler(){
        speedA = new Vector2();
        speedB = new Vector2();

    }

    public boolean pruneCollisions(Body A, Body B){
        return Intersector.overlaps(A.getBounds(), B.getBounds());
    }

    public boolean getCollision(Body A, Body B){
        speedA = A.getSpeed();
        speedB = B.getSpeed();

        polyA = A.getShape();
        polyB = B.getShape();

        polyA.translate(speedA.x, speedA.y);
        polyB.translate(speedB.x, speedB.y);

        if (Intersector.overlapConvexPolygons(polyA, polyB, MTD)){
            correction.mulAdd(MTD.normal, MTD.depth);
            if (speedA.dot(correction) <= 0){ //if this is greater than 0, then we have a bad MTD
                return true;
            } else {
                correction.set(0,0);
                return true;
            }

        }
        return false;
    }

    public void resolve(Body A, Body B){
        if (A.canMove() && B.canMove()){
            correction.scl(0.5f);
            A.resolve(correction);
            B.resolve(correction.scl(-1));
        }
        if (A.canMove() && !B.canMove()){
            A.resolve(correction);
        }
    }

    public Array<Vector2> setPolyToVecArray(Polygon polygon){
        Array<Vector2> polyPoints = new Array<Vector2>();
        float[] vertices = polygon.getTransformedVertices();
        int numVertices = polygon.getTransformedVertices().length;
        polyPoints.clear();
        for (int i = 0; i < numVertices / 2; i++){
            float x = vertices[i * 2];
            float y = vertices[i * 2 + 1];
            polyPoints.add(new Vector2(x, y));
        }
        return polyPoints;
    }

}
//    private Vector2 MTV = new Vector2();
//    private Vector2 correction = new Vector2();
//    private Vector2 nearest = new Vector2();
////    private Array<Ground> ground = new Array<Ground>();
////    private Array<Vector2> path = new Array<Vector2>();
////    private Array<Terrain> terrain = new Array<Terrain>();
////    private Vector2 position = new Vector2();
//    private Array<Vector2> polyPointsA, polyPointsB;
//    private Array<Vector2> edgeDirsA, edgeDirsB;
////    private Vector2 axisA, axisB;
////    private Vector2 minMax = new Vector2();
//    private float minA, maxA;
//    private float minB, maxB;
//
//
//    private int polyIndex;
//
//    public collisionHandler(Array<Ground> walkTerrain) {
//        polyPointsA = new Array<Vector2>();
//        polyPointsB = new Array<Vector2>();
//        edgeDirsA = new Array<Vector2>();
//        edgeDirsB = new Array<Vector2>();
//    }
//
//    public Vector2 findMTV(Array<Vector2> axis, int num){
//        return null;
//    }
//
//    public boolean AxisSeparatePolygons(Vector2 axis, Array<Vector2> A, Array<Vector2> B ){
//        return true;
//    }
//
//    public void CalculateInterval(float axisX, float axisY) {
//        // Project polygon1 onto this axis
//        float min1 = polyPointsA.get(0).dot(axisX, axisY);
//        float max1 = min1;
//        for (int j = 0; j < polyPointsA.size; j++) {
//            float p = polyPointsA.get(j).dot(axisX, axisY);
//            if (p < min1) {
//                min1 = p;
//            } else if (p > max1) {
//                max1 = p;
//            }
//        }
//    }
//
//    public Vector2 updateCollision(Polygon shapeA, Polygon shapeB) {
//        if (sweepPrune(shapeA.getBoundingRectangle(), shapeB.getBoundingRectangle())) {
////            Gdx.app.log("sweep Prune", "success");
//            Vector2 resolve = collisionTest(shapeB, shapeA);
//            if (resolve != null) {
//                return resolve;
//            }
//        }
//        return null;
//    }
//
//
//
//    public boolean sweepPrune(Rectangle shape1, Rectangle shape2) {
//        return Intersector.overlaps(shape1, shape2);
//    }
//
//    public Vector2 collisionTest(Polygon moved, Polygon noMove) {
//
//        if (Intersector.overlapConvexPolygons(moved, noMove, MTD)) {
//            correction.set(0, 0);
////            Gdx.app.log("MTV", MTV.normal.scl(MTV.depth).toString());
//            return correction.mulAdd(MTD.normal, (MTD.depth));
//        }
//        return null;
//    }
//
//    public boolean checkPoint(Vector2 point, Polygon polygon) {
//        Array<Vector2> vec = setPolyToVecArray(polygon);
//        float dist;
//
//        if (Intersector.isPointInPolygon(vec, point)) {
//            for (int i = 0; i < vec.size; i++) {
//                dist = Intersector.distanceSegmentPoint(vec.get(i), vec.get(i == (vec.size - 1) ? 0 : i + 1), point);
//                Gdx.app.log("Test results: " + Integer.toString(i), Float.toString(dist));
//                if (dist < Constants.TOLERANCE) {
////                Gdx.app.log("Point ", "in polygon");
//                    Gdx.app.log("Point", point.toString());
//                    point.set(Intersector.nearestSegmentPoint(vec.get(i), vec.get(i == (vec.size - 1) ? 0 : i + 1), point, nearest));
//                    Gdx.app.log("After Point", point.toString());
//                    return true;
//                }
//
//            }
//        }
//        return false;
//    }
//
//    /** Determines on which side of the given line the point is. Returns -1 if the point is on the left side of the line, 0 if the
//     * point is on the line and 1 if the point is on the right side of the line. Left and right are relative to the lines direction
//     * which is linePoint1 to linePoint2. */
//    public static int pointLineSide (float linePoint1X, float linePoint1Y, float linePoint2X, float linePoint2Y, float pointX,
//                                     float pointY) {
//        return (int)Math.signum((linePoint2X - linePoint1X) * (pointY - linePoint1Y) - (linePoint2Y - linePoint1Y)
//                * (pointX - linePoint1X));
//    }
//
//    public Vector2 getNearest(){
//        return nearest;
//    }
//
//
//    public int prunePath(Array<Vector2> land, Vector2 foot){
//        for (int i = 0; i < land.size - 1; i ++){
//            Vector2 p1 = land.get(i).cpy();
//            Vector2 p2 = land.get(i + 1).cpy();
////            Gdx.app.log("Prune Path, i = " + i, p1.toString() + " : " + foot.toString() + " : " + p2.toString());
//            if (p1.x < foot.x && foot.x < p2.x){
////                Gdx.app.log("Foot", "between line");
//                if (Intersector.distanceSegmentPoint(p1, p2, foot) <= Constants.TOLERANCE) {
////                    Gdx.app.log("Prune Path, i = " + i, p1.toString() + " : " + foot.toString() + " : " + p2.toString());
////                    Gdx.app.log("Prune Patch", "Success");
//                    return i;
//                }
//            }
//        }
//        return -1;
//    }
//}
