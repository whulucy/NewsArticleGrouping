package edu.scu.xli2.photonotesplus;

/**
 * Created by Xiaoyu on 5/10/16.
 */
public class MyPhoto {

    private String description, path;
    public String mLastLocation;
    public String revisedPath;
    public String voiceFile;
    public MyPhoto(String description, String path, String revisedPath, String voiceFile,
                   String mLastLocation) {
        this.description = description;
        this.path = path;
        this.revisedPath = revisedPath;
        this.voiceFile = voiceFile;
        this.mLastLocation = mLastLocation;
    }
    public MyPhoto() {
    }
    /**
     * Gets description.
     *
     * @return Value of description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets new description.
     *
     * @param description New value of description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets new path.
     *
     * @param path New value of path.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets path.
     *
     * @return Value of path.
     */
    public String getPath() {
        return path;
    }

    @Override public String toString() {
        return "Description:" + description;
    }
}