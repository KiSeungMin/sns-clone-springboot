package afoc.snsclonespringboot.data;

public interface DataRepository {
    /*
    - 데이터 저장/불러오기
     */

    Boolean save(Data data);
    Data load(Long id);
}
