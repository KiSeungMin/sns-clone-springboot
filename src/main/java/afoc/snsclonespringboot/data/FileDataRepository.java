package afoc.snsclonespringboot.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileDataRepository implements DataRepository{
    @Override
    public Boolean save(Data data) {
        // check if exists
        File srcFile = new File(data.getSourceDataPath());
        File dstFile = new File(data.getSaveDataPath());
        if(!srcFile.exists() || dstFile.exists()){
            return false;
        }
        // save data
        try {
            Files.copy(
                    Paths.get("", data.getSourceDataPath()),
                    Paths.get("", data.getSaveDataPath()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Data load(Long id) {
        return null;
    }
}
