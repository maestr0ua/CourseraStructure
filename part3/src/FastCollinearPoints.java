import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {

    private final List<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        segments = new ArrayList<>();

        check(points);
        Point[] copy = points.clone();

        List<Point> startPoints = new ArrayList<>();
        List<Point> endPoints = new ArrayList<>();

        for (int p = 0; p < points.length - 1; p++) {

            Arrays.sort(copy, points[p].slopeOrder());

            int startIndex = 0;
            int endIndex = 0;

            double slope = 0;

            for (int i = 0; i < copy.length; i++) {
                if (points[p] == copy[i]) {
                    continue;
                }
                if (compare(points[p].slopeTo(copy[i]), slope)) {
                    endIndex = i;
                } else {
                    if (endIndex - startIndex >= 2) {
                        addSegment(startPoints, endPoints, points[p], copy, startIndex, endIndex);
                    }

                    startIndex = i;
                    slope = points[p].slopeTo(copy[i]);
                }
            }

            if (endIndex - startIndex >= 2) {
                addSegment(startPoints, endPoints, points[p], copy, startIndex, copy.length - 1);
            }
        }
    }

    private boolean compare(double d1, double d2) {
        return Double.compare(d1, d2) == 0;
    }

    private void addSegment(List<Point> startPoints, List<Point> endPoints, Point startPoint, Point[] points, int start, int end) {
        List<Point> collinearPoints = new ArrayList<>(4);

        for (int i = start; i <= end; i++) {
            collinearPoints.add(points[i]);
        }
        collinearPoints.add(startPoint);

        Collections.sort(collinearPoints);

        Point minPoint = Collections.min(collinearPoints);
        Point maxPoint = Collections.max(collinearPoints);

        for (int i = 0; i < startPoints.size(); i++) {

            double existedSlope = startPoints.get(i).slopeTo(endPoints.get(i));
            double slope = startPoint.slopeTo(startPoints.get(i));
            if (compare(slope, existedSlope)) {
                return;
            }
        }
        startPoints.add(minPoint);
        endPoints.add(maxPoint);
        segments.add(new LineSegment(minPoint, maxPoint));
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

    private void check(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }

        if (points.length > 0) {
            if (points[0] == null) {
                throw new IllegalArgumentException();
            }


            mergeSort(points);
            for (int i = 1; i < points.length; i++) {
                if (points[i] == null || points[i] == points[i - 1]) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private static void mergeSort(Comparable [ ] a)
    {
        Comparable[] tmp = new Comparable[a.length];
        mergeSort(a, tmp,  0,  a.length - 1);
    }


    private static void mergeSort(Comparable [ ] a, Comparable [ ] tmp, int left, int right)
    {
        if( left < right )
        {
            int center = (left + right) / 2;
            mergeSort(a, tmp, left, center);
            mergeSort(a, tmp, center + 1, right);
            merge(a, tmp, left, center + 1, right);
        }
    }


    private static void merge(Comparable[ ] a, Comparable[ ] tmp, int left, int right, int rightEnd )
    {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd) {
            if (a[left] == null || a[right] == null) {
                throw new IllegalArgumentException();
            }

            if (a[left].compareTo(a[right]) <= 0) {
                tmp[k++] = a[left++];
            } else {
                tmp[k++] = a[right++];
            }
        }

        while(left <= leftEnd) {   // Copy rest of first half
            if (a[left] == null) {
                throw new IllegalArgumentException();
            }

            tmp[k++] = a[left++];
        }

        while(right <= rightEnd) { // Copy rest of right half
            if (a[right] == null) {
                throw new IllegalArgumentException();
            }

            tmp[k++] = a[right++];
        }

        // Copy tmp back
        for(int i = 0; i < num; i++, rightEnd--)
            a[rightEnd] = tmp[rightEnd];
    }

}