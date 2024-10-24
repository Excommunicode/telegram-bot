package kz.pandev.repository;

import kz.pandev.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Modifying
    @Query(nativeQuery = true, value =
            """
                    INSERT INTO categories(name, parent_id)
                    SELECT :childName, c.id FROM categories c WHERE c.name = :pareantName
                    """)
    int saveChildWithParent(String childName, String parentName);

    @Modifying
    @Query(nativeQuery = true, value =
            """
                    WITH RECURSIVE category_hierarchy AS (
                    SELECT id FROM categories WHERE name = :parentName
                    UNION
                    SELECT c.id FROM categories c
                    JOIN category_hierarchy ch ON  c.parent_id = ch.id)
                    DELETE FROM categories WHERE id IN (SELECT  id FROM category_hierarchy)
                    """)
    int deleteByNameAndId(String parentName);
}
