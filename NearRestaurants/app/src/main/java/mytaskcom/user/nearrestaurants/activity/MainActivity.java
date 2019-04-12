package mytaskcom.user.nearrestaurants.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import mytaskcom.user.nearrestaurants.R;
import mytaskcom.user.nearrestaurants.adapter.RestaurantAdapter;
import mytaskcom.user.nearrestaurants.direction.GPSTracker;
import mytaskcom.user.nearrestaurants.model.RestApiResponse;
import mytaskcom.user.nearrestaurants.model.Result;
import mytaskcom.user.nearrestaurants.rest.ApiClient;
import mytaskcom.user.nearrestaurants.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    ListView list;
    RestaurantAdapter adapter;
    private static String TAG = MainActivity.class.getSimpleName();

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;


    List<Result> Restau_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);


        // check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled()) {
            /*----------to get City-Name from coordinates ------------- */


            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(gpsTracker.getLatitude(),
                        gpsTracker.getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println("aAAAAAAAAAAAAAAAAAAA"+addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();

                getRestFromApi(cityName);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            gpsTracker.showSettingsAlert();
        }

//
        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str =  parent.getAdapter().getItem(position).toString();
                  Result resukltObj =Restau_res.get(position);
                System.out.println("ssssssssssssssss"+resukltObj.getFormattedAddress());
//                Intent i = new Intent(MainActivity.this,MapsActivity.class);
//                i.putExtra("address",resukltObj.getFormattedAddress());
//                startActivity(i);

                Toast.makeText(getApplicationContext(), ""+resukltObj.getName(), Toast.LENGTH_SHORT).show();
            }

        });


    }


    public void getRestFromApi(String location){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait");
        dialog.show();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
        Call<RestApiResponse> call = apiService.getRestaruntDetails("restaurants+in+"+location,"AIzaSyDcQhKexQ60B9rW7RVYcRB0tsacFKCwudc");

            Log.e("locatooooo", "" + call.request());

            call.enqueue(new Callback<RestApiResponse>() {
                @Override
                public void onResponse(Call<RestApiResponse> call, Response<RestApiResponse> response) {
                   if(response.body().getStatus().equalsIgnoreCase("ZERO_RESULTS")) {
                       Toast.makeText(MainActivity.this, "No Restaurant found", Toast.LENGTH_SHORT).show();
                   }else {

                        Restau_res = response.body().getResults();
                       Log.d(TAG, "Number of movies received: " + Restau_res.size());

                       adapter = new RestaurantAdapter(MainActivity.this, Restau_res);
                       list.setAdapter(adapter);
                   }
                    dialog.hide();
                }

                @Override
                public void onFailure(Call<RestApiResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    dialog.hide();

                }
            });

    }

}

