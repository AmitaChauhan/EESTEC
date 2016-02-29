package in.needyourhelp.needyourhelp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class NeedYourHelp extends FirebaseLoginBaseActivity {
    private ImageButton ImgBtn;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private Firebase firebaseDbRef;
    private AuthData userAuthData = null;


    private void showToast(String msg, int length) {
        Toast.makeText(this, msg, length).show();
    }

    private void showError(String header, String message) {
        showToast(header + ": " + message, Toast.LENGTH_LONG);
    }

    private void setAddButtonEnabled(boolean enabled) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addTaskButton);
        fab.setEnabled(enabled);
    }

    private void userAuthenticated(AuthData authData) {
        userAuthData = authData;

        ActionMenuItemView loginItem = (ActionMenuItemView) findViewById(R.id.loginAction);

        if (authData != null) {
            setAddButtonEnabled(true);
            if (loginItem != null) {
                loginItem.setIcon(getResources().getDrawable(R.mipmap.logout));
            }
        } else {
            setAddButtonEnabled(false);
            if (loginItem != null) {
                loginItem.setIcon(getResources().getDrawable(R.mipmap.login));
            }
        }
    }

    @Override
    public Firebase getFirebaseRef() {
        return firebaseDbRef;
    }

    @Override
    public void onFirebaseLoginProviderError(FirebaseLoginError firebaseError) {
        // TODO: Handle error from provider
        showError("Error from login provider", firebaseError.message);
    }

    @Override
    public void onFirebaseLoginUserError(FirebaseLoginError firebaseError) {
        showError("Error while logging in", firebaseError.message);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.loginAction:
                if (userAuthData == null) {
                    // TODO: Log in user
                    showFirebaseLoginPrompt();
                } else {
                    // Log out user.
                    firebaseDbRef.unauth();
                    showToast("User logged out.", Toast.LENGTH_SHORT);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_your_help);
        setUpMapIfNeeded();
        Firebase.setAndroidContext(this);

        final NeedYourHelp self = this;

        firebaseDbRef = new Firebase("https://torrid-inferno-1020.firebaseIO.com");

        firebaseDbRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                self.userAuthenticated(authData);
            }
        });

        EditText mapAddress = (EditText) findViewById(R.id.AddressOnMap);
        mapAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    self.onSearch(v);
                    return false;
                } else {
                    return true;
                }
            }
        });
    }


    public void onSearch(View view) {
        EditText mapAddress = (EditText) findViewById(R.id.AddressOnMap);
        String location = mapAddress.getText().toString();
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(mapAddress.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
        }
        if (mMap != null) {
            setUpMap();
        }
    }


    public void showDialog(View v) {
        MyAlert myAlert = new MyAlert();
        myAlert.show(getFragmentManager(), "My Alert");
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        setEnabledAuthProvider(AuthProviderType.PASSWORD);

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
