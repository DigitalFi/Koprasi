package com.project.rezasaputra.koprasi.Activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.rezasaputra.koprasi.R;

import java.util.List;

/**
 * Created by Muhammad on 6/5/2018.
 */

public class KoperasiAdapter extends RecyclerView.Adapter<KoperasiAdapter.ViewHolder> {

    private Context context;
    private List<Koperasi> list;

    public KoperasiAdapter(Context context, List<Koperasi> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Koperasi kop = list.get(position);

        holder.textTitle.setText(kop.getNama());
        holder.textRating.setText(String.valueOf(kop.getNoBadan()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle, textRating, textYear;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.main_title);
            textRating = itemView.findViewById(R.id.main_nobadan);
        }
    }
}

