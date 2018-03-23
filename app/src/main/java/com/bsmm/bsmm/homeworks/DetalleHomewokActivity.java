package com.bsmm.bsmm.homeworks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import control.GestorDB;
import control.StringSql;
import model.Adapter_Imagen_Min;
import model.Homework;
import model.Imagen;

public class DetalleHomewokActivity extends AppCompatActivity {

    private GestorDB gestorDB;
    private Homework objHomework;
    private RecyclerView rvLista;
    ArrayList<Imagen> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_homewok);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gestorDB = new GestorDB(this);
            objHomework = gestorDB.getHomework(bundle.getInt(StringSql.CLASS_ID));
            TextView tvHora = findViewById(R.id.tvHoraDetalle);
            TextView tvDate = findViewById(R.id.tvFechaDetalle);
            TextView tvSubjet = findViewById(R.id.tvSubjetDetalle);
            TextView tvDescription = findViewById(R.id.tvDescriptionDetalle);

            lista.clear();

            lista = gestorDB.getImgOfHomework(String.valueOf(objHomework.getInId()));

            tvHora.setText(objHomework.getStTime());
            tvDate.setText(objHomework.getStDate());
            tvSubjet.setText(objHomework.getStSubjet());
            tvDescription.setText(objHomework.getStDesc());

            rvLista = findViewById(R.id.rvListaImagenMin);

            rvLista.setHasFixedSize(true);
            rvLista.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            Adapter_Imagen_Min adapter_imagen_min = new Adapter_Imagen_Min(lista);

            adapter_imagen_min.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), VerImagenActivity.class);
                    i.putExtra(StringSql.FOTO_ID, lista.get(rvLista.getChildAdapterPosition(v)).getId_Img());
                    startActivity(i);
                }
            });

            rvLista.setAdapter(adapter_imagen_min);
            rvLista.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.archivar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_archivar) {
            launchDialogConfirmArchivar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchDialogConfirmArchivar() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Esta seguro que completó la tarea?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        gestorDB.archivarHomewok(objHomework);
                        lauchSMS();
                        finish();
                    }
                })
                .setNegativeButton("No", null);
        alert.show();
    }

    private void lauchSMS() {
        Toast.makeText(this, "Clase archivada...", Toast.LENGTH_SHORT).show();
    }
}
