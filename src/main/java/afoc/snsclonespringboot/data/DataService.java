package afoc.snsclonespringboot.data;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface DataService {
    /*
    - 데이터 저장하기
    - 데이터 불러오기
     */
    String getAbsolutePath(String filename);
    String getTestAbsolutePath(String filename);
    Optional<DataInfo> save(MultipartFile file, DataType dataType);
    Optional<DataInfo> load(Long id);


}
