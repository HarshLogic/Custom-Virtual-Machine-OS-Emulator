package vm;

public class Instruction {
    private final Opcode opcode;
    private final Integer operand; // Integer (wrapper) so it can be null if there is no number

    public Instruction(Opcode opcode, Integer operand) {
        this.opcode = opcode;
        this.operand = operand;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public Integer getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        if (operand == null) return opcode.name();
        return opcode.name() + " " + operand;
    }
}