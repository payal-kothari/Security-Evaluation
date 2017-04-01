package payal.Security_Evaluation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *  Author:  Payal Kothari
 */

public class MainActivity extends Activity {

    private final static String BLUETOOTH = "Bluetooth";
    private final static String OS_VERSION = "OS Version";
    private final static String WIFI = "Wifi";
    private final static String PASSWORD = "Password";
    private final static String GPS = "GPS";
    private final static String THIRD_PARTY_APP = "Third Party App";
    private final static String TEN = "10";
    private final static String FIVE = "5";
    private final static String HIGH_RISK = "High Risk";
    private final static String MODERATE_RISK = "Moderate Risk";
    private final static String LOW_RISK = "Low Risk";
    private Context context;
    private ListView listview;
    private String[] sFactors = { BLUETOOTH, OS_VERSION, WIFI, PASSWORD, GPS, THIRD_PARTY_APP };
    private static HashMap<String, String> scoresMap = new HashMap<>();
    private static ArrayList<String> scores = new ArrayList<>();
    private TextView risk;
    private CheckSetting checkSetting;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context=this;
        listview=(ListView)findViewById(R.id.listv);
        risk = (TextView) findViewById(R.id.total_risk);
        initMap();
        checkSettings();
        checkSetting = new CheckSetting(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSettings();
        calculateRisk();
    }

    public void calculateRisk() {
        int total = 0;
        for(int i = 0; i< scores.size(); i++){
            int temp = Integer.parseInt(scores.get(i));
            total = total + temp;
        }

        if(total < 40){
            risk.setText(HIGH_RISK);
            risk.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }else if(total > 50){
            risk.setText(LOW_RISK);
            risk.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }else {
            risk.setText(MODERATE_RISK);
            risk.setBackgroundColor(ContextCompat.getColor(context, R.color.blue));
        }
        risk.setTextColor(ContextCompat.getColor(context, R.color.white));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        checkSettings();
        calculateRisk();
    }

    private void updateScores() {
        scores.clear();
        for(int i =0; i< sFactors.length;i++){
            String factorName = sFactors[i];
            scores.add(scoresMap.get(factorName));
        }

        Adapter adapter = new Adapter(MainActivity.this,R.layout.row,R.id.sFactor, sFactors, scores);
        listview.setAdapter(adapter);
    }


    private void initMap() {
        scoresMap.put(BLUETOOTH, TEN);
        scoresMap.put(OS_VERSION, TEN);
        scoresMap.put(WIFI, TEN);
        scoresMap.put(PASSWORD, TEN);
        scoresMap.put(GPS, TEN);
        scoresMap.put(THIRD_PARTY_APP, TEN);
    }

    private void checkSettings(){
        checkSetting = new CheckSetting(this);
        if(checkSetting.isBluetoothOn()){
            scoresMap.put(BLUETOOTH, FIVE);
        }else {
            scoresMap.put(BLUETOOTH, TEN);
        }

        if(checkSetting.isWifiOn()){
            scoresMap.put(WIFI, FIVE);
        }else {
            scoresMap.put(WIFI, TEN);
        }

        if(checkSetting.isPasswordSet()){
            scoresMap.put(PASSWORD, TEN);
        }else {
            scoresMap.put(PASSWORD, FIVE);
        }

        if(checkSetting.OSVersion()){
            scoresMap.put(OS_VERSION, TEN);
        }else {
            scoresMap.put(OS_VERSION, FIVE);
        }

        if(checkSetting.isGPSOn()) {
            scoresMap.put(GPS, FIVE);
        } else {
            scoresMap.put(GPS, TEN);
        }

        if (checkSetting.isThirdPartyAppAllowed()) {
            scoresMap.put(THIRD_PARTY_APP, FIVE);
        } else {
            scoresMap.put(THIRD_PARTY_APP, TEN);
        }

        updateScores();
    }
}