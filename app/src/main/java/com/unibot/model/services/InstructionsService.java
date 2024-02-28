package com.unibot.model.services;

import android.os.Handler;
import android.os.Looper;

import com.unibot.model.IInstruction;
import com.unibot.model.transmission.IInstructionTransmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InstructionsService {
    private IInstructionTransmitter transmitter;

    public InstructionsService(IInstructionTransmitter transmitter) {
        this.transmitter = transmitter;
    }

    public Promise sendInstruction(IInstruction instruction) {
        return new Promise<>((accept, reject) -> {
            try {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                SendInstructionRequest request = new SendInstructionRequest(instruction, handler, accept, reject);

                executor.execute(transmitter.transmitInstruction(request));
            } catch (Exception e) {
                reject.run(e);
            }
        });
    }
}
