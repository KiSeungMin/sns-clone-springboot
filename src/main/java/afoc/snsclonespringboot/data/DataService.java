package afoc.snsclonespringboot.data;

public interface DataService {
    /*
    - 데이터 저장하기
    - 데이터 불러오기
     */
    Boolean save(Data data);
    Data load(Long id);
}
