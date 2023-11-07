import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Task2 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] NK = reader.readLine().split(" ");
            String[] borders = reader.readLine().split(" ");

            int n = Integer.parseInt(NK[0]);
            int k = Integer.parseInt(NK[1]);
            int[] boardLengths = Arrays.stream(borders)
                    .mapToInt(Integer::parseInt).toArray();
            Arrays.sort(boardLengths);

            findMin(n, k, boardLengths);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void findMin(int n, int k, int[] boardLengths) {
        int remainingBoards = n - k;
        int minUnevenness = boardLengths[boardLengths.length - 1] - boardLengths[0];
        for (int i = 0; i < n - remainingBoards + 1; i++) {
            int unevenness = boardLengths[i + remainingBoards - 1] - boardLengths[i];
            minUnevenness = Math.min(minUnevenness, unevenness);
        }
        System.out.println(minUnevenness);
    }
}