package in.needyourhelp.needyourhelp;
/*
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
*/

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private AlertDialog.Builder dialogBuilder;
    private Button btnAddress;
    private TextView address1;
    private String StrName = "Address";
    private String StrFullAddress = "Current address";

    private void AddressDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final EditText textInput = new EditText(this);
        StrName = "Address";
        dialogBuilder.setTitle("Give us your address");
        dialogBuilder.setMessage("This is will help us in knowing your location");
        dialogBuilder.setView(textInput);

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StrName += textInput.getText().toString();
                Display();
                Toast.makeText(getApplicationContext(), "We have your location", Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "We did not receive your location", Toast.LENGTH_SHORT);
            }
        });
        AlertDialog dialogAddress = dialogBuilder.create();
        dialogAddress.show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void loginMethod(View v)
    {
        Login dialog = new Login();
        dialog.show(getSupportFragmentManager(), "my_login");

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Display() {
        StrFullAddress = "Your address" + StrName;
        address1.setText(StrFullAddress);
    }

    //GoogleMap googleMap;
    //googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
}
