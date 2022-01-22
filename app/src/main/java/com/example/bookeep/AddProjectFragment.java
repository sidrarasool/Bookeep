package com.example.bookeep;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class AddProjectFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    EditText et_name, et_client_name, et_cost, et_desc, et_devs;
    Button btn_add;
    String name, client_name, cost, desc, currency;
    ArrayList<String> devs;
    Spinner spinner;

    public AddProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_project, container, false);

        et_name = rootView.findViewById(R.id.et_project_name);
        et_desc = rootView.findViewById(R.id.et_desc);
        et_cost = rootView.findViewById(R.id.et_cost);
        et_client_name = rootView.findViewById(R.id.et_client_name);
        et_devs = rootView.findViewById(R.id.et_developers);

        spinner = rootView.findViewById(R.id.spin_add_project);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.currencies, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btn_add = rootView.findViewById(R.id.btn_add_project);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

                if(name.isEmpty() || cost.isEmpty() || desc.isEmpty() || client_name.isEmpty() || et_devs.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getBaseContext(), "All Fields Required!", Toast.LENGTH_SHORT).show();
                }
                else{
                    clearFields();

                    Project project = new Project(name, desc, cost, client_name, LocalDate.now().toString(), "", devs, currency);

                    FirebaseController firebaseController = new FirebaseController();
                    firebaseController.addNewProject(project);

                    Toast.makeText(getActivity().getBaseContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    public static AddProjectFragment newInstance(){
        return new AddProjectFragment();
    }

    private void getData(){
        name = et_name.getText().toString();
        client_name = et_client_name.getText().toString();
        cost = et_cost.getText().toString();
        desc = et_desc.getText().toString();

        String dev = et_devs.getText().toString();

        if(!dev.isEmpty()){
            devs = new ArrayList<>();

            Collections.addAll(devs, dev.split(","));
        }
        else devs = null;

    }

    private void clearFields(){
        et_client_name.setText("");
        et_cost.setText("");
        et_desc.setText("");
        et_name.setText("");
        et_devs.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currency = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getContext(), "Select a currency!", Toast.LENGTH_LONG).show();
    }
}

