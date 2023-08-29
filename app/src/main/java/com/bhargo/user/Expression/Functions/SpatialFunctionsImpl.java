package com.bhargo.user.Expression.Functions;

import com.bhargo.user.Expression.Interfaces.SpatialFunctions;
import com.bhargo.user.Expression.LocationtechFunctions;
import com.bhargo.user.pojos.PointWithDistance;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.operation.distance3d.Distance3DOp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SpatialFunctionsImpl implements SpatialFunctions {

    String TAG = "SpatialFunctionsImplWithSTR";

    @Override
    public int getDistance(String Position1, String Position2) {
        double dist = 0.0;
        try {
            Geometry g0 = getGeometryInPoint(Position1);
            Geometry g1 = getGeometryInPoint(Position2);
            Distance3DOp distOp = new Distance3DOp(g0, g1);
            dist = distOp.distance();
        } catch (Exception e) {
            System.out.println(TAG + ": Exception at getDistance==" + e);
        }
        return (int) dist;
    }

    @Override
    public boolean isWithinDistance(String currentPosition, List<String> referencePosition, double radius) {
        boolean returnflag = false;
        Geometry geom = getGeometryInPoint(currentPosition);
        Geometry geoms = getGeometryInMultiPoint(referencePosition);
        returnflag = LocationtechFunctions.isWithinDistance(geoms, geom, radius);
        return returnflag;
    }

    //pending
    @Override
    public boolean isWithinBoundary(String currentPosition, List<String> ListofPositions) {
        Geometry geom = getGeometryInPoint(currentPosition);
        Geometry geoms = getGeometryInMultiPoint(ListofPositions);
        //pending
        return geoms.within(geom);
    }

    @Override
    public List<String> getNearbyValue(String currentPosition, List<String> ListofPositions, double NoofRecords) {
        Geometry g_currentLocationPoint = getGeometryInPoint(currentPosition);
        Geometry g_multiPoints = getGeometryInMultiPoint(ListofPositions);
        Geometry nearestGeomPoints = LocationtechFunctions.strTreeNNk(g_multiPoints, g_currentLocationPoint, (int) NoofRecords);

        List<Coordinate> coordinateList = Arrays.asList(nearestGeomPoints.getCoordinates());
        List<String> nearestPoints = new ArrayList<>();
        for (int i = 0; i < coordinateList.size(); i++) {
            Coordinate coordinate = coordinateList.get(i);
            nearestPoints.add(coordinate.x + "$" + coordinate.y);
        }
        return nearestPoints;
    }

    @Override
    public List<String> getNearbyValueWithinRange(String currentPosition, List<String> ListofPositions, double NoofRecords, int RangeInMeters) {
        Geometry g_currentLocationPoint = getGeometryInPoint(currentPosition);
        Geometry g_multiPoints = getGeometryInMultiPoint(ListofPositions);
        Geometry nearestGeomPoints = LocationtechFunctions.strTreeNNk(g_multiPoints, g_currentLocationPoint, (int) NoofRecords);

        List<Coordinate> coordinateList = Arrays.asList(nearestGeomPoints.getCoordinates());
        Point currentPoint = new GeometryFactory().createPoint(g_currentLocationPoint.getCoordinate());
        List<String> nearestPoints = new ArrayList<>();
        for (int i = 0; i < coordinateList.size(); i++) {
            Coordinate coordinate = coordinateList.get(i);
            Point point = new GeometryFactory().createPoint(coordinate);
            double d = point.distance(currentPoint);
            System.out.println("G filterPoints Distance:" + d);
            if (d < RangeInMeters) {
                nearestPoints.add(coordinate.x + "$" + coordinate.y);
            }
        }
        return nearestPoints;
    }

    @Override
    public List<PointWithDistance> getMultiColumnNearbyValue(String SelectedColumns, String currentPosition, List<String> ListofPositions, double NoofRecords) {
        return getNearbyValueWithDistance(currentPosition, ListofPositions, NoofRecords, true);
    }

    @Override
    public List<PointWithDistance> getMultiColumnNearbyValueWithinRange(String SelectedColumns, String currentPosition, List<String> ListofPositions, double NoofRecords, int RangeInMeters) {
        return getNearbyValueWithinRangeWithDistance(currentPosition, ListofPositions, NoofRecords, RangeInMeters, true);
    }

    @Override
    public int getNearestDistance(String currentPosition, List<String> ListofPositions) {
        Geometry g_currentLocationPoint = getGeometryInPoint(currentPosition);
        Geometry g_multiPoints = getGeometryInMultiPoint(ListofPositions);

        Geometry nearestGeom = LocationtechFunctions.strTreeNN(g_multiPoints, g_currentLocationPoint);
        Point currentPoint = new GeometryFactory().createPoint(g_currentLocationPoint.getCoordinate());
        Point nearestPoint = new GeometryFactory().createPoint(nearestGeom.getCoordinate());
        double d = nearestPoint.distance(currentPoint);
        return (int) d;
    }

    private List<PointWithDistance> getNearbyValueWithinRangeWithDistance(String currentPosition, List<String> ListofPositions, double NoofRecords, int RangeInMeters, boolean calDistance) {
        List<PointWithDistance> filterPositions = getNearbyValueWithDistance(currentPosition, ListofPositions, NoofRecords, calDistance);
        return getNearbyValueWithinRange(currentPosition, filterPositions, RangeInMeters, calDistance);
    }

    private List<PointWithDistance> getNearbyValueWithinRange(String currentPosition, List<PointWithDistance> filterPositions, int RangeInMeters, boolean calDistance) {
        double currLat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double currLon1 = Double.parseDouble(currentPosition.split("\\$")[1]);
        Coordinate currentLocation = new Coordinate(currLat1, currLon1);
        List<PointWithDistance> updatedFilterPoints = new ArrayList<>();
        if (calDistance) {
            for (int i = 0; i < filterPositions.size(); i++) {
                if (filterPositions.get(i).getDistance() < RangeInMeters) {
                    updatedFilterPoints.add(filterPositions.get(i));
                }
            }
        } else {
            List<Coordinate> otherLocations = getCoordinates(filterPositions);
            List<PointWithDistance> filterPoints = new ArrayList<>();
            Point currentPoint = new GeometryFactory().createPoint(currentLocation);
            for (Coordinate location : otherLocations) {
                Point point = new GeometryFactory().createPoint(location);
                double d = point.distance(currentPoint);
                System.out.println("G filterPoints Distance:" + d);
                if (d < RangeInMeters) {
                    PointWithDistance pointWithDistance = new PointWithDistance();
                    pointWithDistance.setDistance(d);
                    pointWithDistance.setPoint(point.getX() + "$" + point.getY());
                    updatedFilterPoints.add(pointWithDistance);
                }
            }
        }
        return updatedFilterPoints;
    }

    private Geometry getGeometryInPoint(String position) {
        double currLat1 = Double.parseDouble(position.split("\\$")[0]);
        double currLon1 = Double.parseDouble(position.split("\\$")[1]);
        Geometry geom = new GeometryFactory().createPoint(new Coordinate(currLat1, currLon1));
        return geom;
    }

    private Geometry getGeometryInMultiPoint(List<String> referencePosition) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < referencePosition.size(); i++) {
            if (referencePosition.get(i) != null && !referencePosition.get(i).trim().equals("")) {
                String[] RowPositions = referencePosition.get(i).split("\\^");
                for (int j = 0; j < RowPositions.length; j++) {
                    double lat2 = Double.parseDouble(RowPositions[j].split("\\$")[0]);
                    double lon2 = Double.parseDouble(RowPositions[j].split("\\$")[1]);
                    coordinates.add(new Coordinate(lat2, lon2));
                }
            }
        }
        Geometry geoms = new GeometryFactory().createMultiPointFromCoords(coordinates.toArray(new Coordinate[0]));
        return geoms;
    }

    private List<PointWithDistance> getNearbyValueWithDistance(String currentPosition, List<String> ListofPositions, double NoofRecords, boolean calDistance) {
        Geometry g_currentLocationPoint = getGeometryInPoint(currentPosition);
        Geometry g_multiPoints = getGeometryInMultiPoint(ListofPositions);
        Geometry nearestGeomPoints = LocationtechFunctions.strTreeNNk(g_multiPoints, g_currentLocationPoint, (int) NoofRecords);

        List<Coordinate> coordinateList = Arrays.asList(nearestGeomPoints.getCoordinates());
        List<PointWithDistance> pointWithDistanceList = new ArrayList<>();
        Point currentPoint = new GeometryFactory().createPoint(g_currentLocationPoint.getCoordinate());
        for (int i = 0; i < coordinateList.size(); i++) {
            PointWithDistance pointWithDistance = new PointWithDistance();
            Coordinate coordinate = coordinateList.get(i);
            if (calDistance) {
                Point point = new GeometryFactory().createPoint(coordinate);
                double d = point.distance(currentPoint);
                int km = (int) (d / 1000);
                int meters = (int) (d - (km * 1000));
                pointWithDistance.setDistance(d);
            }
            pointWithDistance.setPoint(coordinate.x + "$" + coordinate.y);
            pointWithDistanceList.add(pointWithDistance);
        }
        return pointWithDistanceList;
    }

    private List<Coordinate> getCoordinates(List<PointWithDistance> stringList) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            String point = stringList.get(i).getPoint();
            double pointLat1 = Double.parseDouble(point.split("\\$")[0]);
            double pointLon1 = Double.parseDouble(point.split("\\$")[1]);
            coordinates.add(new Coordinate(pointLat1, pointLon1));
        }
        return coordinates;
    }
}
