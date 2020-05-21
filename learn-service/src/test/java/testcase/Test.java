package testcase;


import java.util.*;

public class Test  {
    public static void main(String[] args) throws Exception {
        String s = "";

        int res[] = divingBoard(1, 2 , 3);
    }

//    public static int[] divingBoard(int shorter, int longer, int k) {
//        if (k == 0) return new int[]{};
//
//        int temp[][] = new int[k + 1][k + 1];
//
//        temp[1] = new int[]{shorter, longer};
//
//        for(int i = 2 ; i <= k ;i++) {
//            int j = 0;
//            for(; j < i; j++) {
//                temp[i][j] = temp[i - 1][j] + shorter;
//            }
//            temp[i][j] = i * longer;
//        }
//
//        return temp[k];
//    }

    public static int[] divingBoard(int shorter, int longer, int k) {
        int res[] = new int[k + 1];

        if (k == 0) return res;

        if (shorter == longer) return new int[]{shorter * k};

        int shorterK = k;
        int longerK = k - shorterK;

        int i = 0;
        while (longerK <= k) {
            res[i++] = shorter * shorterK + longer * longerK;
            shorterK--;
            longerK++;
        }

        return res;
    }
}