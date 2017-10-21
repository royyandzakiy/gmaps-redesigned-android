package com.dzakiy.royyan.ghudangmap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    private GoogleMap mMap = null;
    // keep track of selected marker
    private Marker mSelectedMarker;

    /*class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View myContentsView;

        MyInfoWindowAdapter() {

            myContentsView = getLayoutInflater().inflate(R.layout.info_content, null);

        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView title = ((TextView) myContentsView.findViewById(R.id.title));
            TextView snippet = ((TextView) myContentsView.findViewById(R.id.snippet));
            ImageView icon = ((ImageView) myContentsView.findViewById(R.id.icon));

            title.setText(marker.getTitle());
            snippet.setText(marker.getSnippet());
            icon.setImageDrawable(getResources().getDrawable(android.R.drawable.common_google_signin_btn_icon_dark_normal));


            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // gatau buat apa
            return null;
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        otherSettings();

//        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // tambahkan semua marker yang perlu
        addMakersToMap();

        // set listener click event untuk:
            // - MAP click
            // - MAP long_click
            // - Marker
            // - InfoWindow
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

//        mMap.setContentDescription("Tes untuk menunjukkan cara menutup info window jika sebelumnya" + " sedang aktif");

        // pindahkan camera ke lokasi yang ditentukan
         mMap.moveCamera(CameraUpdateFactory.newLatLng(SYDNEY));
        /*LatLngBounds bounds = new LatLngBounds.Builder()
                .include(PERTH)
                .include(SYDNEY)
                .include(BRISBANE)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));*/
    }

    private void addMakersToMap() {
        // Add a marker in Sydney, Australia, and move the camera.
        BitmapDescriptor bitmapDescriptor =
                BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_AZURE);

        mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .icon(bitmapDescriptor)
                .title("Marker in Sydney")
                .snippet("Population: 4,137,400"));
        mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .icon(bitmapDescriptor)
                .title("Marker in Perth")
                .snippet("Population: 2,547,400"));
        mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Marker in Brisbane")
                .snippet("Population: 137,400"));
    }

    private void otherSettings() {
        //mMap.setMyLocationEnabled(true);

        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        //or mMap.getUiSettings().setAllGesturesEnabled(true);

        mMap.setTrafficEnabled(true);
    }

    @Override
    public void onMapClick(final LatLng point) {
        // hilangkan marker yang aktif, jadi netral
        mSelectedMarker = null;
    }

        @Override
        public void onMapLongClick(LatLng point) {
            Marker newMarker = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .snippet(point.toString()));

            newMarker.setTitle(newMarker.getId());
        }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.equals(mSelectedMarker)) {
            // jika user men-tap ulang marker yang sudah aktif dan menunjukkan info window.
            mSelectedMarker = null; // hilangkan marker yang aktif, jadi netral
            return true; // true: artinya kamera jangan pindah ke centre dan jangan buka window
        }

        // jika tidak, jadikan marker yang di-tap sebagai marker aktif
        mSelectedMarker = marker;
        Toast.makeText(getApplicationContext(), marker.getTitle(),Toast.LENGTH_SHORT).show();

        return false; // false: artinya tidak perlu lakukan apa2
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getBaseContext(),
                "Info Window Clicked @" + marker.getId(),
                Toast.LENGTH_SHORT).show();
    }
}