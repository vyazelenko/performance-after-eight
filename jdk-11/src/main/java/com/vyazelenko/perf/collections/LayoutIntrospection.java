package com.vyazelenko.perf.collections;

import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.System.out;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

public class LayoutIntrospection {
    public static void main(String[] args) {
        out.println("=== Lists ===");
        footprint(List.of());
        footprint(emptyList());

        footprint(List.of(1));
        footprint(singletonList(1));

        footprint(List.of(1, 2));
        footprint(new ArrayList<>(List.of(1, 2)));
        footprint(new LinkedList<>(List.of(1, 2)));
        footprint(unmodifiableList(new ArrayList<>(List.of(1, 2))));
        footprint(unmodifiableList(new LinkedList<>(List.of(1, 2))));

        footprint(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0));
        footprint(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));
        footprint(new LinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));
        footprint(unmodifiableList(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))));
        footprint(unmodifiableList(new LinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))));

        out.println("=== Sets ===");
        footprint(Set.of());
        footprint(emptySet());

        footprint(Set.of(1));
        footprint(singleton(1));

        footprint(Set.of(1, 2));
        footprint(new HashSet<>(List.of(1, 2)));
        footprint(new LinkedHashSet<>(List.of(1, 2)));
        footprint(new TreeSet<>(List.of(1, 2)));
        footprint(unmodifiableSet(new HashSet<>(List.of(1, 2))));
        footprint(unmodifiableSet(new LinkedHashSet<>(List.of(1, 2))));
        footprint(unmodifiableSet(new TreeSet<>(List.of(1, 2))));

        footprint(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0));
        footprint(new HashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));
        footprint(new LinkedHashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));
        footprint(new TreeSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)));
        footprint(unmodifiableSet(new HashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))));
        footprint(unmodifiableSet(new LinkedHashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))));
        footprint(unmodifiableSet(new TreeSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))));
    }

    private static void footprint(Object o) {
        out.println(GraphLayout.parseInstance(o).toFootprint());
    }
}
