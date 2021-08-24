package com.doublejony.stackNQueue;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 프린터
 * <p>
 * 일반적인 프린터는 인쇄 요청이 들어온 순서대로 인쇄합니다. 그렇기 때문에 중요한 문서가 나중에 인쇄될 수 있습니다. 이런 문제를 보완하기 위해 중요도가 높은 문서를 먼저 인쇄하는 프린터를 개발했습니다. 이 새롭게 개발한 프린터는 아래와 같은 방식으로 인쇄 작업을 수행합니다.
 * <p>
 * 1. 인쇄 대기목록의 가장 앞에 있는 문서(J)를 대기목록에서 꺼냅니다.
 * 2. 나머지 인쇄 대기목록에서 J보다 중요도가 높은 문서가 한 개라도 존재하면 J를 대기목록의 가장 마지막에 넣습니다.
 * 3. 그렇지 않으면 J를 인쇄합니다.
 * 예를 들어, 4개의 문서(A, B, C, D)가 순서대로 인쇄 대기목록에 있고 중요도가 2 1 3 2 라면 C D A B 순으로 인쇄하게 됩니다.
 * <p>
 * 내가 인쇄를 요청한 문서가 몇 번째로 인쇄되는지 알고 싶습니다. 위의 예에서 C는 1번째로, A는 3번째로 인쇄됩니다.
 * <p>
 * 현재 대기목록에 있는 문서의 중요도가 순서대로 담긴 배열 priorities와 내가 인쇄를 요청한 문서가 현재 대기목록의 어떤 위치에 있는지를 알려주는 location이 매개변수로 주어질 때, 내가 인쇄를 요청한 문서가 몇 번째로 인쇄되는지 return 하도록 solution 함수를 작성해주세요.
 * <p>
 * 제한사항
 * 현재 대기목록에는 1개 이상 100개 이하의 문서가 있습니다.
 * 인쇄 작업의 중요도는 1~9로 표현하며 숫자가 클수록 중요하다는 뜻입니다.
 * location은 0 이상 (현재 대기목록에 있는 작업 수 - 1) 이하의 값을 가지며 대기목록의 가장 앞에 있으면 0, 두 번째에 있으면 1로 표현합니다.
 * 입출력 예
 * priorities	location	return
 * [2, 1, 3, 2]	2	1
 * [1, 1, 9, 1, 1, 1]	0	5
 * 입출력 예 설명
 * 예제 #1
 * <p>
 * 문제에 나온 예와 같습니다.
 * <p>
 * 예제 #2
 * <p>
 * 6개의 문서(A, B, C, D, E, F)가 인쇄 대기목록에 있고 중요도가 1 1 9 1 1 1 이므로 C D E F A B 순으로 인쇄합니다.
 */
@RunWith(DataProviderRunner.class)
public class Stack2 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{2, 1, 3, 2},
                        2,
                        1
                },
                {
                        new int[]{1, 1, 9, 1, 1, 1},
                        0,
                        5
                },
                {
                        new int[]{1, 1, 9, 1, 1, 1},
                        1,
                        6
                },
                {
                        new int[]{1, 2, 9, 1, 1, 1, 2, 3, 4},
                        4,
                        8
                }
        };
        // @formatter:on
    }

    @Getter
    public static class DocumentSet {
        int index;
        int prioritie;

        public DocumentSet(int index, int prioritie) {
            this.index = index;
            this.prioritie = prioritie;
        }

        public int getIndex() {
            return index;
        }

        public int getPrioritie() {
            return prioritie;
        }
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void loopApi(int[] priorities, int location, int expected) {

        Stopwatch timer = Stopwatch.createStarted();

        Map<Integer, Integer> prioritiesList = IntStream.range(0, priorities.length).boxed().collect(Collectors.toMap(i -> i, i -> priorities[i], (a, b) -> b));

        int answer = 0;
        ArrayBlockingQueue<DocumentSet> queue = new ArrayBlockingQueue<>(prioritiesList.size());

        for (int i = 0; i < prioritiesList.size(); i++) {
            queue.add(new DocumentSet(i, prioritiesList.get(i)));
        }

        while (!queue.isEmpty()) {
            int k = 0;
            int l = 0;
            for (int i = 0; i < prioritiesList.size(); i++) {
                if (prioritiesList.get(i+l) != null) {
                    if (queue.peek().getPrioritie() < prioritiesList.get(i+l)) {
                        for (int j = 0; j < i - k; j++) {
                            queue.add(queue.poll());
                        }
                        i = 0;
                        k = 0;
                        l++;
                    }
                } else {
                    k++;
                }
            }
            if (queue.peek().getIndex() == location) {
                answer = (priorities.length + 1) - queue.size();
                break;
            }
            prioritiesList.remove(queue.peek().getIndex());
            queue.poll();
        }

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void useQueue(int[] priorities, int location, int expected) {

        Stopwatch timer = Stopwatch.createStarted();

        int answer = 1;
        PriorityQueue<Object> p = new PriorityQueue<>(Collections.reverseOrder());

        Arrays.stream(priorities).forEach(p::add);

        while (!p.isEmpty()) {
            for (int i = 0; i < priorities.length; i++) {
                if (priorities[i] == (int) p.peek()) {
                    if (i == location) {
                        p.clear();
                        break;
                    }
                    p.poll();
                    answer++;
                }
            }
        }

        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, answer, timer.stop());
    }
}
