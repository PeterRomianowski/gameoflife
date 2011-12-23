package gameoflife.impl;

import gameoflife.GameOfLife;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A naive implementation ...
 */
public class PerosGameOfLive1a implements GameOfLife {

    private Set<Long> _points = new HashSet<Long>();
    private Set<Long> _newPoints;

    public void setCellAlive(int x, int y) {
        _points.add(makePoint(x, y));
    }
    
    private static long makePoint(int x, int y) {
        return (long) x << 32 | (y & 0xFFFFFFFFL);
    }

    public void calculateNextGeneration() {
        _newPoints = new HashSet<Long>();
        for (Long point : _points) {
            final int x = (int) (point >> 32);
            final int y = (int) (point & 0xFFFFFFFFL);
            final int liveNeighbours = getNumberOfLiveNeighbours(x, y);
            if (liveNeighbours >= 2 && liveNeighbours < 4) {
                _newPoints.add(point);
            }
            makeDeadCellsLive(x - 1, y - 1);
            makeDeadCellsLive(x - 1, y);
            makeDeadCellsLive(x - 1, y + 1);
            makeDeadCellsLive(x, y - 1);
            makeDeadCellsLive(x, y + 1);
            makeDeadCellsLive(x + 1, y - 1);
            makeDeadCellsLive(x + 1, y);
            makeDeadCellsLive(x + 1, y + 1);
        }
        _points = _newPoints;
    }

    private int getNumberOfLiveNeighbours(int x, int y) {
        int liveNeighbours = 0;
        long point = makePoint(x, y);
        if (_points.contains(point - 1)) { liveNeighbours++; }
        if (_points.contains(point + 1)) { liveNeighbours++; }
        point -= 1L << 32;
        if (_points.contains(point - 1)) { liveNeighbours++; }
        if (_points.contains(point)) { liveNeighbours++; }
        if (liveNeighbours < 4 && _points.contains(point + 1)) { liveNeighbours++; }
        point += 2L << 32;
        if (liveNeighbours < 4 && _points.contains(point - 1)) { liveNeighbours++; }
        if (liveNeighbours < 4 && _points.contains(point)) { liveNeighbours++; }
        if (liveNeighbours < 4 && _points.contains(point + 1)) { liveNeighbours++; }
        return liveNeighbours;
    }

    private void makeDeadCellsLive(int x, int y) {
        if (getNumberOfLiveNeighbours(x, y) == 3) {
            _newPoints.add(makePoint(x, y));
        }
    }

    public Iterable<Point> getCoordinatesOfAliveCells() {
        final Iterator<Long> i = _points.iterator();
        final Point point = new Point();
        return new PointIterable(i, point);
    }

    private static class PointIterable implements Iterable<Point> {

        private final Iterator<Long> _i;
        private final Point _point;

        private PointIterable(Iterator<Long> i, Point point) {
            _i = i;
            _point = point;
        }

        public Iterator<Point> iterator() {
            return new Iterator<Point>() {
                public boolean hasNext() {
                    return _i.hasNext();
                }

                public Point next() {
                    final Long p = _i.next();
                    // TODO: The interface should not expect new Point objects. I want to re-use a single instance ...
                    return new Point((int) (p >> 32), (int) (p & 0xFFFFFFFFL));
                }

                public void remove() {
                    throw new UnsupportedOperationException("Not implemented yet.");
                }
            };

        }
    }

}
