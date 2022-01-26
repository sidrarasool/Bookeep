package com.example.bookeep;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProjectsFragment extends Fragment {

    RecyclerView rv;
    FirebaseRecyclerAdapter adapter;

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://bookeep-1be5b-default-rtdb.firebaseio.com/");
    private DatabaseReference databaseReference = firebaseDatabase.getReference("Projects");

    public ViewProjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);

        rv = rootView.findViewById(R.id.rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(false);

        populate();

        return rootView;
    }

    private void populate(){
        Query query = databaseReference;

        FirebaseRecyclerOptions<Project> options = new FirebaseRecyclerOptions.Builder<Project>().setQuery(query, Project.class).build();

        adapter = new FirebaseRecyclerAdapter<Project, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_projects, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull final Project project) {
                viewHolder.SetData(project.name, project.clientName, project.description, project.cost, project.startedOn,
                        (project.completedOn.isEmpty()) ? "" : project.completedOn, project.developers, project.currency );


                viewHolder.btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        project.description = viewHolder.et_desc.getText().toString();
                        project.cost = viewHolder.et_cost.getText().toString();

                        String dev = viewHolder.et_devs.getText().toString();
                        if(!dev.isEmpty()){
                            ArrayList<String> devs = new ArrayList<>();

                            Collections.addAll(devs, dev.split(", "));

                            project.setDevelopers(devs);
                        }
                        else{
                            project.setDevelopers(null);
                        }

                        FirebaseController firebaseController = new FirebaseController();

                        firebaseController.UpdateProject(project);
                    }
                });

                viewHolder.btn_completed.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        project.setCompletedOn(LocalDate.now().toString());

                        FirebaseController firebaseController = new FirebaseController();
                        firebaseController.projectComplete(project);

                        Toast.makeText(getContext(), "Project marked complete", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        };

        rv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        rv.setAdapter(null);
        rv = null;
        databaseReference = null;
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_client, tv_started, tv_completed, tag_complete, tv_currency;
        EditText et_desc, et_cost, et_devs;
        Button btn_update, btn_completed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_project_name);
            tv_client = itemView.findViewById(R.id.tv_project_cn);
            tv_started = itemView.findViewById(R.id.tv_project_started);
            tv_completed = itemView.findViewById(R.id.tv_project_completed);
            tv_currency = itemView.findViewById(R.id.tv_project_currency);
            tag_complete = itemView.findViewById(R.id.tag_proj_compelete);

            et_cost = itemView.findViewById(R.id.tv_project_cost);
            et_desc = itemView.findViewById(R.id.tv_project_desc);
            et_devs = itemView.findViewById(R.id.et_project_devs);

            btn_update = itemView.findViewById(R.id.btn_proj_update);
            btn_completed = itemView.findViewById(R.id.btn_proj_completed);
        }

        public void SetData(String name, String client, String desc, String cost, String started, String completed, ArrayList<String> devs, String currency){
            tv_name.setText(name);
            tv_started.setText(started);
            tv_client.setText(client);
            tv_currency.setText(currency);

            et_desc.setText(desc);
            et_cost.setText(cost);

            if(completed.isEmpty()){
                tag_complete.setVisibility(View.INVISIBLE);
                tv_completed.setVisibility(View.INVISIBLE);
            }
            else{
                tv_completed.setText(completed);
            }

            try{
                et_devs.setText(devs.get(0));

                for(int i = 1; i < devs.size(); i++){
                    et_devs.append(", " + devs.get(i));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static ViewProjectsFragment newInstance(){ return new ViewProjectsFragment(); }
}

