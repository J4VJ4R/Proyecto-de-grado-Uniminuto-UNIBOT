package com.example.unibot.model.services;

import java.util.ArrayList;
import java.util.List;

public final class Promise<T> {
    public interface Action<T> {
        void run(T data);
    }

    public interface ErrorAction {
        void run(Exception e);
    }

    public interface PromiseAction<T> {
        void run(Action<T> resolve, Action<Exception> reject);
    }

    public enum State {
        NotStarted,
        Running,
        Resolved,
        Rejected,
    }

    private State state = State.NotStarted;

    private T resolvedValue = null;

    private Exception exception;

    private List<Action<T>> thenActions = new ArrayList<>();

    private List<ErrorAction> catchActions = new ArrayList<>();

    private void runThenActions() {
        try{
            thenActions.forEach(action -> action.run(resolvedValue));
        }catch (Exception e){
            exception = e;
            runCatchActions();
        }
    }

    private void runCatchActions() {
        catchActions.forEach(action -> action.run(exception));
    }

    public static <TResult> Promise<TResult> resolve(TResult value) {
        return new Promise<>((a, r) -> a.run(value));
    }

    public static Promise reject(Exception value) {
        return new Promise<>((a, r) -> r.run(value));
    }


    public Promise(PromiseAction<T> promiseAction) {
        state = State.Running;

        Action<T> accept = (data) -> {
            state = State.Resolved;
            resolvedValue = data;
            runThenActions();
        };

        Action<Exception> reject = (e) -> {
            state = State.Rejected;
            exception = e;
            runCatchActions();
        };

        promiseAction.run(accept, reject);
    }

    public State getState() {
        return state;
    }

    public T getResolvedValue() {
        return resolvedValue;
    }

    public Exception getException() {
        return exception;
    }

    public Promise<T> then(Action<T> thenAction) {
        this.thenActions.add(thenAction);
        if (state == State.Resolved) {
            runThenActions();
        }
        return this;
    }

    public Promise<T> caught(ErrorAction catchAction) {
        this.catchActions.add(catchAction);
        if (state == State.Rejected) {
            runCatchActions();
        }
        return this;
    }

}
