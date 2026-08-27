package memory;

public class PhysicalMemory {
    private MemoryFrame[] frames;
    private int pageSize;

    public PhysicalMemory(int numFrames, int pageSize) {
        this.pageSize = pageSize;
        this.frames = new MemoryFrame[numFrames];

        // Initialize all frames
        for (int i = 0; i < numFrames; i++) {
            frames[i] = new MemoryFrame(pageSize);
        }
    }

    public int readHelper(int frameIndex, int offset) {
        // Base case: Security check
        if (frameIndex < 0 || frameIndex >= frames.length || offset < 0 || offset >= pageSize) {
            throw new RuntimeException("Memory Access Violation!");
        }
        return frames[frameIndex].read(offset);
    }

    public void writeHelper(int frameIndex, int offset, int data) {
        // Base case: Security check
        if (frameIndex < 0 || frameIndex >= frames.length || offset < 0 || offset >= pageSize) {
            throw new RuntimeException("Memory Access Violation!");
        }
        frames[frameIndex].write(offset, data);
    }

    public MemoryFrame getFrame(int index) {
        return frames[index];
    }
}