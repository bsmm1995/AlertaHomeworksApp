package model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bsmm.bsmm.homeworks.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Adapter_Imagen_Min extends RecyclerView.Adapter<Adapter_Imagen_Min.ViewHolder> implements View.OnClickListener {

    ArrayList<Imagen> lista = new ArrayList<>();

    private View.OnClickListener listener;

    public Adapter_Imagen_Min(ArrayList<Imagen> list) {
        lista = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_imagen_min, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(lista.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImagenMin);
        }
    }
}
