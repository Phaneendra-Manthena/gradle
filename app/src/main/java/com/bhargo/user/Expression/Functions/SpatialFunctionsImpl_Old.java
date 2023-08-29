package com.bhargo.user.Expression.Functions;

import android.content.Context;

import com.bhargo.user.Expression.Interfaces.SpatialFunctions;
import com.bhargo.user.pojos.PointWithDistance;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.utils.AppConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


public class SpatialFunctionsImpl_Old implements SpatialFunctions {

    private Context context;
    static final double pi = 3.14159265358979323846;

    public static double PI = 3.14159265;
    public static double TWOPI = 2 * PI;

    public static double deg2rad(double deg) {
        return (deg * pi / 180);
    }

    public static double rad2deg(double rad) {
        return (rad * 180 / pi);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getDistance(String Position1, String Position2) {
        double dist = 0.0;
        try {
            Position2=Position2.split("\\^")[0];
            double lat1 = Double.parseDouble(Position1.split("\\$")[0]);
            double lon1 = Double.parseDouble(Position1.split("\\$")[1]);

            double lat2 = Double.parseDouble(Position2.split("\\$")[0]);
            double lon2 = Double.parseDouble(Position2.split("\\$")[1]);
            double theta;


            theta = lon1 - lon2;

            dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                    + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

            dist = Math.acos(dist);

            dist = rad2deg(dist);

            dist = dist * 60 * 1.1515;

            dist = dist * 1.609344; // in k.m

//            dist = dist * 1000; // in meters

        } catch (Exception e) {
            System.out.println("Exception at getDistance==" + e.toString());
        }
        return (int) dist;

    }

    @Override
    public boolean isWithinDistance(String currentPosition, List<String> referencePosition, double radius) {
        boolean returnflag = false;
        double lat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double lon1 = Double.parseDouble(currentPosition.split("\\$")[1]);

        for (int i = 0; i < referencePosition.size(); i++) {
            String[] RowPositions = referencePosition.get(i).split("\\^");

            for (int j = 0; j < RowPositions.length; j++) {
                double lat2 = Double.parseDouble(RowPositions[j].split("\\$")[0]);
                double lon2 = Double.parseDouble(RowPositions[j].split("\\$")[1]);

                double theta, dist;

                theta = lon1 - lon2;

                dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                        + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

                dist = Math.acos(dist);

                dist = rad2deg(dist);

                dist = dist * 60 * 1.1515;

                dist = dist * 1.609344; // in k.m

                dist = dist * 1000; // in meters

                //return (dist);

                if (dist <= radius) {
                    returnflag = true;
                    break;
                }
            }

        }

        return returnflag;
    }

    @Override
    public boolean isWithinBoundary(String currentPosition, List<String> ListofPositions) {
        boolean insideOutside = false;
        boolean insideloc=false;

        for (int i = 0; i < ListofPositions.size(); i++) {
            String[] RowValue = ListofPositions.get(i).split("\\^");
            ArrayList<Double> lat_array = new ArrayList<Double>();
            ArrayList<Double> long_array = new ArrayList<Double>();

            for (int j = 0; j < RowValue.length; j++) {
                lat_array.add(Double.parseDouble(RowValue[j].split("\\$")[0]));
                long_array.add(Double.parseDouble(RowValue[j].split("\\$")[1]));
            }

            double lat = Double.parseDouble(currentPosition.split("\\$")[0]);
            double lon = Double.parseDouble(currentPosition.split("\\$")[1]);


                insideOutside = coordinate_is_inside_polygon(lat, lon, lat_array, long_array);
                System.out.println("insideOutside===="+insideOutside);
                if (insideOutside) {
                    insideloc=true;
                    break;
                }
        }

        return insideloc;
    }

    @Override
    public List<String> getNearbyValue(String currentPosition, List<String> ListofPositions, double NoofRecords) {
        List<String> nearLocation = new ArrayList<>();
        List<Location> locationswithDistance=new ArrayList<>();
        double Last_distance = 0.0;
        double lat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double lon1 = Double.parseDouble(currentPosition.split("\\$")[1]);

        for (int i = 0; i < ListofPositions.size(); i++) {
            String[] RowPositions = ListofPositions.get(i).split("\\^");
            List<String> listofpoints=new ArrayList<>();
            listofpoints.addAll(Arrays.asList(RowPositions));
            int nearDistance=getNearestDistance(currentPosition,listofpoints);

            Location loc_dist=new Location(ListofPositions.get(i),nearDistance);
            locationswithDistance.add(loc_dist);
        }

        Collections.sort(locationswithDistance, new Comparator<Location>() {
            public int compare(Location o1, Location o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });

        for (int i = 0; i < locationswithDistance.size(); i++) {
            if(i<NoofRecords){
                nearLocation.add(locationswithDistance.get(i).getLocation());
            }
        }

        return nearLocation;
    }

    @Override
    public List<PointWithDistance> getMultiColumnNearbyValue(String SelectedColumns, String currentPosition, List<String> ListofPositions, double NoofRecords) {
        String finalvalue="",ReturnStr="";
        List<String> nearLocation = new ArrayList<>();
        List<Location> locationswithDistance=new ArrayList<>();
        LinkedHashMap<String, List<String>> map_APIData=new LinkedHashMap<String, List<String>>();
        double Last_distance = 0.0;
        double lat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double lon1 = Double.parseDouble(currentPosition.split("\\$")[1]);
        if(SelectedColumns.toLowerCase().endsWith("_allcolumns)")){
            String[] returnControl_List = SelectedColumns.substring(4, SelectedColumns.lastIndexOf(")")).split("\\.");

            if(returnControl_List[0].equalsIgnoreCase(AppConstants.Global_API)) {
               // map_APIData = AppConstants.GlobalObjects.getAPIs_ListHash().get(returnControl_List[1]);
                //nk realm
                map_APIData= RealmDBHelper.getTableDataInLHM(context,returnControl_List[1]);
            }else if(returnControl_List[0].equalsIgnoreCase(AppConstants.Global_FormFields)) {
               // map_APIData = AppConstants.GlobalObjects.getForms_ListHash().get(returnControl_List[1]);
                //nk realm
                map_APIData= RealmDBHelper.getTableDataInLHM(context,returnControl_List[1]);
            }else if(returnControl_List[0].equalsIgnoreCase(AppConstants.Global_Query)) {
                //map_APIData = AppConstants.GlobalObjects.getForms_ListHash().get(returnControl_List[1]);
                //nk realm
                map_APIData= RealmDBHelper.getTableDataInLHM(context,returnControl_List[1]);
            }

            for (int i = 0; i < ListofPositions.size(); i++) {
                String Value="";
                Set<String> apiSet= map_APIData.keySet();
                String[] apiNames = apiSet.toArray(new String[apiSet.size()]);
                for (int x = 0; x < apiNames.length; x++) {
                    Value = Value+"#"+  map_APIData.get(apiNames[x]).get(i);
                }
                if(Value.startsWith("#")){
                    Value=Value.substring(1);
                }


                String[] RowPositions = ListofPositions.get(i).split("\\^");
                List<String> listofpoints=new ArrayList<>();
                listofpoints.addAll(Arrays.asList(RowPositions));
                int nearDistance=getNearestDistance(currentPosition,listofpoints);

                Location loc_dist=new Location(ListofPositions.get(i)+"|"+Value,nearDistance);
                locationswithDistance.add(loc_dist);
            }
        }



        Collections.sort(locationswithDistance, new Comparator<Location>() {
            public int compare(Location o1, Location o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });
        LinkedHashMap<String,String> NameswiseData=new LinkedHashMap<>();

        for (int i = 0; i < locationswithDistance.size(); i++) {
            if(i<NoofRecords){
                String locStr=locationswithDistance.get(i).getLocation().substring(0,locationswithDistance.get(i).getLocation().indexOf("|"));
                String Data=locationswithDistance.get(i).getLocation().substring(locationswithDistance.get(i).getLocation().indexOf("|")+1);
                Set<String> apiSet= map_APIData.keySet();
                String[] apiNames = apiSet.toArray(new String[apiSet.size()]);
                for (int x = 0; x < apiNames.length; x++) {
                    if(NameswiseData.containsKey(apiNames[x])){
                        String Datastr=NameswiseData.get(apiNames[x]);
                        Datastr=Datastr+","+Data.split("\\#")[x];
                        NameswiseData.put(apiNames[x],Datastr);
                    }else{
                        NameswiseData.put(apiNames[x],Data.split("\\#")[x]);
                    }
                }

                nearLocation.add(locStr);
            }
        }

        Set<String> apiSet= NameswiseData.keySet();
        String[] apiNames = apiSet.toArray(new String[apiSet.size()]);
        for (int x = 0; x < apiNames.length; x++) {
            finalvalue = finalvalue+"#"+apiNames[x]+"|"+NameswiseData.get(apiNames[x]);

        }
        finalvalue=finalvalue.substring(finalvalue.indexOf("#")+1);
//        ReturnStr=String.join(",",nearLocation)+"@"+finalvalue;
        ReturnStr=finalvalue;
        return new ArrayList<>();
    }

    @Override
    public List<String> getNearbyValueWithinRange(String currentPosition, List<String> ListofPositions,
                                                  double NoofRecords,int RangeInMeters) {
        List<String> nearLocation = new ArrayList<>();
        List<Location> locationswithDistance=new ArrayList<>();
        double Last_distance = 0.0;
        double lat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double lon1 = Double.parseDouble(currentPosition.split("\\$")[1]);

        for (int i = 0; i < ListofPositions.size(); i++) {
            String[] RowPositions = ListofPositions.get(i).split("\\^");
            List<String> listofpoints=new ArrayList<>();
            listofpoints.addAll(Arrays.asList(RowPositions));
            int nearDistance=getNearestDistance(currentPosition,listofpoints);

            if(nearDistance<=RangeInMeters) {
            Location loc_dist=new Location(ListofPositions.get(i),nearDistance);
            locationswithDistance.add(loc_dist);
            }
        }

        Collections.sort(locationswithDistance, new Comparator<Location>() {
            public int compare(Location o1, Location o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });

        for (int i = 0; i < locationswithDistance.size(); i++) {
            if(i<NoofRecords){
                nearLocation.add(locationswithDistance.get(i).getLocation());
            }
        }

        return nearLocation;
    }

    @Override
    public List<PointWithDistance> getMultiColumnNearbyValueWithinRange(String SelectedColumns, String currentPosition, List<String> ListofPositions,
                                                                        double NoofRecords, int RangeInMeters) {
        List<String> nearLocation = new ArrayList<>();
        List<Location> locationswithDistance=new ArrayList<>();
        double Last_distance = 0.0;
        double lat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double lon1 = Double.parseDouble(currentPosition.split("\\$")[1]);

        for (int i = 0; i < ListofPositions.size(); i++) {
            String[] RowPositions = ListofPositions.get(i).split("\\^");
            List<String> listofpoints=new ArrayList<>();
            listofpoints.addAll(Arrays.asList(RowPositions));
            int nearDistance=getNearestDistance(currentPosition,listofpoints);

            if(nearDistance<=RangeInMeters) {
                Location loc_dist=new Location(ListofPositions.get(i),nearDistance);
                locationswithDistance.add(loc_dist);
            }
        }

        Collections.sort(locationswithDistance, new Comparator<Location>() {
            public int compare(Location o1, Location o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });

        for (int i = 0; i < locationswithDistance.size(); i++) {
            if(i<NoofRecords){
                nearLocation.add(locationswithDistance.get(i).getLocation());
            }
        }

        return new ArrayList<>();
    }


    public List<String> getNearbyValueWithinRange_old(String currentPosition, List<String> ListofPositions,
                                                  double NoofRecords,int RangeInMeters) {
        List<String> nearLocation = new ArrayList<>();
        List<Location> locationswithDistance=new ArrayList<>();
        double Last_distance = 0.0;
        double lat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double lon1 = Double.parseDouble(currentPosition.split("\\$")[1]);

        for (int i = 0; i < ListofPositions.size(); i++) {
            String[] RowPositions = ListofPositions.get(i).split("\\^");

            for (int j = 0; j < RowPositions.length; j++) {
                double lat2 = Double.parseDouble(RowPositions[j].split("\\$")[0]);
                double lon2 = Double.parseDouble(RowPositions[j].split("\\$")[1]);

                double theta, dist;

                theta = lon1 - lon2;

                dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                        + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

                dist = Math.acos(dist);

                dist = rad2deg(dist);

                dist = dist * 60 * 1.1515;

                dist = dist * 1.609344; // in k.m

                dist = dist * 1000; // in meters

                //return (dist);
                if(dist<=RangeInMeters) {
                    Location loc_dist = new Location(RowPositions[j], dist);
                    locationswithDistance.add(loc_dist);
                }

            }

        }

        Collections.sort(locationswithDistance, new Comparator<Location>() {
            public int compare(Location o1, Location o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });

        for (int i = 0; i < locationswithDistance.size(); i++) {
            if(i<NoofRecords){
                nearLocation.add(locationswithDistance.get(i).getLocation());
            }
        }

        return nearLocation;
    }

    @Override
    public int getNearestDistance(String currentPosition, List<String> ListofPositions) {
        double Last_distance = 0.0;
        double lat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double lon1 = Double.parseDouble(currentPosition.split("\\$")[1]);

        for (int i = 0; i < ListofPositions.size(); i++) {
            String[] RowPositions = ListofPositions.get(i).split("\\^");

            for (int j = 0; j < RowPositions.length; j++) {
                double lat2 = Double.parseDouble(RowPositions[j].split("\\$")[0]);
                double lon2 = Double.parseDouble(RowPositions[j].split("\\$")[1]);

                double theta, dist;

                theta = lon1 - lon2;

                dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                        + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

                dist = Math.acos(dist);

                dist = rad2deg(dist);

                dist = dist * 60 * 1.1515;

                dist = dist * 1.609344; // in k.m

                dist = dist * 1000; // in meters

                //return (dist);
                if(Last_distance==0){
                    Last_distance=dist;
                }

                if(dist<=Last_distance) {
                    Last_distance=dist;
                }

            }
        }

        return (int) Last_distance;
    }




    public static boolean coordinate_is_inside_polygon(double latitude, double longitude, ArrayList<Double> lat_array, ArrayList<Double> long_array) {
        int i;
        double angle = 0;
        double point1_lat;
        double point1_long;
        double point2_lat;
        double point2_long;
        int n = lat_array.size();

        for (i = 0; i < n; i++) {
            point1_lat = lat_array.get(i) - latitude;
            point1_long = long_array.get(i) - longitude;
            point2_lat = lat_array.get((i + 1) % n) - latitude;
            //you should have paid more attention in high school geometry.
            point2_long = long_array.get((i + 1) % n) - longitude;
            angle += Angle2D(point1_lat, point1_long, point2_lat, point2_long);
        }

        if (Math.abs(angle) < PI)
            return false;
        else
            return true;
    }

    public static double Angle2D(double y1, double x1, double y2, double x2) {
        double dtheta, theta1, theta2;

        theta1 = Math.atan2(y1, x1);
        theta2 = Math.atan2(y2, x2);
        dtheta = theta2 - theta1;
        while (dtheta > PI)
            dtheta -= TWOPI;
        while (dtheta < -PI)
            dtheta += TWOPI;

        return (dtheta);
    }


    class Location {
        private String Location;
        private double Distance;

        Location(String Location, double Distance) {
            this.Location = Location;
            this.Distance = Distance;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public double getDistance() {
            return Distance;
        }

        public void setDistance(double distance) {
            Distance = distance;
        }
    }
}
