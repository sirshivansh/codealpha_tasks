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
        System.out.println("Hello!");
        Student s1 = new Student("Rahul", 85);
        Student s2 = new Student("Aman", 92);

        System.out.println(s1.name);
        System.out.println(s1.marks);
        System.out.println(s2.name);
        System.out.println(s2.marks);
    }
}
