package afoc.snsclonespringboot.data;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryDataInfoRepository implements DataInfoRepository {
    private static final Map<Long, DataInfo> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Optional<DataInfo> save(DataInfo data) {
        data.setId(++sequence);
        store.put(data.getId(), data);
        return Optional.of(data);
    }

    @Override
    public Optional<DataInfo> load(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Boolean delete(Long id) {
        store.remove(id);
        return true;
    }

    @Override
    public void clear(){
        store.clear();
        sequence = 0L;
    }
}
