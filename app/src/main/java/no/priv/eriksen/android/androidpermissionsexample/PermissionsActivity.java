package no.priv.eriksen.android.androidpermissionsexample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;

public class PermissionsActivity extends AppCompatActivity {

    private TextView locationView;
    private LocationManager locationManager;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        final Activity activity = this;

        // Example of action which requires user's permission in all versions of Android
        final Button locationButton = (Button) findViewById(R.id.updateLocationButton);
        locationView = (TextView) findViewById(R.id.locationView);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationButton.setOnClickListener(view -> RxPermissions.getInstance(activity)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                    updateLocationView();
                }));

        // Example of action which requires user's permission in Android 5 and lower, but not in Android 6.
        final Button updateWifiButton = (Button) findViewById(R.id.updateWifiButton);
        final TextView wifiStateView = (TextView) findViewById(R.id.wifiNameView);
        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        updateWifiButton.setOnClickListener(view -> {
            String network = wifiManager.getConnectionInfo().getSSID();
            wifiStateView.setText(network);
        });
    }

    private void updateLocationView() {
        if (location != null) {
            locationView.setText(String.format("%.2f %.2f", location.getLatitude(), location.getLongitude()));
        } else {
            locationView.setText(R.string.unknownlocation);
        }
    }

}
