package vm;
import com.memory.MMU;
public class CPU {

    private int pc; // Program Counter
    private ExecutionStack stack;
    private MMU mmu; // Connects to memory

    public CPU(MMU mmu) {
        this.stack = new ExecutionStack();
        this.mmu = mmu;
        this.pc = 0;
    }

    public int getPc() {
        return pc;
    }

    public void resetPc() {
        this.pc = 0;
        this.stack.clear();
    }

    // Main execution function
    public void executeStep(Instruction inst) {
        // Base case
        if (inst == null) return;

        Opcode op = inst.getOpcode();
        Integer val = inst.getOperand();

        switch (op) {
            case PUSH:
                stack.push(val);
                pc++;
                break;
            case POP:
                stack.pop();
                pc++;
                break;
            case ADD: case SUB: case MUL: case DIV:
                mathHelper(op);
                pc++;
                break;
            case LOAD:
                loadHelper(val);
                pc++;
                break;
            case STORE:
                storeHelper(val);
                pc++;
                break;
            case JMP:
                pc = val; // Direct jump
                break;
            case JZ:
                jumpIfZeroHelper(val);
                break;
            case HALT:
                // Handled by VirtualMachine wrapper
                break;
        }
    }

    // --- Helper Functions ---

    private void mathHelper(Opcode op) {
        // Choice: pop two elements. Order is important!
        int b = stack.pop();
        int a = stack.pop();

        if (op == Opcode.ADD) stack.push(a + b);
        else if (op == Opcode.SUB) stack.push(a - b);
        else if (op == Opcode.MUL) stack.push(a * b);
        else if (op == Opcode.DIV) {
            if (b == 0) throw new ArithmeticException("Divide by 0");
            stack.push(a / b);
        }
    }

    private void loadHelper(int address) {
        // Get from memory and push to stack
        int data = mmu.readData(address);
        stack.push(data);
    }

    private void storeHelper(int address) {
        // Take from stack and give to memory
        int data = stack.pop();
        mmu.writeData(address, data);
    }

    private void jumpIfZeroHelper(int targetLine) {
        int top = stack.pop();
        // Choice 1: Jump
        if (top == 0) {
            pc = targetLine;
        }
        // Choice 2: Skip
        else {
            pc++;
        }
    }
}