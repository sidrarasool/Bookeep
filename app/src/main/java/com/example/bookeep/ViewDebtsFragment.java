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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDebtsFragment extends Fragment {
    FirebaseController firebaseController;
    RecyclerView rv;
    FirebaseRecyclerAdapter adapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://bookeep-1be5b-default-rtdb.firebaseio.com/");
    private DatabaseReference databaseReference =firebaseDatabase.getReference("Debts");

    public ViewDebtsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);

        rv = rootView.findViewById(R.id.rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        populate();

        return rootView;
    }

    private void populate(){
        Query query = databaseReference;

        FirebaseRecyclerOptions<Debt> options = new FirebaseRecyclerOptions.Builder<Debt>().setQuery(query, Debt.class).build();

        adapter = new FirebaseRecyclerAdapter<Debt, ViewHolder>(options){
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_debts, parent, false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull final Debt debt) {
                viewHolder.setData(debt.from, debt.to, debt.amount, debt.created);

                viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        firebaseController = new FirebaseController();
                        firebaseController.DeleteDebt(debt);
                    }
                });

                viewHolder.btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        debt.amount = viewHolder.et_debt_amount.getText().toString();

                        firebaseController = new FirebaseController();
                        firebaseController.UpdateDebt(debt);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_from, tv_to, tv_created;
        public Button btn_delete, btn_update;
        public EditText et_debt_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_from = itemView.findViewById(R.id.tv_debt_from);
            tv_to = itemView.findViewById(R.id.tv_debt_to);
            et_debt_amount = itemView.findViewById(R.id.et_debt_amount);
            tv_created = itemView.findViewById(R.id.tv_debt_on);

            btn_delete = itemView.findViewById(R.id.btn_debt_delete);
            btn_update = itemView.findViewById(R.id.btn_debt_update);
        }

        public void setData(String from, String to, String amount, String created){
            tv_from.setText(from);
            tv_to.setText(to);
            et_debt_amount.setText(amount);
            tv_created.setText(created);
        }
    }

    @Override
    public void onDestroyView(){
        rv.setAdapter(null);
        rv = null;
        databaseReference = null;
        firebaseDatabase = null;

        super.onDestroyView();
    }

    public static ViewDebtsFragment newInstance(){
        return new ViewDebtsFragment();
    }
}
