package com.example.b.b_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SecondeActivity extends AppCompatActivity {

    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);

        IntentFilter inf = new IntentFilter(MATCHES_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new MatchUpdate(),inf);

        rv = (RecyclerView) findViewById(R.id.rv_match);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        rv.setAdapter(new MatchesAdapter());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.toast:
                Toast.makeText(getApplicationContext(),"Menu!",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public JSONArray getMatchesFromFile(){

        try{
            InputStream is = new FileInputStream(getCacheDir()+"/" + "matches.json");
            byte[] buffer = new byte [is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8")); 
        }catch(IOException e){
            e.printStackTrace();
            return new JSONArray();
        }catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private class MatchesAdapter extends RecyclerView.Adapter< MatchesAdapter.MatchHolder>{

        public MatchesAdapter(){

        }
        @Override
        public MatchesAdapter.MatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_seconde, null);
            MatchHolder bh = new MatchHolder(layoutView);
            return bh;
        }

        @Override
        public void onBindViewHolder( MatchesAdapter.MatchHolder holder, int position) {
            JSONArray matches = getMatchesFromFile();
            try{

                JSONObject jo = matches.getJSONObject(position);
                holder.modifierList(jo.getString("matchID"));
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return getMatchesFromFile().length();
        }

        public class MatchHolder extends RecyclerView.ViewHolder {
            private TextView itemView;

            public MatchHolder(View itemView) {
                super(itemView);
                this.itemView = (TextView) itemView.findViewById(R.id.rv_match_element);
            }
            public void modifierList(String s) {
                itemView.setText(s);
            }
        }
    }

    public static final String MATCHES_UPDATE = "com.octip.cours.inf4042_11.MATCHES_UPDATE";
    public class MatchUpdate extends BroadcastReceiver {
        public void onReceive(Context c, Intent i){
            Log.d("Projet Schrimpf-Kpakpabia",i.getAction());
            Toast.makeText(getApplicationContext(),"Download",Toast.LENGTH_LONG).show();
        }
    }

}
