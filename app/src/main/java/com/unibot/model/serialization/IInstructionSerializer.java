package com.unibot.model.serialization;

import com.unibot.model.IInstruction;

public interface IInstructionSerializer {
    byte[] serialize(IInstruction instruction);
}
