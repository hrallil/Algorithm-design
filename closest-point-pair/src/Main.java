import java.util.*;

/**
 * Algorithm to find the closest pair of points.
 *
 * Done with a group consisting of members:
 * - Mathias Marquar Arhipenko Larsen
 * - Martin Sundman
 * - Rokas Kasperavicius
 */
public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        List<double[]> points = new ArrayList<>();

        // Load data
        for (int i = 0; i < n; i++) {
            // Skip the first number as it is not used
            s.nextInt();

            double x = s.nextDouble();
            double y = s.nextDouble();

            points.add(new double[]{x, y});
        }

        // Sort by x-coordinate
        points.sort(Comparator.comparing(point -> point[0]));
        
        System.out.println(closestPair(points));
    }

    // Find the closest distance between two points
    public static double closestPair(List<double[]> points) {
        // Use brute force if there are 3 or fewer points
        if (points.size() <= 3){
            return bruteForce(points);
        }

        // Divide the list into two sub-lists - left and right
        List<double[]> leftPoints = points.subList(0, points.size() / 2);
        List<double[]> rightPoints = points.subList(points.size() / 2, points.size());

        double dLeft = closestPair(leftPoints);
        double dRight = closestPair(rightPoints);

        double dMin = Math.min(dLeft, dRight);

        // Find the x-coordinate of the L strip between two sub-lists
        double l = (leftPoints.get(leftPoints.size() - 1)[0] + rightPoints.get(0)[0]) / 2;

        // Remove all points which are further than delta from l strip
        List<double[]> deltaPoints = new ArrayList<>();

        // For the left side of the list, iterate from right to left, as all points in delta range
        // will be at the end of the list first
        for (int i = leftPoints.size() - 1; i >= 0; i--) {
            double dist = Math.abs(leftPoints.get(i)[0] - l);

            if (dist <= dMin) {
                deltaPoints.add(leftPoints.get(i));
            } else {
                break;
            }
        }

        for (int i = 0; i < rightPoints.size(); i++) {
            double dist = Math.abs(rightPoints.get(i)[0] - l);

            if(dist <= dMin){
                deltaPoints.add(rightPoints.get(i));
            } else {
                break;
            }
        }

        // Sort the points inside the delta range by the y-coordinate
        deltaPoints.sort(Comparator.comparing(o -> o[1]));

        for (int i = 0; i < deltaPoints.size(); i++) {
            // Check the next 11 neighbours
            for (int j = 1; j < 11; j++) {
                // Outside the deltaPoints bounds
                if(i + j >= deltaPoints.size()){
                    break;
                }

                double dist = dist(deltaPoints.get(i), deltaPoints.get(i + j));

                if (dist < dMin){
                    dMin = dist;
                }
            }
        }

        return dMin;
    }

    public static double dist(double[] p1, double[] p2){
        return Math.sqrt(Math.pow((p1[0] - p2[0]), 2) + Math.pow((p1[1] - p2[1]), 2));
    }

    public static double bruteForce(List<double[]> points){
        double min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double dist = dist(points.get(i), points.get(j));

                if(dist < min){
                    min = dist;
                }
            }
        }

        return min;
    }
}