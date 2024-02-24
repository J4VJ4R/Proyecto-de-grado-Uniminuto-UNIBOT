package com.example.unibot.model.services;

import android.os.Handler;

import com.example.unibot.model.IInstruction;

public final class SendInstructionRequest {
    private IInstruction instruction;
    private Handler handler;

    private Promise.Action accept;

    private Promise.Action reject;

    public SendInstructionRequest(IInstruction instruction, Handler handler, Promise.Action accept, Promise.Action reject) {
        this.instruction = instruction;
        this.handler = handler;
        this.accept = accept;
        this.reject = reject;
    }

    public IInstruction getInstruction() {
        return instruction;
    }

    public void accept() {
        handler.post(() -> accept.run(null));
    }

    public void reject(Exception reason) {
        handler.post(() -> reject.run(reason));
    }
}
