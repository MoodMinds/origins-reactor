package org.moodminds.reactor.adapter;

import org.moodminds.util.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

import static java.util.Objects.requireNonNull;
import static org.moodminds.reactor.adapter.SubscriptionAdapter.fromReactive;

/**
 * The Origin's {@link Subscriber} wrapping implementation of the Reactor's {@link CoreSubscriber} interface.
 *
 * @param <V> the type of the emitting values
 * @param <E> the type of possible exception that might be thrown
 */
public class ReactorSubscriberAdapter<V, E extends Exception> implements CoreSubscriber<V> {

    /**
     * The wrapped {@link Subscriber} holder field.
     */
    protected final Subscriber<V, E> subscriber;

    /**
     * Construct the object with the specified {@link Subscriber}.
     *
     * @param subscriber the specified {@link Subscriber}
     */
    public ReactorSubscriberAdapter(Subscriber<V, E> subscriber) {
        this.subscriber = requireNonNull(subscriber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context currentContext() {
        return ReactorContextAdapter.toReactor(subscriber.getContext());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSubscribe(Subscription subscription) {
        subscriber.onSubscribe(fromReactive(subscription));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNext(V v) {
        subscriber.onNext(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onComplete() {
        subscriber.onComplete();
    }


    /**
     * Return the adapter for the specified the specified {@link Subscriber}.
     *
     * @param subscriber the specified {@link Subscriber}
     * @param <V> the type of the emitting values
     * @param <E> the type of possible exception that might be thrown
     * @return the adapter for the specified the specified {@link Subscriber}
     */
    public static <V, E extends Exception> ReactorSubscriberAdapter<V, E> toReactor(Subscriber<V, E> subscriber) {
        return new ReactorSubscriberAdapter<>(subscriber);
    }
}
