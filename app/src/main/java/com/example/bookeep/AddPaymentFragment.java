package com.example.bookeep;

package com.example.android.bookeep;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPaymentFragment extends Fragment {
    private EditText et_to, et_amount;
    private Button btn_add;

    private String to, amount;

    private Payment payment;

    public AddPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_add_payment, container, false);

        et_amount = RootView.findViewById(R.id.et_amount);
        et_to = RootView.findViewById(R.id.et_to);

        btn_add = RootView.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

                if(amount.isEmpty() || to.isEmpty())
                    Toast.makeText(getActivity().getBaseContext(), "All Fields Required!", Toast.LENGTH_SHORT).show();
                else{
                    FirebaseController firebaseController = new FirebaseController();
                    payment = new Payment(to, Integer.parseInt(amount));

                    firebaseController.addNewPayment(payment);

                    Toast.makeText(getActivity().getBaseContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();

                    clearFields();
                }
            }
        });

        return RootView;
    }

    private void getData(){
        amount = et_amount.getText().toString();
        to = et_to.getText().toString();

    }

    private void clearFields(){
        et_to.setText("");
        et_amount.setText("");
    }

    public static AddPaymentFragment newInstance(){
        return new AddPaymentFragment();
    }
}

