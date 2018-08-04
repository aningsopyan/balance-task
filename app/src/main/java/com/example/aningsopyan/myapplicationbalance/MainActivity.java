package com.example.aningsopyan.myapplicationbalance;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.aningsopyan.myapplicationbalance.database.DatabaseHelper;
import com.example.aningsopyan.myapplicationbalance.model.ModelExpanse;
import com.example.aningsopyan.myapplicationbalance.model.ModelIncome;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper myDB;
    DatabaseReference databaseIncome;
    DatabaseReference databaseExpense;
    RelativeLayout toast_relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast_relative = findViewById(R.id.toast_relative);
        myDB = new DatabaseHelper(this);

        loadFragmrnt(new Dashboard());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the camera action
            loadFragmrnt(new Dashboard());
        } else if (id == R.id.nav_Transaction) {
            loadFragmrnt(new Transaction());
        } else if (id == R.id.nav_sync) {
//            loadFragmrnt(new Synchronize());
            backUp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragmrnt(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.viewFragment, fragment);
        ft.commit();
    }

    public void backUp(){
        loadFragmrnt(new Synchronize());
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
            databaseExpense.child(id).setValue(author)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    loadFragmrnt(new Dashboard());
                    toast_relative.setVisibility(View.VISIBLE);
                    final Animation blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
                    toast_relative.startAnimation(blink);
                }
            });
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


}
