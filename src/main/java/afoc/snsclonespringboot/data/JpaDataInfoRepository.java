package afoc.snsclonespringboot.data;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class JpaDataInfoRepository implements DataInfoRepository {

    @PersistenceContext
    private EntityManager em;

    public JpaDataInfoRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Optional<DataInfo> save(DataInfo data) {
        em.persist(data);

        return Optional.of(data);
    }

    @Override
    public Optional<DataInfo> load(Long id) {
        DataInfo dataInfo = em.find(DataInfo.class, id);

        return Optional.ofNullable(dataInfo);
    }

    @Override
    public Boolean delete(Long id) {
        DataInfo dataInfo = em.find(DataInfo.class, id);

        if (dataInfo == null){
            return false;
        }

        em.remove(dataInfo);
        return true;
    }

    @Override
    public void clear() {

    }
}
