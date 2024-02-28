package com.unibot.model.transmission;

import android.bluetooth.BluetoothSocket;

import com.unibot.model.serialization.IInstructionSerializer;
import com.unibot.model.services.SendInstructionRequest;

import java.io.OutputStream;

public class BluetoothTransmitter implements IInstructionTransmitter {

    private final BluetoothConnection connection;
    private final IInstructionSerializer serializer;

    public BluetoothTransmitter(BluetoothConnection connection, IInstructionSerializer serializer) {
        this.connection = connection;
        this.serializer = serializer;
    }

    @Override
    public Runnable transmitInstruction(SendInstructionRequest request) {
        return () -> {
            try (BluetoothSocket bluetoothSocket = connection.open()){
                byte[] data = serializer.serialize(request.getInstruction());

                OutputStream out = bluetoothSocket.getOutputStream();
                out.write(data);
                out.flush();

                request.accept();

            } catch (Exception e) {
                request.reject(e);
            }
        };
    }
}
