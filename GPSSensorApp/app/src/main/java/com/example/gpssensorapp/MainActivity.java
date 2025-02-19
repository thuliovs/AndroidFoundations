package com.example.gpssensorapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private TextView txtLatitude, txtLongitude, txtEstado, txtExplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referências para os TextViews
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);
        txtEstado = findViewById(R.id.txtEstado);
        txtExplicacao = findViewById(R.id.txtExplicacao);

        // Inicializar o LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Configurar o Listener para o GPS
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Atualizar os valores na interface
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                txtLatitude.setText(String.format("Latitude: %.6f", latitude));
                txtLongitude.setText(String.format("Longitude: %.6f", longitude));
                txtEstado.setText("Localização obtida com sucesso");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Não utilizado neste exemplo
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                txtEstado.setText("GPS Ativado");
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                txtEstado.setText("GPS Desativado");
            }
        };

        // Solicitar permissões
        verificarPermissoes();
    }

    private void verificarPermissoes() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // Solicitar ambas as permissões
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    1
            );
        } else {
            iniciarGPS();
        }
    }

    private void iniciarGPS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
            txtEstado.setText("A processar localização...");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniciarGPS();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarGPS();
            } else {
                txtEstado.setText("Permissão de localização negada.");
            }
        }
    }
}
