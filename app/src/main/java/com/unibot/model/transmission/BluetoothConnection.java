package com.unibot.model.transmission;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
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
