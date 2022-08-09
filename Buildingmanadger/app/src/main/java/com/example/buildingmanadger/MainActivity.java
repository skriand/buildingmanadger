package com.example.buildingmanadger;

import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
//import android.widget.TableLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import android.widget.ImageView;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter;
import androidx.window.layout.DisplayFeature;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.WindowInfoTracker;
import androidx.window.layout.WindowLayoutInfo;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;


public class MainActivity extends AppCompatActivity
        implements Frag0.OnFragmentInteractionListener,
        Frag1.OnFragmentInteractionListener,
        Frag2.OnFragmentInteractionListener {
    ViewPager pager;
    TabLayout tabLayout;

    Timer timer_currep = null;
    Timer timer_caprep = null;
    Timer timer_people = null;
    Timer timer_rent = null;
    Frag0 f0;
    Frag1 f1;
    Frag2 f2;

    Timer timer_transh = null;

    public PagerAdapter adapter;
    public ImageView ImageHouse;
    public ImageView[] iV = new ImageView[7];
    int iVid[] = new int[7];
    public GlobalData gd = new GlobalData();

    LayoutStateChangeCallback layoutStateChangeCallback = new LayoutStateChangeCallback();
    WindowInfoTrackerCallbackAdapter wit;
    //WindowMetrics wm;
    ConstraintLayout root;
    TextView outputText;
    WindowMetricsCalculator wmc;
    Guideline guide_l2;

    ConstraintSet constraintSet = new ConstraintSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        root = findViewById(R.id.root);
        outputText = findViewById(R.id.outputText);

        //Log.d(TAG, "onCreate callback adapter");
        wit = new WindowInfoTrackerCallbackAdapter(
                WindowInfoTracker.Companion.getOrCreate(
                        this
                )
        );
        wmc = WindowMetricsCalculator.Companion.getOrCreate();


        ImageHouse = (ImageView) findViewById(R.id.imageView5);
        setImageHouse();

        iVid[0] = R.id.iV1;
        iVid[1] = R.id.iV2;
        iVid[2] = R.id.iV3;
        iVid[3] = R.id.iV4;
        iVid[4] = R.id.iV5;
        iVid[5] = R.id.iV6;
        iVid[6] = R.id.iV7;

        for (int i = 0; i < 7; i++)
            iV[i] = (ImageView) findViewById(iVid[i]);

        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // Fragment manager чтобы добавить фрагмент в viewpager, мы передадим объект Fragment manager классу adapter.

        FragmentManager manager = getSupportFragmentManager();

        //объект PagerAdapter, передающий объект fragment manager в качестве параметра конструктора класса PagerAdapter.
        //setContentView(R.layout.fragment_frag);
        adapter = new PagerAdapter(manager);

        //устанавливаем Adapter для view pager
        pager.setAdapter(adapter);

        //устанавливаем tablayout с viewpager
        tabLayout.setupWithViewPager(pager);

        //добавление функциональность для tab и viewpager для управления друг другом при изменении страницы или выборе вкладки
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Настраиваем вкладки из adpater
        tabLayout.setTabsFromPagerAdapter(adapter);


        guide_l2 = findViewById(R.id.guideline2);


        final Button bR = (Button) findViewById(R.id.button);
        bR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bReset(v);
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();

        wit.addWindowLayoutInfoListener(
                this,
                runOnUiThreadExecutor(),
                layoutStateChangeCallback);


        timer_people = new Timer();

        timer_people.schedule(new TimerTask() {
                                  @Override
                                  public void run() {

                                      if ((gd.people < gd.Maxpeople[gd.level]) && (gd.state > 50))
                                          gd.people++;
                                      if ((gd.people > 0) && (gd.state < 50)) gd.people--;
                                      try {
                                          Thread.sleep(30000 * gd.rent);
                                      } catch (Exception e) {
                                          e.getLocalizedMessage();
                                      }

                                      runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            f0 = (Frag0) adapter.getItem(0);
                                                            if ((f0 != null) && (f0.view != null))
                                                                f0.SetGlobalData(gd);
                                                        }
                                                    }
                                      );
                                  }
                              }
                , 30000, 30000
        );

        timer_rent = new Timer();

        timer_rent.schedule(new
                                    TimerTask() {
                                        @Override
                                        public void run() {
                                            gd.money += gd.rent * gd.people;

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if ((gd.money >= gd.Maxmoney[gd.level]) && (gd.level < gd.maxlevel)
                                                            && (gd.deptCap == 0) && (gd.deptCur == 0)) {
                                                        gd.level++;
                                                        gd.numB = 0;
                                                        for (int i = 0; i < 7; i++) {
                                                            gd.idB[i] = 0;
                                                            iV[i].setImageResource(0);
                                                        }
                                                        setImageHouse();
                                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                                "Дом улучшен", Toast.LENGTH_SHORT);
                                                        toast.show();
                                                    }
                                                    if ((gd.level > 0) && (gd.money <= gd.Maxmoney[gd.level - 1])) {
                                                        gd.level--;
                                                        for (int i = 0; i < 7; i++)
                                                            iV[i].setImageResource(0);
                                                        setImageHouse();
                                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                                "Уровень понижен", Toast.LENGTH_SHORT);
                                                        toast.show();
                                                    }

                                                    f0 = (Frag0) adapter.getItem(0);
                                                    if ((f0 != null) && (f0.view != null))
                                                        f0.SetGlobalData(gd);
                                                }
                                            });
                                        }
                                    }, 60000, 60000
        );
        timer_currep = new Timer();

        timer_currep.schedule(new TimerTask() {
                                  @Override
                                  public void run() {

                                      gd.deptCur += gd.CurRep[gd.level];
                                      gd.state -= 5;
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {

                                              f2 = (Frag2) adapter.getItem(2);
                                              if ((f2 != null) && (f2.view != null))
                                                  f2.SetGlobalData(gd);
                                              if (gd.state < 0) {
                                                  Toast toast = Toast.makeText(getApplicationContext(),
                                                          "Игра закончена", Toast.LENGTH_SHORT);
                                                  toast.show();
                                                  gd.Clear(MainActivity.this);
                                                  MainActivity.this.finish();
                                              }
                                          }
                                      });
                                  }

                              }, 3 * 60000, 3 * 60000
        );

        timer_caprep = new Timer();

        timer_caprep.schedule(new TimerTask() {
                                  @Override
                                  public void run() {

                                      gd.deptCap += gd.CapRep[gd.level];
                                      gd.state -= 10;
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              f2 = (Frag2) adapter.getItem(2);
                                              if ((f2 != null) && (f2.view != null))
                                                  f2.SetGlobalData(gd);
                                              if (gd.state < 0) {
                                                  Toast toast = Toast.makeText(getApplicationContext(),
                                                          "Игра закончена", Toast.LENGTH_SHORT);
                                                  toast.show();
                                                  gd.Clear(MainActivity.this);
                                                  MainActivity.this.finish();
                                              }
                                          }
                                      });
                                  }
                              }, 6 * 60000, 6 * 60000
        );

        timer_transh = new Timer();
        timer_transh.schedule(new TimerTask() {
                                  @Override
                                  public void run() {

                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              gd.money += 1000 * (gd.level + 1);
                                              f0 = (Frag0) adapter.getItem(0);
                                              if ((f0 != null) && (f0.view != null)) f0.SetGlobalData(gd);
                                              Toast toast = Toast.makeText(getApplicationContext(),
                                                      "Городские власти выделили деньги на развитие", Toast.LENGTH_SHORT);
                                              toast.show();
                                          }
                                      });
                                  }
                              }
                , 10 * 60000, 10 * 60000
        );
    }


    @Override
    public void onPause() {
        super.onPause();
        gd.Write(MainActivity.this);
    }

    public void setAll() {
        for (int i = 0; i < 7; i++) iV[i].setImageResource(gd.idB[i]);

        f0 = (Frag0) adapter.getItem(0);
        if ((f0 != null) && (f0.view != null)) f0.SetGlobalData(gd);

        f1 = (Frag1) adapter.getItem(1);
        if ((f1 != null) && (f1.view != null)) f1.SetGlobalData(gd);

        f2 = (Frag2) adapter.getItem(2);
        if ((f2 != null) && (f2.view != null)) f2.SetGlobalData(gd);
    }

    @Override
    public void onResume() {
        super.onResume();
        gd.Read(MainActivity.this);
        setAll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wit.removeWindowLayoutInfoListener(layoutStateChangeCallback);
    }


    public void setImageHouse() {
        if (gd.state > 75) {
            //ImageHouse.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            findViewById(R.id.view).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            switch (gd.level) {
                case 0:
                    ImageHouse.setImageResource(R.drawable.dom0);
                    break;
                case 1:
                    ImageHouse.setImageResource(R.drawable.dom1);
                    break;
                case 2:
                    ImageHouse.setImageResource(R.drawable.dom2);
                    break;
                default:
                    break;
            }
        } else {
            //ImageHouse.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            findViewById(R.id.view).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            if (gd.state < 25)
                switch (gd.level) {
                    case 0:
                        ImageHouse.setImageResource(R.drawable.dom02);
                        break;
                    case 1:
                        ImageHouse.setImageResource(R.drawable.dom12);
                        break;
                    case 2:
                        ImageHouse.setImageResource(R.drawable.dom22);
                        break;
                    default:
                        break;
                }
            else {
                //ImageHouse.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                findViewById(R.id.view).setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                switch (gd.level) {
                    case 0:
                        ImageHouse.setImageResource(R.drawable.dom01);
                        break;
                    case 1:
                        ImageHouse.setImageResource(R.drawable.dom11);
                        break;
                    case 2:
                        ImageHouse.setImageResource(R.drawable.dom21);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void setB(int Item) {
        ImageView b = null;
        if (gd.numB == 7) return;
        b = iV[gd.numB];

        switch (Item) {
            case 0:
                b.setImageResource(R.drawable.flower);
                gd.idB[gd.numB] = R.drawable.flower;
                break;
            case 1:
                b.setImageResource(R.drawable.tree);
                gd.idB[gd.numB] = R.drawable.tree;
                break;
            case 2:
                b.setImageResource(R.drawable.alc);
                gd.idB[gd.numB] = R.drawable.alc;
                break;
            case 3:
                b.setImageResource(R.drawable.kid);
                gd.idB[gd.numB] = R.drawable.kid;
                break;
            default:
                break;
        }
        gd.numB++;
    }

    public void onFragmentInteraction(int item, Fragment frag) {
        switch (item) {
            case 0:
                Frag0 f0 = (Frag0) frag;
                if (f0 != null) f0.SetGlobalData(gd);
                break;
            case 1:
                Frag1 f1 = (Frag1) frag;
                if (f1 != null) f1.SetGlobalData(gd);
                break;
            case 2:
                Frag2 f2 = (Frag2) frag;
                if (f2 != null) f2.SetGlobalData(gd);
                break;
            default:
                break;

        }
    }

    public void bReset(View v) {
        gd = new GlobalData();
        setAll();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void updateLayout(WindowLayoutInfo windowLayoutInfo) {
        // WindowMetrics синхронный вызов
        /*wm = wmc.computeCurrentWindowMetrics(this);
        String currentMetrics = wm.getBounds().toString();
        wm = wmc.computeMaximumWindowMetrics(this);
        String maxMetrics = wm.getBounds().toString();
        String outMetrics = "\n\n";
        outMetrics += "Current Window Metrics " + currentMetrics;
        outMetrics += "\n";
        outMetrics += "Maximum Window Metrics " + maxMetrics;*/

        // WindowInfoRepository DisplayFeatures
        List<DisplayFeature> displayFeatures = windowLayoutInfo.getDisplayFeatures();
        if (displayFeatures.isEmpty()) {
            /*String out = getString(R.string.no_features);
            out += outMetrics;
            outputText.setText(out);*/
            return;
        } else {
            // обновление экрана
            // Log.d(TAG, "window layout contains display feature/s");
            //String finalOutMetrics = outMetrics;
            displayFeatures.forEach(displayFeature -> {
                FoldingFeature foldingFeature = (FoldingFeature) displayFeature;
                if (foldingFeature.isSeparating()) {
                    //String out = "";
                    LinearLayout myll = (LinearLayout) findViewById(R.id.frag1ll);
                    myll.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout myll1 = (LinearLayout) findViewById(R.id.frag2ll);
                    myll1.setOrientation(LinearLayout.VERTICAL);
                    if (foldingFeature.getOrientation() == FoldingFeature.Orientation.HORIZONTAL) {
                        guide_l2.setGuidelineBegin(foldingFeature.getBounds().bottom);
                        constraintSet.clone(root);
                        constraintSet.connect(R.id.linearLayout, ConstraintSet.BOTTOM, R.id.guideline2, ConstraintSet.TOP, foldingFeature.getBounds().bottom - foldingFeature.getBounds().top);
                        constraintSet.applyTo(root);
                        //out += getString(R.string.hinge_is_horizontal);
                    } else {
                        //guide_l1.setGuidelineBegin(foldingFeature.getBounds().left);
                        guide_l2.setGuidelineEnd(foldingFeature.getBounds().right);
                        constraintSet.clone(root);
                        constraintSet.create(R.id.guideline2, ConstraintSet.VERTICAL_GUIDELINE);
                        constraintSet.connect(R.id.view_pager, ConstraintSet.END, R.id.guideline2, ConstraintSet.END, 0);
                        constraintSet.connect(R.id.tab_layout, ConstraintSet.END, R.id.guideline2, ConstraintSet.END, 0);
                        constraintSet.connect(R.id.tab_layout, ConstraintSet.TOP, R.id.root, ConstraintSet.TOP, 0);
                        constraintSet.connect(R.id.linearLayout, ConstraintSet.START, R.id.guideline2, ConstraintSet.END, foldingFeature.getBounds().right - foldingFeature.getBounds().left);
                        constraintSet.connect(R.id.imageView5, ConstraintSet.START, R.id.guideline2, ConstraintSet.END, foldingFeature.getBounds().right - foldingFeature.getBounds().left);
                        constraintSet.connect(R.id.view, ConstraintSet.START, R.id.guideline2, ConstraintSet.END, foldingFeature.getBounds().right - foldingFeature.getBounds().left);
                        constraintSet.connect(R.id.linearLayout, ConstraintSet.BOTTOM, R.id.root, ConstraintSet.BOTTOM, 0);
                        //constraintSet.connect(R.id.view_pager,ConstraintSet.TOP,R.id.guideline,ConstraintSet.TOP,0);
                        constraintSet.applyTo(root);
                        //out += getString(R.string.hinge_is_vertical);
                    }
                    /*out += "\n";
                    out += "State is " + foldingFeature.getState().toString();
                    out += "\n";
                    out += "OcclusionType is " + foldingFeature.getOcclusionType().toString();
                    out += "\n";
                    out += "isSeparating is " + foldingFeature.isSeparating();
                    out += "\n";
                    out += "Bounds are " + foldingFeature.getBounds().toString();
                    out += finalOutMetrics;
                    outputText.setText(out);*/

                    return;
                }
            });
        }
    }

    class LayoutStateChangeCallback implements Consumer<WindowLayoutInfo> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void accept(WindowLayoutInfo windowLayoutInfo) {
            updateLayout(windowLayoutInfo);
        }
    }


    Executor runOnUiThreadExecutor() {
        return new MyExecutor();
    }

    class MyExecutor implements Executor {
        Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    }
}




