package com.unibot.model;

public class RotateInstruction implements IInstruction {
    private byte partId;

    private Axis axis;

    private Direction direction;

    private float angle;

    public RotateInstruction(byte partId, Axis axis, Direction direction, float angle) {
        this.partId = partId;
        this.axis = axis;
        this.direction = direction;
        this.angle = angle;
    }

    @Override
    public byte getPartId() {
        return partId;
    }

    public Axis getAxis() {
        return axis;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getAngle() {
        return angle;
    }
}
