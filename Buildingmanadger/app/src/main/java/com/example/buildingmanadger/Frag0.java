package com.example.buildingmanadger;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;


public class Frag0 extends Fragment {

    private OnFragmentInteractionListener mListener;
    public TextView people_number; //количество людей XML
    public TextView money_number;
    public TextView state_number;
    public View view;
    public GlobalData gd = null;
    Frag0 f;


    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(int item, Fragment frag);
    }

    public Frag0() {
        // Обязательный пустой public конструктор
        f = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag, container, false);
        money_number = (TextView) view.findViewById(R.id.textView3);
        state_number = (TextView) view.findViewById(R.id.textView);
        people_number = (TextView) view.findViewById(R.id.textView2);
        mListener.onFragmentInteraction(0, f);

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
        money_number.setText(Integer.toString(gd.money));
        state_number.setText(Integer.toString(gd.state));
        people_number.setText(Integer.toString(gd.people) + "/" + Integer.toString(gd.Maxpeople[gd.level]));
        ImageView state_imageView = (ImageView) view.findViewById(R.id.imageView4);
        MainActivity a = (MainActivity) getActivity();
        if (a != null) a.setImageHouse();
        if (gd.state > 75) {
            state_imageView.setImageResource(R.drawable.state_1);
        } else {
            if (gd.state < 25) {
                state_imageView.setImageResource(R.drawable.state_3);
            } else {
                state_imageView.setImageResource(R.drawable.state_2);
            }
        }
    }
}



