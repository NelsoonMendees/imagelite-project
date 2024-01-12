package com.api.imageapi.infra.repository;

import static com.api.imageapi.infra.specs.GenericSpecs.*;
import static com.api.imageapi.infra.specs.ImageSpecs.*;
import static org.springframework.data.jpa.domain.Specification.anyOf;

import java.util.List;

import com.api.imageapi.domain.entity.Image;
import com.api.imageapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    // SELECT * FROM IMAGE WHERE 1 = 1 AND EXTENSION = 'PNG' AND (NAME LIKE 'QUERY'
    // OR TAGS LIKE 'QUERY)

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
        // SELECT * FROM IMAGE WHERE 1 = 1

        Specification<Image> spec = Specification.where(conjunction());

        if (extension != null) {
            // AND EXTENSION = 'PNG'
            spec = spec.and(extensionEqual(extension));
        }

        if (StringUtils.hasText(query)) {
            // AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY)
            spec = spec.and(anyOf(nameLike(query), tagsLike(query)));
        }

        return findAll(spec);
    }
}
