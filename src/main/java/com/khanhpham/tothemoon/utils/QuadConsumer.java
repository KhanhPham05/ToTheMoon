package com.khanhpham.tothemoon.utils;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.state.properties.Property;

@FunctionalInterface
@SuppressWarnings("unchecked")
public interface QuadConsumer<A, B, C, D> {
    void accept(A a, B b, C c, D d);

    default <E extends Comparable<E>, F extends Comparable<F>, G extends Comparable<G>, H extends Comparable<H>> void acceptPropertyPair(Property<E> propertyA, E a, Property<F> bProperty, F b, Property<G> cProperty, G c, Property<H> dProperty, H d) {
        accept((A) Pair.of(propertyA, a), (B) Pair.of(bProperty, b), (C) Pair.of(cProperty, c), (D) Pair.of(dProperty, d));
    }
}
