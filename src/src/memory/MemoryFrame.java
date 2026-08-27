package memory;

public class MemoryFrame {
    private int[] data;

    public MemoryFrame(int pageSize) {
        this.data = new int[pageSize];
    }

    public int read(int offset) {
        return data[offset];
    }

    public void write(int offset, int val) {
        this.data[offset] = val;
    }

    // Used for swapping entire blocks with the disk
    public int[] getData() { return data; }
    public void setData(int[] newData) { this.data = newData; }
}