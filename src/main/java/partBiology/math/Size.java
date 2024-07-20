package partBiology.math;

public class Size {
    private long start;
    private long end;
    private long size;

    public Size() {
    }

    public Size(long start, long end) {
        this.start = start;
        this.end = end;
        setSize();
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
        setSize();
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
        setSize();
    }

    public long getSize() {
        return size;
    }

    private void setSize() {
        this.size = this.end - this.start;
    }

    @Override
    public String toString() {
        return "Start: " + this.start + " End: " + this.end + " Size: " + this.size;
    }
}
