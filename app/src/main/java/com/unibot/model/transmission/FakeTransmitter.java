package com.unibot.model.transmission;

import com.unibot.model.serialization.IInstructionSerializer;
import com.unibot.model.services.SendInstructionRequest;

public class FakeTransmitter implements IInstructionTransmitter {

    private IInstructionSerializer serializer;

    public FakeTransmitter(IInstructionSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public Runnable transmitInstruction(SendInstructionRequest request) {
        return () -> {
            try {
                byte[] data = serializer.serialize(request.getInstruction());
                // Simular una duración arbitraria.
                Thread.sleep(800);
                // Enviar los datos a algún lado...

                request.accept();
            }catch (Exception e) {
                request.reject(e);
            }
        };
    }
}
