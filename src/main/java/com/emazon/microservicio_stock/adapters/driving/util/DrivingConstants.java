package com.emazon.microservicio_stock.adapters.driving.util;

public class DrivingConstants {
    private DrivingConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DEFAULT_PAGE_PARAM = "0";
    public static final String DEFAULT_SIZE_PARAM = "10";
    public static final String DEFAULT_SORT_PARAM = "asc";
    public static final String DEFAULT_SORT_BY_PARAM = "name";

    public static final String HAS_ROLE_ADMIN = "hasRole('ADMIN')";

    public static final String PRODUCT_ID = "productId";
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORIES_ID = "categoriesId";
    public static final String CATEGORIES = "categories";
    public static final String BRAND_ID = "brandId";
    public static final String BRAND_BRAND_ID = "brand.brandId";
    public static final String BRAND_BRAND_NAME = "brand.name";
    public static final String BRAND_BRAND_DESCRIPTION = "brand.description";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FORMAT_MAP_TO_CATEGORIES = "mapToCategories";
}
