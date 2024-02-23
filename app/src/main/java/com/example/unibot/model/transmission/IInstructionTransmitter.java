package com.example.unibot.model.transmission;

import com.example.unibot.model.services.SendInstructionRequest;

public interface IInstructionTransmitter {
    Runnable transmitInstruction(SendInstructionRequest request);
}
