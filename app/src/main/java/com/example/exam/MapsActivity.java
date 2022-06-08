package com.example.exam;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exam.Model.TiempoModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.exam.databinding.ActivityMapsBinding;

import static com.example.exam.Constants.*;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    //___________________
    Button searchButton;
    TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tw = findViewById(R.id.textView);
        searchButton = findViewById(R.id.btnSearch);
        searchButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = 41.3888;
                double lon = 2.159;
                /**
                 * LLamamos a la api
                 */
                //Llamamos a la api
                Apicall apiCall = retrofit.create(Apicall.class);
                Call<TiempoModel> call = apiCall.getWeather(lat, lon, API_KEY);
                call.enqueue(new Callback<TiempoModel>() {
                    @Override
                    public void onResponse(Call<TiempoModel> call, Response<TiempoModel> response) {
                        if (response.code()==200){
                            Log.i("INFO", "FUNCIONA");
                            Log.i("INFO", ""+response.body().getCoord().getLat());
                            Log.i("INFO", ""+response.body().getCoord().getLon());

                            Log.i("INFO", ""+response.body().getWeather().get(0).getId());
                            Log.i("INFO", ""+response.body().getWeather().get(0).getMain());
                            Log.i("INFO", ""+response.body().getWeather().get(0).getDescription());
                            Log.i("INFO", ""+response.body().getWeather().get(0).getIcon());

                            Log.i("INFO", ""+response.body().getBase());
                            Log.i("INFO", ""+response.body().getMain().getTemp());
                            Log.i("INFO", ""+response.body().getMain().getFeels_like());
                            Log.i("INFO", ""+response.body().getMain().getTemp_min());
                            Log.i("INFO", ""+response.body().getMain().getTemp_max());
                            Log.i("INFO", ""+response.body().getMain().getPressure());
                            Log.i("INFO", ""+response.body().getMain().getHumidity());

                            Log.i("INFO", ""+response.body().getVisibility());
                            Log.i("INFO", ""+response.body().getWind().getSpeed());
                            Log.i("INFO", ""+response.body().getWind().getDeg());

                            Log.i("INFO", ""+response.body().getClouds().getAll());
                            Log.i("INFO", ""+response.body().getDt());

                            Log.i("INFO", ""+response.body().getSys().getType());
                            Log.i("INFO", ""+response.body().getSys().getId());
                            Log.i("INFO", ""+response.body().getSys().getCountry());
                            Log.i("INFO", ""+response.body().getSys().getSunrise());
                            Log.i("INFO", ""+response.body().getSys().getSunset());

                            Log.i("INFO", ""+response.body().getTimezone());
                            Log.i("INFO", ""+response.body().getSys().getId());
                            Log.i("INFO", ""+response.body().getName());
                            Log.i("INFO", ""+response.body().getCod());



                            if (response.body().getName().isEmpty())
                                tw.setText("No hay nombre de ciudad");
                            else
                                tw.setText(response.body().toString());
                        }else{
                            Log.i("INFO", "codigo distinto "+ response.code());
                            tw.setText("No hay información disponible");

                        }
                    }

                    @Override
                    public void onFailure(Call<TiempoModel> call, Throwable t) {
                        Log.i("INFO", "Ha fallado");
                    }
                });
            }
        });
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng){
                double lat = latLng.latitude;
                double lon = latLng.longitude;

                LatLng latLongMaker = new LatLng(lat, lon);

                Log.d("Latitud:", "" + lat);
                Log.d("Longitud:", ""+ lon);
                //getAddress(lat, lon);
                //Pongo un marcador
                putMarker(lat, lon);
                //La cámara se acerca y cambia de sítio
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLongMaker, 6));

            }
        });
    }

    //Obten la dirección
    public void getAddress(double lat, double lng) {
        try {
            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lat, lng, 1);
            if (addresses.isEmpty()) {
                Toast.makeText(this, "No s’ha trobat informació", Toast.LENGTH_LONG).show();
            } else {
                if (addresses.size() > 0) {
                    String msg = addresses.get(0).getLocality();

                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "No Location Name Found", Toast.LENGTH_LONG).show();
            Log.d("Error", " Estoy aquí");
        }
    }


    //Poner marcadores
    public void putMarker( double lat, double lon){
        LatLng newPosition = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(newPosition));
        getAddress(lat, lon);
    }
}