package com.bsmm.bsmm.homeworks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;

import control.GestorDB;
import model.Adapter_Subjet;
import model.Subjet;

public class SubjectFragment extends Fragment implements View.OnClickListener {
    private ArrayList<Subjet> lista = new ArrayList<>();
    private Adapter_Subjet adapter_subjet;
    private GestorDB gestorDB;
    private RecyclerView rvLista;
    private FloatingActionButton fbAddSubjet;

    public SubjectFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject, container, false);
        rvLista = view.findViewById(R.id.rvListaSubjet);
        rvLista.setLayoutManager(new LinearLayoutManager(getContext()));
        fbAddSubjet = view.findViewById(R.id.fbAddSubject);
        rvLista.setHasFixedSize(true);
        gestorDB = new GestorDB(getContext());
        registerForContextMenu(rvLista);
        lista.addAll(gestorDB.getAllSubject());
        adapter_subjet = new Adapter_Subjet(lista);
        adapter_subjet.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteSubjet(rvLista.getChildAdapterPosition(view));
                return false;
            }
        });

        rvLista.setAdapter(adapter_subjet);

        rvLista.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvLista.setItemAnimator(new DefaultItemAnimator());

        fbAddSubjet.setOnClickListener(this);

        return view;
    }

    private void deleteSubjet(int position) {
        final Subjet obj = lista.get(position);
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Esta seguro de eliminar: ")
                .setMessage(obj.getStName())
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        gestorDB.deleteSubject(obj);
                        lauchSMS("Asignatura eliminada...");
                        updateRecyclerView();
                    }
                })
                .setNegativeButton("No", null);
        alert.show();
    }

    private void lauchSMS(String sms) {
        Toast.makeText(getContext(), sms, Toast.LENGTH_SHORT).show();
    }

    private void updateRecyclerView() {
        lista.clear();
        lista.addAll(gestorDB.getAllSubject());
        adapter_subjet.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v == fbAddSubjet) {
            final EditText txName = new EditText(getContext());
            txName.setHint("Escriba un  nombre");
            final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Nueva asigantura")
                    .setView(txName)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String name = txName.getText().toString().trim().toUpperCase();
                            name = removeSpecialChar(name);

                            if (gestorDB.existsSubjet(name)) {
                                lauchSMS("Este nombre ya existe");
                            } else if (!name.isEmpty()) {
                                gestorDB.insertSubject(name);
                                updateRecyclerView();
                            }
                        }

                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
            alert.show();
        }
    }

    private String removeSpecialChar(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }
}
