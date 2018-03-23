package com.bsmm.bsmm.homeworks;

import android.os.Bundle;
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
import model.Adapter_Homework;
import model.Homework;

public class HomeworkArchivadasFragment extends Fragment {

    public HomeworkArchivadasFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework_archivadas, container, false);
        RecyclerView rvLista;
        rvLista = view.findViewById(R.id.rvListaHomeworkArchivadas);
        rvLista.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLista.setHasFixedSize(true);

        ArrayList<Homework> lista = new ArrayList<>();

        lista.addAll(new GestorDB(getContext()).getAllHomeworksTotal());

        rvLista.setAdapter(new Adapter_Homework(lista));

        rvLista.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvLista.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

}
