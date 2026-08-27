package controller;

import com.service.VirtualMachineService;
import com.dto.VMStateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vm")
public class VMController {

    private final VirtualMachineService vmService;

    public VMController(VirtualMachineService vmService) {
        this.vmService = vmService;
    }

    // Endpoint 1: Start the CPU from line 0
    @PostMapping("/start")
    public ResponseEntity<String> startVM() {
        try {
            vmService.startVirtualMachine();
            return ResponseEntity.ok("Virtual Machine started successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint 2: Run exactly one instruction (Fetch-Decode-Execute)
    @PostMapping("/step")
    public ResponseEntity<VMStateResponse> executeStep() {

        // Base case: check if VM is running before stepping
        if (!vmService.isRunning()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Execute one step and return the new state of the RAM and CPU
        VMStateResponse currentState = vmService.executeNextStep();
        return ResponseEntity.ok(currentState);
    }

    // Endpoint 3: Just look at the current state without running anything
    @GetMapping("/state")
    public ResponseEntity<VMStateResponse> getVMState() {
        return ResponseEntity.ok(vmService.getCurrentState());
    }
}