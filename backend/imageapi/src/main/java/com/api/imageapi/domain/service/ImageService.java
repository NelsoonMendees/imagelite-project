package com.api.imageapi.domain.service;

import java.util.Optional;
import java.util.List;

import com.api.imageapi.domain.entity.Image;
import com.api.imageapi.domain.enums.ImageExtension;

public interface ImageService {
    Image save(Image image);

    Optional<Image> getById(String id);

    List<Image> search(ImageExtension extension, String query);
}
