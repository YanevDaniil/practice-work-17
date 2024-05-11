import exception.FileNameAlreadyExistsException;
import files.File;
import files.FileStorage;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FileStorageTest {

    public final String MAX_SIZE_EXCEPTION = "DIFFERENT MAX SIZE";
    public final String NULL_FILE_EXCEPTION = "NULL FILE";
    public final String SPACE_STRING = " ";
    public final String FILE_PATH_STRING = "@D:\\JDK-intellij-downloader-info.txt";
    public final String CONTENT_STRING = "Some text";
    public final String REPEATED_STRING = "AA";
    public final String WRONG_SIZE_CONTENT_STRING = "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext";
    public final String TIC_TOC_TOE_STRING = "tictoctoe.game";

    public final int NEW_SIZE = 5;
    public final int TWENTY_ONE = 21;
    public final int FIFTY = -50;
    public final int ZERO = 0;

    public FileStorage storage;

    @BeforeTest
    public void setUp() {
        storage = new FileStorage(NEW_SIZE);
    }

    /* Метод, выполняемый перед группами */
    @BeforeGroups(groups = "testExistFunction")
    public void setNewStorage() {
        storage = new FileStorage();
    }

    /* ПРОВАЙДЕРЫ */
    @DataProvider(name = "testSizeData")
    public Object[][] newData() {
        return new Object[][]{{TWENTY_ONE}, {FIFTY}, {ZERO}};
    }

    @DataProvider(name = "testFilesForStorage")
    public Object[][] newFiles() {
        return new Object[][] { {new File(REPEATED_STRING, CONTENT_STRING)},
                {new File(SPACE_STRING, WRONG_SIZE_CONTENT_STRING)},
                {new File(FILE_PATH_STRING, CONTENT_STRING)} };
    }

    @DataProvider(name = "testFilesForDelete")
    public Object[][] filesForDelete() {
        return new Object[][] { {REPEATED_STRING}, {TIC_TOC_TOE_STRING}};
    }

    @DataProvider(name = "nullExceptionTest")
    public Object[][] dataNullExceptionTest(){
        return new Object[][] { {new File (null, null)}, {null} };
    }

    @DataProvider(name = "testFileForException")
    public Object[][] newExceptionFile() {
        return new Object[][] { {new File(REPEATED_STRING, CONTENT_STRING)} };
    }

    /* Тестирование конструктора – рефлексия */
    @Test(dataProvider = "testSizeData")
    public void testFileStorage(int size) {
        try {
            storage = new FileStorage(size);

            int correctSize = size < 0 ? 100 : size;        // validation in constructor

            Field field = FileStorage.class.getDeclaredField("maxSize");
            field.setAccessible(true);
            Assert.assertEquals( (int) field.getDouble(storage), correctSize, MAX_SIZE_EXCEPTION );         // method with reflection "private storage.getMaxSize"
        } catch (Exception ignored) {}
    }

    /* Тестирование записи файла */
    @Test (dataProvider = "testFilesForStorage", groups = "testExistFunction")
    public void testWrite(File file) throws FileNameAlreadyExistsException {
        storage = new FileStorage(200);
        Assert.assertTrue(storage.write(file));
    }

    //
    @Test (dataProvider = "testFilesForStorage", groups = "testExistFunction")
    public void testWrite_setZeroStorageSize_shouldReturnFalse(File file) throws FileNameAlreadyExistsException {
        storage = new FileStorage(0);
        Assert.assertFalse(storage.write(file));
    }

    /* Тестирование записи дублирующегося файла */
    @Test (dataProvider = "testFileForException", expectedExceptions = FileNameAlreadyExistsException.class)
    public void testWriteException(File file) throws FileNameAlreadyExistsException {
        storage.write(file);
        storage.write(file);        // write the same file
    }

    /* Тестирование проверки существования файла */
    @Test (dataProvider = "testFilesForStorage", groups = "testExistFunction")
    public void testIsExists(File file) {
        String name = file.getFilename();
        Assert.assertFalse(storage.isExists(name));
        try {
            storage.write(file);
        } catch (Exception ignored) {}
    }

    /* Тестирование удаления файла */
    @Test (dataProvider = "testFilesForDelete", dependsOnMethods = "testFileStorage")
    public void testDelete(String fileName) {
        try {
            storage = new FileStorage(TWENTY_ONE);
            storage.write(new File(TIC_TOC_TOE_STRING, CONTENT_STRING));
            storage.write(new File(REPEATED_STRING, CONTENT_STRING));
            Assert.assertTrue(storage.delete(fileName));

        } catch (Exception ignored) {}

    }

    /* Тестирование получения файлов */
    @Test
    public void testGetFiles(){
        for (File f : storage.getAllFiles()) {
            Assert.assertNotNull(f);
        }
    }

    /* Тестирование получения файла */
    @Test (dataProvider = "testFilesForStorage")
    public void testGetFile(File file) throws FileNameAlreadyExistsException {
        storage = new FileStorage(200);
        storage.write(file);

        Assert.assertEquals(storage.getFile(file.getFilename()), file);
    }



    // Testing the deletion of all files
    @Test
    public void testDeletionAllFiles() {
        storage.deleteAllFiles();
        Assert.assertEquals(storage.getAllFiles(), new ArrayList<>());
    }
}
