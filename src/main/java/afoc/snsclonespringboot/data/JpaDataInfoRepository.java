package afoc.snsclonespringboot.data;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public void clear() {

    }
}
