package com.martian.robots;

import com.martian.robots.model.MarsPosition;
import com.martian.robots.model.Robot;
import com.martian.robots.service.PlanetMarsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MartianRobotsMain {

    public static void main(String[] args) throws IOException {
        String file = Objects.requireNonNull(MartianRobotsMain.class.getResource("/example.txt")).getFile();
        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            List<String> lines = stream.filter(l -> !l.isEmpty()).collect(Collectors.toList());
            String[] lineArray = lines.get(0).split(" ");
            PlanetMarsService marsService = new PlanetMarsService(Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1]));
            List<String> robotLines = lines.subList(1, lines.size());
            IntStream.range(0, robotLines.size() / 2)
                    .forEach(i -> {
                        Robot r = createRobotFromLine(robotLines.get(i * 2));
                        assert robotLines.get(i * 2 + 1).length() < 100;
                        Arrays.stream(robotLines.get(i * 2 + 1).split(""))
                                .forEach(instruction -> marsService.move(r, instruction));
                        System.out.println(r);
                    });

        }
    }

    public static Robot createRobotFromLine(String line) {
        String[] lineArray = line.split(" ");
        MarsPosition position = new MarsPosition(Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1]));
        Robot.Orientation orientation = Robot.Orientation.valueOf(lineArray[2]);
        return new Robot(position, orientation, false);
    }

}
