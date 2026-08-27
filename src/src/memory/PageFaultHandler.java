package memory;
import com.replacement.LRUSwapper;
import storage.DiskManager;

public class PageFaultHandler {
    private LRUSwapper lruSwapper;
    private DiskManager diskManager;
    private PhysicalMemory physicalMemory;
    private PageTable pageTable;

    public PageFaultHandler(LRUSwapper lruSwapper, DiskManager diskManager, PhysicalMemory physicalMemory, PageTable pageTable) {
        this.lruSwapper = lruSwapper;
        this.diskManager = diskManager;
        this.physicalMemory = physicalMemory;
        this.pageTable = pageTable;
    }

    public int handleFault(int virtualPage) {
        // Step 1: Ask LRU Swapper where to put this new page.
        // It returns the frame it evicted (or -1 if there was free space).
        // (Assuming we assign frame 0, 1, 2 sequentially if RAM isn't full yet - simplified here)
        int frameToUse = lruSwapper.getFreeFrameOrEvict(virtualPage);

        int evictedPage = lruSwapper.getEvictedPage();

        // Choice 1: RAM was full, someone got kicked out
        if (evictedPage != -1) {
            // Save old data to disk
            int[] oldData = physicalMemory.getFrame(frameToUse).getData();
            diskManager.writePageToDisk(evictedPage, oldData);
            pageTable.invalidateEntry(evictedPage);
        }

        // Step 2: Load the new data from disk into the free frame
        int[] newData = diskManager.readPageFromDisk(virtualPage);
        physicalMemory.getFrame(frameToUse).setData(newData);

        // Step 3: Update Page Table
        pageTable.updateEntry(virtualPage, frameToUse);

        return frameToUse;
    }
}