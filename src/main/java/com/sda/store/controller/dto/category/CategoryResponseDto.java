package com.sda.store.controller.dto.category;

import java.util.List;

public class CategoryResponseDto {

    private Long id;

    private String name;

    private List<CategoryResponseDto> subCategory;

    private String parentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryResponseDto> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<CategoryResponseDto> subCategory) {
        this.subCategory = subCategory;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
