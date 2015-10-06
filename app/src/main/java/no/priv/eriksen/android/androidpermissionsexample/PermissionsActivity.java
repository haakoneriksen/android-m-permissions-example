package no.priv.eriksen.android.androidpermissionsexample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class PermissionsActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 1;
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

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                    String[] permissions = new String[1];
                    permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
                    ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST_CODE);
                } else {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                updateLocationView();
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

    @SuppressWarnings("ResourceType")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    if (permissions.length > 0 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        updateLocationView();
                    }
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void updateLocationView() {
        if (location != null) {
            locationView.setText(String.format("%.2f %.2f", location.getLatitude(), location.getLongitude()));
        } else {
            locationView.setText(R.string.unknownlocation);
        }
    }

}
