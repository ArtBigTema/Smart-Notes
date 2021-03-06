package av.smartnotes.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.util.List;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityController;
import av.smartnotes.substance.Node;
import av.smartnotes.substance.Priority;
import av.smartnotes.substance.controller.NodeController;
import av.smartnotes.util.Constant;
import av.smartnotes.util.MyLocationListener;
import av.smartnotes.util.Utils;

@EActivity
public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        View.OnClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker marker;
    private ImageView imageView;

    @Extra
    protected boolean viewMode;
    @Extra
    protected long id;

    private int color = Color.MAGENTA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        imageView = (ImageView) findViewById(R.id.iv_map_save);
        imageView.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!Utils.isOnline(this)) {
            new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme))
                    .setTitle(R.string.alert_dialog_title)
                    .setMessage(R.string.alert_dialog_online_map)
                    .setCancelable(true)
                    .create()
                    .show();
        }

        if (mMap != null) {
            addMarkers();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng point;
        int zoom = 12;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constant.SAMARA, zoom));

        if (id > -1) {
            Node node = NodeController.get(id);
            point = new LatLng(node.getLat(), node.getLng());
            color = Priority.values()[node.getPriority()].id();
            zoom = 15;
        } else {
            point = MyLocationListener.getImHere();

            if (point != null) {
                Utils.putDouble(this, R.string.key_lat, point.latitude);
                Utils.putDouble(this, R.string.key_lng, point.longitude);
            } else {
                double lat = Utils.getDouble(this, R.string.key_lat, Constant.SAMARA.latitude);
                double lng = Utils.getDouble(this, R.string.key_lng, Constant.SAMARA.longitude);
                point = new LatLng(lat, lng);
            }
        }
        addMarkers();

        if (viewMode) {
            imageView.setVisibility(View.GONE);
            googleMap.setOnInfoWindowClickListener(this);
        } else {
            mMap.setOnMapClickListener(this);
            onMapClick(point);
        }

        final int finalZoom = zoom;
        final LatLng finalPoint = point;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(finalPoint, finalZoom));
            }
        }, Constant.DURATION);
    }

    private void addMarkers() {
        mMap.clear();
        List<Node> nodes = NodeController.getAbsAll();
        for (Node node : nodes) {
            addMarker(node.getId(), node.getLat(), node.getLng(),
                    node.getTitle(), node.getId().equals(id),
                    Priority.values()[node.getPriority()].id());
        }
    }

    public void addMarker(Long id, double lat, double lng, String title, boolean showAlways, int color) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(title)
                .snippet(id.toString())
                .icon(getMarkerIcon(color));
        if (showAlways) {
            this.marker = mMap.addMarker(marker);
            this.marker.showInfoWindow();
        } else {
            mMap.addMarker(marker);
        }
    }

    public BitmapDescriptor getMarkerIcon(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.map_add_marker))
                .icon(getMarkerIcon(color)));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        long id = Long.valueOf(marker.getSnippet());

        if (id != this.id) {
            ActivityController.startDetailActivity(this, id, true);
        }
    }

    @Override
    public void onClick(View v) {
        if (marker != null) {
            Intent intent = new Intent();
            intent.putExtra(Constant.EXTRA_LAT, marker.getPosition().latitude);
            intent.putExtra(Constant.EXTRA_LNG, marker.getPosition().longitude);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme))
                    .setTitle(R.string.alert_dialog_title)
                    .setMessage(R.string.alert_dialog_select_map)
                    .setCancelable(true)
                    .create()
                    .show();
        }
    }
}