package com.unibot.model;

public class ClawInstruction implements IInstruction {
    private byte partId;

    private boolean isClosed;

    public ClawInstruction(byte partId, boolean isClosed) {
        this.partId = partId;
        this.isClosed = isClosed;
    }

    @Override
    public byte getPartId() {
        return partId;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
