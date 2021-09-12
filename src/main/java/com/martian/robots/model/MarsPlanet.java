package com.martian.robots.model;

import java.util.Objects;

public final class MarsPlanet {
    public static final int MAX_COORDINATE = 50;

    public final int x;
    public final int y;
    public final int width;
    public final int height;

    public MarsPlanet(int width, int height) {
        assert width <= MAX_COORDINATE;
        assert height <= MAX_COORDINATE;
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarsPlanet that = (MarsPlanet) o;
        return x == that.x && y == that.y && width == that.width && height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }
}
