package org.moodminds.reactor.adapter;

import org.reactivestreams.Subscription;

import java.util.concurrent.Flow;

import static java.util.Objects.requireNonNull;

/**
 * The {@link Subscription} wrapping implementation of the Reactor's {@link Flow.Subscription} interface.
 */
public class SubscriptionAdapter implements Flow.Subscription {

    /**
     * The wrapped {@link Subscription} holder field.
     */
    protected final Subscription subscription;

    /**
     * Construct the object with the specified {@link Subscription}.
     *
     * @param subscription the specified {@link Subscription}
     */
    public SubscriptionAdapter(Subscription subscription) {
        this.subscription = requireNonNull(subscription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void request(long n) {
        this.subscription.request(n);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {
        this.subscription.cancel();
    }


    /**
     * Return the adapter for the specified {@link Subscription}.
     *
     * @param subscription the specified {@link Subscription}.
     * @return the adapter for the specified {@link Subscription}
     */
    public static SubscriptionAdapter fromReactive(Subscription subscription) {
        return new SubscriptionAdapter(subscription);
    }
}
