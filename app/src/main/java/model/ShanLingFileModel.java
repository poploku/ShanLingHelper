package model;

public class ShanLingFileModel {
    private String path;
    private String name;
    private long ctime;
    private long size;

    public ShanLingFileModel(String path, String name, long ctime, long size) {
        this.path = path;
        this.name = name;
        this.ctime = ctime;
        this.size = size;
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

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
