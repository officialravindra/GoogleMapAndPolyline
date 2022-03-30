package in.premad.maisha_task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ArrayList markerPoints = new ArrayList();
    private ArrayList<LatLng> polyLinePoints;
    private Polyline mPolyline;
    public static LatLng pickup;
    public static LatLng drop;
    int currentPt = 0;
    Marker mMarker,mMarker2;
    private Marker marker;

    Activity activity;
    double Late,Long,lat;
    String id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    FloatingActionButton next;
    WebView webView;

    private static int REQUESTCODE_TURNON_GPS = 2000;
    LocationManager locationManager;

    Dialog alertDialog;

    private Map<Marker, String> markerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        // myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

                Toast.makeText(MapsActivity.this, "Updating Location", Toast.LENGTH_SHORT).show();

                String[] parts = value.split(",");
                Double Late = Double.valueOf(parts[0]); // 004
                Double Long = Double.valueOf(parts[1]); // 034556

                if (mMarker != null) {
                    mMarker.remove();
                }
              //  Toast.makeText(MapsActivity.this, ""+value, Toast.LENGTH_SHORT).show();
                mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Late, Long)));

                CameraPosition cameraPosition = new CameraPosition.Builder().target(mMarker.getPosition()).zoom(15.5f).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

              /*  LatLngBounds boundsIndia = new LatLngBounds(new LatLng(Late, Long), new LatLng(1.3525, 103.9447));
                int padding = 0; // offset from edges of the map in pixels
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(boundsIndia, padding);
                mMap.animateCamera(cameraUpdate);
*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MapsActivity.this, ""+error.toException(), Toast.LENGTH_SHORT).show();
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        /*Late = Double.parseDouble(String.valueOf(26.252));
        Long = Double.parseDouble(String.valueOf(26.252));
*/




            /*CameraPosition cameraPosition = new CameraPosition.Builder().target(mMarker.getPosition()).zoom(15.5f).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            LatLngBounds boundsIndia = new LatLngBounds(new LatLng(1.290270, 103.851959), new LatLng(1.3525, 103.9447));
            int padding = 0; // offset from edges of the map in pixels
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(boundsIndia, padding);
            mMap.animateCamera(cameraUpdate);*/



        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Toast.makeText(MapsActivity.this, "Your Current Location", Toast.LENGTH_SHORT).show();
                    return false;
            }
        });



    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}