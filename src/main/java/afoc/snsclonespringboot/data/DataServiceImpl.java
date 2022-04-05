package afoc.snsclonespringboot.data;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @Override
    public Boolean save(MultipartFile multipartFile, DataType dataType) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null)
            return false;

        String storeFilename = createStoreFileName(originalFilename);

        // check
        System.out.println("originalFilename = " + originalFilename);
        System.out.println("storeFilename = " + storeFilename);

        File saveFile = new File(storeFilename);
        if(saveFile.isFile())
            return false;

        if(!saveFile.getParentFile().exists()) {
            boolean res = saveFile.getParentFile().mkdirs();
            if (!res)
                return false;
        }

        System.out.println("saveFile.getAbsolutePath() = " + saveFile.getAbsolutePath());
        multipartFile.transferTo(new File(saveFile.getAbsolutePath()));

        DataInfo dataInfo = DataInfo.builder()
                .dataType(dataType)
                .saveDataPath(storeFilename)
                .build();

        Optional<DataInfo> ret = dataInfoRepository.save(dataInfo);
        return ret.isPresent();
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        String absolutePath = Paths.get(rootDataPath, uuid + "." + ext).toFile().getAbsolutePath();
        System.out.println("absolutePath = " + absolutePath);
        return Paths.get(rootDataPath, uuid + "." + ext).toString();
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    @Override
    public Optional<DataInfo> load(Long id) {
        return dataInfoRepository.load(id);
    }
}
