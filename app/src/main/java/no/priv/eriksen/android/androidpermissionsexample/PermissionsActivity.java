package no.priv.eriksen.android.androidpermissionsexample;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PermissionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        // Example of action which requires user's permission in all versions of Android
        final Button locationButton = (Button) findViewById(R.id.updateLocationButton);
        final TextView locationView = (TextView) findViewById(R.id.locationView);
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    locationView.setText(String.format("%.2f %.2f", location.getLatitude(), location.getLongitude()));
                } else {
                    locationView.setText(R.string.unknownlocation);
                }
            }
        });


        // Example of action which requires user's permission in Android 5 and lower, but not in Android 6.
        final Button updateWifiButton = (Button) findViewById(R.id.updateWifiButton);
        final TextView wifiStateView = (TextView) findViewById(R.id.wifiNameView);
        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        updateWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String network = wifiManager.getConnectionInfo().getSSID();
                wifiStateView.setText(network);
            }
        });
    }

}
