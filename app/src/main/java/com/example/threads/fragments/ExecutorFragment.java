package com.example.threads.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.threads.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorFragment extends Fragment {

    private TextView tvExecutorResult;
    private Button btnStartExecutor;
    private ExecutorService executorService;

    public ExecutorFragment() {
        // Required empty public constructor
    }

    public static ExecutorFragment newInstance(String param1, String param2) {
        ExecutorFragment fragment = new ExecutorFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_executor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        tvExecutorResult = view.findViewById(R.id.tvExecutorResult);
        btnStartExecutor = view.findViewById(R.id.btnStartExecutor);

        // Inicializar ExecutorService con un pool fijo de 2 threads
        executorService = Executors.newFixedThreadPool(2);

        // Configurar el botón para iniciar la tarea
        btnStartExecutor.setOnClickListener(v -> startTask());
    }

    private void startTask() {
        tvExecutorResult.setText("Ejecutando tareas...");

        // Tarea 1
        executorService.submit(() -> {
            System.out.println("Tarea 1 ejecutada en: " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000); // Simula trabajo en hilo 1
                updateUI("Tarea 1 completada");
            } catch (InterruptedException e) {
                e.printStackTrace();
                updateUI("Error en Tarea 1");
            }
        });

        // Tarea 2
        executorService.submit(() -> {
            System.out.println("Tarea 2 ejecutada en: " + Thread.currentThread().getName());

            try {
                Thread.sleep(5000); // Simula trabajo en hilo 2
                updateUI("Tarea 2 completada");
            } catch (InterruptedException e) {
                e.printStackTrace();
                updateUI("Error en Tarea 2");
            }
        });
    }

    private void updateUI(String result) {
        // Volver al hilo principal para actualizar la UI
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> tvExecutorResult.setText(result));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Apagar el ExecutorService al destruir el fragmento
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
