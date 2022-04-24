package org.moodminds.reactor.adapter;

import org.moodminds.util.AbstractAssociation;
import org.moodminds.util.KeyValue;
import org.moodminds.util.Subscriber;
import reactor.core.CoreSubscriber;

import java.util.Iterator;
import java.util.concurrent.Flow;

import static java.util.Objects.requireNonNull;
import static org.moodminds.reactor.adapter.ReactorSubscriptionAdapter.toReactive;
import static org.moodminds.util.Subscriber.context;

/**
 * The Reactor's {@link org.reactivestreams.Subscriber} wrapping implementation of the Origin's {@link Subscriber} interface.
 *
 * @param <V> the type of the emitting values
 * @param <E> the type of possible exception that might be thrown
 */
public class SubscriberAdapter<V, E extends Exception> implements Subscriber<V, E> {

    /**
     * The wrapped {@link org.reactivestreams.Subscriber} holder field.
     */
    protected final org.reactivestreams.Subscriber<V> subscriber;

    /**
     * Construct the object with the specified {@link org.reactivestreams.Subscriber}.
     *
     * @param subscriber the specified {@link org.reactivestreams.Subscriber}
     */
    public SubscriberAdapter(org.reactivestreams.Subscriber<V> subscriber) {
        this.subscriber = requireNonNull(subscriber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context getContext() {
        return subscriber instanceof CoreSubscriber ? new ContextAdapter(((CoreSubscriber<V>) subscriber).currentContext()) : context();
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
    public void onException(E exception) {
        subscriber.onError(exception);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onThrowable(Throwable throwable) {
        subscriber.onError(throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscriber.onSubscribe(toReactive(subscription));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNext(V item) {
        subscriber.onNext(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onComplete() {
        subscriber.onComplete();
    }


    /**
     * The Reactor's {@link reactor.util.context.Context} wrapping implementation of the Origin's {@link Context}.
     */
    public static class ContextAdapter extends AbstractAssociation<Object, Object> implements Context {

        /**
         * The wrapped {@link reactor.util.context.Context} holder field.
         */
        private final reactor.util.context.Context context;

        /**
         * Construct the object with the specified {@link reactor.util.context.Context}.
         *
         * @param context the specified {@link reactor.util.context.Context}
         */
        private ContextAdapter(reactor.util.context.Context context) {
            this.context = requireNonNull(context);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<KeyValue<Object, Object>> iterator() {
            return context.stream().map(KeyValue::keyValue).iterator();
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
        public Object get(Object id) {
            return context.get(id);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Context set(Object key, Object value) {
            return new ContextAdapter(context.put(key, value));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Context delete(Object key) {
            return new ContextAdapter(context.delete(key));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String toStringThis() {
            return "(this Context)";
        }
    }

    /**
     * Return the adapter for the specified {@link org.reactivestreams.Subscriber}.
     *
     * @param subscriber the specified {@link org.reactivestreams.Subscriber}
     * @param <V> the type of the emitting values
     * @param <E> the type of possible exception that might be thrown
     * @return the adapter for the specified {@link org.reactivestreams.Subscriber}
     */
    public static <V, E extends Exception> SubscriberAdapter<V, E> fromReactive(org.reactivestreams.Subscriber<V> subscriber) {
        return new SubscriberAdapter<>(subscriber);
    }
}
