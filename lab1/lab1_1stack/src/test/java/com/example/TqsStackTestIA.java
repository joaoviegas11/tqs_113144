package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTestIA {

    private TqsStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new TqsStack<>();
    }

    @Test
    void testStackIsEmptyOnCreation() {
        assertTrue(stack.isEmpty(), "Stack should be empty on creation");
        assertEquals(0, stack.size(), "Stack size should be zero on creation");
    }

    @Test
    void testPushIncreasesSize() {
        stack.push(10);
        assertFalse(stack.isEmpty(), "Stack should not be empty after push");
        assertEquals(1, stack.size(), "Stack size should be 1 after one push");
    }

    @Test
    void testPopReturnsLastPushedElement() {
        stack.push(5);
        stack.push(20);
        assertEquals(20, stack.pop(), "Pop should return the last pushed element");
        assertEquals(1, stack.size(), "Stack size should decrease after pop");
    }

    @Test
    void testPeekReturnsLastElementWithoutRemoving() {
        stack.push(7);
        stack.push(15);
        assertEquals(15, stack.peek(), "Peek should return the last pushed element");
        assertEquals(2, stack.size(), "Stack size should remain the same after peek");
    }

    @Test
    void testPopOnEmptyStackThrowsException() {
        assertThrows(NoSuchElementException.class, stack::pop, "Pop on empty stack should throw exception");
    }

    @Test
    void testPeekOnEmptyStackThrowsException() {
        assertThrows(NoSuchElementException.class, stack::peek, "Peek on empty stack should throw exception");
    }

    @Test
    void testStackWithLimitedCapacity() {
        TqsStack<Integer> limitedStack = new TqsStack<>(2);
        limitedStack.push(1);
        limitedStack.push(2);
        assertThrows(IllegalStateException.class, () -> limitedStack.push(3), "Pushing beyond limit should throw exception");
    }

    @Test
    void testStackWithZeroOrNegativeLimitIsUnlimited() {
        TqsStack<Integer> zeroLimitStack = new TqsStack<>(0);
        TqsStack<Integer> negativeLimitStack = new TqsStack<>(-5);
        
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 1000; i++) {
                zeroLimitStack.push(i);
                negativeLimitStack.push(i);
            }
        }, "Stacks with zero or negative limit should be unlimited");
    }
}
