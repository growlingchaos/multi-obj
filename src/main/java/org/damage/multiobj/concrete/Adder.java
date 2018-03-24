package org.damage.multiobj.concrete;

import org.springframework.stereotype.Component;

@Component
public class Adder implements Addition<Integer> {
    @Override
    public Integer add(Integer first, Integer second) {
        return first + second;
    }
}
