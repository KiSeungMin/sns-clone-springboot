package afoc.snsclonespringboot.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DataServiceImpl implements DataService{
    private final DataInfoRepository dataInfoRepository;

    @Override
    public Boolean save(DataInfo data) {
        Optional<DataInfo> ret = dataInfoRepository.save(data);
        return ret.isPresent();
    }

    @Override
    public Optional<DataInfo> load(Long id) {
        return dataInfoRepository.load(id);
    }
}
