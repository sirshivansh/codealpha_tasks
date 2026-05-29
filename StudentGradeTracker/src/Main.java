import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class Main {
    public static void displayStudents(ArrayList<Student> students) {
        for (Student s : students) {
            System.out.printf("%-20s %d%n", s.name, s.marks);
        }
    }

    public static void generateReport(ArrayList<Student> students) {
        int sum = 0;
        int highest = students.get(0).marks;
        int lowest = students.get(0).marks;

        for (Student s : students) {
            sum = sum + s.marks;

            if (s.marks > highest) {
                highest = s.marks;
            }

            if (s.marks < lowest) {
                lowest = s.marks;
            }
        }
        System.out.println("---------------------------------------");

        System.out.println("Total Marks    : " + sum);

        //creating average value
        double average = (double) sum / students.size();
        System.out.printf("Average Marks  : %.2f%n", average);

        //output of highest value
        System.out.println("Highest Marks  : " + highest);

        //output of lowest value
        System.out.println("Lowest Marks   : " + lowest);
    }

    public static void saveStudentsToFile(ArrayList<Student> students){
        try{
            FileWriter writer = new FileWriter("students.txt");
            for (Student s : students){
                writer.write(s.name + "," + s.marks + "\n");
            } writer.close();
            System.out.println("Data Saved Successfully!");//.close() should come after loop ends.
        } catch (IOException e){
            System.out.println("Error saving the file!");
        }
    }

    public static void loadStudentsFromFile(ArrayList<Student> students){
        try{
            BufferedReader reader =
                    new BufferedReader(new FileReader("students.txt")); // File Reader = access files, // Buffered Reader = reads lines easily
            String lines;
            while ((lines = reader.readLine()) != null){
                String[] data = lines.split(",");
                students.add(
                        new Student(
                                data[0],
                                Integer.parseInt(data[1])
                        )
                );
            } reader.close();
        } catch (IOException e) {
            System.out.println("No previous student data was found!");
        }
    }

    public static void displayStudentWithmarks(ArrayList<Student> students){
        for(int i=0; i < students.size(); i++){
            Student s = students.get(i);
            System.out.println("%d. %-20s %d%n", i+1, s.name, s.marks);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        loadStudentsFromFile(students);

        while (true) {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Add Student");
            System.out.println("2. View Student");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");

            System.out.println("Enter your choice: ");
            int choice = sc.nextInt(); // there is \n waiting in buffer.
            sc.nextLine(); //It clears the leftover name form the buffer.
            switch (choice) {
                case 1:
                    System.out.println("Enter student name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter student marks: ");
                    int marks = sc.nextInt();
                    if (marks < 0 || marks > 100) {
                        System.out.println("Invalid marks! Marks should be between 0 and 100");
                        break; //now invalid students wont be added.
                    }

                    Student s = new Student(name, marks);

                    students.add(s);

                    System.out.println("Student Added successfully!");
                    saveStudentsToFile(students);
                    break;
                case 2:
                    System.out.println("\n =========== STUDENT LIST ===========");
                    System.out.printf("%-20s %s%n", "Student Name", "Marks");
                    System.out.println("---------------------------------------");
                    if (students.isEmpty()) {
                        System.out.println("No students available!");
                        break;
                    }
                    displayStudents(students);
                    System.out.println("---------------------------------------");
                    break;
                case 3:
                    System.out.println("\n =========== REPORT ===========");
                    if (students.isEmpty()) {
                        System.out.println("No students available!");
                        break;
                    }
                    generateReport(students);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }

/*        System.out.println("Enter number of students: ");
        int n = sc.nextInt();*/
/*

        for (int i = 1; i <= n; i++) {
            System.out.println("Enter student name: ");
            String name = sc.next(); //.next() reads one word input

            System.out.println("Enter student marks: ");
            int marks = sc.nextInt();

            Student s = new Student(name, marks);
            students.add(s);
*/

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

/*
        System.out.println("\n========== STUDENT REPORT ==========");
        System.out.printf("%-20s %s%n", "Student Name", "Marks"); // %s = string, %-20s = left aligned, print string using 20 spaces
        System.out.println("-----------------------------------");
*/

    //creating total value
    // for ( datatype variable: dataset name)
//        displayStudents(students);
//        generateReport(students);


    // System.out.println(students.size());
}
