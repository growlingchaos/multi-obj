package org.damage.multiobj.concrete;

import org.springframework.stereotype.Component;

@Component
public class Multiplier implements Multiplication<Integer> {
    @Override
    public Integer multiply(Integer first, Integer second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Null can not be multiplied");
        }
        return first * second;
    }
}
