package memory;

public class PageTableEntry {
    public int physicalFrame;
    public boolean isValid; // True if it's in RAM, False if it's on Disk

    public PageTableEntry(int physicalFrame, boolean isValid) {
        this.physicalFrame = physicalFrame;
        this.isValid = isValid;
    }
}