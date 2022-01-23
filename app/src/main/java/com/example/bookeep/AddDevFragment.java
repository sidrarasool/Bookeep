package com.example.android.bookeep;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDevFragment extends Fragment {
    EditText et_name, et_technologies;
    Button btn_add;
    ArrayList<String> technologies;
    String name;

    public AddDevFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_add_dev, container, false);

        et_name = RootView.findViewById(R.id.et_name);
        et_technologies = RootView.findViewById(R.id.et_technologies);

        btn_add = RootView.findViewById(R.id.btn_add_dev);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

                if(name.isEmpty())
                    Toast.makeText(getActivity().getBaseContext(), "Name required!", Toast.LENGTH_SHORT).show();

                else{
                    clearFields();

                    Developer developer = new Developer(name, LocalDate.now().toString(), technologies);

                    FirebaseController firebaseController = new FirebaseController();
                    firebaseController.addNewDeveloper(developer);

                    Toast.makeText(getActivity().getBaseContext(), "Dev added successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  RootView;
    }

    private void getData(){
        name = et_name.getText().toString();

        String techs = et_technologies.getText().toString();

        if(!techs.isEmpty()){
            technologies = new ArrayList<String>();

            Collections.addAll(technologies, techs.split(","));
        }

        else
            technologies = new ArrayList<String>(0);
    }

    private void clearFields(){
        et_name.setText("");
        et_technologies.setText("");
    }

    public static AddDevFragment newInstance(){
        return new AddDevFragment();
    }
}