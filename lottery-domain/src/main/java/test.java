import java.util.*;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        if (n == 0 || m == 0 || k == 0) {
            System.out.println(0);
            return;
        }
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        long ans = 0;
        long[] dp = new long[n];
        Arrays.fill(dp, -1);
        int lastUsed = -1;
        for (int i = 0; i < m; i++) {
            long[] next = new long[n];
            long max = 0;
            int maxIndex = -1;
            for (int j = 0; j < n; j++) {
                if (dp[j] >= 0 && j != lastUsed) {
                    if (i == m-1 || k == 1) {
                        ans = Math.max(ans, dp[j] + a[j]);
                    }
                    if (dp[j] > max) {
                        boolean canUse = true;
                        for (int p = 0; p < n; p++) {
                            if (p != j && dp[p] >= 0 && dp[p] == dp[j]) {
                                canUse = false;
                                break;
                            }
                        }
                        if (canUse) {
                            max = dp[j];
                            maxIndex = j;
                        }
                    }
                }
                if (i == 0 || j != maxIndex) {
                    next[j] = Math.max(next[j], dp[j] + a[j]);
                } else if (lastUsed != maxIndex || dp[lastUsed] < max) {
                    next[j] = Math.max(next[j], dp[j] + a[j]);
                }
            }
            for (int j = 0; j < n; j++) {
                if (dp[j] >= 0 && j == maxIndex) {
                    if (i == m-1 || k == 1) {
                        ans = Math.max(ans, dp[j] + a[j]);

                    }
                    dp[j] += a[j];
                    if (k > 1) {
                        for (int p = 0; p < n; p++) {
                            if (p != j && dp[p] >= 0 && dp[p] == dp[j]) {
                                dp[p] = -1;
                            }
                        }
                    }
                    lastUsed = j;
                } else {
                    dp[j] = next[j];
                }
            }
        }
        System.out.println(ans);

    }

}
