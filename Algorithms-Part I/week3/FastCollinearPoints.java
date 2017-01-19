/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: Point.java, LineSegment.java
 *  
 *  An fast method to find segment.
 *  Given a point p, the following method determines whether p participates in a set of 4 or more collinear points. 
 *  1. Think of p as the origin. 
 *  2. For each other point q, determine the slope it makes with p. 
 *  3. Sort the points according to the slopes they makes with p. 
 *  4. Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. 
 *     If so, these points, together with p, are collinear. 
 *  Applying this method for each of the n points in turn yields an efficient algorithm to the problem.
 *
 ******************************************************************************/
 
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    private LineSegment[] arrSegments;
    
    private void checkPoints(Point[] points) {               // check if the input is legal
        for (int i = 0; i < points.length-1; i++) {
            if (points[i] == null) {                         // check if the point is null
                throw new NullPointerException();
            }
            
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) {                     // check if the point is null
                    throw new NullPointerException();
                }
                
                if (points[i].compareTo(points[j]) == 0) {   // check if the point is repeated
                    throw new IllegalArgumentException();
                }
            }
        }
    }   
    
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        checkPoints(points);
        List<LineSegment> segmentsList = new ArrayList<>();;
        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        for (Point beginPoint : points) {
            // sort the pointsCopy by the slope to the beginPoint
            Arrays.sort(pointsCopy, beginPoint.slopeOrder());   
            List<Point> slopePointList = new ArrayList<>();
            
            // create two variables to store slope and previous slope for comparing if they are same
            double slope = 0;
            double preSlope = 0;
            
            for (int i = 1; i < pointsCopy.length; i++) {
                // calculate the slope between beginPoint and other points
                slope = beginPoint.slopeTo(pointsCopy[i]);   
                if (slope == preSlope) {
                    slopePointList.add(pointsCopy[i]);         // find points with same slope and save them
                } else {
                    if (slopePointList.size() >= 3) {
                        slopePointList.add(beginPoint);        // save the beginPoint
                        Collections.sort(slopePointList);      // sort points by x, y
                        
                        Point startPoint = slopePointList.get(0);     // get startPoint of segment
                        // if startPoint is equal to beginPoint, then the segment will be stored.
                        // It is just a rule to avoid the repeated segment. There are other method to avoid this.
                        // But it can not avoid repeated calculation to improve the efficient.
                        if (startPoint == beginPoint) {
                            Point endPoint = slopePointList.get(slopePointList.size()-1);  // get endPoint of segment
                            segmentsList.add(new LineSegment(startPoint, endPoint));    // save the segment
                        }
                    }
                    slopePointList.clear();
                    slopePointList.add(pointsCopy[i]);
                }
                preSlope = slope;    // update the slope
            }
            
            if (slopePointList.size() >= 3) {
                slopePointList.add(beginPoint);
                Collections.sort(slopePointList);
                
                Point startPoint = slopePointList.get(0);
                if (startPoint == beginPoint) {
                    Point endPoint = slopePointList.get(slopePointList.size()-1);
                    segmentsList.add(new LineSegment(startPoint, endPoint));
                }
            }
        }
        arrSegments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }
    
    public int numberOfSegments() {       // the number of line segments
        return arrSegments.length;
    }
    
    public LineSegment[] segments() {      // the line segments
        return Arrays.copyOf(arrSegments, numberOfSegments());
    }
}
