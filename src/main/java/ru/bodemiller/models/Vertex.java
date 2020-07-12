package ru.bodemiller.models;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Vertex<T> {
    private T id;

    public Vertex(T id) {
        this.id = id;
    }
}

