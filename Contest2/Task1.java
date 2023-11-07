import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Task1 {
    public static void main(String[] args) {
        test();
    }

    static int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void test() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(System.out)) {

            String[] firstDateVal = reader.readLine().split(" ");
            String[] secondDateVal = reader.readLine().split(" ");

            int[] firstDate = new int[6];
            for (int i = 0; i < 6; i++) {
                firstDate[i] = Integer.parseInt(firstDateVal[i]);
            }
            int[] secondDate = new int[6];
            for (int i = 0; i < 6; i++) {
                secondDate[i] = Integer.parseInt(secondDateVal[i]);
            }
            var s1 = getTime(firstDate[0], firstDate[1], firstDate[2], firstDate[3], firstDate[4], firstDate[5]);
            var s2 = getTime(secondDate[0], secondDate[1], secondDate[2], secondDate[3], secondDate[4], secondDate[5]);
            var s3 = s2 - s1;
            var daysC = s3 / (60 * 60 * 24);
            var sec = s3 % (60 * 60 * 24);
            writer.println(daysC + " " + sec);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static long getTime(int y, int m, int d, int h, int min, int sec) {
        long sum = (long) y * 365 * 24 * 60 * 60;
        for (int i = 0; i < m - 1; i++) {
            sum += (long) daysInMonth[i] * 24 * 60 * 60;
        }
        sum += (long) d * 24 * 60 * 60;
        sum += (long) h * 60 * 60;
        sum += (long) min * 60;
        sum += sec;
        return sum;
    }
}
