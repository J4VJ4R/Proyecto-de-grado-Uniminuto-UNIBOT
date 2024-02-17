package com.example.unibot;

// ConexionBluetooth.java

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.UUID;

public class ConexionBluetooth extends AsyncTask<Void, Void, Void> {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;

    @Override
    protected Void doInBackground(Void... params) {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothDevice = bluetoothAdapter.getRemoteDevice("90:0f:0c:b5:ff:bf"); // Reemplaza con la dirección MAC de tu portátil
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // UUID estándar para el perfil SPP (Serial Port Profile)
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();

            // Ahora puedes enviar datos a través de bluetooth usando el flujo de salida de bluetoothSocket.getOutputStream()

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

