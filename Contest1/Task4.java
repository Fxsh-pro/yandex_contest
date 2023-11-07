import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Task4 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] info = reader.readLine().split(" ");
            int k = Integer.parseInt(info[0]);
            int n = Integer.parseInt(info[1]);
            int m = Integer.parseInt(info[2]);
            Map<Long, Pair> intervals = new HashMap<>();
            for (int i = 0; i < n; i++) {
                String[] excavationsInfo = reader.readLine().split(" ");
                long day = Long.parseLong(excavationsInfo[0]);
                long number = Long.parseLong(excavationsInfo[1]);
                var interval = intervals.get(number);
                if (interval == null) {
                    intervals.put(number, new Pair(day, day));
                } else {
                    interval.end = day;
                }
            }
            var list = intervals.entrySet().stream()
                    .sorted((entry1, entry2) -> {
                        long time1 = entry1.getValue().getTime();
                        long time2 = entry2.getValue().getTime();
                        return Long.compare(time2, time1);
                    })
                    .toList();

            m -= list.size();
            if (m < 0) {
                System.out.println(-1);
                return;
            }
            if (k == 1) {
                var res = intervals.entrySet().stream().findFirst().get().getValue().getTime();
                if (res <= m) {
                    System.out.println(0);
                    return;
                }
                System.out.println(Math.max(res - m, 0));
                return;
            }

            list = list.stream().filter(entry -> entry.getValue().getTime() != 0).toList();
            int sum = 0;
            for (var entry : list) {
                if (m > 0) {
                    m--;
                    continue;
                }
                sum += entry.getValue().getTime();
            }
            System.out.println(sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Pair {
        long start, end;

        public Pair(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public long getTime() {
            return end - start;
        }

        @Override
        public String toString() {
            return start + " " + end;
        }
    }
}

