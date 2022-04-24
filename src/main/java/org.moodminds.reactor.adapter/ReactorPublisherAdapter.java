package org.moodminds.reactor.adapter;

import org.moodminds.lang.Publishable;
import org.reactivestreams.Subscriber;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;

import static java.util.Objects.requireNonNull;
import static org.moodminds.reactor.adapter.SubscriberAdapter.fromReactive;

/**
 * The Origin's {@link Publishable} wrapping implementation of the Reactor's {@link CorePublisher} interface.
 *
 * @param <V> the type of the emitting values
 * @param <E> the type of possible exception that might be thrown
 */
public class ReactorPublisherAdapter<V, E extends Exception> implements CorePublisher<V> {

    /**
     * The wrapped {@link Publishable} holder field.
     */
    protected final Publishable<V, E> publishable;

    /**
     * Construct the object with the specified {@link Publishable}.
     *
     * @param publishable the specified {@link Publishable}
     */
    public ReactorPublisherAdapter(Publishable<V, E> publishable) {
        this.publishable = requireNonNull(publishable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribe(CoreSubscriber<? super V> coreSubscriber) {
        subscribe((Subscriber<? super V>) coreSubscriber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribe(Subscriber<? super V> s) {
        publishable.subscribe(fromReactive(s));
    }


    /**
     * Return the adapter for the specified {@link Publishable}.
     *
     * @param publishable the specified {@link Publishable}
     * @param <V> the type of the emitting values
     * @param <E> the type of possible exception that might be thrown
     * @return the adapter for the specified {@link Publishable}.
     */
    public static <V, E extends Exception> ReactorPublisherAdapter<V, E> toReactor(Publishable<V, E> publishable) {
        return new ReactorPublisherAdapter<>(publishable);
    }
}
