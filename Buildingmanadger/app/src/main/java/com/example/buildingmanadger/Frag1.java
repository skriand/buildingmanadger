package com.example.buildingmanadger;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class Frag1 extends Fragment {
    View view;
    Frag1 f;
    TextView rent;
    ImageButton ibB[] = new ImageButton[4];
    int[] coastB = {10, 20, 30, 40};
    int[] idB = new int[4];
    public GlobalData gd = null;
    private OnFragmentInteractionListener mListener;

    public Frag1() {
        // Обязательный пустой public конструктор
        f = this;
    }

    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(int item, Fragment frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_frag1, container, false);
        rent = (TextView) view.findViewById(R.id.textView5);

        final ImageButton buttonplus =
                (ImageButton) view.findViewById(R.id.imageButton);
        buttonplus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                plus(v);
            }
        });

        ImageButton buttonminus =
                (ImageButton) view.findViewById(R.id.imageButton2);
        buttonminus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                minus(v);
            }
        });

        idB[0] = R.id.iB1;
        idB[1] = R.id.iB2;
        idB[2] = R.id.iB3;
        idB[3] = R.id.iB4;
        for (int i = 0; i < 4; i++) {
            ibB[i] = (ImageButton) view.findViewById(idB[i]);
            ibB[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setb(v);
                }
            });
        }

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


    public void plus(View v) {
        mListener.onFragmentInteraction(1, f);
        if (gd.rent < gd.MaxRent[gd.level]) gd.rent += 1;
        rent.setText(Integer.toString(gd.rent));
    }

    public void minus(View v) {
        mListener.onFragmentInteraction(1, f);
        if (gd.rent > 1) gd.rent -= 1;
        rent.setText(Integer.toString(gd.rent));
    }

    int findItem(int id) {
        for (int i = 0; i < 4; i++)
            if (idB[i] == id) return (i);
        return (-1);
    }

    public void setb(View v) {
        ImageButton b = (ImageButton) v;
        int id = b.getId();
        int i = findItem(id);
        if (i < 0) return;
        mListener.onFragmentInteraction(1, f);
        if (coastB[i] <= gd.money) {
            gd.money -= coastB[i];
            gd.state += coastB[i] / 2;
            MainActivity a = (MainActivity) getActivity();
            if (a != null) a.setB(i);
            Frag0 f0 = (Frag0) a.adapter.getItem(0);
            if ((f0 != null) && (f0.view != null))
                f0.SetGlobalData(gd);
        }

    }

    public void SetGlobalData(GlobalData GD) {
        gd = GD;
        rent.setText(Integer.toString(gd.rent));
    }
}
