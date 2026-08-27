package dto;

import java.util.List;

public class VMStateResponse {

    private int programCounter;
    private boolean isHalted;
    private List<Integer> executionStack; // What numbers are currently on the stack

    // Nested DTOs for the memory state
    private MemoryResponse memorySnapshot;
    private PageTableResponse pageTableSnapshot;

    // Constructors
    public VMStateResponse() {}

    public VMStateResponse(int programCounter, boolean isHalted, List<Integer> executionStack,
                           MemoryResponse memorySnapshot, PageTableResponse pageTableSnapshot) {
        this.programCounter = programCounter;
        this.isHalted = isHalted;
        this.executionStack = executionStack;
        this.memorySnapshot = memorySnapshot;
        this.pageTableSnapshot = pageTableSnapshot;
    }

    // Getters and Setters
    public int getProgramCounter() { return programCounter; }
    public void setProgramCounter(int programCounter) { this.programCounter = programCounter; }

    public boolean isHalted() { return isHalted; }
    public void setHalted(boolean halted) { isHalted = halted; }

    public List<Integer> getExecutionStack() { return executionStack; }
    public void setExecutionStack(List<Integer> executionStack) { this.executionStack = executionStack; }

    public MemoryResponse getMemorySnapshot() { return memorySnapshot; }
    public void setMemorySnapshot(MemoryResponse memorySnapshot) { this.memorySnapshot = memorySnapshot; }

    public PageTableResponse getPageTableSnapshot() { return pageTableSnapshot; }
    public void setPageTableSnapshot(PageTableResponse pageTableSnapshot) { this.pageTableSnapshot = pageTableSnapshot; }
}