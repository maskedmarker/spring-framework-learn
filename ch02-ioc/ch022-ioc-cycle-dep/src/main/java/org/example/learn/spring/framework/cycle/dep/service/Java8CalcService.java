package org.example.learn.spring.framework.cycle.dep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("Java8")
@Component
public class Java8CalcService implements CalculateService {

    @Autowired
    private SumOperator sumOperator;

    @Override
    public int sum(Integer... ints) {
        return sumOperator.op(ints);
    }
}
