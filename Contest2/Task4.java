import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class Task4 {
    public static void main(String[] args) {
        test();
    }
    static final Map<Integer, Integer> map = new HashMap<>();
    static int[] result;
    static boolean[] seen;
    public static void test() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(System.out)) {

            int n = Integer.parseInt(reader.readLine());
            seen = new boolean[n + 1];
            result = new int[n + 1];

            String[] langs = reader.readLine().split(" ");
            var workers = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            Node head = func(langs, workers, 0);
            f(head, 0,0);

            StringBuilder output = new StringBuilder();
            for (int i = 1; i <= n; i++) { // Assuming 1-based index for employees
                output.append(result[i]);
                if (i < n) {
                    output.append(" ");
                }
            }
            writer.print(output);

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void f(Node node, int lastA, int lastB) {
        if (node == null) return;
        if (node.index == 0){}
        else if (Objects.equals(node.lan, "A")) {
//            map.put(node.index, lastA);
            result[node.index] = lastA;
        } else {
            result[node.index] = lastB;
//            map.put(node.index, lastB);
        }
        for (var i : node.children) {
            f(i, node.lan.contains("A") ? 0 : lastA + 1, node.lan.contains("B") ? 0 : lastB + 1);
        }
    }

    private static Node func(String[] langs, int[] workers, int i) {
        Node node;
        if (i == 0) node = new Node(workers[i], "AB");
        else node = new Node(workers[i], langs[workers[i] - 1]);
        if (seen[node.index]) return null;
        seen[node.index] = true;
        for (int j = i + 1; j < workers.length; j++) {
            if (workers[j] == workers[i]) break;
            Node child = func(langs, workers, j);
            if (child != null) {
                node.children.add(child);
            }
        }
        return node;
    }

    static class Node {
        int index;
        String lan;
        List<Node> children;

        public Node(int i, String l) {
            index = i;
            lan = l;
            children = new ArrayList<>();
        }
    }
}