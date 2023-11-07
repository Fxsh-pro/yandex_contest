import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task5 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            String[] nm = reader.readLine().split(" ");
            String[] programInfo = reader.readLine().split(" ");
            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            Map<Integer, Program> programMap = new HashMap<>();

            for (int i = 1; i <= m; i++) {
                Program program = new Program(i, Integer.parseInt(programInfo[i - 1]));
                programMap.put(program.id, program);
            }

            List<Student> students = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                String[] studentInfo = reader.readLine().split(" ");
                int rank = Integer.parseInt(studentInfo[0]);
                int preferenceCount = Integer.parseInt(studentInfo[1]);
                List<Integer> preferences = new ArrayList<>();
                for (int j = 0; j < preferenceCount; j++) {
                    preferences.add(Integer.parseInt(studentInfo[2 + j]));
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
        } catch (IOException e) {
            throw new RuntimeException(e);
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
