package edu.scu.xli2.photonotesplus;

/**
 * Created by Xiaoyu on 6/2/16.
 */
//public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {
//    private GoogleMap mMap;
//    LatLng photoAddress;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // setContentView(R.layout.activity_maps);
//        setContentView(R.layout.map_view);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        String location = getIntent().getExtras().getString("photoLocation");
//
//
//        // set interesting locations
//        latLngOperatHouse = new LatLng(-33.8570, 151.2148);
//
//        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (connectLine != null) return;
//                connectLine = mMap.addPolyline(new PolylineOptions()
//                        .add(latLngOperatHouse, latLngGovHouse)
//                        .width(2)
//                        .color(Color.BLUE)
//                );
//            }
//        });
//
//        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (connectLine == null) return;
//                connectLine.remove();
//                connectLine = null;
//            }
//        });
//    }
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        mMap.addMarker(new MarkerOptions().position(latLngOperatHouse).title("Sydney Opera House").snippet("Sydney, AU"));
//        mMap.addMarker(new MarkerOptions().position(latLngGovHouse).title("Government House").snippet("Sydney, AU"));
//
//        LatLng midway = new LatLng((latLngOperatHouse.latitude + latLngGovHouse.latitude)/2,
//                (latLngOperatHouse.longitude+latLngGovHouse.longitude)/2);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(midway, 17.0f));
//
//        // set up UI control
//        UiSettings ui = mMap.getUiSettings();
//        ui.setAllGesturesEnabled(true);
//        ui.setCompassEnabled(true);
//        ui.setZoomControlsEnabled(true);
//    }
//}
