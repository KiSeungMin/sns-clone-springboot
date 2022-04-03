package afoc.snsclonespringboot.data;

import java.util.Optional;

public interface DataService {
    /*
    - 데이터 저장하기
    - 데이터 불러오기
     */
    Boolean save(DataType dataType, String sourceDataPath);
    Optional<DataInfo> load(Long id);
}
