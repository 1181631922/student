package cn.edu.sjzc.student.bean;

/**
 * Created by 亚风 on 2015/05/28/0028.
 */
public class EvaluationHistoryBean extends BaseBean {
    private String History;
    private String Teacher;
    private String Course;

    public EvaluationHistoryBean(String history, String teacher, String course) {
        History = history;
        Teacher = teacher;
        Course = course;
    }

    public String getHistory() {
        return History;
    }

    public void setHistory(String history) {
        History = history;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    @Override
    public String toString() {
        return "EvaluationHistoryBean{" +
                "History='" + History + '\'' +
                ", Teacher='" + Teacher + '\'' +
                ", Course='" + Course + '\'' +
                '}';
    }
}
