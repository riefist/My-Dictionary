package com.muhamadarief.mykamus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.muhamadarief.mykamus.entity.Dictionary;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private KamusHelper kamusHelper;
    RecyclerView recyclerView;
    ResultAdapter resultAdapter;
    ArrayList<Dictionary> mResults = new ArrayList<>();


    public static MainFragment newInstance(int page){
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final EditText edt_word = (EditText) rootView.findViewById(R.id.edt_word);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.rv_result);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        resultAdapter = new ResultAdapter(getContext());
        recyclerView.setAdapter(resultAdapter);

        final int page = getArguments().getInt("page");

        kamusHelper = new KamusHelper(getContext());
        kamusHelper.open();


        edt_word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (page == 0){
                    if (TextUtils.isEmpty(charSequence.toString().trim())){
                        if (mResults != null){
                            resultAdapter.removeList();
                        }
                    } else {
                        mResults = kamusHelper.getWordEng(charSequence.toString());
                        if (mResults != null){
                            resultAdapter.setListResult(mResults);
                            resultAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    if (TextUtils.isEmpty(charSequence.toString().trim())){
                        if (mResults != null){
                            resultAdapter.removeList();
                        }
                    } else {
                        mResults = kamusHelper.getWordInd(charSequence.toString());
                        if (mResults != null){
                            Log.d("mResult", ""+mResults.size());
                            resultAdapter.setListResult(mResults);
                            resultAdapter.notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edt_word.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if (motionEvent.getRawX() >= (edt_word.getRight()) - edt_word.getCompoundDrawables()
                            [DRAWABLE_RIGHT].getBounds().width()){
                        edt_word.setText("");
                        return true;
                    }
                }

                return false;

            }
        });

        return rootView;
    }

}
