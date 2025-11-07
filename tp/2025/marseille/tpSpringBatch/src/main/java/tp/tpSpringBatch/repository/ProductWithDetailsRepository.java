package tp.tpSpringBatch.repository;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tp.tpSpringBatch.model.ProductWithDetails;

//SpringData JPA Repository
public interface ProductWithDetailsRepository extends JpaRepository<ProductWithDetails,Integer> {
    //main iherited methods: save, findById, findAll, delete, ...
    @Query("SELECT p FROM ProductWithDetails p WHERE p.id >= :minId AND p.id <= :maxId")
    Slice<ProductWithDetails> findFromMinIdToMaxId(Integer minId, Integer maxId,
                                                   org.springframework.data.domain.PageRequest pageRequest);
}
