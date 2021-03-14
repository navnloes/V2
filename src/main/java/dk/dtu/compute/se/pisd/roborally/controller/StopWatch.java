package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is StopWatch also called timer/hourglass in the assignment
 * and StopWatch it can be implemented anywhere
 * @author Mohamud Yusuf and Rohan Khalid
 */
public class StopWatch extends Subject {
    int interval;
    int activeInterval;
    int delay;
    int period;
    Timer timer;
    ArrayList<StopWatchListener> listeners;

    public StopWatch(int interval, int delay, int period) {
        this.timer = new Timer();
        this.interval = interval;
        this.delay = delay;
        this.period = period;
        listeners = new ArrayList<>();
    }

    public void addListener(StopWatchListener listener){
        listeners.add(listener);
    }

    public void startTimer(){
        this.timer = new Timer();
        activeInterval = interval;
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run() {
                setInterval();
            }
        }, delay, period);
    }

    public final int setInterval() {
        if (activeInterval == 1) {
            timer.cancel();
            for (StopWatchListener l : listeners){
                l.onZero();
            }
        }
        --activeInterval;

        notifyChange();

        return activeInterval;
    }

    public void stop() {
        timer.cancel();
    }


    public int getInterval() {
        return interval;
    }

    public int getActiveInterval() {
        return activeInterval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public interface StopWatchListener{
        void onZero();

    }


}

