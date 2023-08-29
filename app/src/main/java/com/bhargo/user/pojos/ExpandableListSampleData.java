package com.bhargo.user.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListSampleData {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> empty = new ArrayList<String>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("India");
        cricket.add("Pakistan");
        cricket.add("Australia");
        cricket.add("England");
        cricket.add("South Africa");

        List<String> football = new ArrayList<String>();
        football.add("Brazil");
        football.add("Spain");
        football.add("Germany");
        football.add("Netherlands");
        football.add("Italy");

        List<String> basketball = new ArrayList<String>();
        basketball.add("United States");
        basketball.add("SpainSpainSpainSpainSpainSpainSpainSpainSpainSpainSpainSpainSpainSpain");
        basketball.add("Argentina");
        basketball.add("France");
        basketball.add("Russia");

        expandableListDetail.put("Denmark", empty);
        expandableListDetail.put("Cricket Teams", cricket);
        expandableListDetail.put("Football Teams", football);
        expandableListDetail.put("Basketball Teams", basketball);
        return expandableListDetail;
    }
}