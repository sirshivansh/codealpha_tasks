import java.util.ArrayList;

class Student{
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}
public class Main {
    public static void main(String[] args) {

        // System.out.println("Hello!");
        Student s1 = new Student("Rahul", 85);
        Student s2 = new Student("Aman", 92);
        Student s3 = new Student("Shivansh", 100);
        /*
        System.out.println(s1.name);
        System.out.println(s1.marks);
        System.out.println(s2.name);
        System.out.println(s2.marks);
        */

        ArrayList<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        int sum = 0;

        //creating total value
        for (Student s: students) {
            System.out.println(s.name + ":" + s.marks);
            sum = sum + s.marks;
        }
        System.out.println(sum);

        //creating average value
        double average = (double)sum/students.size();
        System.out.printf("Average marks: %.2f",average);

        // System.out.println(students.size());
    }
}
