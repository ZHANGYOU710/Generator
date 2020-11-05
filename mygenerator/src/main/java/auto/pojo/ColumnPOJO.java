package auto.pojo;

/**
 * 描 述: 请描述功能
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */
public class ColumnPOJO {
    // 列名
    private Object conlumName;
    // 驼峰列名
    private Object humpConlumName;
    // 列类型
    private Object conlumTypeName;

    private String javaType;

    private Object commentName;

    private boolean key;

    public Object getConlumName() {
        return conlumName;
    }

    public Object getHumpConlumName() {
        return humpConlumName;
    }

    public void setHumpConlumName(Object humpConlumName) {
        this.humpConlumName = humpConlumName;
    }

    public void setConlumName(Object conlumName) {
        this.conlumName = conlumName;
    }

    public Object getConlumTypeName() {
        if (conlumTypeName.toString().equals("INT"))
            return "INTEGER";
        else
            return conlumTypeName.toString();
    }

    public Object getCommentName() {
        return commentName;
    }

    public void setCommentName(Object commentName) {
        this.commentName = commentName;
    }

    public void setConlumTypeName(Object conlumTypeName) {
        this.conlumTypeName = conlumTypeName;
    }


    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public boolean getKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public ColumnPOJO() {
    }

    public ColumnPOJO(Object conlumName, Object humpConlumName, Object conlumTypeName, String commentName, String javaType, Boolean key) {
        this.conlumName = conlumName;
        this.humpConlumName = humpConlumName;
        this.conlumTypeName = conlumTypeName;
        this.commentName = commentName;
        this.javaType = javaType;
        this.key = key;
    }

    public ColumnPOJO(Object conlumName, Object conlumTypeName) {
        this.conlumName = conlumName;
        this.conlumTypeName = conlumTypeName;
    }

    @Override
    public String toString() {
        return "ColumnPOJO{" +
                "conlumName=" + conlumName +
                ", humpConlumName=" + humpConlumName +
                ", conlumTypeName=" + conlumTypeName +
                '}';
    }
}
