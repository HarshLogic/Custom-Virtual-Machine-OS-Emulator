package vm;

public class InstructionParser {
    public static Instruction parse(String line) {
        // Base case: If line is empty, do nothing
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.trim().split("\\s+");

        // Convert string to Opcode enum
        Opcode opcode = Opcode.valueOf(parts[0].toUpperCase());
        Integer operand = null;

        // Choice: Check if this instruction has a second part (a number)
        if (parts.length > 1) {
            if (parts[1].startsWith("0x")) {
                operand = Integer.parseInt(parts[1].substring(2), 16); // Handle Hex
            } else {
                operand = Integer.parseInt(parts[1]); // Handle Base 10
            }
        }

        return new Instruction(opcode, operand);
    }
}