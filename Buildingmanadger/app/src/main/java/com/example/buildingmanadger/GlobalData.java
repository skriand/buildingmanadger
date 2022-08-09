package com.example.buildingmanadger;

import android.content.SharedPreferences;


public class GlobalData {
    public int maxlevel = 2;
    public int level = 0;
    public int people = 0;
    public int money = 10;
    public int rent = 1; //квартирная плата
    public int state = 100; //состояние дома
    public int happy = 100;
    public int[] CurRep = {50, 500, 2500}; //текущий ремонт
    public int[] CapRep = {500, 5000, 25000}; //капитальный ремонт
    public int deptCur = 0;
    public int deptCap = 0;
    public int[] Maxmoney = {5000, 10000, 20000};
    public int[] Maxpeople = {10, 100, 500};
    public int[] MaxRent = {5, 10, 20};
    public int numB = 0;
    public int idB[] = {0, 0, 0, 0, 0, 0, 0};

    public GlobalData() {

    }

    public void Clear(MainActivity a) {
        if (a == null) return;
        SharedPreferences settingsActivity = a.getSharedPreferences("PersonalAccount", a.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settingsActivity.edit();
        prefEditor.clear();
        prefEditor.commit();
    }

    public void Write(MainActivity a) {
        if (a == null) return;
        SharedPreferences settingsActivity = a.getSharedPreferences("PersonalAccount", a.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settingsActivity.edit();
        prefEditor.clear();
        prefEditor.putInt("maxlevel", maxlevel);
        prefEditor.putInt("level", level);
        prefEditor.putInt("people", people);
        prefEditor.putInt("money", money);
        prefEditor.putInt("rent", rent);
        prefEditor.putInt("state", state);
        prefEditor.putInt("happy", happy);
        for (int i = 0; i < 3; i++) prefEditor.putInt("CurRep" + Integer.toString(i), CurRep[i]);
        for (int i = 0; i < 3; i++) prefEditor.putInt("CapRep" + Integer.toString(i), CapRep[i]);
        prefEditor.putInt("deptCur", deptCur);
        prefEditor.putInt("deptCap", deptCap);
        for (int i = 0; i < 3; i++)
            prefEditor.putInt("Maxmoney" + Integer.toString(i), Maxmoney[i]);
        for (int i = 0; i < 3; i++)
            prefEditor.putInt("Maxpeople" + Integer.toString(i), Maxpeople[i]);
        for (int i = 0; i < 3; i++) prefEditor.putInt("MaxRent" + Integer.toString(i), MaxRent[i]);
        prefEditor.putInt("numB", numB);
        for (int i = 0; i < 7; i++) prefEditor.putInt("idB" + Integer.toString(i), idB[i]);
        prefEditor.commit();
    }

    public void Read(MainActivity a) {
        SharedPreferences settingsActivity = a.getSharedPreferences("PersonalAccount", a.MODE_PRIVATE);

        maxlevel = settingsActivity.getInt("maxlevel", maxlevel);
        level = settingsActivity.getInt("level", level);
        people = settingsActivity.getInt("people", people);
        money = settingsActivity.getInt("money", money);
        rent = settingsActivity.getInt("rent", rent);
        state = settingsActivity.getInt("state", state);
        happy = settingsActivity.getInt("happy", happy);
        for (int i = 0; i < 3; i++)
            CurRep[i] = settingsActivity.getInt("CurRep" + Integer.toString(i), CurRep[i]);
        for (int i = 0; i < 3; i++)
            CapRep[i] = settingsActivity.getInt("CapRep" + Integer.toString(i), CapRep[i]);
        deptCur = settingsActivity.getInt("deptCur", deptCur);
        deptCap = settingsActivity.getInt("deptCap", deptCap);
        for (int i = 0; i < 3; i++)
            Maxmoney[i] = settingsActivity.getInt("Maxmoney" + Integer.toString(i), Maxmoney[i]);
        for (int i = 0; i < 3; i++)
            Maxpeople[i] = settingsActivity.getInt("Maxpeople" + Integer.toString(i), Maxpeople[i]);
        for (int i = 0; i < 3; i++)
            MaxRent[i] = settingsActivity.getInt("MaxRent" + Integer.toString(i), MaxRent[i]);
        numB = settingsActivity.getInt("numB", numB);
        for (int i = 0; i < 7; i++)
            idB[i] = settingsActivity.getInt("idB" + Integer.toString(i), idB[i]);

    }
}
