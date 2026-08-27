package memory;

import java.util.HashMap;

public class PageTable {
    private HashMap<Integer, PageTableEntry> table = new HashMap<>();

    public int getFrame(int virtualPage) {
        // Base case: Check if the page exists and is valid (in RAM)
        if (table.containsKey(virtualPage) && table.get(virtualPage).isValid) {
            return table.get(virtualPage).physicalFrame;
        }
        return -1; // -1 means Page Fault
    }

    public void updateEntry(int virtualPage, int physicalFrame) {
        table.put(virtualPage, new PageTableEntry(physicalFrame, true));
    }

    public void invalidateEntry(int virtualPage) {
        if (table.containsKey(virtualPage)) {
            table.get(virtualPage).isValid = false;
        }
    }
}