package xyz.parkh.doing.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class UtilTests {

    @Test
    public void givenUsingApache_whenGeneratingRandomStringBounded_thenCorrect() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        System.out.println(generatedString);
    }

}
