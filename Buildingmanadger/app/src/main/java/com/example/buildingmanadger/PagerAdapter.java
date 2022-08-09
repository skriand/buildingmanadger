package com.example.buildingmanadger;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {
    Frag0 frag0;
    Frag1 frag1;
    Frag2 frag2;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                if (frag0 == null)
                    frag0 = new com.example.buildingmanadger.Frag0();
                frag = frag0;
                break;
            case 1:
                if (frag1 == null)
                    frag1 = new com.example.buildingmanadger.Frag1();
                frag = frag1;
                break;
            case 2:
                if (frag2 == null)
                    frag2 = new com.example.buildingmanadger.Frag2();
                frag = frag2;
                break;
        }
        return (frag);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = "Статистика";
                break;
            case 1:
                title = "Блага";
                break;
            case 2:
                title = "Долги";
        }

        return title;
    }
}
