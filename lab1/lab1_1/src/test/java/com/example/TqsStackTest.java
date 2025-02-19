package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class TqsStackTest {

    @Test
    void testIsEmpty() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        assertEquals(true, stack.isEmpty());
    }

    @Test
    void testSize0() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        assertEquals(0, stack.size());
    }

    @Test
    void testPushN() {
        Random random = new Random();
        TqsStack<Integer> stack = new TqsStack<Integer>();
        int n = random.nextInt(100) + 1;
        for (int i = 0; i < n; i++) {
            stack.push(random.nextInt(100));
        }
        assertEquals(false, stack.isEmpty());
        assertEquals(n, stack.size());

    }

    @Test
    void testPop() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        int x = stack.pop();
        assertEquals(4, x);
    }

    @Test
    void testPeek() {
        TqsStack<Integer> stack = new TqsStack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        int size = stack.size();
        int x = stack.peek();
        assertEquals(4, x);
        assertEquals(size, stack.size());
    }

    @Test
    void testPopN() {
        Random random = new Random();
        TqsStack<Integer> stack = new TqsStack<Integer>();
        int n = random.nextInt(100) + 1;
        for (int i = 0; i < n; i++) {
            stack.push(random.nextInt(100));
        }
        assertEquals(n, stack.size());
        for (int i = 0; i < n; i++) {
            stack.pop();
        }
        assertEquals(true, stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void testPoppingNoSuchElementException() {
        assertThrows(NoSuchElementException.class, ()->{
            TqsStack<Integer> stack = new TqsStack<Integer>();
            stack.pop();
        });
    }

    @Test
    void testPeekingNoSuchElementException() {
        assertThrows(NoSuchElementException.class, ()->{
            TqsStack<Integer> stack = new TqsStack<Integer>();
            stack.peek();
        });
    }

    @Test
    void testPushFullStack() {
        TqsStack<Integer> stack = new TqsStack<Integer>(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThrows(IllegalStateException.class, ()->{
            stack.push(4);
        });
    }

    // @Test
    // void testPopTopN() {
    //     TqsStack<Integer> stack = new TqsStack<Integer>();
    //     stack.push(1);
    //     stack.push(2);
    //     stack.push(3);
    //     stack.push(4);
    //     stack.push(5);
    //     stack.push(6);
    //     int x = stack.popTopN(5);
    //     assertEquals(1, stack.size());
    //     assertEquals(2, x);
    // }

}
