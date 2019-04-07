package cc.lgiki.shanlinghelper.model;

public class ShanLingFileModel implements Comparable<ShanLingFileModel> {
    private String path;
    private String name;
    private Long ctime;
    private Long size;

    public ShanLingFileModel(String path, String name, Long ctime, Long size) {
        this.path = path;
        this.name = name;
        this.ctime = ctime;
        this.size = size;
    }

    public ShanLingFileModel(String path, String name, Long ctime) {
        this.path = path;
        this.name = name;
        this.ctime = ctime;
        this.size = null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public boolean isFile() {
        return this.size != null;
    }

    @Override
    public int compareTo(ShanLingFileModel o) {
        return this.name.compareTo(o.name);
    }
}
