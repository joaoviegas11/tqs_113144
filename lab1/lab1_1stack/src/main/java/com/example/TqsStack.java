package com.example;

import java.util.ArrayList;

public class TqsStack<T> {
    private final ArrayList<T> stack;
    private int limit = 0;

    public TqsStack() {
        stack = new ArrayList<>();
    }

    public TqsStack(int limit) {
        stack = new ArrayList<>();
        this.limit = limit;
        if (limit < 1) {
            this.limit = 0;
        }
    }

    void push(T val) {
        if (limit != 0 && size() == limit) {
            throw new IllegalStateException();
        }
        stack.add(val);
    }

    T pop() {
        return stack.removeLast();
    }

    T peek() {
        return stack.getLast();
    }

    int size() {
        return stack.size();
    }

    boolean isEmpty() {
        return stack.isEmpty();
    }
    // T popTopN(int n) {
    //     for (int i = 0; i < n-1; i++) {
    //         pop();
    //     }
    //     return pop();
    // }
}
