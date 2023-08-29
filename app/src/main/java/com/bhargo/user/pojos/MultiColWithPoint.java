package com.bhargo.user.pojos;

import java.util.LinkedHashMap;
import java.util.List;

public class MultiColWithPoint {

    List<String> listOfPoints;
    LinkedHashMap<String, List<String>> map_pointtoCols = new LinkedHashMap<String, List<String>>();



    public LinkedHashMap<String, List<String>> getMap_pointtoCols() {
        return map_pointtoCols;
    }

    public void setMap_pointtoCols(LinkedHashMap<String, List<String>> map_pointtoCols) {
        this.map_pointtoCols = map_pointtoCols;
    }

    public List<String> getListOfPoints() {
        return listOfPoints;
    }

    public void setListOfPoints(List<String> listOfPoints) {
        this.listOfPoints = listOfPoints;
    }
}
