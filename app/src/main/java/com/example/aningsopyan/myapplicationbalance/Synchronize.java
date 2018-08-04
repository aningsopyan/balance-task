package com.example.aningsopyan.myapplicationbalance;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aningsopyan.myapplicationbalance.database.DatabaseHelper;
import com.example.aningsopyan.myapplicationbalance.model.ModelExpanse;
import com.example.aningsopyan.myapplicationbalance.model.ModelIncome;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Synchronize extends Fragment {
    DatabaseHelper myDB;
    DatabaseReference databaseIncome;
    DatabaseReference databaseExpense;

    public Synchronize(){
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_synchronize, container, false);
//        backUp();
        return view;
    }

    public void backUp() {
        databaseExpense = FirebaseDatabase.getInstance().getReference();
        databaseExpense.setValue(null);

        databaseIncome = FirebaseDatabase.getInstance().getReference();
        databaseIncome.setValue(null);

        Log.i("Expense", String.valueOf(getExpense().size()));
        Log.i("Income", String.valueOf(getIncome().size()));

        for (ModelExpanse exp : getExpense()) {
            databaseExpense = FirebaseDatabase.getInstance().getReference("expense");

            String id = databaseExpense.push().getKey();
            //creating an Author Object
            ModelExpanse author = new ModelExpanse(exp.getDescription(), exp.getAmount());
            //Saving the Author
            databaseExpense.child(id).setValue(author);
        }

        for (ModelIncome inco : getIncome()) {
            databaseIncome = FirebaseDatabase.getInstance().getReference("income");

            String id = databaseIncome.push().getKey();
            //creating an Author Object
            ModelIncome author = new ModelIncome(inco.getDescription(), inco.getAmount());
            //Saving the Author
            databaseIncome.child(id).setValue(author);
        }
    }

    public List<ModelExpanse> getExpense() {
        List<ModelExpanse> expense = new ArrayList<>();
        Cursor exp = myDB.get_Expenses();
        if (exp.getCount() == 0) {
        } else {

            Double total = 0d;
            while (exp.moveToNext()) {
                ModelExpanse temp = new ModelExpanse(exp.getString(1), exp.getDouble(2));
                expense.add(temp);
                total = total + exp.getDouble(2);
            }
        }
        return expense;
    }

    public List<ModelIncome> getIncome() {
        List<ModelIncome> income = new ArrayList<>();
        Cursor inc = myDB.get_Income();
        if (inc.getCount() == 0) {
        } else {

            Double total = 0d;
            while (inc.moveToNext()) {
                ModelIncome temp = new ModelIncome(inc.getString(1), inc.getDouble(2));
                income.add(temp);
                total = total + inc.getDouble(2);
            }
        }

        return income;
    }
}
/*
    public void backUp(){
        databaseExpense= FirebaseDatabase.getInstance().getReference();
        databaseExpense.setValue(null);

        databaseIncome= FirebaseDatabase.getInstance().getReference();
        databaseIncome.setValue(null);

        Log.i("Expense", String.valueOf(getExpense().size()) );
        Log.i("Income", String.valueOf(getIncome().size()) );

        for (ModelExpanse exp: getExpense()){
            databaseExpense = FirebaseDatabase.getInstance().getReference("expense");

            String id = databaseExpense.push().getKey();
            //creating an Author Object
            ModelExpanse author = new ModelExpanse(exp.getDescription(), exp.getAmount());
            //Saving the Author
            databaseExpense.child(id).setValue(author);
        }

        for (ModelIncome inco: getIncome()){
            databaseIncome = FirebaseDatabase.getInstance().getReference("income");

            String id = databaseIncome.push().getKey();
            //creating an Author Object
            ModelIncome author = new ModelIncome(inco.getDescription(),inco.getAmount());
            //Saving the Author
            databaseIncome.child(id).setValue(author);
        }
    }

    public List<ModelExpanse> getExpense(){
        List<ModelExpanse> expense= new ArrayList<>();
        Cursor exp = myDB.get_Expenses();
        if(exp.getCount() == 0){
        }
        else{

            Double total= 0d;
            while(exp.moveToNext()){
                ModelExpanse temp= new ModelExpanse(exp.getString(1), exp.getDouble(2));
                expense.add(temp);
                total= total+exp.getDouble(2);
            }
        }
        return  expense;
    }

    public List<ModelIncome> getIncome(){
        List<ModelIncome> income= new ArrayList<>();
        Cursor inc = myDB.get_Income();
        if(inc.getCount() == 0){
        }
        else{

            Double total= 0d;
            while(inc.moveToNext()){
                ModelIncome temp= new ModelIncome(inc.getString(1), inc.getDouble(2));
                income.add(temp);
                total= total+inc.getDouble(2);
            }
        }
        return income;
    }
*/
