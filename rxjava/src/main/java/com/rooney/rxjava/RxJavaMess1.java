package com.rooney.rxjava;

import rx.Observable;
import rx.functions.Action1;

/**
 * Questions
 * <li>how to have multi subs</li>
 */
public class RxJavaMess1 {

    public static void main(String[] args) {
        hello("jim", "bob", "Arthur");
    }


    public static void observableFromSource() {
        //set up source
        Observable<String> o1 = Observable.from(new String[] {"a", "b", "c"});
        Observable<String> o2 = Observable.just("one element");


    }

    public static void hello(String... names) {
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }
        });
    }
}
