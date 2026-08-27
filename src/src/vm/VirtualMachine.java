package vm;

import java.util.List;

public class VirtualMachine {

    private CPU cpu;
    private List<Instruction> program;
    private boolean isHalted;

    public VirtualMachine(CPU cpu) {
        this.cpu = cpu;
        this.isHalted = true;
    }

    public void loadProgram(List<Instruction> program) {
        this.program = program;
        this.isHalted = true;
    }

    public void start() {
        // Base case
        if (program == null || program.isEmpty()) {
            throw new RuntimeException("No code loaded");
        }
        cpu.resetPc();
        this.isHalted = false;
    }

    public void step() {
        // Base case: Machine is stopped or program finished
        if (isHalted || cpu.getPc() >= program.size()) {
            isHalted = true;
            return;
        }

        Instruction current = program.get(cpu.getPc());

        // Choice: Check if we need to halt
        if (current.getOpcode() == Opcode.HALT) {
            isHalted = true;
            return;
        }

        // Execute the instruction
        cpu.executeStep(current);
    }

    public boolean isHalted() {
        return isHalted;
    }
}