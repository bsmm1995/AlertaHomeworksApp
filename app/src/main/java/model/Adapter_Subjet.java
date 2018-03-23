package model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsmm.bsmm.homeworks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Subjet extends RecyclerView.Adapter<Adapter_Subjet.ViewHolder> implements View.OnLongClickListener {

    private ArrayList<Subjet> lista;

    private View.OnLongClickListener listener;

    public Adapter_Subjet(ArrayList<Subjet> list) {
        this.lista = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adapter_subject, parent, false);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(lista.get(position).getStName());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onLongClick(View view) {
        if(listener!=null){
            listener.onLongClick(view);
        }
        return false;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
