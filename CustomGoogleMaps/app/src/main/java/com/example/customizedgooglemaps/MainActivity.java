package com.example.customizedgooglemaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verificar permissões
        if (!hasLocationPermission()) {
            requestLocationPermission();
        } else {
            initializeMap();
        }

        // Botão para Lisboa
        Button lisbonButton = findViewById(R.id.btn_lisbon);
        lisbonButton.setOnClickListener(v -> moveToLisbon());

        // Botão para Porto
        Button portoButton = findViewById(R.id.btn_porto);
        portoButton.setOnClickListener(v -> moveToPorto());

        // Botão para Faro
        Button faroButton = findViewById(R.id.btn_faro);
        faroButton.setOnClickListener(v -> moveToFaro());
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeMap();
            }
        }
    }

    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Aplicar o estilo do mapa a partir do map_style.json
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
            if (!success) {
                Log.e("MapsActivity", "Falha ao carregar o estilo do mapa.");
            }
        } catch (Exception e) {
            Log.e("MapsActivity", "Erro ao processar o estilo do mapa.", e);
        }
    }

    private void moveToLisbon() {
        LatLng lisbon = new LatLng(38.7169, -9.1399); // Coordenadas de Lisboa
        if (mMap != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .position(lisbon)
                    .title("Lisboa")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_small))); // Usar o pin personalizado
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lisbon, 12));
        }
    }

    private void moveToPorto() {
        LatLng porto = new LatLng(41.1579, -8.6291); // Coordenadas do Porto
        if (mMap != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .position(porto)
                    .title("Porto")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_small))); // Usar o pin personalizado
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(porto, 12));
        }
    }

    private void moveToFaro() {
        LatLng faro = new LatLng(37.0194, -7.9322); // Coordenadas de Faro
        if (mMap != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .position(faro)
                    .title("Faro")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_small))); // Usar o pin personalizado
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(faro, 12));
        }
    }

}
