package tp.tpSpringBatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.tpSpringBatch.model.ProductWithDetails;
//SpringData JPA Repository
public interface ProductWithDetailsRepository extends JpaRepository<ProductWithDetails,Integer> {
    //main iherited methods: save, findById, findAll, delete, ...
}
