package cn.edu.sjzc.student.bean;

public class FindTeacherBean implements Comparable {
    private String FindName;
    private String FindPhone;
    private String FindOffice;
    private String FindEmail;

    public FindTeacherBean(String findName, String findPhone, String findOffice, String findEmail) {
        FindName = findName;
        FindPhone = findPhone;
        FindOffice = findOffice;
        FindEmail = findEmail;
    }

    public String getFindName() {
        return FindName;
    }

    public void setFindName(String findName) {
        FindName = findName;
    }

    public String getFindPhone() {
        return FindPhone;
    }

    public void setFindPhone(String findPhone) {
        FindPhone = findPhone;
    }

    public String getFindOffice() {
        return FindOffice;
    }

    public void setFindOffice(String findOffice) {
        FindOffice = findOffice;
    }

    public String getFindEmail() {
        return FindEmail;
    }

    public void setFindEmail(String findEmail) {
        FindEmail = findEmail;
    }

    @Override
    public String toString() {
        return "FindTeacherBean{" +
                "FindName='" + FindName + '\'' +
                ", FindPhone=" + FindPhone +
                ", FindOffice='" + FindOffice + '\'' +
                ", FindEmail='" + FindEmail + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object another) {
        // TODO Auto-generated method stub
        return 0;
    }

}
