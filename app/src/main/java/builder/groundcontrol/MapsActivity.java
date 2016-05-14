package builder.groundcontrol;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    Boolean Is_MAP_Moveable = true; // to detect map is movable
    Button btn_draw_State;
    FrameLayout fram_map;
    Projection projection;
    double latitude,longitude;
    ArrayList<LatLng> val;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
         fram_map = (FrameLayout) findViewById(R.id.fram_map);
         btn_draw_State = (Button) findViewById(R.id.btn_draw_State);
        ((View)btn_draw_State.getParent()).setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                Log.d("Frame","Touched");
                return false;
            }
        });
        val = new ArrayList<>();
        btn_draw_State.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Is_MAP_Moveable != true) {
                    Is_MAP_Moveable = true;
                } else {
                    Is_MAP_Moveable = false;
                }
            }
        });
        fram_map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (Is_MAP_Moveable == true)
                {
                    return false;

                } else
                {

                    float x = event.getX();
                    float y = event.getY();

                    int x_co = Math.round(x);
                    int y_co = Math.round(y);

                    projection = mMap.getProjection();
                    Point x_y_points = new Point(x_co, y_co);

                    LatLng latLng = mMap.getProjection().fromScreenLocation(x_y_points);
                    latitude = latLng.latitude;

                    longitude = latLng.longitude;

                    int eventaction = event.getAction();
                    switch (eventaction)
                    {
                        case MotionEvent.ACTION_DOWN:
                            // finger touches the screen
                            val.add(new LatLng(latitude, longitude));

                        case MotionEvent.ACTION_MOVE:
                            // finger moves on the screen
                            val.add(new LatLng(latitude, longitude));

                        case MotionEvent.ACTION_UP:
                            // finger leaves the screen
                            Draw_Map();
                            break;
                    }


                }
                return true;
            }
        });
        mapFragment.getMapAsync(this);




    }
    PolylineOptions rectOptions;
    public void Draw_Map() {
        rectOptions = new PolylineOptions();
        rectOptions.addAll(val);
        rectOptions.color(Color.BLUE);
         mMap.addPolyline(rectOptions);
        //Log.d("Draw Map",polygon.toString());
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(12.9716, 77.5946);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Bangalore"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f));
    }
}
