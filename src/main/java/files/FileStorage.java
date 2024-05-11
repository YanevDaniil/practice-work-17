package files;

import exception.FileNameAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileStorage {
    private ArrayList<File> files = new ArrayList<>();
    private double availableSize = 100;
    private double maxSize = 100;

    /**
     * Construct object and set max storage size and available size according passed values
     * @param size files.FileStorage size
     */
    public FileStorage(int size) {
        if (size < 0) {
            System.out.println("size of File Storage can't be less than zero!");
        } else {
            maxSize = size;
            availableSize = size;
        }
    }

    /**
     * Construct object and set max storage size and available size based on default value=100
     */
    public FileStorage() {}

    /**
     * Write file in storage if filename is unique and size is not more than available size
     * @param file to save in file storage
     * @return result of file saving
     * @throws FileNameAlreadyExistsException in case of already existent filename
     */
    public boolean write(File file) throws FileNameAlreadyExistsException {
        if (isExists(file.getFilename())) {
            throw new FileNameAlreadyExistsException();
        }
        if (file.getSize() >= availableSize) {
            System.out.println("Not enough storage space for File '" + file.getFilename() + "'");
            return false;
        }
        files.add(file);
        availableSize -= file.getSize();

        return true;
    }

    /**
     * @return available size of file storage
     */
    public double getAvailableSize() {
        return availableSize;
    }

    /**
     * Check is file exist in storage
     * @param fileName to search
     * @return result of checking
     */
    public boolean isExists(String fileName) {
        return files.stream().anyMatch(file -> file.getFilename().equals(fileName));
    }

    /**
     * Delete file from storage
     * @param fileName of file to delete
     * @return result of file deleting
     */
    public boolean delete(String fileName) {
        return files.remove(getFile(fileName));
    }

    /**
     * Get all Files saved in the storage
     * @return list of files
     */
    public List<File> getAllFiles() {
        return files;
    }

    /**
     * Get file by filename
     * @param fileName of file to get
     * @return file
     */
    public File getFile(String fileName) {
        if (isExists(fileName)) {
            Optional<File> optionalFile = files.stream().filter(file -> file.getFilename().equals(fileName)).findFirst();
            if (optionalFile.isPresent()) return optionalFile.get();
        }

        // better to return a "new File()" with "new file" filename and empty content, because null produces NullPointerException
        return null;
    }

    /**
     * Remove all files
     */
    public void deleteAllFiles() {
        files.clear();
    }
}
