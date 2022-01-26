package com.example.android.bookeep;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDevelopersFragment extends Fragment {

    RecyclerView rv;
    FirebaseRecyclerAdapter adapter;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://bookeep-1be5b-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference = firebaseDatabase.getReference("Developers");

    public ViewDevelopersFragment() {
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

        FirebaseRecyclerOptions<Developer> options = new FirebaseRecyclerOptions.Builder<Developer>().setQuery(query, Developer.class).build();

        adapter = new FirebaseRecyclerAdapter<Developer, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_developer, parent, false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull final Developer developer) {
                viewHolder.SetData(developer.name, developer.joined, developer.technologies);

                viewHolder.btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String techs = viewHolder.et_techs.getText().toString();

                        if(!techs.isEmpty()){
                            ArrayList<String> technologies = new ArrayList<String>();

                            Collections.addAll(technologies, techs.split(", "));

                            developer.technologies = technologies;
                        }

                        FirebaseController firebaseController = new FirebaseController();
                        firebaseController.UpdateDeveloper(developer);
                    }
                });

                viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseController firebaseController = new FirebaseController();
                        firebaseController.deleteDeveloper(developer.name);
                    }
                });
            }
        };

        rv.setAdapter(adapter);
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

    @Override
    public void onDestroyView(){
        rv.setAdapter(null);
        rv = null;
        databaseReference = null;

        super.onDestroyView();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_name, tv_joined;
        public EditText et_techs;
        public Button btn_update, btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_dev_name);
            tv_joined = itemView.findViewById(R.id.tv_dev_joined);

            et_techs = itemView.findViewById(R.id.et_dev_tech);

            btn_update = itemView.findViewById(R.id.btn_dev_update);
            btn_delete = itemView.findViewById(R.id.btn_dev_delete);
        }

        public void SetData(String name, String joined, ArrayList<String> techs){
            tv_name.setText(name);
            tv_joined.setText(joined);

            try {
                et_techs.setText(techs.get(0));

                for(int i = 1; i < techs.size(); i++){
                    et_techs.append(", " + techs.get(i));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public static ViewDevelopersFragment newInstance(){ return new ViewDevelopersFragment(); }
}
