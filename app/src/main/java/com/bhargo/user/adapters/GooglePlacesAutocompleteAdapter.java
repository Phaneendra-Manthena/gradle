package com.bhargo.user.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bhargo.user.R;
import com.bhargo.user.pojos.AutoPlaceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/*mobileapps.nk*/
/*private void setAutocompletePlaces() {
        startLocation.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));
        startLocation.setOnItemClickListener(this);
    }
    */

/*
*   @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (view.getId() == R.id.startLocation) {
        AutoPlaceModel description = (AutoPlaceModel) parent.getItemAtPosition(position);
        Log.e("Selected Location :", description.getPlaceName());

        startLocation.setText(description.getPlaceName());
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Alert");
        pd.setMessage("Fetching place coordinates, Please wait.");
        pd.show();

        Places.GeoDataApi.getPlaceById(mGoogleApiClient, description.getPlaceId())
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess()) {
                            final Place myPlace = places.get(0);
                            LatLng queriedLocation = myPlace.getLatLng();
                            start = myPlace.getLatLng();

                            Log.v("Latitude is", "" + queriedLocation.latitude);
                            Log.v("Longitude is", "" + queriedLocation.longitude);

//                                latLngString = queriedLocation.latitude + "," + queriedLocation.longitude;
//                                latLng = new LatLng(queriedLocation.latitude, queriedLocation.longitude);

                           // drawPathOnMap();
                            if (pd != null && pd.isShowing()) pd.dismiss();
                        }
                        places.release();
                    }
                });
//        }
    }
*/


public class GooglePlacesAutocompleteAdapter extends ArrayAdapter<AutoPlaceModel> implements Filterable {
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyAD47cgCMH-X5YeyMoVIGbaMCBw63Fs3T4";
    private static final String LOG_TAG = "PLACE_SEARCH_LOG_TAG";

    private ArrayList<AutoPlaceModel> resultList;
    private Context mContext;


    public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.mContext = context;
    }

    public static ArrayList<AutoPlaceModel> autocomplete(String input) {
        ArrayList<AutoPlaceModel> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in|country:us|country:vi|country:gu|country:mp");//country:gr
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: " + url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<AutoPlaceModel>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                AutoPlaceModel apm = new AutoPlaceModel();
                apm.setPlaceId(predsJsonArray.getJSONObject(i).getString("place_id"));
//                apm.setId(predsJsonArray.getJSONObject(i).getString("id"));
                apm.setPlaceName(predsJsonArray.getJSONObject(i).getString("description"));
                resultList.add(apm);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public AutoPlaceModel getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        //if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (position != (resultList.size() - 1))
            view = inflater.inflate(R.layout.row_google_place, null);
        else
            view = inflater.inflate(R.layout.row_google_place_logo, null);
        //}
        //else {
        //    view = convertView;
        //}

        if (position != (resultList.size() - 1)) {
            TextView autocompleteTextView = view.findViewById(R.id.locName);
            if (!resultList.get(position).getPlaceName().equalsIgnoreCase("Footer")) {
            autocompleteTextView.setText(resultList.get(position).getPlaceName());}
        } else {
//            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            // not sure what to do <img draggable="false" class="emoji" alt="😀" src="https://s.w.org/images/core/emoji/72x72/1f600.png">
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = autocomplete(constraint.toString());
                    AutoPlaceModel apm = new AutoPlaceModel();
                    apm.setPlaceName("Footer");
                    apm.setPlaceId("-1");
                    resultList.add(apm);
                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
