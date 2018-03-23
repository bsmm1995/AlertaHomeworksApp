package model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bsmm.bsmm.homeworks.R;

import java.util.ArrayList;

import static com.bsmm.bsmm.homeworks.R.drawable.ic_launcher_background;

public class Adapter_Imagen extends RecyclerView.Adapter<Adapter_Imagen.ViewHolder> implements View.OnLongClickListener {

    ArrayList<Imagen> lista = new ArrayList<>();
    private View.OnLongClickListener listener;

    public Adapter_Imagen(ArrayList<Imagen> list) {
        this.lista = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_imagen, parent, false);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivFoto.setImageBitmap(lista.get(position).getImg());
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public boolean onLongClick(View v) {
        if (listener != null) {
            listener.onLongClick(v);
        }
        return false;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFotoAdapter);
        }
    }
}
