package payal.Security_Evaluation;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *  Author:  Payal Kothari
 */

public class Adapter extends ArrayAdapter<String> {
    private final static String BLUETOOTH = "Bluetooth";
    private final static String OS_VERSION = "OS Version";
    private final static String WIFI = "Wifi";
    private final static String PASSWORD = "Password";
    private final static String GPS = "GPS";
    private final static String THIRD_PARTY_APP = "Third Party App";
    private final static String ON = "ON";
    private final static String OFF = "OFF";
    private final static String SET = "SET";
    private final static String NOT_SET = "NOT SET";
    private final static String ALLOWED = "ALLOWED";
    private final static String NOT_ALLOWED = "NOT ALLOWED";
    private int groupId;
    private String[] factorList;
    private ArrayList<String> scoreList;
    private Context context;
    private CheckSetting checkSetting;
    public Adapter(Context context, int viewGroup, int id, String[] factorList, ArrayList<String> scoreList){
        super(context,viewGroup, id, factorList);
        this.context= context;
        this.groupId = viewGroup;
        this.factorList = factorList;
        this.scoreList = scoreList;
        this.checkSetting = new CheckSetting(context);
    }

    static class ViewHolder {
        public TextView securityFactor;
        public TextView setting;
        public TextView score;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder = new ViewHolder();
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(groupId, parent, false);
            viewHolder.securityFactor = (TextView) view.findViewById(R.id.sFactor);
            viewHolder.setting = (TextView) view.findViewById(R.id.settings);
            viewHolder.score = (TextView) view.findViewById(R.id.score);
            view.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) view.getTag();
        final String factorName = factorList[position];

        if(factorName.equals(BLUETOOTH)){
            if(checkSetting.isBluetoothOn()){
                holder.setting.setText(ON);
            }
            else {
                holder.setting.setText(OFF);
            }
        }

        if(factorName.equals(WIFI)){
            if(checkSetting.isWifiOn()){
                holder.setting.setText(ON);
            }else {
                holder.setting.setText(OFF);
            }
        }

        if(factorName.equals(GPS)){
            if (checkSetting.isGPSOn()) {
                holder.setting.setText(ON);
            }else {
                holder.setting.setText(OFF);
            }

        }

        if(factorName.equals(OS_VERSION)){
            holder.setting.setText(Build.VERSION.RELEASE);
        }

        if(factorName.equals(PASSWORD)){
            if(checkSetting.isPasswordSet()){
                holder.setting.setText(SET);
            }else {
                holder.setting.setText(NOT_SET);
            }
        }

        if(factorName.equals(THIRD_PARTY_APP)){
            if (checkSetting.isThirdPartyAppAllowed()) {
                    holder.setting.setText(ALLOWED);
            } else {
                holder.setting.setText(NOT_ALLOWED);
            }
        }

        holder.securityFactor.setText(factorName);
        holder.score.setText(scoreList.get(position));
        return view;
    }

}
