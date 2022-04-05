package afoc.snsclonespringboot.data;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface DataService {
    /*
    - 데이터 저장하기
    - 데이터 불러오기
     */
    Boolean save(MultipartFile file, DataType dataType) throws IOException;
    Optional<DataInfo> load(Long id);
}
