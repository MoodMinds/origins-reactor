package org.moodminds.reactor.adapter;

import org.moodminds.util.Subscriber;
import reactor.util.context.Context;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static org.moodminds.util.Cast.cast;

/**
 * The {@link Subscriber.Context} wrapping implementation of the Reactor's {@link Context} interface.
 */
public class ReactorContextAdapter implements Context {

    /**
     * The wrapped {@link Subscriber.Context} holder field.
     */
    protected final Subscriber.Context context;

    /**
     * Construct the object with the specified {@link Subscriber.Context} object.
     *
     * @param context the specified {@link Subscriber.Context} object
     */
    public ReactorContextAdapter(Subscriber.Context context) {
        this.context = requireNonNull(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T get(Object o) {
        return cast(context.get(o));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasKey(Object o) {
        return context.containsKey(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context put(Object o, Object o1) {
        return ReactorContextAdapter.toReactor(context.set(o, o1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context delete(Object o) {
        return ReactorContextAdapter.toReactor(context.delete(o));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return context.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Map.Entry<Object, Object>> stream() {
        return context.stream().map(kv -> new SimpleImmutableEntry<>(kv.getKey(), kv.getValue()));
    }


    /**
     * Return the adapter for the specified {@link Subscriber.Context}.
     *
     * @param context the specified {@link Subscriber.Context}
     * @return the adapter for the specified {@link Subscriber.Context}.
     */
    public static ReactorContextAdapter toReactor(Subscriber.Context context) {
        return new ReactorContextAdapter(context);
    }
}
