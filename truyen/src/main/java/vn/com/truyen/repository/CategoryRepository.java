package vn.com.truyen.repository;

import vn.com.truyen.domain.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Category entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

//    @Query(value = "select distinct category from Category category left join fetch category.truyens",
//        countQuery = "select count(distinct category) from Category category")
//    Page<Category> findAllWithEagerRelationships(Pageable pageable);
//
//    @Query("select distinct category from Category category left join fetch category.truyens")
//    List<Category> findAllWithEagerRelationships();
    
    /**Tim nhu vay se khong phan trang cac phan tu duoi duoc.... nhung la cach viet code rat ngon cho cau lenh sql.*/
//    @Query("select category from Category category left join fetch category.truyens where category.id =:id")
//    Optional<Category> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query(value = "select ca from Category ca where ca.name LIKE %:name%")
    Page<Category> findAllCategorys(Pageable pageable, @Param("name") String name);
}
