package com.bsmm.bsmm.homeworks;

import android.content.Intent;
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

import java.util.ArrayList;

import control.GestorDB;
import control.StringSql;
import model.Adapter_Homework;
import model.Homework;

public class HomeworkFragment extends Fragment implements View.OnClickListener {
    private ArrayList<Homework> lista = new ArrayList<>();
    private RecyclerView rvLista;
    private FloatingActionButton fbAddHomework;

    public HomeworkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homework, container, false);
        rvLista = view.findViewById(R.id.rvListaHomeworks);
        rvLista.setLayoutManager(new LinearLayoutManager(getContext()));
        fbAddHomework = view.findViewById(R.id.fbAddHomework);

        loadRecyclerView();

        return view;
    }

    private void loadRecyclerView() {
        rvLista.setHasFixedSize(true);
        GestorDB gestorDB = new GestorDB(getContext());

        lista.clear();
        lista.addAll(gestorDB.getAllHomeworks());
        Adapter_Homework adapter_homework = new Adapter_Homework(lista);
        adapter_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DetalleHomewokActivity.class);
                i.putExtra(StringSql.CLASS_ID, lista.get(rvLista.getChildAdapterPosition(v)).getInId());
                startActivity(i);
            }
        });
        rvLista.setAdapter(adapter_homework);

        rvLista.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvLista.setItemAnimator(new DefaultItemAnimator());
        fbAddHomework.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == fbAddHomework) {
            Intent i = new Intent(getContext(), HomeworkNew.class);
            startActivity(i);
        }
    }
}
