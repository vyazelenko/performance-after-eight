package com.vyazelenko.perf.collections;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class MapBenchmark extends AbstractCollectionBenchmark {

    public enum Kind {
        MAP_OF_ENTRIES {
            @Override
            public <K, V> Map<K, V> create(Entry<K, V>... entries) {
                return Map.ofEntries(entries);
            }
        },
        HASH_MAP {
            @Override
            public <K, V> Map<K, V> create(Entry<K, V>... entries) {
                return new HashMap<>(Map.ofEntries(entries));
            }
        },
        LINKED_HASH_MAP {
            @Override
            public <K, V> Map<K, V> create(Entry<K, V>... entries) {
                return new LinkedHashMap<>(Map.ofEntries(entries));
            }
        },
        TREE_MAP {
            @Override
            public <K, V> Map<K, V> create(Entry<K, V>... entries) {
                return new TreeMap<>(Map.ofEntries(entries));
            }
        };

        public abstract <K, V> Map<K, V> create(Entry<K, V>... entries);
    }

    @Param
    private Kind kind;

    private Map<Object, Object> map;
    private Object contains;

    @Override
    protected void doSetup(Object[] values) {
        map = kind.create(Stream.of(values).map(v -> Map.entry(v, v)).toArray(Entry[]::new));
        contains = values[values.length >> 1];
    }

    @Benchmark
    public Object containsKey() {
        return map.containsKey(contains);
    }

    @Benchmark
    public Object containsValue() {
        return map.containsValue(contains);
    }

    @Benchmark
    public void entrySet(Blackhole bh) {
        Set<Entry<Object, Object>> set = this.map.entrySet();
        for (Object o : set) {
            bh.consume(o);
        }
    }
}
