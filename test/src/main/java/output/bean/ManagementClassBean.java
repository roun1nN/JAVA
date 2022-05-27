package output.bean;

import java.util.List;

/**
 * @author bxw
 */
public class ManagementClassBean {
    private int sort;
    private String name;
    private List<ManagementBean> mbList;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ManagementBean> getMbList() {
        return mbList;
    }

    public void setMbList(List<ManagementBean> mbList) {
        this.mbList = mbList;
    }
}
