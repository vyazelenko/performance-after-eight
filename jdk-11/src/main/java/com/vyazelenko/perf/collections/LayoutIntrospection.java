package com.vyazelenko.perf.collections;

import org.openjdk.jol.info.GraphLayout;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.IntFunction;

import static java.lang.System.out;
import static java.util.Map.entry;

public class LayoutIntrospection {
    public static void main(String[] args) {
        out.println("=== Lists ===");
        footprint(List.of());
        footprint(Collections.emptyList());

        footprint(List.of(1));
        footprint(Collections.singletonList(1));
        footprint(new ArrayList<>(List.of(1)));
        footprint(new LinkedList<>(List.of(1)));

        footprint(List.of(1, 2));
        footprint(new ArrayList<>(List.of(1, 2)));
        footprint(new LinkedList<>(List.of(1, 2)));

        footprint(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0));
        footprint(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));
        footprint(new LinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));

        out.println("=== Sets ===");
        footprint(Set.of());
        footprint(Collections.emptySet());

        footprint(Set.of(1));
        footprint(Collections.singleton(1));
        footprint(new HashSet<>(List.of(1)));
        footprint(new LinkedHashSet<>(List.of(1)));
        footprint(new TreeSet<>(List.of(1)));

        footprint(Set.of(1, 2));
        footprint(new HashSet<>(List.of(1, 2)));
        footprint(new LinkedHashSet<>(List.of(1, 2)));
        footprint(new TreeSet<>(List.of(1, 2)));

        footprint(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0));
        footprint(new HashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));
        footprint(new LinkedHashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));
        footprint(new TreeSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));

        out.println("=== Maps ===");
        footprint(Map.of());
        footprint(Collections.emptyMap());

        footprint(Map.of(1, 1));
        footprint(Collections.singletonMap(1, 1));
        footprint(newHashMap(entry(1, 1)));
        footprint(newLinkedHashMap(entry(1, 1)));
        footprint(newTreeMap(entry(1, 1)));

        footprint(Map.of(1, 1, 2, 2));
        footprint(newHashMap(entry(1, 1), entry(2, 2)));
        footprint(newLinkedHashMap(entry(1, 1), entry(2, 2)));
        footprint(newTreeMap(entry(1, 1), entry(2, 2)));

        footprint(Map.of(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 0, 0));
        footprint(newHashMap(entry(1, 1), entry(2, 2), entry(3, 3), entry(4, 4), entry(5, 5), entry(6, 6), entry(7, 7), entry(8, 8), entry(9, 9), entry(0, 0)));
        footprint(newLinkedHashMap(entry(1, 1), entry(2, 2), entry(3, 3), entry(4, 4), entry(5, 5), entry(6, 6), entry(7, 7), entry(8, 8), entry(9, 9), entry(0, 0)));
        footprint(newTreeMap(entry(1, 1), entry(2, 2), entry(3, 3), entry(4, 4), entry(5, 5), entry(6, 6), entry(7, 7), entry(8, 8), entry(9, 9), entry(0, 0)));

        footprint(Map.ofEntries(collidingEntrySet(16)));
        footprint(newHashMap(collidingEntrySet(16)));
        footprint(newLinkedHashMap(collidingEntrySet(16)));
        footprint(newTreeMap(collidingEntrySet(16)));
    }

    private static void footprint(Object o) {
        out.println(GraphLayout.parseInstance(o).toFootprint());
    }

    private static <K, V> Map<K, V> newHashMap(Entry<K, V>... entries) {
        return newMap(size -> new HashMap<>(size), entries);
    }

    private static <K, V> Map<K, V> newLinkedHashMap(Entry<K, V>... entries) {
        return newMap(size -> new LinkedHashMap<>(size), entries);
    }

    private static <K, V> Map<K, V> newTreeMap(Entry<K, V>... entries) {
        return newMap(size -> new TreeMap<>(), entries);
    }

    private static <K, V> Map<K, V> newMap(IntFunction<Map<K, V>> mapProvider, Entry<K, V>[] entries) {
        Map<K, V> map = mapProvider.apply(entries.length);
        for (Entry<K, V> e : entries) {
            map.put(e.getKey(), e.getValue());
        }
        return map;
    }

    private static Entry<CollidingKey, Integer>[] collidingEntrySet(int size) {
        List<Entry<CollidingKey, Integer>> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(entry(new CollidingKey(i), i));
        }
        return list.toArray(new Entry[0]);
    }

    private static class CollidingKey implements Comparable<CollidingKey> {
        private final int key;

        private CollidingKey(int key) {
            this.key = key;
        }

        @Override
        public int hashCode() {
            return 42;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CollidingKey)) {
                return false;
            }
            return key == ((CollidingKey) obj).key;
        }

        @Override
        public int compareTo(CollidingKey o) {
            return Integer.compare(key, o.key);
        }
    }

}
