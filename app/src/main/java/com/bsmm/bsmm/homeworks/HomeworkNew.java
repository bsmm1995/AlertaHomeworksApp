package com.bsmm.bsmm.homeworks;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import control.GestorDB;
import model.Adapter_Imagen;
import model.Homework;
import model.Imagen;
import model.Subjet;

public class HomeworkNew extends AppCompatActivity implements View.OnClickListener {

    final int REQUEST_CAMERA = 1;
    private GestorDB gestorDB;

    private FloatingActionButton fbAddFoto;
    private Spinner spinner;
    private TextView tvTime;
    private TextView tvDate;
    private RecyclerView rvFotos;
    private EditText etDescription;
    private Adapter_Imagen adapter_imagen;
    private int inItemSelected = 0;
    private ArrayList<Imagen> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_new);

        fbAddFoto = findViewById(R.id.fbAddfotoNewClass);
        spinner = findViewById(R.id.spinerSubjets);
        tvDate = findViewById(R.id.tvAddfecha);
        tvTime = findViewById(R.id.tvAddHora);
        rvFotos = findViewById(R.id.rvFotosNuevaClass);
        etDescription = findViewById(R.id.etDescription);

        etDescription.setEnabled(false);
        fbAddFoto.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        loadSpinner();
        gestorDB = new GestorDB(this);
        //////////////////////

        rvFotos.setLayoutManager(new LinearLayoutManager(this));
        rvFotos.setHasFixedSize(true);

        adapter_imagen = new Adapter_Imagen(lista);

        adapter_imagen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteSubjet(view);
                return false;
            }
        });

        rvFotos.setAdapter(adapter_imagen);

        rvFotos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvFotos.setItemAnimator(new DefaultItemAnimator());

    }

    private void deleteSubjet(final View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Quiere eliminar la foto")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        lista.remove(rvFotos.getChildAdapterPosition(view));
                        adapter_imagen.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null);
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_save) {
            if (camposCompletos()) {
                Homework objNew = new Homework();
                objNew.setStDesc(etDescription.getText().toString().trim());
                objNew.setStSubjet(String.valueOf(spinner.getItemAtPosition(inItemSelected)));
                objNew.setStDate(tvDate.getText().toString().trim());
                objNew.setStTime(tvTime.getText().toString().trim());
                objNew.setImgs(lista);
                gestorDB.insertHomework(objNew);
                this.finish();
            } else {
                lauchSMS();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void lauchSMS() {
        Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
    }

    private boolean camposCompletos() {
        return !etDescription.getText().toString().trim().isEmpty() && !tvTime.getText().toString().contentEquals("HORA") && !tvDate.getText().toString().contentEquals("FECHA") && inItemSelected != 0;
    }

    private void loadSpinner() {
        ArrayList<Subjet> objects = new GestorDB(this).getAllSubject();
        ArrayList<String> values = new ArrayList<>();
        values.add("Seleccione materia...");
        for (Subjet obj : objects) {
            values.add(obj.getStName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_subjet, values);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos > 0) {
                    inItemSelected = pos;
                    etDescription.setEnabled(true);
                } else {
                    inItemSelected = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == tvDate) {
            getFechaMaxHomework();
        } else if (v == tvTime) {
            getHoraMaxHomework();
        } else if (v == fbAddFoto) {
            makeFoto();
        }
    }

    private void makeFoto() {
        if (existsPermisoCamera()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        } else {
            getPermisosCamera();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    assert extras != null;
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Imagen img = new Imagen();
                    img.setImg(imageBitmap);
                    lista.add(img);
                    adapter_imagen.notifyDataSetChanged();
                }
                break;
        }
    }

    private boolean existsPermisoCamera() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private static final String CERO = "0";
    private static final String LIMIT = "-";

    public void getFechaMaxHomework() {

        final Calendar c = Calendar.getInstance();

        final int mes = c.get(Calendar.MONTH);
        final int dia = c.get(Calendar.DAY_OF_MONTH);
        final int anio = c.get(Calendar.YEAR);

        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                tvDate.setText(diaFormateado + LIMIT + mesFormateado + LIMIT + year);
            }

        }, anio, mes, dia);

        recogerFecha.show();
    }

    public void getHoraMaxHomework() {

        final Calendar c = Calendar.getInstance();

        final int hora = c.get(Calendar.HOUR_OF_DAY);
        final int minuto = c.get(Calendar.MINUTE);

        Toast.makeText(getBaseContext(), hora + ":" + minuto, Toast.LENGTH_SHORT).show();

        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tvTime.setText(hourOfDay + ":" + minute);
            }
        }, hora, minuto, false);

        recogerHora.show();
    }

    public void getPermisosCamera() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeFoto();
                }
                break;
        }
    }
}
