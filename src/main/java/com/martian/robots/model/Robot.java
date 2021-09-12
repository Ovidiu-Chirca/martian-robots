package com.martian.robots.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class Robot {
    private final AtomicReference<MarsPosition> position;
    private final AtomicReference<Orientation> orientation;
    private final AtomicBoolean isLost;

    public Robot(MarsPosition position, Orientation orientation, boolean isLost) {
        this.position = new AtomicReference<>(position);
        this.orientation = new AtomicReference<>(orientation);
        this.isLost = new AtomicBoolean(isLost);
    }

    public AtomicReference<MarsPosition> getPosition() {
        return position;
    }

    public AtomicReference<Orientation> getOrientation() {
        return orientation;
    }

    public AtomicBoolean getIsLost() {
        return isLost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Robot robot = (Robot) o;
        return Objects.equals(position, robot.position) && Objects.equals(orientation, robot.orientation) && Objects.equals(isLost, robot.isLost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, orientation, isLost);
    }

    @Override
    public String toString() {
        return position.get() + " " + orientation.get() + ((isLost.get()) ? " LOST" : "");
    }

    public enum Orientation {
        N, S, E, W
    }
}
