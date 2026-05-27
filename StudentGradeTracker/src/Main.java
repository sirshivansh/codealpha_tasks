import java.util.ArrayList;
import java.util.Scanner;

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
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        System.out.println("Enter number of students: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.println("Enter student name: ");
            String name = sc.next(); //.next() reads one word input

            System.out.println("Enter student marks: ");
            int marks = sc.nextInt();

            Student s = new Student(name, marks);
            students.add(s);

        }
        // System.out.println("Hello!");
        /*
        Student s1 = new Student("Rahul", 85);
        Student s2 = new Student("Aman", 92);
        Student s3 = new Student("Shivansh", 100);
        */
        /*
        System.out.println(s1.name);
        System.out.println(s1.marks);
        System.out.println(s2.name);
        System.out.println(s2.marks);
        */

        //ArrayList<Student> students = new ArrayList<>();
        /*students.add(s1);
        students.add(s2);
        students.add(s3);*/

        System.out.println("\n========== STUDENT REPORT ==========");
        System.out.printf("%-20s %s%n", "Student Name", "Marks"); // %s = string, %-20s = left aligned, print string using 20 spaces
        System.out.println("-----------------------------------");

        int sum = 0;
        int highest = students.get(0).marks;
        int lowest = students.get(0).marks;

        //creating total value
        // for ( datatype variable: dataset name)
        for (Student s: students) {
            System.out.printf("%-20s %d%n", s.name, s.marks);
            sum = sum + s.marks;

            if (s.marks > highest) {
                highest = s.marks;
            }

            if (s.marks < lowest) {
                lowest = s.marks;
            }
        }
        System.out.println("-----------------------------------");

        System.out.println("Total Marks    : "+ sum);

        //creating average value
        double average = (double)sum/students.size();
        System.out.printf("Average Marks  : %.2f%n",average);

        //output of highest value
        System.out.println("Highest Marks  : "+ highest);

        //output of lowest value
        System.out.println("Lowest Marks   : " + lowest);

        // System.out.println(students.size());
    }
}
