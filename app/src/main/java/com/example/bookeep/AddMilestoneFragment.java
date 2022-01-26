package com.example.bookeep;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMilestoneFragment extends Fragment {
    private EditText et_proj_name, et_exrate, et_desc, et_value;
    private Button btn_add;

    private String name, xrate, desc, value;

    public AddMilestoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_add_milestone, container, false);

        et_proj_name = rootView.findViewById(R.id.et_add_mile_proj_name);
        et_desc = rootView.findViewById(R.id.et_add_mile_desc);
        et_exrate = rootView.findViewById(R.id.et_add_mile_xrate);
        et_value = rootView.findViewById(R.id.et_add_mile_amount);

        btn_add = rootView.findViewById(R.id.btn_add_mile_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

                if(name.isEmpty()){
                    Toast.makeText(getActivity().getBaseContext(), "Project Name Required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://bookeep-1be5b-default-rtdb.firebaseio.com/");
                DatabaseReference databaseReference = firebaseDatabase.getReference("Projects/" + name);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Milestone milestone = new Milestone(value, xrate, LocalDate.now().toString(), desc);

                            FirebaseController firebaseController = new FirebaseController();
                            firebaseController.addMilestone(milestone, name);

                            clearFields();

                            Toast.makeText(getActivity().getBaseContext(), "Milestone Added.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity().getBaseContext(), "Project Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity().getBaseContext(), "Couldn't Add Milestone!", Toast.LENGTH_SHORT).show();
                    }
                });

                firebaseDatabase = null;
                databaseReference = null;
            }
        });

        return rootView;
    }

    public static AddMilestoneFragment newInstance(){
        return new AddMilestoneFragment();
    }

    private void getData(){
        name = et_proj_name.getText().toString();
        xrate = et_exrate.getText().toString();
        desc = et_desc.getText().toString();
        value = et_value.getText().toString();
    }

    private void clearFields(){
        et_value.setText("");
        et_exrate.setText("");
        et_desc.setText("");
        et_proj_name.setText("");
    }
}

