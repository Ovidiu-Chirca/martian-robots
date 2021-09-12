package com.martian.robots.service;

import com.martian.robots.model.Robot;
import com.martian.robots.model.MarsPlanet;
import com.martian.robots.model.MarsPosition;
import com.martian.robots.model.MoveInstruction;

import java.util.HashSet;
import java.util.Set;

public final class PlanetMarsService {
    private final MarsPlanet marsPlanet;
    private final Set<MarsPosition> scent;

    public PlanetMarsService(int width, int height) {
        this(new MarsPlanet(width, height), new HashSet<>());
    }

    public PlanetMarsService(MarsPlanet marsPlanet, Set<MarsPosition> robotScent) {
        this.marsPlanet = marsPlanet;
        this.scent = robotScent;
    }

    public void move(Robot r, String moveInstruction) {
        switch (MoveInstruction.valueOf(moveInstruction)) {
            case L:
                turnLeft(r);
                break;
            case R:
                turnRight(r);
                break;
            case F:
                if(!r.getIsLost().get()) {
                    moveForward(r);
                }
                break;
            default:
                throw new UnsupportedOperationException(moveInstruction);
        }
    }

    protected void turnLeft(Robot r) {
        switch (r.getOrientation().get()) {
            case N:
                r.getOrientation().compareAndSet(Robot.Orientation.N, Robot.Orientation.W);
                break;
            case W:
                r.getOrientation().compareAndSet(Robot.Orientation.W, Robot.Orientation.S);
                break;
            case S:
                r.getOrientation().compareAndSet(Robot.Orientation.S, Robot.Orientation.E);
                break;
            case E:
                r.getOrientation().compareAndSet(Robot.Orientation.E, Robot.Orientation.N);
                break;
            default:
                throw new UnsupportedOperationException(r.getOrientation().toString());
        }
    }

    protected void turnRight(Robot r) {
        switch (r.getOrientation().get()) {
            case N:
                r.getOrientation().compareAndSet(Robot.Orientation.N, Robot.Orientation.E);
                break;
            case E:
                r.getOrientation().compareAndSet(Robot.Orientation.E, Robot.Orientation.S);
                break;
            case S:
                r.getOrientation().compareAndSet(Robot.Orientation.S, Robot.Orientation.W);
                break;
            case W:
                r.getOrientation().compareAndSet(Robot.Orientation.W, Robot.Orientation.N);
                break;
            default:
                throw new UnsupportedOperationException(r.getOrientation().toString());
        }
    }

    protected void moveForward(Robot r) {
        MarsPosition newPosition = moveForwardMarsPosition(r.getPosition().get(), r.getOrientation().get());
        var isLost = determineIfLost(newPosition);
        if (isLost) {
            // An instruction to move “off” the world from a grid point from which a robot has been previously lost is simply ignored by the current robot
            if(scent.contains(r.getPosition().get())){
                return;
            }
            scent.add(r.getPosition().get());
            r.getIsLost().set(isLost);
        }
        r.getPosition().set(newPosition);
    }

    protected MarsPosition moveForwardMarsPosition(MarsPosition position, Robot.Orientation o) {
        switch (o) {
            case N:
                return new MarsPosition(position.getX(), position.getY() + 1);
            case W:
                return new MarsPosition(position.getX() - 1, position.getY());
            case S:
                return new MarsPosition(position.getX(), position.getY() - 1);
            case E:
                return new MarsPosition(position.getX() + 1, position.getY());
            default:
                throw new UnsupportedOperationException(o.toString());
        }

    }

    protected boolean determineIfLost(MarsPosition p) {
        return (p.x < marsPlanet.getX() || p.x > (marsPlanet.getX() + marsPlanet.getWidth())) ||
                (p.y < marsPlanet.getY() || p.y > (marsPlanet.getY() + marsPlanet.getHeight()));
    }


}
