package org.moodminds.reactor.adapter;

import org.moodminds.lang.Publishable;
import org.moodminds.util.Subscriber;
import org.reactivestreams.Publisher;
import reactor.core.CorePublisher;

import static java.util.Objects.requireNonNull;
import static org.moodminds.reactor.adapter.ReactorSubscriberAdapter.toReactor;

/**
 * The Reactor's {@link Publisher} wrapping implementation of the Origin's {@link Publishable} interface.
 *
 * @param <V> the type of the emitting values
 * @param <E> the type of possible exception that might be thrown
 */
public class PublishableAdapter<V, E extends Exception> implements Publishable<V, E> {

    /**
     * The wrapped {@link Publisher} holder field.
     */
    protected final Publisher<V> publisher;

    /**
     * Construct the object with the specified {@link Publisher}.
     *
     * @param publisher the specified {@link Publisher} to wrap
     */
    public PublishableAdapter(Publisher<V> publisher) {
        this.publisher = requireNonNull(publisher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribe(Subscriber<? super V, ? super E> subscriber) {
        ReactorSubscriberAdapter<? super V, ? super E> reactorSubscriber = toReactor(subscriber);
        if (publisher instanceof CorePublisher)
            ((CorePublisher<V>) publisher).subscribe(reactorSubscriber);
        else publisher.subscribe(reactorSubscriber);
    }

    /**
     * Return the adapter for the specified {@link Publisher}.
     *
     * @param publisher the specified {@link Publisher}
     * @param <V> the type of the emitting values
     * @param <E> the type of possible exception that might be thrown
     * @return the adapter for the specified {@link Publisher}.
     */
    public static <V, E extends Exception> PublishableAdapter<V, E> fromReactive(Publisher<V> publisher) {
        return new PublishableAdapter<>(publisher);
    }
}
