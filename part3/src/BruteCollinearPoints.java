import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {

    private final List<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        segments = new ArrayList<>();

        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int p = 0; p < points.length; p++) {
            if (points[p] == null) {
                throw new IllegalArgumentException();
            }

            for (int q = p + 1; q < points.length; q++) {
                if (points[q] == null || points[p].equals(points[q])) {
                    throw new IllegalArgumentException();
                }

                double slopeToQ = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < points.length; r++) {
                    double slopeToR = points[p].slopeTo(points[r]);
                    if (compare(slopeToQ, slopeToR)) {
                        for (int s = r + 1; s < points.length; s++) {
                            double slopeToS = points[p].slopeTo(points[s]);
                            if (compare(slopeToQ, slopeToS)) {
                                // Create the list of collinear points and sort them.
                                List<Point> collinearPoints = new ArrayList<>(4);
                                collinearPoints.add(points[p]);
                                collinearPoints.add(points[q]);
                                collinearPoints.add(points[r]);
                                collinearPoints.add(points[s]);
                                Collections.sort(collinearPoints);

                                Point minPoint = Collections.min(collinearPoints);
                                Point maxPoint = Collections.max(collinearPoints);
                                LineSegment newSegment = new LineSegment(minPoint, maxPoint);

                                segments.add(newSegment);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean compare(double d1, double d2) {
        return Double.compare(d1, d2) == 0;
    }


    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[segments.size()];
        return segments.toArray(lineSegments);
    }

}