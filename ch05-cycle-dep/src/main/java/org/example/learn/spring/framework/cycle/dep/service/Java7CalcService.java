package org.example.learn.spring.framework.cycle.dep.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("Java7")
@Component
public class Java7CalcService implements CalculateService {

    @Override
    public int sum(Integer... ints) {
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }

        return sum;
    }
}
