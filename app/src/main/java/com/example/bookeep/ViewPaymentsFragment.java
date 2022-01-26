package com.example.bookeep;

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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPaymentsFragment extends Fragment {

    RecyclerView rv;
    FirebaseRecyclerAdapter adapter;

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://bookeep-1be5b-default-rtdb.firebaseio.com/");
    private DatabaseReference databaseReference = firebaseDatabase.getReference("Payments");

    public ViewPaymentsFragment() {
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

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Payment>().setQuery(query, Payment.class).build();

        adapter = new FirebaseRecyclerAdapter<Payment, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_payments, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull final Payment payment) {
                viewHolder.SetData(payment.name, String.valueOf(payment.amount));

                viewHolder.btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        payment.UpdatePayment(Integer.parseInt(viewHolder.et_amount.getText().toString()));

                        FirebaseController firebaseController = new FirebaseController();
                        firebaseController.UpdatePayment(payment);
                    }
                });
            }
        };

        rv.setAdapter(adapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_to, et_amount;
        public Button btn_add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_to = itemView.findViewById(R.id.tv_pay_to);

            et_amount = itemView.findViewById(R.id.et_pay_amount);

            btn_add = itemView.findViewById(R.id.btn_pay_add);
        }

        public void SetData(String to, String amount){
            et_amount.setText(amount);
            tv_to.setText(to);
        }
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

    public static ViewPaymentsFragment newInstance(){ return new ViewPaymentsFragment(); }
}

