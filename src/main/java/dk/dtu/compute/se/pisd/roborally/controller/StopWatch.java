package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

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

    //GameController gameController;

    private Duration time = Duration.ZERO;

    private Label label;

    /**
     * This is the constructor of the stopwatch
     * @param interval int interval
     * @param delay int delay
     * @param period int period
     * @param displayLabel Label displayLabel
     */
    public StopWatch(int interval, int delay, int period, Label displayLabel) {
        this.timer = new Timer();
        this.interval = interval;
        this.delay = delay;
        this.period = period;
        label = displayLabel;
        //this.gameController = gameController;
    }

    /**
     * This method starts the timer
     */
    public void startTimer(){
        this.timer = new Timer();
        activeInterval = interval;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    setInterval();
                });
            }
        }, delay, period);
    }

    /**
     * This method sets the active interval
     * @return int activeInterval
     */
    public final int setInterval() {
        if (activeInterval == 1) {
            timer.cancel();
        }
        --activeInterval;
        notifyChange();
        label.setText(activeInterval + "");
        return activeInterval;
    }

    /**
     * This method stops the timer
     */
    public void stop() {
        timer.cancel();
    }

    /**
     * This getter returns the interval
     * @return int interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * This getter returns the activeInterval
     * @return int activeInterval
     */
    public int getActiveInterval() {
        return activeInterval;
    }

    /**
     * This method sets the interval
     * @param interval int interval
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

}