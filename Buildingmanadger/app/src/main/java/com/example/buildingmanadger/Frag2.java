package com.example.buildingmanadger;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;


public class Frag2 extends Fragment {
    Frag2 f;
    private OnFragmentInteractionListener mListener;
    public GlobalData gd = null;
    public TextView cur_dept;
    public TextView cap_dept;
    public View view;

    public Frag2() {
        // Обязательный пустой public конструктор
        f = this;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int item, Fragment frag);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_frag2, container, false);

        cur_dept = (TextView) view.findViewById(R.id.textView8);
        cap_dept = (TextView) view.findViewById(R.id.textView9);
        mListener.onFragmentInteraction(2, f);

        final Button buttoncur =
                (Button) view.findViewById(R.id.buttoncur);
        buttoncur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getcur(v);
            }
        });

        Button buttoncap =
                (Button) view.findViewById(R.id.buttoncap);
        buttoncap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getcap(v);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Activity a;
        if (activity instanceof Activity) {
            a = (Activity) activity;
            try {
                mListener = (OnFragmentInteractionListener) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a.toString()
                        + " must implement OnNoteClickedListener");
            }
        }
    }

    public void SetGlobalData(GlobalData GD) {
        gd = GD;
        cap_dept.setText(Integer.toString(gd.deptCap));
        cur_dept.setText(Integer.toString(gd.deptCur));
    }

    public void getcur(View view) {
        mListener.onFragmentInteraction(2, f);
        if (gd.money > gd.deptCur) {
            gd.money -= gd.deptCur;
            gd.state += (gd.deptCur / 50) * 5;
            gd.deptCur = 0;
            cur_dept.setText(Integer.toString(gd.deptCur));
        } else {
            gd.deptCur -= gd.money;
            gd.state += (gd.money / 50) * 5;
            gd.money = 0;
            cur_dept.setText(Integer.toString(gd.deptCur));
        }
        MainActivity a = (MainActivity) getActivity();
        if (a != null) {
            Frag0 f0 = (Frag0) a.adapter.getItem(0);
            if ((f0 != null) && (f0.view != null))
                f0.SetGlobalData(gd);
        }
    }

    public void getcap(View view) {
        mListener.onFragmentInteraction(2, f);
        if (gd.money > gd.deptCap) {
            gd.money -= gd.deptCap;
            gd.state += (gd.deptCap / 500) * 10;
            gd.deptCap = 0;
            cap_dept.setText(Integer.toString(gd.deptCap));
        } else {
            gd.deptCap -= gd.money;
            gd.state += (gd.money / 500) * 10;
            gd.money = 0;
            cap_dept.setText(Integer.toString(gd.deptCap));
        }
        MainActivity a = (MainActivity) getActivity();
        if (a != null) {
            Frag0 f0 = (Frag0) a.adapter.getItem(0);
            if ((f0 != null) && (f0.view != null))
                f0.SetGlobalData(gd);
        }
    }
}