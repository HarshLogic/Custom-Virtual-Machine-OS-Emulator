package replacement;

import java.util.HashMap;

public class LRUSwapper implements PageReplacementAlgorithm {

    // Head and tail dummy nodes (Your exact logic)
    private LRUNode head = new LRUNode(-1, -1);
    private LRUNode tail = new LRUNode(-1, -1);

    private int cap;
    private HashMap<Integer, LRUNode> m = new HashMap<>();

    // Extra state to help the OS know what got kicked out
    private int lastEvictedPage = -1;
    private int nextFreeFrame = 0; // Assigns frames 0, 1, 2 until cap is reached

    public LRUSwapper(int capacity) {
        this.cap = capacity;
        head.next = tail;
        tail.prev = head;
    }

    // Helper: Function to add a node right after head (Your exact logic)
    private void addNode(LRUNode newNode) {
        LRUNode temp = head.next;
        newNode.next = temp;
        newNode.prev = head;
        head.next = newNode;
        temp.prev = newNode;
    }

    // Helper: Function to remove a given node from list (Your exact logic)
    private void deleteNode(LRUNode delNode) {
        LRUNode delPrev = delNode.prev;
        LRUNode delNext = delNode.next;
        delPrev.next = delNext;
        delNext.prev = delPrev;
    }

    @Override
    public int getFrame(int virtualPage) {
        // Base case: If key exists in cache
        if (m.containsKey(virtualPage)) {
            LRUNode resNode = m.get(virtualPage);
            int resFrame = resNode.physicalFrame;

            // Move accessed node to front (Your exact logic)
            m.remove(virtualPage);
            deleteNode(resNode);
            addNode(resNode);
            m.put(virtualPage, head.next);

            return resFrame;
        }
        // Not found
        return -1;
    }

    @Override
    public int getFreeFrameOrEvict(int virtualPage) {
        // Reset the tracker
        lastEvictedPage = -1;

        // Choice 1: RAM is completely full, we must evict the LRU (tail.prev)
        if (m.size() == cap) {
            LRUNode lruNode = tail.prev;

            // Save which page we are kicking out so DiskManager can save it
            lastEvictedPage = lruNode.virtualPage;
            int freedFrame = lruNode.physicalFrame; // The physical array slot we just freed

            // Delete old mapping (Your exact logic)
            m.remove(lruNode.virtualPage);
            deleteNode(lruNode);

            // Insert new node at front using the freed frame
            addNode(new LRUNode(virtualPage, freedFrame));
            m.put(virtualPage, head.next);

            return freedFrame;
        }

        // Choice 2: RAM is not full yet. Just use the next available frame.
        int frameToUse = nextFreeFrame;
        nextFreeFrame++; // increment for next time

        addNode(new LRUNode(virtualPage, frameToUse));
        m.put(virtualPage, head.next);

        return frameToUse;
    }

    @Override
    public int getEvictedPage() {
        return lastEvictedPage;
    }
}