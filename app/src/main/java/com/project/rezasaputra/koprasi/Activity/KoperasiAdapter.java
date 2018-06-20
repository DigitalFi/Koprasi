package com.project.rezasaputra.koprasi.Activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.rezasaputra.koprasi.R;

import java.util.List;

/**
 * Created by Muhammad on 6/5/2018.
 */

public class KoperasiAdapter extends RecyclerView.Adapter<KoperasiAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Koperasi item);
    }

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
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final Koperasi koperasi = list.get(position);

            holder.textNama.setText(koperasi.getNama());
            holder.textNama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // on klik ini buat pesan toastnya yhaaa  :)
                    Toast toast= Toast.makeText(context,
                            "Nama Kop : "+koperasi.getIdKop().toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textNama;
            public TextView textNoBadan;

            public ViewHolder(View itemView) {
                super(itemView);

                textNama = itemView.findViewById(R.id.main_title);
                textNoBadan = itemView.findViewById(R.id.main_nobadan);



            }
        }

    }

