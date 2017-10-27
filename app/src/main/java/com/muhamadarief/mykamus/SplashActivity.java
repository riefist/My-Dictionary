package com.muhamadarief.mykamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.muhamadarief.mykamus.entity.Dictionary;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.crypto.KeyAgreement;

public class SplashActivity extends AppCompatActivity {
    private KamusHelper kamusHelper;
    AppPreference appPreference;
    TextView txt_prepare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        appPreference = new AppPreference(this);

        txt_prepare = (TextView) findViewById(R.id.txt_prepare);
        if (!appPreference.isFirstRun()){
            txt_prepare.setText("Loading....");
        }

        new LoadEndInd().execute();
    }

    private class LoadEndInd extends AsyncTask<Void, Void, ArrayList<Dictionary>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            kamusHelper = new KamusHelper(SplashActivity.this);
        }

        @Override
        protected ArrayList<Dictionary> doInBackground(Void... voids) {
            boolean isFirstRun = appPreference.isFirstRun();
            if (isFirstRun){
                ArrayList<Dictionary> Dictionarys = preLoadRaw();
                
                kamusHelper.open();
                kamusHelper.insertTransactionEng(Dictionarys);


                ArrayList<Dictionary> dictionaries = preLoadRawIndo();
                kamusHelper.insertTransactionInd(dictionaries);
                kamusHelper.close();
                appPreference.setFirstRun(false);
            } else {
                try {
                    synchronized (this){
                        this.wait(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Dictionary> arrayList) {
            super.onPostExecute(arrayList);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    private ArrayList<Dictionary> preLoadRaw(){
        ArrayList<Dictionary> Dictionarys = new ArrayList<>();
        String line = null;
        BufferedReader bufferedReader;
        try {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.english_indonesia);

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int count = 0;
            do {
                line = bufferedReader.readLine();
                String[] splitstr = line.split("\t", 2);
                Dictionary Dictionary;
                Dictionary = new Dictionary(splitstr[0], splitstr[1]);
                Dictionarys.add(Dictionary);
                count++;
            } while (line != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Dictionarys;
    }

    private ArrayList<Dictionary> preLoadRawIndo(){
        ArrayList<Dictionary> Dictionarys = new ArrayList<>();
        String line = null;
        BufferedReader bufferedReader;
        try {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.indonesia_english);

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int count = 0;
            do {
                line = bufferedReader.readLine();
                String[] splitstr = line.split("\t", 2);
                Dictionary Dictionary;
                Dictionary = new Dictionary(splitstr[0], splitstr[1]);
                Dictionarys.add(Dictionary);
                count++;
            } while (line != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Dictionarys;
    }
}
