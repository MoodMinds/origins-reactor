package org.moodminds.reactor.adapter;

import org.reactivestreams.Subscription;

import java.util.concurrent.Flow;

import static java.util.Objects.requireNonNull;

/**
 * The {@link Flow.Subscription} wrapping implementation of the Reactor's {@link Subscription} interface.
 */
public class ReactorSubscriptionAdapter implements Subscription {

    /**
     * The wrapped {@link Flow.Subscription} holder field.
     */
    protected final Flow.Subscription subscription;

    /**
     * Construct the object with the specified {@link Flow.Subscription}.
     *
     * @param subscription the specified {@link Flow.Subscription}
     */
    public ReactorSubscriptionAdapter(Flow.Subscription subscription) {
        this.subscription = requireNonNull(subscription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void request(long n) {
        subscription.request(n);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {
        subscription.cancel();
    }


    /**
     * Return the adapter for the specified {@link Flow.Subscription}.
     *
     * @param subscription the specified {@link Flow.Subscription}.
     * @return the adapter for the specified {@link Flow.Subscription}
     */
    public static ReactorSubscriptionAdapter toReactive(Flow.Subscription subscription) {
        return new ReactorSubscriptionAdapter(subscription);
    }
}
