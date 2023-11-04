import org.example.FileFinder;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class FIleFinderTests {

    FileFinder fileFinder = new FileFinder();

    @Test
    public void checkIfDiskExistTestReturnTrue() {
        String diskName = "D:\\";
        Assert.assertTrue(fileFinder.checkIfDiskExist(diskName));
    }

    @Test
    public void checkIfDiskExistTestReturnFalse() {
        String diskName = "F:\\";
        Assert.assertFalse(fileFinder.checkIfDiskExist(diskName));
    }

    @Test
    public void folderSearcherTestReturnPathReturnPath() {
        String diskName = "D:\\";
        String folderName = "logs";
        Assert.assertEquals("D:\\logs", fileFinder.folderSearcher(diskName, folderName));
    }

    @Test
    public void folderSearcherTestReturnPathReturnEmpty() {
        String diskName = "D:\\";
        String folderName = "lodgs";
        Assert.assertEquals("", fileFinder.folderSearcher(diskName, folderName));
    }


}
