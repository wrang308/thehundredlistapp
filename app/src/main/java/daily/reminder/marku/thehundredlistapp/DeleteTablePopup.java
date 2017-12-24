package daily.reminder.marku.thehundredlistapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by marku on 2017-12-19.
 */

public class DeleteTablePopup extends DialogFragment implements  View.OnClickListener{

    Button deleteTableYes, deleteTableNo;
    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.popup_window, null);
        deleteTableYes = (Button) view.findViewById(R.id.deleteTableYes);
        deleteTableNo = (Button) view.findViewById(R.id.deleteTableNo);
        deleteTableYes.setOnClickListener(this);
        deleteTableNo.setOnClickListener(this);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.deleteTableYes){
            communicator.onDialogMessage("deleteTableYes");
            System.out.println("YESBUTTON");
            dismiss();
        }
        else if(view.getId()== R.id.deleteTableNo){
            System.out.println("NOBUTTON LAWL");
            dismiss();
        }
    }
    interface Communicator{
        public void onDialogMessage(String message);
    }
}
