package com.unibot.model.transmission;

import com.unibot.model.services.SendInstructionRequest;

public interface IInstructionTransmitter {
    Runnable transmitInstruction(SendInstructionRequest request);
}
