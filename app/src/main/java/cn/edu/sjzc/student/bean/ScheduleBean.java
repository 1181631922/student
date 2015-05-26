package cn.edu.sjzc.student.bean;

/**
 * Created by 亚风 on 2015/05/26/0026.
 */
public class ScheduleBean extends BaseBean {
    private String Id;
    private String Title;

    public ScheduleBean(String id, String title) {
        Id = id;
        Title = title;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
