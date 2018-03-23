package model;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsmm.bsmm.homeworks.R;
import com.bsmm.bsmm.homeworks.ServicioNotificacion;

import java.util.ArrayList;

import control.Metodos;

public class Adapter_Homework extends RecyclerView.Adapter<Adapter_Homework.ViewHolder> implements View.OnClickListener {

    ArrayList<Homework> lista = new ArrayList<>();

    private View.OnClickListener listener;

    public Adapter_Homework(ArrayList<Homework> list) {
        this.lista = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_homework, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvDate.setText(lista.get(position).getStDate() + "  " + lista.get(position).getStTime());
        holder.tvSubjet.setText(lista.get(position).getStSubjet());
        holder.tvDescription.setText(lista.get(position).getStDesc());
        int[] valores = new Metodos().getDiasRestantes(lista.get(position).getStDate());
        String cad = null;
        boolean esMañana = false;
        boolean esHoy = false;
        if (valores != null) {
            esHoy = valores[0] == valores[1] && valores[2] == 0;
            esMañana = valores[0] == 1 && valores[1] == 0 && valores[2] == 0;
            if (esHoy) {
                cad = "";
            } else if (esMañana) {
                valores = new Metodos().getHorasOfManiana(lista.get(position).getStTime());
                if (valores != null) {
                    cad = "Dispone de " + valores[0] + " horas y " + valores[1] + " minutos";
                }
            } else {
                cad = "Tiempo restante: " + valores[0] + " dias, " + valores[1] + " meses, " + valores[2] + " años";
            }
        } else {
            cad = null;
        }

        if (cad == null) {
            holder.tvSmsfechaEntrega.setText("!Fecha caducada!");
            holder.tvSmsfechaEntrega.setTextColor(Color.RED);
        } else if (cad.isEmpty()) {

            if (esHoy) {
                valores = new Metodos().getHorasRestantes(lista.get(position).getStTime());
                if (valores == null) {
                    holder.tvSmsfechaEntrega.setText("¡Hora caducada!");
                    holder.tvSmsfechaEntrega.setTextColor(Color.RED);
                } else {
                    cad = "Dispone de " + valores[0] + " horas y " + valores[1] + " minutos";
                    holder.tvSmsfechaEntrega.setText(cad);
                }
            } else {
                holder.tvSmsfechaEntrega.setText(cad);
            }
        } else {
            holder.tvSmsfechaEntrega.setText(cad);
        }
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

        private TextView tvDate;
        private TextView tvDescription;
        private TextView tvSubjet;
        private TextView tvSmsfechaEntrega;


        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDescription = itemView.findViewById(R.id.tvDescriptionDetalle);
            tvSubjet = itemView.findViewById(R.id.tvSubjetName);
            tvSmsfechaEntrega = itemView.findViewById(R.id.tvDias_item_homework);

        }
    }

}


