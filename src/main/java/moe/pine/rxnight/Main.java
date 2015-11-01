package moe.pine.rxnight;

import java.util.Random;
import rx.*;
import rx.functions.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi, RxNight!!");

        // Observable.just
        Observable.just("yano").subscribe(new Action1<String>() {
            @Override
            public void call(String name) {
                System.out.println("Hello, " + name + "!"); // Hello yano
            }
        });

        // Observable.from
        String[] names = {"miyamori", "ema", "zuka"};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String name) {
                System.out.println("Hello, " + name + "!"); // Hello, miyamori!
            }
        });

        // Observable.empty
        Observable.<String>empty()
            .doOnCompleted(new Action0() {
                @Override
                public void call() {
                    System.out.println("Completed");
                }
            })
            .subscribe(new Action1<String>() {
                @Override
                public void call(String name) {
                    System.out.println("Hello, " + name + "!"); // not performed ...
                }
            });

        // Observable.range
        Observable.range(1, 10).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer value) {
                System.out.println(value); // 1, 2, ..., 10
            }
        });

        Observable
            .range(1, 10)
            .map(new Func1<Integer, Integer>() {
                @Override
                public Integer call(Integer value) {
                    return value * 2; // 2, 4, 6, ...
                }
            })
            .reduce(0, new Func2<Integer, Integer, Integer>() { // sum
                @Override
                public Integer call(Integer sum, Integer value) {
                    return sum + value;
                }
            })
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer value) {
                    System.out.println(value); // 110
                }
            });

        // repeat
        Observable
            .just(1)
            .repeat(3)
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer value) {
                    System.out.println(value); // 1, 1, 1
                }
            });

        Observable
            .from(new Boolean[]{ true, false })
            .repeat(3)
            .subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean value) {
                    System.out.println(value); // true, false, ...
                }
            });

        // create
        Observable<Integer> random = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> observer) {
                Random random = new Random();
                observer.onNext(random.nextInt(10)); // 0 ~ 9
                observer.onCompleted();
            }
        });

        random
            .repeat()
            .take(10)
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer value) {
                    System.out.println(value); // 7, 0, 8, ...
                }
            });
    }
}
