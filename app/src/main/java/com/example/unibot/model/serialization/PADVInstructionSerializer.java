package com.example.unibot.model.serialization;

import com.example.unibot.model.Axis;
import com.example.unibot.model.ClawInstruction;
import com.example.unibot.model.Direction;
import com.example.unibot.model.IInstruction;
import com.example.unibot.model.RotateInstruction;

public class PADVInstructionSerializer implements IInstructionSerializer {
    @Override
    public byte[] serialize(IInstruction instruction) {
        if (instruction instanceof RotateInstruction) {
            return serializeRotateInstruction((RotateInstruction) instruction);
        }

        if (instruction instanceof ClawInstruction) {
            return serializeClawInstruction((ClawInstruction) instruction);
        }

        throw new IllegalArgumentException("The given instruction type: " + instruction.getClass().getName() + " is not yet supported.");
    }

    private byte[] serializeParts(byte part, Axis axis, Direction direction, float value) {
        int intBits = Float.floatToIntBits(value);

        return new byte[]{
                part,
                (byte) axis.getValue(),
                (byte) direction.getValue(),
                (byte) (intBits >> 24),
                (byte) (intBits >> 16),
                (byte) (intBits >> 8),
                (byte) (intBits),
        };
    }

    private byte[] serializeClawInstruction(ClawInstruction instruction) {
        return serializeParts(
                instruction.getPartId(),
                instruction.isClosed() ? Axis.Y : Axis.X,
                Direction.CLOCKWISE,
                0
            );
    }

    private byte[] serializeRotateInstruction(RotateInstruction instruction) {
        return serializeParts(
                instruction.getPartId(),
                instruction.getAxis(),
                instruction.getDirection(),
                instruction.getAngle()
        );
    }


}
