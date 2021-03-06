package vn.com.truyen.service;

import vn.com.truyen.service.dto.CategoryDTO;
import vn.com.truyen.service.mapper.CategoryMapper;
import vn.com.truyen.service.mess.CategoryMess;
import vn.com.truyen.service.mess.TruyenMess;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link vn.com.truyen.domain.Category}.
 */
public interface CategoryService {

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryDTO save(CategoryDTO categoryDTO);

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryDTO> findAll(Pageable pageable);
    /**
     * 
     * @param pageNo
     * @param pageSize
     * @param name
     * @param sortType
     * @param sortBy
     * @return
     */
    CategoryMess findAllCategorys(Integer pageNo, Integer pageSize, String name, String sortType, String sortBy);
    
    /**
     * 
     * @param pageNo
     * @param pageSize
     * @param name
     * @param sortBy
     * @return
     */
    TruyenMess findAllTruyenbyCategoryId(Integer pageNo, Integer pageSize, Long id, String name, String sortBy);
    
    
//    /**
//     * Get all the categories with eager load of many-to-many relationships.
//     *
//     * @return the list of entities.
//     */
//    Page<CategoryDTO> findAllWithEagerRelationships(Pageable pageable);


//    /**
//     * Get the "id" category.
//     *
//     * @param id the id of the entity.
//     * @return the entity.
//     */
//    Optional<CategoryDTO> findOne(Long id);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
