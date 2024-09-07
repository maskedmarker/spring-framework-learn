package org.example.learn.spring.hello.context.annotation.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Profile("Java8")
@Component
public class SumOperator {

    public int op(Integer... ints) {
        return Stream.of(ints).reduce(0, Integer::sum);
    }
}
