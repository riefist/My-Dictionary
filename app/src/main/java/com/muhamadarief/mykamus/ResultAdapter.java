package com.muhamadarief.mykamus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muhamadarief.mykamus.entity.Dictionary;

import java.util.ArrayList;

/**
 * Created by Muhamad Arief on 18/10/2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.HolderItem> {

    private Context context;
    private ArrayList<Dictionary> mResults = new ArrayList<>();


    public void setListResult(ArrayList<Dictionary> mResults) {
        this.mResults = mResults;
    }

    public ArrayList<Dictionary> getListResult() {
        return mResults;
    }

    public void removeList(){
        getListResult().clear();
        notifyDataSetChanged();
    }

    public ResultAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ResultAdapter.HolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new HolderItem(view);
    }


    @Override
    public void onBindViewHolder(ResultAdapter.HolderItem holder, int position) {
        Dictionary dictionary = getListResult().get(position);
        holder.txt_word.setText(dictionary.getWord());
        holder.txt_mean.setText(dictionary.getTranslate());
    }

    @Override
    public int getItemCount() {
        return getListResult().size();
    }

    public class HolderItem extends RecyclerView.ViewHolder {
        TextView txt_word, txt_mean;

        public HolderItem(View itemView) {
            super(itemView);
            txt_word = (TextView) itemView.findViewById(R.id.txt_word);
            txt_mean = (TextView) itemView.findViewById(R.id.txt_mean);
        }
    }
}
