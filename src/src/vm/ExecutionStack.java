package vm;
import java.util.Stack;

public class ExecutionStack {
    private Stack<Integer> stack = new Stack<>();

    public void push(int val) {
        stack.push(val);
    }

    public int pop() {
        // Base case check to prevent crashes
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack Underflow");
        }
        return stack.pop();
    }

    public int peek() {
        if (stack.isEmpty()) throw new RuntimeException("Stack is empty");
        return stack.peek();
    }

    public void clear() {
        stack.clear();
    }
}