package com.hidiu.web.utils;

import org.testng.annotations.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * @title: 这里面纯粹本人测试，可删除
 * @projectName frame-web
 * @description: TODO
 * @author fancie/1084961@qq.com
 * @date 2022-02-05 12:01:01
 */
public class TestReactor {

    public static void main(String[] args){

        Integer[] array = new Integer[]{1,2,3,4,5,6};
        Flux.fromArray(array);
        List<Integer> list = Arrays.asList(array);
        Flux.fromIterable(list);
        Stream<Integer> stream = list.stream();
        Flux.fromStream(stream);

        Flux.just(1, 2, 3, 4, 5, 6).subscribe();
        System.out.println();
        Mono.just(1).subscribe(System.out::println);

        Flux.just(1, 2, 3, 4, 5, 6).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Completed!"));

        Mono.error(new Exception("some error")).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Completed!")
        );
    }

    private Flux<Integer> generateFluxFrom1To6() {
        return Flux.just(1, 2, 3, 4, 5, 6);
    }

    private Mono<Integer> generateMonoWithError() {
        return Mono.error(new Exception("some error"));
    }
    @Test
    public void testViaStepVerifier() {
        StepVerifier.create(generateFluxFrom1To6())
                .expectNext(1, 2, 3, 4, 5, 6)
                .expectComplete()
                .verify();
        StepVerifier.create(generateMonoWithError())
                .expectErrorMessage("some error")
                .verify();
    }

}
