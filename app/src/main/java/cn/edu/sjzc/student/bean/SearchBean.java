package cn.edu.sjzc.student.bean;

public class SearchBean implements Comparable {
    private String SearchName;
    private String SearchPhone;

    public SearchBean(String searchName, String searchPhone) {
        SearchName = searchName;
        SearchPhone = searchPhone;
    }

    public String getSearchName() {
        return SearchName;
    }

    public void setSearchName(String searchName) {
        SearchName = searchName;
    }

    public String getSearchPhone() {
        return SearchPhone;
    }

    public void setSearchPhone(String searchPhone) {
        SearchPhone = searchPhone;
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "SearchName='" + SearchName + '\'' +
                ", SearchPhone='" + SearchPhone + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object another) {
        // TODO Auto-generated method stub
        return 0;
    }

}
