// MainActivity.java
package com.example.unibot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import com.example.unibot.model.Axis;
import com.example.unibot.model.Direction;
import com.example.unibot.model.IInstruction;
import com.example.unibot.model.RotateInstruction;
import com.example.unibot.model.serialization.IInstructionSerializer;
import com.example.unibot.model.serialization.PADVInstructionSerializer;
import com.example.unibot.model.services.InstructionsService;
import com.example.unibot.model.transmission.BluetoothConnection;
import com.example.unibot.model.transmission.BluetoothTransmitter;
import com.example.unibot.model.transmission.FakeTransmitter;
import com.example.unibot.model.transmission.IInstructionTransmitter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 2;

    private Button sendInstruction;

    private InstructionsService instructionsService;

    private IInstructionSerializer serializer;

    private IInstructionTransmitter transmitter;

    private String remoteDeviceMac = "90:0f:0c:b5:ff:bf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serializer = new PADVInstructionSerializer();

        // Usar el bluetooth transmitter cuando se implemente bien.
        transmitter = new BluetoothTransmitter(new BluetoothConnection(remoteDeviceMac), serializer);


        // transmitter = new FakeTransmitter(serializer);

        instructionsService = new InstructionsService(transmitter);

        init();
        setupBluetooth();
    }

    private void setupBluetooth(){
        // Verificar y solicitar permisos en tiempo de ejecución si es necesario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_BLUETOOTH_PERMISSION);
            } else {
                bluetoothReady();
            }
        } else {
            bluetoothReady();
        }
    }

    private void init() {
        sendInstruction = findViewById(R.id.sendInstruction);
        sendInstruction.setEnabled(false);
    }

    private void bluetoothReady() {
        sendInstruction.setEnabled(true);
        sendInstruction.setOnClickListener(event -> {
            sendInstruction.setEnabled(false);

            IInstruction rotate = new RotateInstruction(
                    (byte) 1,
                    Axis.X,
                    Direction.CLOCKWISE,
                    15.5f
            );

            instructionsService.sendInstruction(rotate).then(r -> {
                        Toast.makeText(MainActivity.this, "Instrucción enviada con éxito", Toast.LENGTH_SHORT).show();
                        sendInstruction.setEnabled(true);
                    })
                    .caught(ex -> {
                        Toast.makeText(MainActivity.this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        sendInstruction.setEnabled(true);
                    });
        });
    }

    // Manejar la respuesta de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                bluetoothReady();
            } else {
                Toast.makeText(MainActivity.this, "No se pudo obtener permisos para acceder al bluetooth", Toast.LENGTH_SHORT).show();
            }
        }
    }


}