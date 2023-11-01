package io.bellsoft.hotcode.profiling;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class TopKProfile<T> implements Profile<T> {

    private final HashMap<T, Integer> samples;
    private int maxCount;
    private int total;

    public TopKProfile() {
        this.samples = new HashMap<>();
    }

    @Override
    public boolean addSample(T elem) {
        int newCount = samples.getOrDefault(elem, 0) + 1;
        samples.put(elem, newCount);
        maxCount = Math.max(maxCount, newCount);
        total++;
        return newCount == 1;
    }

    private final Comparator<Map.Entry<T, Integer>> DESC = new Comparator<>() {
        @Override
        public int compare(Entry<T, Integer> o1, Entry<T, Integer> o2) {
            return -o1.getValue().compareTo(o2.getValue());
        }
    };
    
    @Override
    public List<T> getTop(int k) {
        if (k < 1) {
            throw new IllegalArgumentException("at least 1 top element should be specified");
        }
        int n = samples.entrySet().size();
        if (n == 0) {
            return Collections.emptyList();
        }
        var pq = new PriorityQueue<Map.Entry<T, Integer>>(n, DESC);
        pq.addAll(samples.entrySet());
        int len = Math.min(k, pq.size());
        var result = new ArrayList<T>(len);
        for (int i = 0; i < len; i++) {
            result.add(pq.poll().getKey());
        }
        return result;
    }

    @Override
    public int getTotalUnique() {
        return samples.size();
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public int occurrences(T elem) {
        return samples.getOrDefault(elem, 0);
    }

    @Override
    public void clear() {
        samples.clear();
        maxCount = 0;
        total = 0;
    }
}
