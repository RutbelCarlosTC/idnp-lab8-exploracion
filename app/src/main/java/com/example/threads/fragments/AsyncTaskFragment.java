package com.example.threads.fragments;

import android.os.AsyncTask;
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

public class AsyncTaskFragment extends Fragment {

    private TextView tvResult;
    private Button btnStartTask;

    public AsyncTaskFragment() {
        // Required empty public constructor
    }

    public static AsyncTaskFragment newInstance(String param1, String param2) {
        AsyncTaskFragment fragment = new AsyncTaskFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_async_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        tvResult = view.findViewById(R.id.tvResult);
        btnStartTask = view.findViewById(R.id.btnStartTask);

        // Configurar el botón para iniciar la tarea
        btnStartTask.setOnClickListener(v -> {
            new CounterAsyncTask().execute(10); // Contar hasta 10
        });
    }

    // Clase interna AsyncTask
    private class CounterAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvResult.setText("Iniciando contador...");
            btnStartTask.setEnabled(false);
        }

        @Override
        protected String doInBackground(Integer... params) {
            int maxCount = params[0];
            for (int i = 1; i <= maxCount; i++) {
                try {
                    Thread.sleep(1000); // Espera 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i); // Actualiza el progreso
            }
            return "¡Tarea completada!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int count = values[0];
            tvResult.setText("Contador: " + count);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvResult.setText(result);
            btnStartTask.setEnabled(true);
        }
    }
}
