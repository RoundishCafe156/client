package cc.hyperium.utils;

public class SimpleAnimValue {
    private Long startMs;
    private Long duration;

    private float start;
    private float end;

    public SimpleAnimValue(Long duration, float start, float end) {
        this.duration = duration;
        this.start = start;
        this.end = end;
        this.startMs = System.currentTimeMillis();
    }

    public float getValue() {
        if (end - start == 0)
            return end;
        float v = start + ((float) (System.currentTimeMillis() - startMs)) * (((float) duration) / (end - start));
        return end > start ? Math.min(v, end) : Math.max(v, end);
    }

    public boolean isFinished() {
        return getValue() == end;
    }
}