package dto;

import java.util.Map;

public class RegisterResponse {

    // Key: Register Name (e.g., "R1"), Value: Current Integer Value
    private Map<String, Integer> registers;

    public RegisterResponse() {}

    public RegisterResponse(Map<String, Integer> registers) {
        this.registers = registers;
    }

    public Map<String, Integer> getRegisters() { return registers; }
    public void setRegisters(Map<String, Integer> registers) { this.registers = registers; }
}