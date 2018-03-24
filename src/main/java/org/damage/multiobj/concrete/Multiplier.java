package org.damage.multiobj.concrete;

import org.springframework.stereotype.Component;

@Component
public class Multiplier implements Multiplication<Integer> {
    @Override
    public Integer multiply(Integer first, Integer second) {
        return first * second;
    }
}
