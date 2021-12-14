package com.doublejony.backjun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> input = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp;

        while ((temp = br.readLine()) != null && !temp.isEmpty()) {
            input.add(temp);
        }
        String answer = new Main().solution(input.toArray(new String[input.size()]));

        System.out.println(answer);
    }

    public String solution(String[] input) {

        String[] admin = input[2].split(" ");

        long major = Long.parseLong(admin[0]);
        long minor = Long.parseLong(admin[1]);

        long answer = 0;

        String[] amount = input[1].split(" ");

        for (String s : amount) {
            long a = Long.parseLong(s) - major;
            answer += a > 0 ? a / minor + ((a % minor == 0) ? 0 : 1) + 1 : 1;
        }

        return Long.toString(answer);
    }
}
