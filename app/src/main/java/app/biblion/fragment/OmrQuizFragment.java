package app.biblion.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.biblion.R;
import app.biblion.adpater.OmrQuizAdapter;
import app.biblion.util.Constant;


public class OmrQuizFragment extends Fragment {
    View mView;
    TextView txt_Timer;
    Button btn_Next, btn_Previous;
    RecyclerView recyclerView_Omr;
    private OmrQuizAdapter omrQuizAdapter;
    int count = 0;
    int[] num = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_omr_quiz, container, false);
        getActivity().setTitle("OMR Quiz");

        StartQuizDialog();
        init();
        clicked();
        return mView;
    }

    private void init() {

        txt_Timer = mView.findViewById(R.id.txt_timer);
        Constant.btnNext = mView.findViewById(R.id.btn_next);
        btn_Previous = mView.findViewById(R.id.btn_previous);
        recyclerView_Omr = mView.findViewById(R.id.omr_recycle_view);
        recyclerView_Omr.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView_Omr.setLayoutManager(layoutManager);
        omrQuizAdapter = new OmrQuizAdapter(getActivity(), num);
        recyclerView_Omr.setHasFixedSize(true);
        omrQuizAdapter.notifyDataSetChanged();
        recyclerView_Omr.setAdapter(omrQuizAdapter);
    }




    private void clicked() {
        Constant.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 11) {
                    Constant.btnNext.setEnabled(false);
                    btn_Previous.setEnabled(true);
                    Toast.makeText(getActivity(), "End of the Quiz", Toast.LENGTH_SHORT).show();
                } else {
                    btn_Previous.setEnabled(true);
                    Constant.btnNext.setEnabled(true);
                    count = ++count;
                    omrQuizAdapter.notifyDataSetChanged();
                    recyclerView_Omr.scrollToPosition(count);

                }
            }
        });

        btn_Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 11) {
                    btn_Previous.setEnabled(false);
                    Constant.btnNext.setEnabled(true);
                    Toast.makeText(getActivity(), "Cannot go back", Toast.LENGTH_SHORT).show();
                } else {
                    btn_Previous.setEnabled(true);
                    Constant.btnNext.setEnabled(true);
                    omrQuizAdapter.notifyDataSetChanged();
                    count = --count;
                    recyclerView_Omr.scrollToPosition(count);

                }
            }
        });
    }


    void Timerstart() {

        new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long sec = millisUntilFinished / 1000;
                txt_Timer.setText("" + sec);

            }

            @Override
            public void onFinish() {
                txt_Timer.setText("0");
                TimeFinishAlert();
                //recyclerView_Omr.scrollToPosition(count + 1);

            }


        }.start();

    }


    public void StartQuizDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Omr Quiz");
        alert.setMessage("In Omr Quiz session there is 5 Question. to complete quiz you have 10 seconds");
        alert.setCancelable(false);
        alert.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Timerstart();
                dialog.cancel();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragment = new QuizFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contant_frame, fragment);
                fragmentTransaction.commit();

            }


        });
        AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setLayout(600, 300);
        alertDialog.show();

    }

    private void TimeFinishAlert() {
        final AlertDialog.Builder timealert = new AlertDialog.Builder(getActivity());
        timealert.setTitle("Quiz App");
        timealert.setMessage("Time Up");
        timealert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragment = new QuizFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contant_frame, fragment);
                fragmentTransaction.commit();
            }
        });
        AlertDialog alertDialog = timealert.create();
        alertDialog.show();
    }
}