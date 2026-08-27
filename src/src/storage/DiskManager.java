package storage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiskManager {

    private final String diskFilePath = "virtual_disk.dat"; // Our fake hard drive file
    private final int pageSize; // Number of integers per page (e.g., 16)
    private final int BYTES_PER_INT = 4; // In Java, an int always takes 4 bytes

    public DiskManager(int pageSize) {
        this.pageSize = pageSize;
        initializeDiskHelper();
    }

    // Called by the PageFaultHandler when RAM is full and we need to save old data
    public void writePageToDisk(int virtualPage, int[] data) {
        // Base case: Security check
        if (data == null || data.length != pageSize) {
            throw new RuntimeException("Disk Error: Invalid page data size.");
        }

        long byteOffset = calculateOffsetHelper(virtualPage);

        try (RandomAccessFile disk = new RandomAccessFile(diskFilePath, "rw")) {
            disk.seek(byteOffset); // Jump directly to the exact location

            // Write each integer in the array to the file
            for (int i = 0; i < pageSize; i++) {
                disk.writeInt(data[i]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to disk: " + e.getMessage());
        }
    }

    // Called by the PageFaultHandler when CPU asks for a page that isn't in RAM
    public int[] readPageFromDisk(int virtualPage) {
        // Base case check is handled naturally by the array initialization
        int[] data = new int[pageSize];
        long byteOffset = calculateOffsetHelper(virtualPage);

        try (RandomAccessFile disk = new RandomAccessFile(diskFilePath, "r")) {
            // If the file is smaller than our offset, it means this page has never
            // been written to yet. We just return the empty array of 0s.
            if (byteOffset >= disk.length()) {
                return data;
            }

            disk.seek(byteOffset); // Jump directly to the location

            // Read the integers back into the array
            for (int i = 0; i < pageSize; i++) {
                data[i] = disk.readInt();
            }
        } catch (IOException e) {
            // Ignore EOF (End of File) errors. If we hit EOF, it just means the rest of the
            // page is blank memory (0s).
            System.out.println("Warning: Reading uninitialized disk space at page " + virtualPage);
        }

        return data;
    }

    // --- Helper Functions ---

    // Calculates exactly where to put the cursor in the file
    private long calculateOffsetHelper(int virtualPage) {
        // Example: If page size is 16, 1 page = 16 ints * 4 bytes = 64 bytes.
        // Page 0 starts at byte 0. Page 1 starts at byte 64. Page 2 starts at byte 128.
        return (long) virtualPage * pageSize * BYTES_PER_INT;
    }

    // Ensures the .dat file actually exists when the VM starts
    private void initializeDiskHelper() {
        try {
            File diskFile = new File(diskFilePath);
            if (!diskFile.exists()) {
                diskFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create virtual disk file!");
        }
    }
}