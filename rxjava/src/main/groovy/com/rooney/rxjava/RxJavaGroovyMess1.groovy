package com.rooney.rxjava;

import rx.Observable;
import rx.functions.Action1;

/**
 * This example shows a custom Observable that blocks
 * when subscribed to (does not spawn an extra thread).
 */
def customObservableBlocking() {
    return Observable.create({ aSubscriber ->
        50.times { i ->
            if (!aSubscriber.unsubscribed) {
                aSubscriber.onNext("value_${i}")
            }
        }
        // after sending all values we complete the sequence
        if (!aSubscriber.unsubscribed) {
            aSubscriber.onCompleted()
        }
    })
}

customObservableBlocking().subscribe { println(it) }

/**
 * This example shows a custom Observable that does not block
 * when subscribed to as it spawns a separate thread.
 */
def customObservableNonBlocking() {
    return Observable.create({ subscriber ->
        Thread.start {
            for (i in 0..<75) {
                if (subscriber.unsubscribed) {
                    return
                }
                subscriber.onNext("value_${i}")
            }
            // after sending all values we complete the sequence
            if (!subscriber.unsubscribed) {
                subscriber.onCompleted()
            }
        }
    } as Observable.OnSubscribe)
}

// To see output:
customObservableNonBlocking().subscribe { println(it) }