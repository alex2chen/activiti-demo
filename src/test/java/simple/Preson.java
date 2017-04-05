package simple;

import java.io.Serializable;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/2/14
 */
public class Preson implements Serializable {
    private Integer id; // 编号
    private String name; //姓名
    private String xxxxxx;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXxxxxx() {
        return xxxxxx;
    }

    public void setXxxxxx(String xxxxxx) {
        this.xxxxxx = xxxxxx;
    }
}
