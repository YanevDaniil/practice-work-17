package files;

import exception.IllegalFilenameException;

public class File {
    private String filename;
    private String content;
    private double size;
    private String extension;

    /**
     * Construct object with passed filename and content, set extension based
     * on filename and calculate size as half content length.
     * @param filename files.File name (mandatory) with extension (optional), without directory tree (path separators:
     *                 <a href="https://en.wikipedia.org/wiki/Path_(computing)#Representations_of_paths_by_operating_system_and_shell">link</a>)
     * @param content files.File content (could be empty, but must be set)
     */
    public File(String filename, String content) {
        // Need to validate filename with RegEx
        if (!filename.contains(".")) throw new IllegalFilenameException();

        this.filename = filename;
        this.content = content;
        this.size = content.length() / 2.0;
        this.extension = filename.split("\\.")[filename.split("\\.").length - 1];
    }

    /**
     * Get exactly file size
     * @return files.File size
     */
    public double getSize() {
        return (int) size;
    }

    /**
     * Get files.File filename
     * @return files.File filename
     */
    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return "File{" +
                "filename='" + filename + '\'' +
                ", size=" + size +
                '}';
    }
}
