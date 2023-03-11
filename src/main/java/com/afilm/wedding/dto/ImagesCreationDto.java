package com.afilm.wedding.dto;

import com.afilm.wedding.domain.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImagesCreationDto {

    private List<Image> images;


    public void addimage(Image image) {
        this.images.add(image);
    }
}
