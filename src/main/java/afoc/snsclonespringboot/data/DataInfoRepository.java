package afoc.snsclonespringboot.data;

import java.util.Optional;

public interface DataInfoRepository {
    /*
    - 데이터 저장/불러오기
     */

    Optional<DataInfo> save(DataInfo data);
    Optional<DataInfo> load(Long id);

    // extra
    void clear();
}
