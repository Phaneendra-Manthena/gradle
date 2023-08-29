package com.bhargo.user.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SampleData {

    String jsonSchema = "{\"type\":\"object\",\"properties\":{\"States\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"StateID\":{\"type\":\"string\"},\"StateName\":{\"type\":\"string\"},\"AreaCoordinates\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"Districts\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"DistrictID\":{\"type\":\"string\"},\"DistrictName\":{\"type\":\"string\"},\"Mandals\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"MandalID\":{\"type\":\"string\"},\"MandalName\":{\"type\":\"string\"},\"Schools\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"SchoolID\":{\"type\":\"string\"},\"SchoolName\":{\"type\":\"string\"},\"Address\":{\"type\":\"object\",\"properties\":{\"DoorNo\":{\"type\":\"string\"},\"LandMark\":{\"type\":\"string\"},\"Village\":{\"type\":\"string\"}}},\"Classes\":{\"type\":\"array\"}}}}}}}}}}}}}}}";
    String jsonObj = "{\"States\":[{\"StateID\":\"01\",\"StateName\":\"Andhra Pradesh\",\"AreaCoordinates\":[\"71.88978\",\"69.09885\",\"67.966434\"],\"Districts\":[{\"DistrictID\":\"01\",\"DistrictName\":\"Visakha Patnam\",\"Mandals\":[{\"MandalID\":\"01\",\"MandalName\":\"Pendurthi\",\"Schools\":[{\"SchoolID\":\"01\",\"SchoolName\":\"Mahathi Public School\",\"Address\":{\"DoorNo\":\"1-111\",\"LandMark\":\"Near CMR Shopping Mall\",\"Village\":\"Pursotha puram\"},\"Classes\":[]}]},{\"MandalID\":\"02\",\"MandalName\":\"Kanchara Palem\",\"Schools\":[]}]},{\"DistrictID\":\"02\",\"DistrictName\":\"Anakapalli\",\"Mandals\":[{\"MandalID\":\"01\",\"MandalName\":\"Sabbavaram\",\"Schools\":[{\"SchoolID\":\"01\",\"SchoolName\":\"Mother Public School\",\"Address\":{\"DoorNo\":\"1-110\",\"LandMark\":\"Opp. Ramalayam\",\"Village\":\"Amrutha puram\"},\"Classes\":[{\"ClassID\":\"01\",\"ClassName\":\"10th\",\"Sections\":[{\"SectionID\":\"01\",\"SectionName\":\"A\"}]}]}]},{\"MandalID\":\"02\",\"MandalName\":\"Parawada\",\"Schools\":[]}]}]},{\"StateID\":\"02\",\"StateName\":\"Bihar\",\"AreaCoordinates\":[\"72.88978\",\"83.09885\",\"85.966434\"],\"Districts\":[]}]}";
    String sampleHashMapObj="{States=[{StateID=01, StateName=Andhra Pradesh, AreaCoordinates=[71.88978, 69.09885, 67.966434], Districts=[{DistrictID=01, DistrictName=Visakha Patnam, Mandals=[{MandalID=01, MandalName=Pendurthi, Schools=[{SchoolID=01, SchoolName=Mahathi Public School, Address={DoorNo=1-111, LandMark=Near CMR Shopping Mall, Village=Pursotha puram}, Classes=[]}]}, {MandalID=02, MandalName=Kanchara Palem, Schools=[]}]}, {DistrictID=02, DistrictName=Anakapalli, Mandals=[{MandalID=01, MandalName=Sabbavaram, Schools=[{SchoolID=01, SchoolName=Mother Public School, Address={DoorNo=1-110, LandMark=Opp. Ramalayam, Village=Amrutha puram}, Classes=[{ClassID=01, ClassName=10th, Sections=[{SectionID=01, SectionName=A}]}]}]}, {MandalID=02, MandalName=Parawada, Schools=[]}]}]}, {StateID=02, StateName=Bihar, AreaCoordinates=[72.88978, 83.09885, 85.966434], Districts=[]}]}";

    private HashMap<String, Object> dataSources() {
        HashMap<String, Object> dataSoures = new HashMap<String, Object>();
        //states
        List<String> stateIDs = new ArrayList<>();
        stateIDs.add("1");
        stateIDs.add("2");
        List<String> stateNames = new ArrayList<>();
        stateNames.add("s1");
        stateNames.add("s2");
        List<String> stateAreas = new ArrayList<>();
        stateAreas.add("37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922");
        stateAreas.add("37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922 ^37.4220936$-122.083922");
        HashMap<String, List<String>> states = new HashMap<String, List<String>>();
        states.put("states_state_id",stateIDs);
        states.put("states_state_name",stateNames);
        states.put("states_areacoordiates",stateAreas);
        dataSoures.put("States",states);
        //districts
        List<String> distIDs = new ArrayList<>();
        distIDs.add("1");
        distIDs.add("2");
        distIDs.add("3");
        List<String> distNames = new ArrayList<>();
        distNames.add("d1");
        distNames.add("d2");
        distNames.add("d3");
        List<String> diststateid = new ArrayList<>();
        diststateid.add("s1");
        diststateid.add("s1");
        diststateid.add("s2");
        HashMap<String, List<String>> dists = new HashMap<String, List<String>>();
        dists.put("dist_id",distIDs);
        dists.put("dist_name",distNames);
        dists.put("dist_state_id",diststateid);
        dataSoures.put("Districts",dists);
        //mandals
        List<String> mids = new ArrayList<>();
        mids.add("1");
        mids.add("2");
        mids.add("3");
        mids.add("4");
        mids.add("5");
        List<String> mnamess = new ArrayList<>();
        mnamess.add("m1");
        mnamess.add("m2");
        mnamess.add("m3");
        mnamess.add("m4");
        mnamess.add("m5");
        List<String> mdistids = new ArrayList<>();
        mdistids.add("s1");
        mdistids.add("s1");
        mdistids.add("s1");
        mdistids.add("s1");
        mdistids.add("s2");
        List<String> mstateids = new ArrayList<>();
        mstateids.add("d1");
        mstateids.add("d2");
        mstateids.add("d1");
        mstateids.add("d2");
        mstateids.add("d3");
        HashMap<String, List<String>> mandals = new HashMap<String, List<String>>();
        mandals.put("mandals_mandal_id",mids);
        mandals.put("mandals_mandal_name",mnamess);
        mandals.put("mandals_dist_id",mdistids);
        mandals.put("mandals_stateid",mstateids);
        dataSoures.put("Mandals",mandals);
        //schools
        List<String> ssids = new ArrayList<>();
        ssids.add("1");
        ssids.add("2");
        ssids.add("3");
        ssids.add("4");
        ssids.add("5");
        List<String> ssnames = new ArrayList<>();
        ssnames.add("ss1");
        ssnames.add("ss2");
        ssnames.add("ss3");
        ssnames.add("ss4");
        ssnames.add("ss5");
        List<String> ssmandalids = new ArrayList<>();
        ssmandalids.add("m1");
        ssmandalids.add("m1");
        ssmandalids.add("m2");
        ssmandalids.add("m3");
        ssmandalids.add("m5");

        HashMap<String, List<String>> schools = new HashMap<String, List<String>>();
        schools.put("schools_school_id",ssids);
        schools.put("schools_school_name",ssnames);
        schools.put("schools_mandal_id",ssmandalids);

        dataSoures.put("Schools",schools);



        return dataSoures;
    }

}
