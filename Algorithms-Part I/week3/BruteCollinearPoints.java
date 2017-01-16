/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: Point.java, LineSegment.java
 *  
 *  A method that examines 4 points at a time 
 *  and checks whether they all lie on the same line segment, 
 *  returning all such line segments. 
 *  To check whether the 4 points p, q, r, and s are collinear, 
 *  check whether the three slopes between p and q, between p and r, and between p and s are all equal. 
 *
 ******************************************************************************/

import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
    private LineSegment[] arrSegments;
    
    private void checkPoints(Point[] points) {         // check if the input is legal
        for (int i = 0; i < points.length-1; i++) {
            if (points[i] == null) {                   // check if the point is null
                throw new NullPointerException();
            }
            
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) {               // check if the point is null
                    throw new NullPointerException();
                }
                
                if (points[i].compareTo(points[j]) == 0) {   // check if the point is repeated
                    throw new IllegalArgumentException();
                }
            }
        }
    }
    
    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        checkPoints(points);
        
        ArrayList<LineSegment> targetSeg = new ArrayList<>();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);    // make a copy of points
        Arrays.sort(pointsCopy);                                      // sort points by x and y
        
        for (int p = 0; p < pointsCopy.length-3; p++) {
            for (int q = p + 1; q < pointsCopy.length-2; q++) {
                for (int r = q + 1; r < pointsCopy.length-1; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        double cp1 = pointsCopy[p].slopeTo(pointsCopy[q]);  // error
                        double cp2 = pointsCopy[p].slopeTo(pointsCopy[s]);
                        double cp3 = pointsCopy[p].slopeTo(pointsCopy[r]);
                        if ((cp1 == cp2) && (cp1 == cp3)) {                // check if slopes are same 
                            targetSeg.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }
        
        arrSegments = targetSeg.toArray(new LineSegment[targetSeg.size()]);
    }
    
    public int numberOfSegments() {       // the number of line segments
        return arrSegments.length;
    }
    
    public LineSegment[] segments() {               // the line segments
        return Arrays.copyOf(arrSegments, numberOfSegments());
    }
}
