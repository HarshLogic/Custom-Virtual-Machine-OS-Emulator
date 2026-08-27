import com.memory.*;
import com.replacement.LRUSwapper;
import com.storage.DiskManager;
import com.vm.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Initializing Virtual Machine...");

        // Step 1: Define System Limits
        int PAGE_SIZE = 16;
        int RAM_CAPACITY = 4; // Only 4 frames of RAM to force the LRU Swapper to work quickly

        // Step 2: Initialize Storage and Swapper (The lowest levels)
        DiskManager diskManager = new DiskManager(PAGE_SIZE);
        LRUSwapper lruSwapper = new LRUSwapper(RAM_CAPACITY);

        // Step 3: Initialize Memory Management (The Middleman)
        PhysicalMemory ram = new PhysicalMemory(RAM_CAPACITY, PAGE_SIZE);
        PageTable pageTable = new PageTable();
        PageFaultHandler faultHandler = new PageFaultHandler(lruSwapper, diskManager, ram, pageTable);

        MMU mmu = new MMU(ram, pageTable, faultHandler);

        // Step 4: Initialize the Execution Engine (The Brain)
        CPU cpu = new CPU(mmu);
        VirtualMachine vm = new VirtualMachine(cpu);

        // Step 5: Write a Dummy Assembly Program
        // Let's do: 10 + 20 = 30, and save the result to Virtual Memory Address 0
        List<Instruction> program = new ArrayList<>();
        program.add(new Instruction(Opcode.PUSH, 10));
        program.add(new Instruction(Opcode.PUSH, 20));
        program.add(new Instruction(Opcode.ADD, null));
        program.add(new Instruction(Opcode.STORE, 0)); // Store the 30 into memory
        program.add(new Instruction(Opcode.HALT, null));

        // Step 6: Load and Run!
        System.out.println("Loading program into VM...");
        vm.loadProgram(program);
        vm.start();

        System.out.println("Execution Started.");
        while (!vm.isHalted()) {
            // Print the current line being executed for debugging
            System.out.println("Executing PC: " + cpu.getPc());
            vm.step();
        }

        // Step 7: Verify the result
        System.out.println("Execution Finished!");
        System.out.println("Checking Memory Address 0... Result = " + mmu.readData(0));
    }
}