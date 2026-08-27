package replacement;

public interface PageReplacementAlgorithm {

    // Called when the CPU asks for a page. Checks if it's in RAM.
    int getFrame(int virtualPage);

    // Called during a Page Fault. It finds a free frame or kicks out an old one.
    int getFreeFrameOrEvict(int virtualPage);

    // Returns the Virtual Page that was just kicked out (or -1 if none)
    int getEvictedPage();
}