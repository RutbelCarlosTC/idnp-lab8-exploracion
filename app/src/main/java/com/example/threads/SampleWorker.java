package com.example.threads;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SampleWorker extends Worker {

    public SampleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Obtener datos de entrada
        String task = getInputData().getString("task");

        // Simular trabajo
        try {
            Thread.sleep(3000); // Simula un retraso de 3 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }

        // Preparar datos de salida
        Data outputData = new Data.Builder()
                .putString("result", task + " Completado")
                .build();

        // Devolver el resultado con los datos de salida
        return Result.success(outputData);
    }
}
