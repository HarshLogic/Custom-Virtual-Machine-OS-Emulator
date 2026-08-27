package service;
import com.vm.Instruction;
import com.dto.VMStateResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VMService {

    private List<Instruction> currentProgram;
    private boolean isRunning;

    public VMService() {
        this.isRunning = false;
    }

    public void loadProgram(List<Instruction> program) {
        // Base case
        if (program == null || program.isEmpty()) {
            throw new RuntimeException("Cannot load an empty program.");
        }
        this.currentProgram = program;
        this.isRunning = false; // Reset state when new code is loaded
    }

    public void startVirtualMachine() {
        // Base case: Can't start without code
        if (currentProgram == null) {
            throw new RuntimeException("No program loaded! Upload a file first.");
        }
        this.isRunning = true;
        // In the future, we will tell the CPU to reset its Program Counter to 0 here
    }

    public boolean isRunning() {
        return isRunning;
    }

    public List<Instruction> getCurrentProgram() {
        return currentProgram;
    }

    // Gathers the current data for the frontend
    public VMStateResponse getCurrentState() {
        // We will build this DTO properly when the memory package is finished
        return new VMStateResponse();
    }
}