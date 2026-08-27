package service;
import com.dto.VMStateResponse;
import org.springframework.stereotype.Service;
@Service
public class ExecutionService {

    private final VMService vmService;
    // private final CPU cpu; <-- We will inject the CPU here later

    public ExecutionService(VMService vmService) {
        this.vmService = vmService;
    }

    public VMStateResponse executeNextStep() {
        // Base case: Machine must be turned on
        if (!vmService.isRunning()) {
            throw new RuntimeException("Virtual Machine is not running. Call /start first.");
        }

        // Logic to execute one instruction will go here:
        // cpu.executeStep( vmService.getCurrentProgram() );

        // Return the fresh state after the instruction finishes
        return vmService.getCurrentState();
    }
}