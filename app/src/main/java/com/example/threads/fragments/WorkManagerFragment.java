package com.example.threads.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.threads.R;
import com.example.threads.SampleWorker;

public class WorkManagerFragment extends Fragment {

    private TextView tvWorkManagerResult;
    private Button btnStartWorkManager;

    public WorkManagerFragment() {
        // Required empty public constructor
    }

    public static WorkManagerFragment newInstance(String param1, String param2) {
        WorkManagerFragment fragment = new WorkManagerFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        tvWorkManagerResult = view.findViewById(R.id.tvWorkManagerResult);
        btnStartWorkManager = view.findViewById(R.id.btnStartWorkManager);

        // Configurar el botón para iniciar WorkManager
        btnStartWorkManager.setOnClickListener(v -> startWorkManagerTask());
    }

    private void startWorkManagerTask() {
        // Crear datos de entrada para el Worker
        Data inputData = new Data.Builder()
                .putString("task", "Descargando datos...")
                .build();

        // Crear una solicitud de WorkManager de una sola vez
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SampleWorker.class)
                .setInputData(inputData)
                .build();

        // Observar el estado del trabajo
        WorkManager.getInstance(requireContext())
                .getWorkInfoByIdLiveData(workRequest.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            // Obtener datos de salida
                            String result = workInfo.getOutputData().getString("result");
                            tvWorkManagerResult.setText(result);
                        }
                    }
                });

        // Enviar la solicitud al WorkManager
        WorkManager.getInstance(requireContext()).enqueue(workRequest);

        // Actualizar la UI mientras el trabajo está en proceso
        tvWorkManagerResult.setText("Tarea en progreso...");
    }
}
