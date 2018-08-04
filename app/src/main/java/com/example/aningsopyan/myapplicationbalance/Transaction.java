package com.example.aningsopyan.myapplicationbalance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aningsopyan.myapplicationbalance.database.DatabaseHelper;

public class Transaction extends Fragment{

    DatabaseHelper myDB;
    EditText desc_income, desc_expense, amo_income, amo_expense;
    Button addIncome, addExpense;

    public Transaction() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.layout_transaction, container, false);
        myDB = new DatabaseHelper(getContext());
        desc_income = view.findViewById(R.id.description_income);
        desc_expense = view.findViewById(R.id.description_expense);
        amo_income = view.findViewById(R.id.amount_income);
        amo_expense = view.findViewById(R.id.amount_expense);
        addIncome = view.findViewById(R.id.save_income);
        addExpense = view.findViewById(R.id.save_expense);

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cek if income null
                String description = desc_expense.getText().toString().trim();
                String amount = amo_expense.getText().toString().trim();

                if (description.equals("")){
                    desc_expense.setError("Please fill the description");
                }else if (amount.equals("")){
                    amo_expense.setError("Please fill the amount");
                }else if (description.equals("") && amount.equals("")){
                    desc_expense.setError("Please fill the description");
                    amo_expense.setError("Please fill the amount");
                }else {
                    myDB.add_expenses(description, Double.valueOf(amount));
                    Toast.makeText(getContext(),"Success add new expanse",Toast.LENGTH_SHORT).show();
                    desc_expense.setText("");
                    amo_expense.setText("");

                }
            }
        });

        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = desc_income.getText().toString().trim();
                String amount = amo_income.getText().toString().trim();

                if (description.equals("")){
                    desc_income.setError("Please fill the description");
                }else if (amount.equals("")){
                    amo_income.setError("Please fill the amount");
                }else if (description.equals("") && amount.equals("")){
                    desc_income.setError("Please fill the description");
                    amo_income.setError("Please fill the amount");
                }else {
                    myDB.add_income(description, Double.valueOf(amount));
                    Toast.makeText(getContext(),"Success add new income",Toast.LENGTH_SHORT).show();
                    desc_income.setText("");
                    amo_income.setText("");

                }
            }
        });

        return view;
    }
}
