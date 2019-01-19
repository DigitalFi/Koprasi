package com.project.rezasaputra.koprasi.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.rezasaputra.koprasi.R;

import java.util.List;

/**
 * Created by Muhammad on 1/6/2019.
 */

public class BisnisAdapter extends RecyclerView.Adapter<BisnisAdapter.ViewHolder> {
    private Context context;
    private List<Bisnis> list;

    public BisnisAdapter(Context context, List<Bisnis> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public BisnisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_layout_bisnis, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BisnisAdapter.ViewHolder holder, final int position) {
        Bisnis bisnis = list.get(position);
        holder.txtBisnis.setText(bisnis.getNm_kop());
        holder.txtUsaha.setText(bisnis.getNo_Bdn_kop());
        holder.txtStatus.setText(bisnis.getStatus_kop());
        holder.txtTgl.setText(bisnis.getTgl_kop());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = list.get(position).getNm_kop();
                String nomor = list.get(position).getNo_Bdn_kop();
                String status = list.get(position).getStatus_kop();
                String tanggal = list.get(position).getTgl_kop();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtBisnis, txtUsaha, txtTgl, txtStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            txtBisnis = itemView.findViewById(R.id.nama);
            txtUsaha = itemView.findViewById(R.id.badan_hukum);
            txtStatus = itemView.findViewById(R.id.status);
            txtTgl = itemView.findViewById(R.id.tanggal);
        }
    }
}
