import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Task2 {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(System.out)) {
            StringBuilder output = new StringBuilder();

            String[] info = reader.readLine().split(" ");
            String[] firstCards = reader.readLine().split(" ");
            String[] secondCard = reader.readLine().split(" ");
            int n = Integer.parseInt(info[0]);
            int m = Integer.parseInt(info[1]);
            int q = Integer.parseInt(info[2]);
            Map<Long, Long> firstMap = new HashMap<>();
            Map<Long, Long> secondMap = new HashMap<>();

            for (int i = 0; i < n; i++) {
                long val = Long.parseLong(firstCards[i]);
                firstMap.compute(val, (key, v) -> v == null ? 1 : v + 1);
            }
            for (int i = 0; i < m; i++) {
                long val = Long.parseLong(secondCard[i]);
                secondMap.compute(val, (key, v) -> v == null ? 1 : v + 1);
            }
            int dif = count(firstMap, secondMap);
            for (int i = 0; i < q; i++) {
                String[] move = reader.readLine().split(" ");
                long val = Long.parseLong(move[2]);
                if (Objects.equals(move[1], "A")) {
                    dif += countDif(firstMap, secondMap, Objects.equals(move[0], "1"), val);
                } else {
                    dif += countDif(secondMap, firstMap, Objects.equals(move[0], "1"), val);
                }
                output.append(dif).append(" ");
            }
            output.deleteCharAt(output.length() - 1);
            writer.print(output);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int countDif(Map<Long, Long> firstMap, Map<Long, Long> secondMap, boolean insert, long val) {
        int newDif = 0;
        if (insert) firstMap.compute(val, (key, v) -> v == null ? 1 : v + 1);
        else {
            var res = firstMap.get(val);
            if (res == 1) firstMap.remove(val);
            else firstMap.put(val, res - 1);
        }
        var count = firstMap.get(val) == null ? 0 : firstMap.get(val);
        if (insert) {
            if (!secondMap.containsKey(val)) newDif++;
            else if (secondMap.get(val) >= count) newDif--;
            else newDif++;
        } else {
            if (!secondMap.containsKey(val)) newDif--;
            else if (secondMap.get(val) > count) newDif++;
            else newDif--;
        }
        return newDif;
    }

    private static int count(Map<Long, Long> firstMap, Map<Long, Long> secondMap) {
        int differentElementsCount = 0;

        for (Map.Entry<Long, Long> entry : firstMap.entrySet()) {
            Long key = entry.getKey();
            Long valueInFirstMap = entry.getValue();
            Long valueInSecondMap = secondMap.get(key);

            if (valueInSecondMap != null && !valueInSecondMap.equals(valueInFirstMap)) {
                differentElementsCount += Math.abs(valueInFirstMap - valueInSecondMap);
            } else if (valueInSecondMap == null) {
                differentElementsCount += valueInFirstMap;
            }
        }

        for (Map.Entry<Long, Long> entry : secondMap.entrySet()) {
            Long key = entry.getKey();
            Long valueInSecondMap = entry.getValue();
            if (!firstMap.containsKey(key)) {
                differentElementsCount += valueInSecondMap;
            }
        }
        return differentElementsCount;
    }
}
