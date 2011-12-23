package gameoflife.impl;

import gameoflife.GameOfLife;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A naive implementation ...
 */
public class PerosGameOfLive1 implements GameOfLife {
    
    private Set<Point> _points = new HashSet<Point>();
    private Set<Point> _newPoints;

    public void setCellAlive(int x, int y) {
        _points.add(new Point(x, y));
    }

    public void calculateNextGeneration() {
        _newPoints = new HashSet<Point>();
        for (Point point : _points) {
            final int liveNeighbours = getNumberOfLiveNeighbours(point.x, point.y);
            if (liveNeighbours >= 2 && liveNeighbours < 4) {
                _newPoints.add(point);
            }
            makeDeadCellsLive(point.x - 1, point.y - 1);
            makeDeadCellsLive(point.x - 1, point.y);
            makeDeadCellsLive(point.x - 1, point.y + 1);
            makeDeadCellsLive(point.x, point.y - 1);
            makeDeadCellsLive(point.x, point.y + 1);
            makeDeadCellsLive(point.x + 1, point.y - 1);
            makeDeadCellsLive(point.x + 1, point.y);
            makeDeadCellsLive(point.x + 1, point.y + 1);
        }
        _points = _newPoints;
    }

    private int getNumberOfLiveNeighbours(int x, int y) {
        int liveNeighbours = 0;
        if (_points.contains(new Point(x - 1, y - 1))) { liveNeighbours++; }
        if (_points.contains(new Point(x - 1, y))) { liveNeighbours++; }
        if (_points.contains(new Point(x - 1, y + 1))) { liveNeighbours++; }
        if (_points.contains(new Point(x, y - 1))) { liveNeighbours++; }
        if (_points.contains(new Point(x, y + 1))) { liveNeighbours++; }
        if (_points.contains(new Point(x + 1, y - 1))) { liveNeighbours++; }
        if (_points.contains(new Point(x + 1, y))) { liveNeighbours++; }
        if (_points.contains(new Point(x + 1, y + 1))) { liveNeighbours++; }
        return liveNeighbours;
    }

    private void makeDeadCellsLive(int x, int y) {
        if (getNumberOfLiveNeighbours(x, y) == 3) {
            _newPoints.add(new Point(x, y));
        }
    }

    public Iterable<Point> getCoordinatesOfAliveCells() {
        return _points;
    }
}
