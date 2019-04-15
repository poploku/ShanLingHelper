package cc.lgiki.shanlinghelper.model;

public class ShanLingFileModel implements Comparable<ShanLingFileModel> {
    private String path;
    private String name;
    private Long ctime;
    private Long size;

    public static final int FILE_TYPE_UNKNOWN = -1;
    public static final int FILE_TYPE_FOLDER = 0;
    public static final int FILE_TYPE_MUSIC = 1;
    public static final int FILE_TYPE_PICTURE = 2;
    public static final int FILE_TYPE_LYRIC = 3;

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

    public int getFileType() {
        if (!this.isFile()) {
            return FILE_TYPE_FOLDER;
        } else {
            String fileNameInLowerCase = name.toLowerCase();
            if (fileNameInLowerCase.endsWith("mp3") || fileNameInLowerCase.endsWith("ape")
                    || fileNameInLowerCase.endsWith("flac") || fileNameInLowerCase.endsWith("wav")
                    || fileNameInLowerCase.endsWith("dsf") || fileNameInLowerCase.endsWith("dff")
                    || fileNameInLowerCase.endsWith("aiff") || fileNameInLowerCase.endsWith("iso")
                    || fileNameInLowerCase.endsWith("m4a") || fileNameInLowerCase.endsWith("aac")
                    || fileNameInLowerCase.endsWith("mp2") || fileNameInLowerCase.endsWith("ac3")
                    || fileNameInLowerCase.endsWith("dts") || fileNameInLowerCase.endsWith("aif")
                    || fileNameInLowerCase.endsWith("ogg") || fileNameInLowerCase.endsWith("wma")
                    || fileNameInLowerCase.endsWith("cue") || fileNameInLowerCase.endsWith("m3u")
                    || fileNameInLowerCase.endsWith("m3u8")) {
                return FILE_TYPE_MUSIC;
            } else if (fileNameInLowerCase.endsWith("png") || fileNameInLowerCase.endsWith("jpg") || fileNameInLowerCase.endsWith("jpeg")) {
                return FILE_TYPE_PICTURE;
            } else if (fileNameInLowerCase.endsWith("lrc")) {
                return FILE_TYPE_LYRIC;
            } else {
                return FILE_TYPE_UNKNOWN;
            }
        }
    }

    @Override
    public int compareTo(ShanLingFileModel o) {
        return this.name.compareTo(o.name);
    }
}
