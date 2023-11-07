import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Task5Second {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(System.out)) {
            int n = Integer.parseInt(reader.readLine());
            Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
            List<List<Integer>> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                reader.readLine();
                var nums = reader.readLine().split(" ");
                List<Integer> sublist = new ArrayList<>();
                for (var j : nums) {
                    sublist.add(Integer.parseInt(j));
                }
                list.add(sublist);
            }
            int s = 0;
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    s+= find(list.get(i),list.get(j));
                }
            }
            writer.println(s);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static int find(List<Integer> list1, List<Integer> list2) {
        int s = 0;
        for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
            if (!Objects.equals(list1.get(i), list2.get(i))) break;
            s++;
        }
        return s;
    }
}
