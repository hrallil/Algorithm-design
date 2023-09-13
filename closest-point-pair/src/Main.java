import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        List<double[]> points = new ArrayList<double[]>();

        //load data
        for (int i = 0; i < n; i++) {
            s.nextInt();
            Double x = s.nextDouble();
            Double y = s.nextDouble();
            //System.out.println("x: " + x +" y: " + y);

            double[] p = {x,y};
            points.add(p);
        }
        
        Collections.sort(points, Comparator.comparing(o -> o[0]));
        
        System.out.println(closestspair(points));


    }

    public static double closestspair(List<double[]> points){
        if(points.size() <= 3){
            return bruteForce(points);
        }
        
        List<double[]> left = points.subList(0, points.size()/2);
        List<double[]> right = points.subList(points.size()/2, points.size());
        double dleft = closestspair(left);
        double dright = closestspair(right);
        double dmin = dleft<dright?dleft:dright;
        double l = (left.get(left.size() - 1)[0] + right.get(0)[0])/2;
        

        List<double[]> deltaPoints = new ArrayList<double[]>();
        for (int i = left.size()-1; i >= 0; i--) {
            double dist = Math.abs(left.get(i)[0] - l);
            if ( dist <= dmin) {
                deltaPoints.add(left.get(i));
            }else{
                break;
            }
        }

        for (int i = 0; i < right.size(); i++) {
            double dist = Math.abs(right.get(i)[0] - l);
            if(dist <=dmin){
                deltaPoints.add(right.get(i));
            }else{
                break;
            }
        }

        Collections.sort(deltaPoints, Comparator.comparing(o -> o[1]));

        for (int i = 0; i < deltaPoints.size(); i++) {
            for (int j = 1; j < 11; j++) {
                if(j+i>= deltaPoints.size()){
                    break;
                }
                double dist = dist(deltaPoints.get(i), deltaPoints.get(i+j));
                if(dist < dmin){
                    System.out.println("dist:" + dist + " at point: " +  Arrays.toString(deltaPoints.get(i)) + " and " + Arrays.toString(deltaPoints.get(i+j))); 
                    dmin = dist;

                }
            }
        }

        return dmin;
        
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

    class Point{
        float x;
        float y;

    }
}