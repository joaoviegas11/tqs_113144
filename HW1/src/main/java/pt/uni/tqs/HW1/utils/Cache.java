package pt.uni.tqs.HW1.utils;

public class Cache<T> {
    private T data;
    private long timestamp;
    private final long ttl;

    public Cache(long ttl) {
        this.ttl = ttl;
        this.timestamp = 0;
    }

    public void put(T data) {
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public T get() {
        return isValid() ? data : null;
    }

    public boolean isValid() {
        return data != null && (System.currentTimeMillis() - timestamp) < ttl;
    }

}
