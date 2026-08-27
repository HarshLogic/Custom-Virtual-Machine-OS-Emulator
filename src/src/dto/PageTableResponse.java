package dto;

import java.util.Map;

public class PageTableResponse {

    // Key: Virtual Page Number, Value: Physical Frame Index
    private Map<Integer, Integer> validMappings;

    public PageTableResponse() {}

    public PageTableResponse(Map<Integer, Integer> validMappings) {
        this.validMappings = validMappings;
    }

    public Map<Integer, Integer> getValidMappings() { return validMappings; }
    public void setValidMappings(Map<Integer, Integer> validMappings) { this.validMappings = validMappings; }
}