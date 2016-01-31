package in.needyourhelp.needyourhelp;

import android.app.AlertDialog;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by amita on 1/31/16.
 */
public class Login extends DialogFragment {
    LayoutInflater inflater;
    EditText user, pass;
    View v;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInsatnceState) {
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.login, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user = (EditText) v.findViewById(R.id.username);
                pass = (EditText) v.findViewById(R.id.password);
                Toast.makeText(getActivity(),"Welcome, " + user.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }



}
