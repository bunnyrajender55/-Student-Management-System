public class Grade {
    private int courseId;
    private double value;

    public Grade(int courseId, double value) {
        this.courseId = courseId;
        this.value = value;
    }

    // Getters and setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "courseId=" + courseId +
                ", value=" + value +
                '}';
    }
}
