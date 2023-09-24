import java.util.*;

public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        Map<Integer, Program> programMap = new HashMap<>();

        for (int i = 1; i <= m; i++) {
            Program program = new Program(i, scanner.nextInt());
            programMap.put(program.id, program);
        }

        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            int rank = scanner.nextInt();
            int preferenceCount = scanner.nextInt();
            List<Integer> preferences = new ArrayList<>();
            for (int j = 0; j < preferenceCount; j++) {
                preferences.add(scanner.nextInt());
            }
            Student student = new Student(i, rank, preferences);
            students.add(student);
        }
        students = students.stream().sorted().toList();

        for (var student : students) {
            for (int programId : student.preferences) {
                if (student.programId == -1) {
                    var program = programMap.get(programId);
                    program.join(student);
                }
            }
        }
        students = students.stream().sorted(Comparator.comparingInt(student -> student.id)).toList();
        for (int i = 0; i < students.size(); i++) {
            String res = Integer.toString(students.get(i).programId);
            if (i != students.size() - 1) res += " ";
            System.out.print(res);
        }
    }

    static class Student implements Comparable<Student> {
        int id;
        int rank;
        int programId = -1;
        List<Integer> preferences;

        public Student(int id, int rank, List<Integer> preferences) {
            this.id = id;
            this.rank = rank;
            this.preferences = preferences;
        }

        @Override
        public int compareTo(Student other) {
            return Integer.compare(this.rank, other.rank);
        }
    }

    static class Program {
        int id;
        int maxPeople;
        TreeSet<Student> participants = new TreeSet<>();

        public Program(int id, int maxPeople) {
            this.id = id;
            this.maxPeople = maxPeople;
        }

        public void join(Student student) {
            if (participants.size() < maxPeople) {
                participants.add(student);
                student.programId = id;
            } else {
                if (participants.first().rank < student.rank) return;
                participants.first().programId = -1;
                participants.pollFirst();
                participants.add(student);
                student.programId = id;
            }
        }
    }
}
