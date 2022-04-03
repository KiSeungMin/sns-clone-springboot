package afoc.snsclonespringboot.data;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    public Boolean save(DataType dataType, String sourceDataPath) {
        Optional<String> ext = Optional.ofNullable(sourceDataPath)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(sourceDataPath.lastIndexOf(".") + 1));

        if(ext.isEmpty())
            return false;

        String saveDataPath = Paths.get(rootDataPath, UUID.randomUUID().toString()) + ext.get();

        // check
        System.out.println("sourceDataPath = " + sourceDataPath);
        System.out.println("saveDataPath = " + saveDataPath);

        File file = new File(saveDataPath);
        if(file.isFile())
            return false;

        try {
            Files.move(Paths.get(sourceDataPath), Paths.get(saveDataPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        DataInfo dataInfo = DataInfo.builder()
                .dataType(dataType)
                .sourceDataPath(sourceDataPath)
                .saveDataPath(saveDataPath)
                .build();

        Optional<DataInfo> ret = dataInfoRepository.save(dataInfo);
        return ret.isPresent();
    }

    @Override
    public Optional<DataInfo> load(Long id) {
        return dataInfoRepository.load(id);
    }
}
