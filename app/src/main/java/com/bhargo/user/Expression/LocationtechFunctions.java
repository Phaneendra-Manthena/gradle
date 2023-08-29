package com.bhargo.user.Expression;

import com.bhargo.user.Expression.GeometryDistanceComparator;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.index.SpatialIndex;
import org.locationtech.jts.index.quadtree.Quadtree;
import org.locationtech.jts.index.strtree.AbstractNode;
import org.locationtech.jts.index.strtree.Boundable;
import org.locationtech.jts.index.strtree.GeometryItemDistance;
import org.locationtech.jts.index.strtree.STRtree;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.buffer.BufferOp;
import org.locationtech.jts.operation.distance.DistanceOp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LocationtechFunctions {
    private static STRtree indexSTRcache = null;
    private static Geometry indexSTRGeom = null;

    private static void loadIndex(Geometry geom, SpatialIndex index) {
        geom.apply(new GeometryFilter() {

            public void filter(Geometry geom) {
                // only insert atomic geometries
                if (geom instanceof GeometryCollection) return;
                index.insert(geom.getEnvelopeInternal(), geom);
            }

        });
    }

    public static Geometry strTreeBounds(Geometry geoms) {
        STRtree index = new STRtree();
        loadIndex(geoms, index);
        List bounds = new ArrayList();
        addBounds(index.getRoot(), bounds, geoms.getFactory());
        return geoms.getFactory().buildGeometry(bounds);
    }

    private static void addBounds(Boundable bnd, List bounds,
                                  GeometryFactory factory) {
        // don't include bounds of leaf nodes
        if (!(bnd instanceof AbstractNode)) return;

        Envelope env = (Envelope) bnd.getBounds();
        bounds.add(factory.toGeometry(env));
        if (bnd instanceof AbstractNode) {
            AbstractNode node = (AbstractNode) bnd;
            List children = node.getChildBoundables();
            for (Iterator i = children.iterator(); i.hasNext(); ) {
                Boundable child = (Boundable) i.next();
                addBounds(child, bounds, factory);
            }
        }
    }

    public static Geometry strTreeQueryCached(Geometry geoms, Geometry queryEnv) {
        if (indexSTRGeom != geoms || indexSTRcache == null) {
            indexSTRcache = new STRtree();
            loadIndex(geoms, indexSTRcache);
            indexSTRGeom = geoms;
        }
        // if no query env provided query everything inserted
        if (queryEnv == null) queryEnv = geoms;
        List result = indexSTRcache.query(queryEnv.getEnvelopeInternal());
        return geoms.getFactory().buildGeometry(result);
    }

    public static Geometry strTreeQuery(Geometry geoms, Geometry queryEnv) {
        STRtree index = new STRtree();
        loadIndex(geoms, index);
        // if no query env provided query everything inserted
        if (queryEnv == null) queryEnv = geoms;
        List result = index.query(queryEnv.getEnvelopeInternal());
        return geoms.getFactory().buildGeometry(result);
    }

    public static Geometry strTreeNN(Geometry geoms, Geometry geom) {
        STRtree index = new STRtree();
        loadIndex(geoms, index);
        Object result = index.nearestNeighbour(geom.getEnvelopeInternal(), geom, new GeometryItemDistance());
        return (Geometry) result;
    }

    public static Geometry strTreeNNInSet(Geometry geoms) {
        STRtree index = new STRtree();
        loadIndex(geoms, index);
        Object[] result = index.nearestNeighbour(new GeometryItemDistance());
        Geometry[] resultGeoms = new Geometry[]{(Geometry) result[0], (Geometry) result[1]};
        return geoms.getFactory().createGeometryCollection(resultGeoms);
    }

    public static Geometry strTreeNNk(Geometry geoms, Geometry geom, int k) {
        STRtree index = new STRtree();
        loadIndex(geoms, index);
        Object[] knnObjects = index.nearestNeighbour(geom.getEnvelopeInternal(), geom, new GeometryItemDistance(), k);
        List knnGeoms = new ArrayList(Arrays.asList(knnObjects));
        Geometry geometryCollection = geoms.getFactory().buildGeometry(knnGeoms);
        return geometryCollection;
    }

    public static Geometry quadTreeQuery(Geometry geoms, Geometry queryEnv) {
        Quadtree index = buildQuadtree(geoms);
        // if no query env provided query everything inserted
        if (queryEnv == null) queryEnv = geoms;
        List result = index.query(queryEnv.getEnvelopeInternal());
        return geoms.getFactory().buildGeometry(result);
    }

    private static Quadtree buildQuadtree(Geometry geom) {
        final Quadtree index = new Quadtree();
        geom.apply(new GeometryFilter() {

            public void filter(Geometry geom) {
                // only insert atomic geometries
                if (geom instanceof GeometryCollection) return;
                index.insert(geom.getEnvelopeInternal(), geom);
            }

        });
        return index;
    }

    public static List<String> getNearbyValueWithinRange(String currentPosition,List<String> filterPositions,int RangeInMeters){
        double currLat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double currLon1 = Double.parseDouble(currentPosition.split("\\$")[1]);
        Coordinate currentLocation = new Coordinate(currLat1, currLon1);;
        List<Coordinate> otherLocations = getCoordinates(filterPositions);
        List<String> filterPoints = new ArrayList<>();
        Point currentPoint = new GeometryFactory().createPoint(currentLocation);
        for (Coordinate location : otherLocations) {
            Point point = new GeometryFactory().createPoint(location);
            double d = point.distance(currentPoint);
            System.out.println("G filterPoints Distance:"+d);
            if (d < RangeInMeters) {
                filterPoints.add(point.getX()+"$"+point.getY());
            }
        }
        System.out.println("G filterPoints Distances:"+filterPoints);
        return filterPoints;
    }

    public static List<Coordinate> getCoordinates(List<String> stringList) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            String point=stringList.get(i);
            double pointLat1 = Double.parseDouble(point.split("\\$")[0]);
            double pointLon1 = Double.parseDouble(point.split("\\$")[1]);
            coordinates.add(new Coordinate(pointLat1, pointLon1));
        }
        return coordinates;
    }

    public double distance(Geometry geoms, Geometry geom) {
        return geoms.distance(geom);
    }

    public static boolean isWithinDistance(Geometry geoms, Geometry geom, double dist) {
        return geoms.isWithinDistance(geom, dist);
    }

    public Geometry nearestPoints(Geometry geoms, Geometry geom) {
        Coordinate[] pts = DistanceOp.nearestPoints(geoms, geom);
        return geoms.getFactory().createLineString(pts);
    }

    public void getNearestPointAndDistances() {
        Coordinate currentLocation = getCurrentGpsPoint();
        List<Coordinate> otherLocations = getOtherGpsPoints();
        List<String> l_distanceFromCurrentLocation = new ArrayList<>();
        Point currentPoint = new GeometryFactory().createPoint(currentLocation);
        Point nearstPoint = null;
        double distance = 100;
        for (Coordinate location : otherLocations) {
            Point point = new GeometryFactory().createPoint(location);
            double d = point.distance(currentPoint);
            l_distanceFromCurrentLocation.add(String.valueOf(d));
            if (d < distance) {
                distance = d;
                nearstPoint = point;
            }
        }
        System.out.println("Geometry Nearest Point Distance:" + distance);
        System.out.println("Geometry Nearest Point:" + nearstPoint + " X:" + nearstPoint.getX() + " Y:" + nearstPoint.getY());
        System.out.println("Geometry Distances:" + l_distanceFromCurrentLocation);
        try {
            geometryReadingTypes();
            getNearestPointWithHugeData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void geometryReadingTypes() throws ParseException {
        // read a geometry from a WKT string (using the default geometry factory)
        Geometry g1 = new WKTReader().read("LINESTRING (16.486570 80.630518, 16.644525 81.158295, 16.844415 82.246667,17.101418 78.510193,18.089076 83.403752,18.323892 83.936944)");
        System.out.println("Geometry 1: " + g1);
        // create a geometry by specifying the coordinates directly
        List<Coordinate> coordinates = getOtherGpsPoints();
        // use the default factory, which gives full double-precision
        Geometry g2 = new GeometryFactory().createLineString(coordinates.toArray(new Coordinate[0]));
        Geometry gMultiPoints = new GeometryFactory().createMultiPointFromCoords(coordinates.toArray(new Coordinate[0]));
        System.out.println("Geometry 2: " + g2);
        System.out.println("Geometry MultiPoints: " + gMultiPoints);
        // compute the intersection of the two geometries
        Geometry g3 = g1.intersection(g2);
        System.out.println("G1 intersection G2: " + g3);

        // create a point
        Geometry point = new GeometryFactory().createPoint(getCurrentGpsPoint());
        System.out.println("Point Geometry: " + point);
        Geometry nearsetPoint = nearestPoints(gMultiPoints, point);
        System.out.println("Nearset Point Geometry: " + nearsetPoint);
        // compute whether point is on g1
        System.out.println("Geometry Point within g1: " + g1.contains(point));

        /*findClosestPoint(
                "POLYGON ((200 180, 60 140, 60 260, 200 180))",
                "POINT (140 280)");
        findClosestPoint(
                "POLYGON ((200 180, 60 140, 60 260, 200 180))",
                "MULTIPOINT (140 280, 140 320)");*/
        //no use
        findClosestPoint(
                "LINESTRING (16.486570 80.630518, 16.644525 81.158295, 16.844415 82.246667,17.101418 78.510193,18.089076 83.403752,18.323892 83.936944)",
                "POINT (17.686815 83.218483)");
        /*findClosestPoint(
                "LINESTRING (100 100, 200 200)",
                "LINESTRING (100 200, 200 100)");
        findClosestPoint(
                "LINESTRING (100 100, 200 200)",
                "LINESTRING (150 121, 200 0)");
        findClosestPoint(
                "POLYGON (( 76 185, 125 283, 331 276, 324 122, 177 70, 184 155, 69 123, 76 185 ), ( 267 237, 148 248, 135 185, 223 189, 251 151, 286 183, 267 237 ))",
                "LINESTRING ( 153 204, 185 224, 209 207, 238 222, 254 186 )");
        findClosestPoint(
                "POLYGON (( 76 185, 125 283, 331 276, 324 122, 177 70, 184 155, 69 123, 76 185 ), ( 267 237, 148 248, 135 185, 223 189, 251 151, 286 183, 267 237 ))",
                "LINESTRING ( 120 215, 185 224, 209 207, 238 222, 254 186 )");*/
    }

    public void findClosestPoint(String wktA, String wktB) {
        GeometryFactory fact = new GeometryFactory();
        WKTReader wktRdr = new WKTReader(fact);
        try {
            Geometry A = wktRdr.read(wktA);
            Geometry B = wktRdr.read(wktB);
            System.out.println("Geometry findClosestPoint ");
            System.out.println("Geometry A: " + A);
            System.out.println("Geometry B: " + B);
            DistanceOp distOp = new DistanceOp(A, B);

            double distance = distOp.distance();
            System.out.println("Geometry Distance = " + distance);

            Coordinate[] closestPt = distOp.nearestPoints();
            LineString closestPtLine = fact.createLineString(closestPt);
            System.out.println("Geometry Closest points: " + closestPtLine
                    + " (distance = " + closestPtLine.getLength() + ")");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getNearestPointWithHugeData() {
        List<Coordinate> coordinates = getOtherGpsPoints();
        Coordinate currentLocation = getCurrentGpsPoint();
        // use the default factory, which gives full double-precision
        Geometry g_multiPoints = new GeometryFactory().createMultiPointFromCoords(coordinates.toArray(new Coordinate[0]));
        Geometry g_currentLocationPoint = new GeometryFactory().createPoint(currentLocation);
        //1st Way
        STRtree index = new STRtree();
        g_multiPoints.apply(new GeometryFilter() {
            public void filter(Geometry geom) {
                // only insert atomic geometries
                if (geom instanceof GeometryCollection) return;
                index.insert(geom.getEnvelopeInternal(), geom);
            }
        });
        Geometry nearestPoint_G = (Geometry) index.nearestNeighbour(g_currentLocationPoint.getEnvelopeInternal(), g_currentLocationPoint, new GeometryItemDistance());
        System.out.println("Geometry nearestPoint_G:" + nearestPoint_G);
        Object[] nearestPoint_GG = index.nearestNeighbour(g_currentLocationPoint.getEnvelopeInternal(), g_currentLocationPoint, new GeometryItemDistance(), 3);
        List topKList = Arrays.asList(nearestPoint_GG);
        System.out.println("Geometry nearestPoint_G 3:" + topKList);
        //2nd Way
        //=================//
        List<Geometry> otherLocations = getOtherGpsPointsInGeometry();
        Point currentLocationPoint = new GeometryFactory().createPoint(currentLocation);
        STRtree stRtreeIndex = new STRtree();
        for (Geometry p : otherLocations) {
            stRtreeIndex.insert(p.getEnvelopeInternal(), p);
        }
        stRtreeIndex.build();

        Geometry nearestPoint_G1 = (Geometry) stRtreeIndex.nearestNeighbour(currentLocationPoint.getEnvelopeInternal(), currentLocationPoint, new GeometryItemDistance());
        Point p = new GeometryFactory().createPoint(nearestPoint_G1.getCoordinate());
        double lat = nearestPoint_G1.getCoordinate().x;
        double log = nearestPoint_G1.getCoordinate().y;
        System.out.println("Geometry nearestPoint_G1:" + nearestPoint_G1);
        Object[] nearestPoint_G11 = stRtreeIndex.nearestNeighbour(currentLocationPoint.getEnvelopeInternal(), currentLocationPoint, new GeometryItemDistance(), 2);
        topKList = Arrays.asList(nearestPoint_G11);
        System.out.println("Geometry nearestPoint_G1 2:" + topKList);
        //MultiPoints
        BufferOp bufferOp = new BufferOp(currentLocationPoint);
        bufferOp.setEndCapStyle(BufferOp.CAP_ROUND);
        // bufferOp.setSingleSided(true); // if you only need a buffer on one side of the point
        bufferOp.setQuadrantSegments(8); // for smoother rounded buffer
        Geometry buffer_g = bufferOp.getResultGeometry(100.0);
        Point buffer = buffer_g.getInteriorPoint();
        // Query the spatial index to find points within the buffer
        List<Point> pointsWithinDistance = new ArrayList<>();
        for (Geometry g1 : otherLocations) {
            Point point = g1.getInteriorPoint();
            if (buffer.contains(point)) {
                pointsWithinDistance.add(point);
            }
        }

        System.out.println("Geometry pointsWithinDistance:" + pointsWithinDistance);

        Envelope envelope = currentLocationPoint.getEnvelopeInternal();
        stRtreeIndex = new STRtree();
        List<Point> points = stRtreeIndex.query(envelope);
        Geometry G1 = LocationtechFunctions.quadTreeQuery(g_multiPoints, g_currentLocationPoint.getEnvelope());
        System.out.println("Geometry G1:" + G1);
        Geometry G2 = LocationtechFunctions.strTreeBounds(g_multiPoints);
        System.out.println("Geometry G2:" + G2);
        Geometry G3 = LocationtechFunctions.strTreeNN(g_multiPoints, g_currentLocationPoint);
        System.out.println("Geometry G3:" + G3);
        Geometry G4 = LocationtechFunctions.strTreeNNInSet(g_multiPoints);
        System.out.println("Geometry G4:" + G4);
        Geometry G5 = LocationtechFunctions.strTreeNNk(g_multiPoints, g_currentLocationPoint, 3);
        System.out.println("Geometry G5:" + G5);
        Geometry G6 = LocationtechFunctions.strTreeQuery(g_multiPoints, g_currentLocationPoint.getEnvelope());
        System.out.println("Geometry G6:" + G6);
        Geometry G7 = LocationtechFunctions.strTreeQueryCached(g_multiPoints, g_currentLocationPoint.getEnvelope());
        System.out.println("Geometry G7:" + G7);
        testKNearestNeighbors();
    }

    public void testKNearestNeighbors() {
        int topK = 3;
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = getCurrentGpsPoint();
        Point queryCenter = geometryFactory.createPoint(coordinate);
        List<Geometry> testDataset = getOtherGpsPointsInGeometry();
        //List<Geometry> correctData = new ArrayList<Geometry>();

        GeometryDistanceComparator distanceComparator = new GeometryDistanceComparator(queryCenter, true);
        Collections.sort(testDataset, distanceComparator);


        STRtree strtree = new STRtree();
        for (int i = 0; i < testDataset.size(); i++) {
            strtree.insert(testDataset.get(i).getEnvelopeInternal(), testDataset.get(i));
        }
        strtree.build();
        /*
         * Shoot a random query to make sure the STR-Tree is built.
         */
        //strtree.query(new Envelope(1 + 0.1, 1 + 0.1, 2 + 0.1, 2 + 0.1));
        /*
         * Issue the KNN query.
         */
        Object[] testTopK = strtree.nearestNeighbour(queryCenter.getEnvelopeInternal(), queryCenter,
                new GeometryItemDistance(), topK);
        List topKList = Arrays.asList(testTopK);
        Collections.sort(topKList, distanceComparator);
        System.out.println("Geometry topKList:" + topKList);
    }

    private List<Coordinate> getOtherGpsPoints() {
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(16.486570, 80.630518));//Vijayawada
        coordinates.add(new Coordinate(16.644525, 81.158295));//Eluru
        coordinates.add(new Coordinate(16.844415, 82.246667));//Kakinada
        coordinates.add(new Coordinate(17.101418, 78.510193));//Hyderabad
        coordinates.add(new Coordinate(18.089076, 83.403752));//Vizianagaram
        coordinates.add(new Coordinate(18.323892, 83.936944));//Sirkakulam
        coordinates.add(new Coordinate(14.357974, 79.970260));//nellore
        coordinates.add(new Coordinate(14.411184, 78.870894));//kadapa
        coordinates.add(new Coordinate(15.811194, 78.057363));//kurnool
        coordinates.add(new Coordinate(12.964412, 80.298848));//Chennai

        return coordinates;
    }

    private List<Geometry> getOtherGpsPointsInGeometry() {
        List<Geometry> otherLocations = new ArrayList<>();
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(16.486570, 80.630518)));//5 Vijayawada
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(16.644525, 81.158295)));//4 Eluru
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(16.844415, 82.246667)));//3 Kakinada
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(17.101418, 78.510193)));//6 Hyderabad
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(18.089076, 83.403752)));//1 Vizianagaram
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(18.323892, 83.936944)));//2 Sirkakulam
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(14.357974, 79.970260)));//8 nellore
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(14.411184, 78.870894)));//9 kadapa
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(15.811194, 78.057363)));//7 kurnool
        otherLocations.add(new GeometryFactory().createPoint(new Coordinate(12.964412, 80.298848)));//10 Chennai
        return otherLocations;
    }

    private Coordinate getCurrentGpsPoint() {
        Coordinate currentLocation = new Coordinate(17.686815, 83.218483);
        return currentLocation;
    }
}
