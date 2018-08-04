package com.example.aningsopyan.myapplicationbalance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aningsopyan.myapplicationbalance.R;
import com.example.aningsopyan.myapplicationbalance.model.ModelIncome;

import java.util.List;

public class AdapterIncome  extends RecyclerView.Adapter<AdapterIncome.IncomeViewHolder> {

    private List<ModelIncome> income;
    private int rowLayout;
    private Context context;

    public AdapterIncome(List<ModelIncome> income, int rowLayout, Context context) {
        this.income = income;
        this.rowLayout = rowLayout;
        this.context = context;

    }


    @Override
    public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new IncomeViewHolder(view);

    }
    @Override
    public void onBindViewHolder(IncomeViewHolder holder, final int position) {
        //Log.d("Judul",albums.get(position).getName());
        Log.i("Income","adada");

        holder.desc.setText(income.get(position).getDescription());
        holder.amount.setText("Rp. " +String.valueOf(income.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return income.size();
    }

    public static class IncomeViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout list_Layout;
        TextView desc;
        TextView amount;

        public IncomeViewHolder(View v) {
            super(v);
            list_Layout = v.findViewById(R.id.list_layout);
            desc = v.findViewById(R.id.text_desc);
            amount = v.findViewById(R.id.text_amount);
        }
    }
}