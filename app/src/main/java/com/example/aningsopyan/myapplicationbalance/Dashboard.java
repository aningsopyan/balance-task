package com.example.aningsopyan.myapplicationbalance;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aningsopyan.myapplicationbalance.adapter.AdapterExpanse;
import com.example.aningsopyan.myapplicationbalance.adapter.AdapterIncome;
import com.example.aningsopyan.myapplicationbalance.database.DatabaseHelper;
import com.example.aningsopyan.myapplicationbalance.model.ModelExpanse;
import com.example.aningsopyan.myapplicationbalance.model.ModelIncome;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Fragment {
    DatabaseHelper myDB;
    RecyclerView recyclerViewExpense, recyclerViewIncome;
    View view;
    TextView income_total, expense_total, balance;
    Double valueBalance;

    public Dashboard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_dashboard, container, false);
        myDB = new DatabaseHelper(getContext());
        income_total = view.findViewById(R.id.total_income);
        expense_total = view.findViewById(R.id.total_expenses);
        balance = view.findViewById(R.id.balance);
        valueBalance = 0d;

        //show data income
        recyclerViewIncome = view.findViewById(R.id.rv_income);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(getContext()));
        showIncome();

        //show data expense
        recyclerViewExpense = view.findViewById(R.id.rv_expenses);
        recyclerViewExpense.setLayoutManager(new LinearLayoutManager(getContext()));
        showExpense();


        return view;
    }

    public void showExpense(){

        Cursor exp = myDB.get_Expenses();
        if(exp.getCount() == 0){
            Toast.makeText(getContext(), "Expense is empty", Toast.LENGTH_SHORT).show();
        }
        else{
            List<ModelExpanse> expense= new ArrayList<>();
            Double total= 0d;
            while(exp.moveToNext()){
                ModelExpanse temp= new ModelExpanse(exp.getString(1), exp.getDouble(2));
                expense.add(temp);
                total= total+exp.getDouble(2);
            }

            recyclerViewExpense.setAdapter(new AdapterExpanse(expense, R.layout.list_data, getContext()));
            expense_total.setText("Rp. "+String.valueOf(total));

            valueBalance= valueBalance-total;
            Log.i("jumlahexp",String.valueOf(expense.size()));
//            Log.i("xxx",String.valueOf(expense));
        }

        balance.setText("Rp. "+String.valueOf(valueBalance));
    }

    public void showIncome(){
        Cursor inc = myDB.get_Income();
        if(inc.getCount() == 0){
            Toast.makeText(getContext(), "Income is empty", Toast.LENGTH_SHORT).show();
        }
        else{
            List<ModelIncome> income= new ArrayList<>();
            Double total= 0d;
            while(inc.moveToNext()){
                ModelIncome temp= new ModelIncome(inc.getString(1), inc.getDouble(2));
                income.add(temp);
                total= total+inc.getDouble(2);
            }

            AdapterIncome adapter= new AdapterIncome(income, R.layout.list_data, view.getContext());
            recyclerViewIncome.setAdapter(adapter);

            income_total.setText("Rp. "+String.valueOf(total));
            valueBalance= valueBalance+total;
        }

    }

}
