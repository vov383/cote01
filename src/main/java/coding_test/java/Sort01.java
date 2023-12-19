package coding_test.java;

import java.util.*;

public class Sort01 {

    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        int i;
        int j;
        int k;
        for(int c=0; c<commands.length; c++){
            i = commands[c][0];
            j = commands[c][1];
            k = commands[c][2];

            // 배열 일부분 추출
            int[] temp= Arrays.copyOfRange(array, i - 1, j);

            // 부분 배열 정렬
            Arrays.sort(temp);

            // 정렬된 배열에서 k번째 원소 찾기
            answer[c] = temp[k -1];

        }

        return answer;
    }
}

