package memory;

public class MMU {
    private final int PAGE_SIZE = 16; // 16 integers per page

    private PhysicalMemory physicalMemory;
    private PageTable pageTable;
    private PageFaultHandler faultHandler;

    public MMU(PhysicalMemory memory, PageTable table, PageFaultHandler handler) {
        this.physicalMemory = memory;
        this.pageTable = table;
        this.faultHandler = handler;
    }

    // MAIN METHOD: CPU calls this to READ
    public int readData(int virtualAddress) {
        // Get the page and offset using helpers
        int page = getPageHelper(virtualAddress);
        int offset = getOffsetHelper(virtualAddress);

        int frame = pageTable.getFrame(page);

        // Choice 1: Page Fault (Not in RAM)
        if (frame == -1) {
            // Throw exception (logging it), then let the handler fix it
            System.out.println("Page Fault at Virtual Page: " + page);
            frame = faultHandler.handleFault(page);
        }

        // Choice 2: It's in RAM (Hit). Read from physical memory.
        return physicalMemory.readHelper(frame, offset);
    }

    // MAIN METHOD: CPU calls this to WRITE
    public void writeData(int virtualAddress, int data) {
        int page = getPageHelper(virtualAddress);
        int offset = getOffsetHelper(virtualAddress);

        int frame = pageTable.getFrame(page);

        // Choice 1: Page Fault
        if (frame == -1) {
            System.out.println("Page Fault at Virtual Page: " + page);
            frame = faultHandler.handleFault(page);
        }

        // Write to physical memory
        physicalMemory.writeHelper(frame, offset, data);
    }

    // --- Math Helpers ---
    // Instead of doing math everywhere, we isolate it here.
    private int getPageHelper(int virtualAddress) {
        return virtualAddress / PAGE_SIZE;
    }

    private int getOffsetHelper(int virtualAddress) {
        return virtualAddress % PAGE_SIZE;
    }
}