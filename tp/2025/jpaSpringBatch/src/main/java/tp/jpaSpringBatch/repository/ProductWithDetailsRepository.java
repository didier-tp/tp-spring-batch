package tp.jpaSpringBatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.jpaSpringBatch.model.ProductWithDetails;
//SpringData JPA Repository
public interface ProductWithDetailsRepository extends JpaRepository<ProductWithDetails,Integer> {
    //main iherited methods: save, findById, findAll, delete, ...
}
