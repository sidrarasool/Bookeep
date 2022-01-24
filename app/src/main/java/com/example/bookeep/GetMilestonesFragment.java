package com.example.bookeep;



import android.app.ActionBar;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class GetMilestonesFragment extends Fragment {
    private EditText et_search;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private String search="";
    private Button btn_search;

    private  FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://bookeep-1be5b-default-rtdb.firebaseio.com/");
    private DatabaseReference databaseReference = firebaseDatabase.getReference("Milestones");

    public GetMilestonesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_get_milestones, container, false);

        recyclerView = rootView.findViewById(R.id.rv_get_milestone);

        et_search = rootView.findViewById(R.id.et_get_milestone);

        btn_search = rootView.findViewById(R.id.btn_search_milestone);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = et_search.getText().toString();

                if(search.isEmpty()){
                    Toast.makeText(getActivity().getBaseContext(), "Project Name Required!", Toast.LENGTH_SHORT).show();
                }
                else populate();

                et_search.setText("");
            }
        });

        return rootView;
    }

    private void populate(){
        if(search.isEmpty()) return;

        Query query = databaseReference.child(search);

        FirebaseRecyclerOptions<Milestone> options = new FirebaseRecyclerOptions.Builder<Milestone>().setQuery(query, Milestone.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Milestone, ViewHolder>(options) {

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_milestones, parent, false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Milestone milestone) {
                viewHolder.setData(milestone.description, search, milestone.receivedOn, milestone.amount, milestone.exchangeRate);
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        if(firebaseRecyclerAdapter != null)
            firebaseRecyclerAdapter.stopListening();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_project, tv_value, tv_desc, tv_xrate, tv_received;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_desc = itemView.findViewById(R.id.tv_view_mile_desc);
            tv_project = itemView.findViewById(R.id.tv_view_mile_name);
            tv_received = itemView.findViewById(R.id.tv_view_mile_received);
            tv_value = itemView.findViewById(R.id.tv_view_mile_value);
            tv_xrate = itemView.findViewById(R.id.tv_view_mile_xrate);
        }

        public void setData(String desc, String project, String received, String value, String xrate){
            tv_desc.setText(desc);
            tv_xrate.setText(xrate);
            tv_project.setText(project);
            tv_received.setText(received);
            tv_value.setText(value);
        }
    }

    @Override
    public void onDestroyView(){
        recyclerView.setAdapter(null);
        recyclerView = null;
        databaseReference = null;
        firebaseDatabase = null;

        super.onDestroyView();
    }

    public static GetMilestonesFragment newInstance(){
        return new GetMilestonesFragment();
    }
}

