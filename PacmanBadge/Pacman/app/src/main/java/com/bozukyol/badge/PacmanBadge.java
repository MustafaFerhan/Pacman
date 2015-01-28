package com.bozukyol.badge;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Mustafa Ferhan Akman
 * @since 04 May 2014 - 23:38
 */
public class PacmanBadge {

    public static int INITIAL_DISTANCE        = 1;
    public static int DISTANCE_ADDED_TO_QUEUE = 2;
    public static int BADGE_ACHIEVED          = 3;
    public static int POTHOLES_ADDED_TO_QUEUE = 4;

    Queue<PacmanNode> queue = new ConcurrentLinkedQueue<PacmanNode>();
    float indexKM = 0f;
    int indexPotholeCount = 0;
    int thresholdKM = 10;
    int thresholdPothole = 20;

    private static PacmanBadge instance;

    public static PacmanBadge getInstance() {
        if(instance == null) {
            instance = new PacmanBadge();
        }

        return instance;
    }

    private PacmanBadge() {
    }

    public int calculatePacman(float currentTotalKM, int currentTotalPotholeCount) {

        float headDistance = currentTotalKM - indexKM;
        int headPotholeCount = currentTotalPotholeCount - indexPotholeCount;

        if(queue.size() == 0) {
            indexKM = headDistance;
            indexPotholeCount = headPotholeCount;

            //first time, set initial index
            queue.add(new PacmanNode(0, 0));
            return INITIAL_DISTANCE;//false;
        }


        // add queue until threshold distance (KM)
        if(headDistance < thresholdKM){
            queue.add(new PacmanNode(headDistance,headPotholeCount));
            return DISTANCE_ADDED_TO_QUEUE; //false;
        }

        // If the user has been gone 'thresholdKM' but not reach required 'threshold pothole'
        if(headDistance >= thresholdKM && headPotholeCount >= thresholdPothole){

            //User badge'i almaya hak kazandı!
            //BadgeUtil.getInstance().setPacmanBadge(context);
            return BADGE_ACHIEVED; //true;
        }

        // User reach threshold distance but there is no threshold pothole.
        // The queue is polling where indexKM less than thresholdKM
        if(headDistance >= thresholdKM && headPotholeCount < thresholdPothole) {

            queue.add(new PacmanNode(headDistance,headPotholeCount));

            do {
                PacmanNode node = queue.poll();

                if(node != null) {
                    indexKM = node.getNewDistance();
                    indexPotholeCount = node.getNewPotholeCount();
                }
            } while (currentTotalKM - indexKM >= thresholdKM);
        }

        return POTHOLES_ADDED_TO_QUEUE;//false;
    }

    private class PacmanNode {

        float newDistance;
        int newPotholeCount;

        public PacmanNode(float distance, int pothole){
            newDistance = distance;
            newPotholeCount = pothole;
        }

        public float getNewDistance() {
            return newDistance;
        }

        public int getNewPotholeCount() {
            return newPotholeCount;
        }

        @Override
        public String toString() {
            return "PacmanNode{" +
                    "newDistance=" + newDistance +
                    ", newPotholeCount=" + newPotholeCount +
                    '}';
        }
    }

    public void clearPacmanQueue(){
        queue.clear();
    }

}
