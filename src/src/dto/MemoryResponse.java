package dto;

import java.util.Map;

public class MemoryResponse {

    // Key: Physical Frame Index, Value: The array of integers in that frame
    private Map<Integer, int[]> activeFrames;

    public MemoryResponse() {}

    public MemoryResponse(Map<Integer, int[]> activeFrames) {
        this.activeFrames = activeFrames;
    }

    public Map<Integer, int[]> getActiveFrames() { return activeFrames; }
    public void setActiveFrames(Map<Integer, int[]> activeFrames) { this.activeFrames = activeFrames; }
}