package com.example.demo;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.seasar.doma.DomaException;

public class DemoPublisher<T> implements Publisher<T>, Subscription {

    private final Supplier<Stream<T>> supplier;
    private Stream<T> stream;
    private Iterator<T> it;
    private Subscriber<? super T> s;

    public DemoPublisher(final Supplier<Stream<T>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public void subscribe(final Subscriber<? super T> s) {
        this.s = s;
        this.s.onSubscribe(this);
    }

    @Override
    public void request(final long n) {
        try {
            if (it == null) {
                stream = supplier.get();
                it = stream.iterator();
            }
            for (long i = 0L; i < n && it.hasNext(); i++) {
                s.onNext(it.next());
            }
            if (it.hasNext() == false) {
                stream.close();
                s.onComplete();
            }
        } catch (final DomaException e) {
            s.onError(e);
            if (stream != null) {
                stream.close();
            }
        }
    }

    @Override
    public void cancel() {
        if (stream != null) {
            stream.close();
        }
    }
}
