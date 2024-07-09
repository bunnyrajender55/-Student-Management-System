import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {
    private List<Student> students;
    private static final String FILENAME = "students.txt";
    private static int nextStudentId = 1; // For auto-incrementing student IDs

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        student.setId(nextStudentId++);
        students.add(student);
        saveStudentsToFile();
    }

    public void deleteStudent(int studentId) {
        students.removeIf(student -> student.getId() == studentId);
        saveStudentsToFile();
    }

    public void updateStudent(int studentId, String newName) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                student.setName(newName);
                break;
            }
        }
        saveStudentsToFile();
    }

    public void addCourseToStudent(int studentId, Course course) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                student.addCourse(course);
                break;
            }
        }
        saveStudentsToFile();
    }

    public void addGradeToStudent(int studentId, Grade grade) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                student.addGrade(grade);
                break;
            }
        }
        saveStudentsToFile();
    }

    public Student findStudentById(int studentId) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        return null;
    }

    public void displayStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Student student : students) {
                writer.println(student.getId() + "," + student.getName());
            }
        } catch (IOException e) {
            System.err.println("Error saving students to file: " + e.getMessage());
        }
    }

    private void loadStudentsFromFile() {
        students.clear();
        try (Scanner scanner = new Scanner(new File(FILENAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                Student student = new Student(id, name);
                students.add(student);
                if (id >= nextStudentId) {
                    nextStudentId = id + 1;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Students file not found. Creating new file.");
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.loadStudentsFromFile();

        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nMENU");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student Name");
            System.out.println("4. Add Course to Student");
            System.out.println("5. Add Grade to Student");
            System.out.println("6. Search Student");
            System.out.println("7. Display Students");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = input.nextLine();
                    Student newStudent = new Student(0, name); // ID will be auto-assigned
                    system.addStudent(newStudent);
                    break;
                case 2:
                    System.out.print("Enter student ID to delete: ");
                    int deleteId = input.nextInt();
                    input.nextLine(); // Consume newline left-over
                    system.deleteStudent(deleteId);
                    break;
                case 3:
                    System.out.print("Enter student ID to update: ");
                    int updateId = input.nextInt();
                    input.nextLine(); // Consume newline left-over
                    System.out.print("Enter new name: ");
                    String newName = input.nextLine();
                    system.updateStudent(updateId, newName);
                    break;
                case 4:
                    System.out.print("Enter student ID to add course: ");
                    int studentId1 = input.nextInt();
                    input.nextLine(); // Consume newline left-over
                    System.out.print("Enter course ID: ");
                    int courseId = input.nextInt();
                    input.nextLine(); // Consume newline left-over
                    System.out.print("Enter course name: ");
                    String courseName = input.nextLine();
                    System.out.print("Enter course credits: ");
                    int credits = input.nextInt();
                    input.nextLine(); // Consume newline left-over
                    Course newCourse = new Course(courseId, "", courseName, credits);
                    system.addCourseToStudent(studentId1, newCourse);
                    break;
                case 5:
                    System.out.print("Enter student ID to add grade: ");
                    int studentId2 = input.nextInt();
                    input.nextLine(); // Consume newline left-over
                    System.out.print("Enter course ID: ");
                    int gradeCourseId = input.nextInt();
                    input.nextLine(); // Consume newline left-over
                    System.out.print("Enter grade value: ");
                    double value = input.nextDouble();
                    input.nextLine(); // Consume newline left-over
                    Grade newGrade = new Grade(gradeCourseId, value);
                    system.addGradeToStudent(studentId2, newGrade);
                    break;
                case 6:
                    System.out.print("Enter student ID to search: ");
                    int searchId = input.nextInt();
                    input.nextLine(); // Consume newline left-over
                    Student foundStudent = system.findStudentById(searchId);
                    if (foundStudent != null) {
                        System.out.println("Student found:");
                        System.out.println(foundStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 7:
                    System.out.println("List of students:");
                    system.displayStudents();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 8);

        input.close();
    }
}
