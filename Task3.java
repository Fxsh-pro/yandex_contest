import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Task3 {
    private static final int ROOT_BRANCH = 1;
    private static List<Node> graph;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            int size = Integer.parseInt(reader.readLine());
            graph = new ArrayList<>(size + ROOT_BRANCH);
            initGraph(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int maxDepth = findMaxDeep(graph.get(0));
        System.out.println(findNodeIndexByDepth(maxDepth));
    }

    private static void initGraph(BufferedReader reader) throws IOException {
        Node root = new Node(0, 0);
        graph.add(root);

        int count = 0;
        while(reader.ready()) {
            int parent = Integer.parseInt(reader.readLine());
            Node parentNode = graph.get(parent);
            Node node = new Node(++count, parentNode.depth + 1);
            graph.add(node);
            parentNode.putChildren(node);
        }
    }

    private static int findMaxDeep(Node root) {
        if (root.children.isEmpty()) {
            return root.depth;
        }
        int maxDeep = root.depth;
        for (Node node : root.children) {
            int childrenDeep = findMaxDeep(node);
            maxDeep = Math.max(maxDeep, childrenDeep);
        }
        return maxDeep;
    }

    private static int findNodeIndexByDepth(int depth) {
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).depth == depth) {
                return i;
            }
        }
        return 0;
    }

    static class Node {
        int branch;
        int depth;
        List<Node> children;

        public Node(int branch, int depth) {
            this.branch = branch;
            this.depth = depth;
            children = new ArrayList<>();
        }

        public void putChildren(Node node) {
            children.add(node);
        }
    }
}