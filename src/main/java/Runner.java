import exception.FileNameAlreadyExistsException;
import files.File;
import files.FileStorage;

public class Runner {

    public static final String WRONG_SIZE_CONTENT_STRING = "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext";

    public static void main(String[] args) throws FileNameAlreadyExistsException {
        FileStorage fileStorage = new FileStorage();

        File file1 = new File("file1.txt", "some content");
        File file2 = new File("file2.txt", "lalala");
        File file3 = new File("file3.txt", "hello");
        File filePath = new File("@D:\\JDK-intellij-downloader-info.txt", "some");
        File fileWithoutExtension = new File("identity", "content");

        System.out.println(fileStorage.getAvailableSize());

        System.out.println(fileStorage.write(file1));
        System.out.println(file1.getSize());

        System.out.println(fileStorage.write(file2));
        System.out.println(file2.getSize());

        System.out.println(fileStorage.write(file3));
        System.out.println(file3.getSize());

        System.out.println(fileStorage.getAvailableSize());

        System.out.println(fileStorage.write(filePath));
        System.out.println(fileStorage.getAvailableSize());

        // System.out.println(fileWithoutExtension.filename);          // identity
        // System.out.println(fileWithoutExtension.extension);         // identity

        // fileStorage.getFile("doesn't exist").getSize();      // NullPointerException


        File fileNotEnoughSpaceInStorage = new File("fileNotEnoughSpaceInStorage.md", WRONG_SIZE_CONTENT_STRING);
        fileStorage.write(fileNotEnoughSpaceInStorage);
        System.out.println(fileStorage.getAvailableSize());
    }
}