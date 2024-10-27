package com.movsesiv.capacitormocklocationcheck;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

import java.util.List;

@CapacitorPlugin(name = "MockLocationCheck", permissions = {@Permission(alias = "location", strings = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})})
public class MockLocationCheckPlugin extends Plugin {

    @PluginMethod
    public void isLocationMocked(PluginCall call) {
        // Check if location permissions are granted
        if (!hasLocationPermission()) {
            // Permissions not granted, request permissions
            requestAllPermissions(call, "permissionCallback");
            return;
        }

        // Permissions are granted, proceed to check if location is mocked
        checkIfLocationIsMocked(call);
    }

    @PermissionCallback
    private void permissionCallback(PluginCall call) {
        if (hasLocationPermission()) {
            // Permissions have been granted, proceed to check if location is mocked
            checkIfLocationIsMocked(call);
        } else {
            // Permissions not granted, reject the call
            call.reject("Location permissions not granted.");
        }
    }

    private void checkIfLocationIsMocked(PluginCall call) {
        Location location = getCurrentLocation();

        if (location == null) {
            call.reject("Location not available.");
            return;
        }

        boolean isMock = false;

        Log.i("MockLocationCheck", "Location obtained: " + location.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            isMock = location.isFromMockProvider();
            Log.i("MockLocationCheck", "isFromMockProvider: " + isMock);
        } else {
            String provider = location.getProvider();
            isMock = !provider.equals("gps") && !provider.equals("network");
            Log.i("MockLocationCheck", "Provider: " + provider + ", isMock: " + isMock);
        }

        JSObject ret = new JSObject();
        ret.put("isLocationMocked", isMock);
        call.resolve(ret);
    }

    private Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;

        for (String provider : providers) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                if (location == null) {
                    continue;
                }
                if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = location;
                }
            } catch (SecurityException e) {
                // Handle the exception if permissions are not granted
                e.printStackTrace();
            }
        }
        return bestLocation;
    }

    private boolean hasLocationPermission() {
        Context context = getContext();
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}