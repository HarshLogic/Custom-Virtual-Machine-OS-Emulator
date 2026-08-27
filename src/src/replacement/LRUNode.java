package replacement;

public class LRUNode {
    public int virtualPage;
    public int physicalFrame;
    public LRUNode next;
    public LRUNode prev;

    public LRUNode(int virtualPage, int physicalFrame) {
        this.virtualPage = virtualPage;
        this.physicalFrame = physicalFrame;
    }
}