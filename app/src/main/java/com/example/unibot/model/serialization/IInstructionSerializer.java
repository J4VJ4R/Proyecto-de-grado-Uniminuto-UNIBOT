package com.example.unibot.model.serialization;

import com.example.unibot.model.IInstruction;

public interface IInstructionSerializer {
    byte[] serialize(IInstruction instruction);
}
