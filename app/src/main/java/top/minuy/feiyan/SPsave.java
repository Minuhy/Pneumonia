package top.minuy.feiyan;

import android.content.Context;
import android.content.SharedPreferences;

public class SPsave {
    private SPsave(){}
    static SPsave sp;
    SharedPreferences spdata;
    final static String TAB_JSON = "tabjson";

    public static SPsave getSp() {
        if(sp==null){
            sp = new SPsave();
        }
        return sp;
    }

    public void InitSP(Context context){
        spdata = context.getSharedPreferences("feiyan",Context.MODE_PRIVATE);
    }

    public String getTabJson(){
        return spdata.getString(TAB_JSON,"");
    }

    public void setTabJson(String jsonStr){
        spdata.edit().putString(TAB_JSON,jsonStr).commit();
    }
}
