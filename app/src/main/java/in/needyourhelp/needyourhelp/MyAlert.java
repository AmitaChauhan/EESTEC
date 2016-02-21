package in.needyourhelp.needyourhelp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by amita on 2/17/16.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import in.needyourhelp.needyourhelp.R;

/**
 * Created by amita on 2/17/16.
 */
public class MyAlert extends DialogFragment {
    private String StrName = "Address";
    /*private String StrName = "Address";
    private String StrFullAddress = "Current address";

    private ImageButton addBtn;
    private TextView tsk;
    AlertDialog.Builder dialogBuilder;*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter your task here");
        // builder = new AlertDialog.Builder(this);
        final EditText textInput = new EditText(getActivity());
        StrName = "Address";
        //dialogBuilder.setTitle("Enter your task);
        //  dialogBuilder.setMessage("This is will help us in knowing your location");
        builder.setView(textInput);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StrName += textInput.getText().toString();

                Toast.makeText(getActivity(), "You have entered the task", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "You have not entered any task", Toast.LENGTH_SHORT).show();
            }
        });
        // AlertDialog dialogAddress = builder.create();

        return builder.create();
    }
}

    /*@Override
    private void taskDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final EditText textInput = new EditText(this);
        StrName = "Address";
        dialogBuilder.setTitle("Enter your task);
                //  dialogBuilder.setMessage("This is will help us in knowing your location");
                dialogBuilder.setView(textInput);

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StrName += textInput.getText().toString();

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

}
*/




