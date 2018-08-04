package com.example.aningsopyan.myapplicationbalance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aningsopyan.myapplicationbalance.R;
import com.example.aningsopyan.myapplicationbalance.model.ModelExpanse;

import java.util.List;

public class AdapterExpanse  extends RecyclerView.Adapter<AdapterExpanse.ExpensesViewHolder> {

    private List<ModelExpanse> expense;
    private int rowLayout;
    private Context context;

    public AdapterExpanse(List<ModelExpanse> expense, int rowLayout, Context context) {
        this.expense = expense;
        this.rowLayout = rowLayout;
        this.context = context;
    }


    @Override
    public ExpensesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ExpensesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpensesViewHolder holder, final int position) {
        //Log.d("Judul",albums.get(position).getName());
        holder.desc.setText(expense.get(position).getDescription());
        holder.amount.setText("Rp. "+String.valueOf(expense.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return expense.size();
    }

    public static class ExpensesViewHolder extends RecyclerView.ViewHolder{

        TextView desc;
        TextView amount;

        public ExpensesViewHolder(View v) {
            super(v);
            desc = v.findViewById(R.id.text_desc);
            amount = v.findViewById(R.id.text_amount);
        }
    }

}
