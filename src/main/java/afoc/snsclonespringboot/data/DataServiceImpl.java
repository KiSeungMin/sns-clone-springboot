package afoc.snsclonespringboot.data;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DataServiceImpl implements DataService{
    private final DataInfoRepository dataInfoRepository;

    @Value("${constant.rootDataPath}")
    private String rootDataPath;

    // Test
    @Value("${constant.rootTestDataPath}")
    private String rootTestDataPath;
    @Value("${isTest}")
    private boolean isTest;

    @Override
    public Optional<DataInfo> save(MultipartFile multipartFile, DataType dataType){
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null)
            return Optional.empty();

        String storeFilename = createStoreFileName(originalFilename);

        File saveFile = new File(getAbsolutePath(storeFilename));
        if(saveFile.isFile())
            return Optional.empty();

        if(!saveFile.getParentFile().exists()) {
            boolean res = saveFile.getParentFile().mkdirs();
            if (!res)
                return Optional.empty();
        }

        try {
            multipartFile.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        DataInfo dataInfo = DataInfo.builder()
                .dataType(dataType)
                .saveDataPath(storeFilename)
                .build();

        return dataInfoRepository.save(dataInfo);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return Paths.get(uuid + "." + ext).toString();
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    @Override
    public Optional<DataInfo> load(Long id) {
        return dataInfoRepository.load(id);
    }

    @Override
    public String getAbsolutePath(String path){
        return Paths.get(rootDataPath, path).toAbsolutePath().toString();
    }

    // Test
    @Override
    public String getTestAbsolutePath(String path){
        if(!isTest)
            throw new IllegalStateException();
        return Paths.get(rootTestDataPath, path).toAbsolutePath().toString();
    }
}
