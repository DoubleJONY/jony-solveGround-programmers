package com.doublejony.practice.dynamic;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 등굣길
 * <p>
 * 계속되는 폭우로 일부 지역이 물에 잠겼습니다. 물에 잠기지 않은 지역을 통해 학교를 가려고 합니다. 집에서 학교까지 가는 길은 m x n 크기의 격자모양으로 나타낼 수 있습니다.
 *
 * 아래 그림은 m = 4, n = 3 인 경우입니다.
 *
 * image0.png
 *
 * 가장 왼쪽 위, 즉 집이 있는 곳의 좌표는 (1, 1)로 나타내고 가장 오른쪽 아래, 즉 학교가 있는 곳의 좌표는 (m, n)으로 나타냅니다.
 *
 * 격자의 크기 m, n과 물이 잠긴 지역의 좌표를 담은 2차원 배열 puddles이 매개변수로 주어집니다. 오른쪽과 아래쪽으로만 움직여 집에서 학교까지 갈 수 있는 최단경로의 개수를 1,000,000,007로 나눈 나머지를 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 격자의 크기 m, n은 1 이상 100 이하인 자연수입니다.
 * m과 n이 모두 1인 경우는 입력으로 주어지지 않습니다.
 * 물에 잠긴 지역은 0개 이상 10개 이하입니다.
 * 집과 학교가 물에 잠긴 경우는 입력으로 주어지지 않습니다.
 * 입출력 예
 * m	n	puddles	return
 * 4	3	[[2, 2]]	4
 */
@RunWith(DataProviderRunner.class)
public class Dynamic3 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        4,
                        3,
                        new int[][] {{2, 2}},
                        4
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void useForeach(int m, int n, int[][] puddles, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(m, n, puddles), timer.stop());
    }

    class Solution {
        public int solution(int m, int n, int[][] puddles) {
            int answer = 0;

            int[][] map  = new int[n][m];

            for (int[] puddle : puddles) {
                map[puddle[1] - 1][puddle[0] - 1] = -1;
            }

            map[0][0] = 1;
            for (int i = 0; i < n-1; i++) {
                for (int j = 0; j < m-1; j++) {
                    if(map[i][j] == -1) {
                        continue;
                    }
                    if(map[i][j+1] != -1) {
                        map[i][j+1] += 1;
                    }
                    if(map[i+1][j] != -1) {
                        map[i+1][j] += 1;
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                if(map[i][m-1] != -1) {
                    answer += map[i][m-1];
                }
            }
            for (int i = 0; i < m; i++) {
                if(map[n-1][i] != -1) {
                    answer += map[n-1][i];
                }
            }

            return answer;
        }
    }
}
