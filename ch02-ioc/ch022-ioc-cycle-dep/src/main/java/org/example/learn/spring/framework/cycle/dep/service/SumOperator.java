package org.example.learn.spring.framework.cycle.dep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Profile("Java8")
@Component
public class SumOperator {

    @Autowired
    CalculateService calculateService;

    public int op(Integer... ints) {
        return Stream.of(ints).reduce(0, Integer::sum);
    }
}
