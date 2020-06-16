package com.pohinian.nis.generator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {

    static int[] divideByPower2(int input, int k) {
        int i = 1 << k;
        log.info("{}", i);
        int[] r = new int[2];
        r[0] = input / i;
        r[1] = input % i;
        return r;
    }

    @org.junit.jupiter.api.Test
    public void test() {
        int[] r;
        r = divideByPower2(5, 1);
        log.info("몫 : {}, 나머지: {}", r[0], r[1]);
        r = divideByPower2(5, 2);
        log.info("몫 : {}, 나머지: {}", r[0], r[1]);
        r = divideByPower2(341, 5);
        log.info("몫 : {}, 나머지: {}", r[0], r[1]);
        r = divideByPower2(0, 0);
        log.info("몫 : {}, 나머지: {}", r[0], r[1]);
        r = divideByPower2(0, 1);
        log.info("몫 : {}, 나머지: {}", r[0], r[1]);
    }

}
