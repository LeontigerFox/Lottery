package com.banana69.lottery.test.common;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/13/17:36
 * @description:
 */
public class IdxTest {
    @Test
    public void test_idx() {
        int hashCode = 0;
        for (int i = 0; i < 5; i++) {
            hashCode = i * 0x61c88647 + 0x61c88647;
            int idx = hashCode & 4;
            System.out.println("斐波那契散列：" + idx + " 普通散列：" + (String.valueOf(i).hashCode() & 15));
        }
    }
}
