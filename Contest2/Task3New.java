import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;


public class Task3New {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(System.out)) {
            String[] info = reader.readLine().split(" ");
            int n = Integer.parseInt(info[0]);
            int m = Integer.parseInt(info[1]);
            int q = Integer.parseInt(info[2]);
            info = reader.readLine().split(" ");
            Map<Integer, String> map = new HashMap<>();
            for (int i = 0; i < info.length; i++) {
                map.put(i, info[(int) i]);
            }

            long[][] nums = new long[n][m];
            for (int i = 0; i < n; i++) {
                info = reader.readLine().split(" ");
                for (int j = 0; j < info.length; j++) {
                    nums[i][j] = Long.parseLong(info[j]);
                }
            }
            Map<String, List<Long>> borders = new HashMap<>();
            for (int i = 0; i < q; i++) {
                info = reader.readLine().split(" ");
                long val = Long.parseLong(info[2]);
                String[] finalInfo = info;
                borders.computeIfAbsent(info[0], k -> new ArrayList<>(Arrays.asList(Objects.equals(finalInfo[1], ">") ? val : Long.MIN_VALUE, Objects.equals(finalInfo[1], ">") ? Long.MAX_VALUE : val)))
                        .set(Objects.equals(info[1], ">") ? 0 : 1, Objects.equals(info[1], ">") ? Math.max(val, borders.get(info[0]).get(0)) : Math.min(val, borders.get(info[0]).get(1)));
            }
            long sum = 0;
            for (int i = 0; i < n; i++) {
                long s = 0;
                for (int j = 0; j < m; j++) {
                    long val = nums[i][j];
                    s += val;
                    var name = map.get(j);
                    if (borders.containsKey(name) && !check(val, borders.get(name))) {
                        s = 0;
                        break;
                    }
                }
                sum += s;
            }
            writer.print(sum);
            writer.flush();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean check(long val, List<Long> border) {
        return val > border.get(0) && val < border.get(1);
    }
}
//4 4 0
//a s d
//1000000000 1000000000 1000000000 1000000000
//1000000000 1000000000 1000000000 1000000000
//1000000000 1000000000 1000000000 1000000000
//1000000000 1000000000 1000000000 1000000000