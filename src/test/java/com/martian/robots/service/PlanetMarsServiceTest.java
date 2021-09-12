package com.martian.robots.service;

import com.martian.robots.model.MarsPosition;
import com.martian.robots.model.Robot;
import com.martian.robots.model.MarsPlanet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlanetMarsServiceTest {

    @Test
    void moveWithInstructionLeft() {
        PlanetMarsService planetMarsService = new PlanetMarsService(10, 10);
        Robot r = new Robot(new MarsPosition(5, 5), Robot.Orientation.N, false);
        planetMarsService.move(r, "L");
        Assertions.assertEquals(Robot.Orientation.W, r.getOrientation().get());
    }

    @Test
    void moveWithInstructionRight() {
        PlanetMarsService planetMarsService = new PlanetMarsService(10, 10);
        Robot r = new Robot(new MarsPosition(5, 5), Robot.Orientation.N, false);
        planetMarsService.move(r, "R");
        Assertions.assertEquals(Robot.Orientation.E, r.getOrientation().get());
    }

    @Test
    void moveWithInstructionForward() {
        PlanetMarsService planetMarsService = new PlanetMarsService(10, 10);
        Robot r = new Robot(new MarsPosition(5, 5), Robot.Orientation.N, false);
        planetMarsService.move(r, "F");
        Assertions.assertEquals(new MarsPosition(5, 6), r.getPosition().get());
    }

    @Test
    void moveIsLostWithInstructionForward() {
        PlanetMarsService planetMarsService = new PlanetMarsService(10, 10);
        Robot r = new Robot(new MarsPosition(-1, -1), Robot.Orientation.N, true);
        planetMarsService.move(r, "F");
        Assertions.assertEquals(new MarsPosition(-1, -1), r.getPosition().get());
    }

    @Test
    void turnLeft() {
        PlanetMarsService planetMarsService = new PlanetMarsService(10, 10);
        Robot r1 = new Robot(new MarsPosition(5, 5), Robot.Orientation.N, false);
        Robot r2 = new Robot(new MarsPosition(5, 5), Robot.Orientation.S, true);
        Robot r3 = new Robot(new MarsPosition(5, 5), Robot.Orientation.E, false);
        Robot r4 = new Robot(new MarsPosition(5, 5), Robot.Orientation.W, true);

        planetMarsService.turnLeft(r1);
        planetMarsService.turnLeft(r2);
        planetMarsService.turnLeft(r3);
        planetMarsService.turnLeft(r4);

        Assertions.assertEquals(Robot.Orientation.W, r1.getOrientation().get());
        Assertions.assertEquals(Robot.Orientation.E, r2.getOrientation().get());
        Assertions.assertEquals(Robot.Orientation.N, r3.getOrientation().get());
        Assertions.assertEquals(Robot.Orientation.S, r4.getOrientation().get());
    }

    @Test
    void turnRight() {
        PlanetMarsService planetMarsService = new PlanetMarsService(10, 10);
        Robot r1 = new Robot(new MarsPosition(5, 5), Robot.Orientation.N, false);
        Robot r2 = new Robot(new MarsPosition(5, 5), Robot.Orientation.S, true);
        Robot r3 = new Robot(new MarsPosition(5, 5), Robot.Orientation.E, false);
        Robot r4 = new Robot(new MarsPosition(5, 5), Robot.Orientation.W, true);

        planetMarsService.turnRight(r1);
        planetMarsService.turnRight(r2);
        planetMarsService.turnRight(r3);
        planetMarsService.turnRight(r4);

        Assertions.assertEquals(Robot.Orientation.E, r1.getOrientation().get());
        Assertions.assertEquals(Robot.Orientation.W, r2.getOrientation().get());
        Assertions.assertEquals(Robot.Orientation.S, r3.getOrientation().get());
        Assertions.assertEquals(Robot.Orientation.N, r4.getOrientation().get());
    }

    @Test
    void moveForwardSuccess() {
        Set<MarsPosition> robotScent = new HashSet<>();
        PlanetMarsService planetMarsService = new PlanetMarsService(new MarsPlanet(10, 10), robotScent);
        Robot r = new Robot(new MarsPosition(5, 5), Robot.Orientation.N, false);

        planetMarsService.moveForward(r);
        assertEquals(new MarsPosition(5, 6), r.getPosition().get());
        assertEquals(Robot.Orientation.N, r.getOrientation().get());
        assertFalse(r.getIsLost().get());
        assertTrue(robotScent.isEmpty());
    }

    @Test
    void moveForwardLostRobot() {
        Set<MarsPosition> robotScent = new HashSet<>();
        PlanetMarsService planetMarsService = new PlanetMarsService(new MarsPlanet(10, 10), robotScent);
        Robot r = new Robot(new MarsPosition(0, 5), Robot.Orientation.W, false);

        planetMarsService.moveForward(r);
        assertEquals(new MarsPosition(-1, 5), r.getPosition().get());
        assertEquals(Robot.Orientation.W, r.getOrientation().get());
        assertTrue(r.getIsLost().get());
        assertTrue(robotScent.contains(new MarsPosition(0, 5)));
    }

    @Test
    void moveForwardNoCange() {
        Set<MarsPosition> robotScent = new HashSet<>();
        robotScent.add(new MarsPosition(3, 0));
        PlanetMarsService planetMarsService = new PlanetMarsService(new MarsPlanet(10, 10), robotScent);
        Robot r = new Robot(new MarsPosition(3, 0), Robot.Orientation.S, false);

        planetMarsService.moveForward(r);
        assertEquals(new MarsPosition(3, 0), r.getPosition().get());
        assertEquals(Robot.Orientation.S, r.getOrientation().get());
        assertFalse(r.getIsLost().get());
        assertEquals(1, robotScent.size());
    }

    @Test
    void moveForwardMarsPositionOrientation() {
        PlanetMarsService planetMarsService = new PlanetMarsService(10, 10);
        Assertions.assertEquals(new MarsPosition(5, 6), planetMarsService.moveForwardMarsPosition(new MarsPosition(5, 5), Robot.Orientation.N));
        Assertions.assertEquals(new MarsPosition(5, 4), planetMarsService.moveForwardMarsPosition(new MarsPosition(5, 5), Robot.Orientation.S));
        Assertions.assertEquals(new MarsPosition(6, 5), planetMarsService.moveForwardMarsPosition(new MarsPosition(5, 5), Robot.Orientation.E));
        Assertions.assertEquals(new MarsPosition(4, 5), planetMarsService.moveForwardMarsPosition(new MarsPosition(5, 5), Robot.Orientation.W));
    }

    @Test
    void determineIfLostWhenTrue() {
        PlanetMarsService planetMarsService = new PlanetMarsService(10, 10);
        assertTrue(planetMarsService.determineIfLost(new MarsPosition(-1, 0)));
        assertTrue(planetMarsService.determineIfLost(new MarsPosition(0, -1)));
        assertTrue(planetMarsService.determineIfLost(new MarsPosition(11, 0)));
        assertTrue(planetMarsService.determineIfLost(new MarsPosition(0, 11)));
    }

    @Test
    void determineIfLostWhenFalse() {
        PlanetMarsService planetMarsService = new PlanetMarsService(10, 10);
        assertFalse(planetMarsService.determineIfLost(new MarsPosition(0, 0)));
        assertFalse(planetMarsService.determineIfLost(new MarsPosition(10, 10)));
        assertFalse(planetMarsService.determineIfLost(new MarsPosition(10, 0)));
        assertFalse(planetMarsService.determineIfLost(new MarsPosition(0, 10)));
        assertFalse(planetMarsService.determineIfLost(new MarsPosition(5, 5)));
    }
}