package com.example.unibot.model.transmission;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.unibot.model.serialization.IInstructionSerializer;
import com.example.unibot.model.services.SendInstructionRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public final class BluetoothConnection {
    private final UUID uuid;
    private final BluetoothAdapter bluetoothAdapter;
    private final String remoteDeviceMac;

    public BluetoothConnection(String remoteDeviceMac) {
        this.remoteDeviceMac = remoteDeviceMac;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // UUID estándar para el perfil SPP (Serial Port Profile)
    }

    public BluetoothSocket open() throws IOException, SecurityException {
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(remoteDeviceMac); // Reemplaza con la dirección MAC de tu portátil
        BluetoothSocket bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        bluetoothSocket.connect();

        return bluetoothSocket;

    }
}
