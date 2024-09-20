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
    public static final String HAS_ROLE_AUX_BODEGA_AND_ADMIN = "hasAnyRole('AUX_BODEGA', 'ADMIN')";

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
    public static final String FORMAT_MAP_TO_CATEGORIES_IDS = "mapToCategoriesIds";

    public static final String RESPONSE_CODE_200="200";
    public static final String RESPONSE_CODE_201="201";
    public static final String RESPONSE_CODE_400="400";
    public static final String RESPONSE_CODE_404="404";
    public static final String RESPONSE_CODE_409="409";

    public static final String TAG_BRAND_NAME = "Brand";
    public static final String TAG_BRAND_DESCRIPTION = "Brand API";
    public static final String SAVE_BRAND_SUMMARY = "Save a new brand";
    public static final String SAVE_BRAND_DESCRIPTION = "Creates a new brand in the database";
    public static final String SAVE_BRAND_RESPONSE_201_DESCRIPTION = "Brand created successfully";
    public static final String SAVE_BRAND_RESPONSE_400_DESCRIPTION = "Invalid input";
    public static final String SAVE_BRAND_RESPONSE_409_DESCRIPTION = "Brand already exists";

    public static final String GET_BRAND_SUMMARY = "Get brand";
    public static final String GET_BRAND_DESCRIPTION = "Recover a brand";
    public static final String GET_BRAND_RESPONSE_201_DESCRIPTION = "Brand retrieved successfully";
    public static final String GET_BRAND_RESPONSE_400_DESCRIPTION = "Invalid param";
    public static final String GET_BRAND_RESPONSE_404_DESCRIPTION = "Brand not found";

    public static final String GET_ALL_BRANDS_PAGINATED_SUMMARY = "Get all brands paginated";
    public static final String GET_ALL_BRANDS_PAGINATED_DESCRIPTION = "Retrieves a paginated list of brands";
    public static final String GET_ALL_BRANDS_PAGINATED_RESPONSE_200_DESCRIPTION = "Brands retrieved successfully";
    public static final String GET_ALL_BRANDS_PAGINATED_RESPONSE_400_DESCRIPTION = "Invalid pagination parameters";

    public static final String DELETE_BRAND_SUMMARY = "Delete brand";
    public static final String DELETE_BRAND_DESCRIPTION = "Delete a brand";
    public static final String DELETE_BRAND_RESPONSE_201_DESCRIPTION = "Brand deleted successfully";
    public static final String DELETE_BRAND_RESPONSE_400_DESCRIPTION = "Invalid param";
    public static final String DELETE_BRAND_RESPONSE_404_DESCRIPTION = "Brand not found";

    public static final String TAG_CATEGORY_NAME = "Category";
    public static final String TAG_CATEGORY_DESCRIPTION = "Category API";
    public static final String SAVE_CATEGORY_SUMMARY = "Save a new category";
    public static final String SAVE_CATEGORY_DESCRIPTION = "Creates a new category in the database";
    public static final String SAVE_CATEGORY_RESPONSE_201_DESCRIPTION = "Category created successfully";
    public static final String SAVE_CATEGORY_RESPONSE_400_DESCRIPTION = "Invalid input";
    public static final String SAVE_CATEGORY_RESPONSE_409_DESCRIPTION = "Category already exists";

    public static final String GET_CATEGORY_SUMMARY = "Get category";
    public static final String GET_CATEGORY_DESCRIPTION = "Recover a category";
    public static final String GET_CATEGORY_RESPONSE_201_DESCRIPTION = "Category retrieved successfully";
    public static final String GET_CATEGORY_RESPONSE_400_DESCRIPTION = "Invalid param";
    public static final String GET_CATEGORY_RESPONSE_404_DESCRIPTION = "Category not found";

    public static final String GET_ALL_CATEGORIES_PAGINATED_SUMMARY = "Get all categories paginated";
    public static final String GET_ALL_CATEGORIES_PAGINATED_DESCRIPTION = "Retrieves a paginated list of categories";
    public static final String GET_ALL_CATEGORIES_PAGINATED_RESPONSE_200_DESCRIPTION = "Categories retrieved successfully";
    public static final String GET_ALL_CATEGORIES_PAGINATED_RESPONSE_400_DESCRIPTION = "Invalid pagination parameters";

    public static final String DELETE_CATEGORY_SUMMARY = "Delete category";
    public static final String DELETE_CATEGORY_DESCRIPTION = "Delete a category";
    public static final String DELETE_CATEGORY_RESPONSE_201_DESCRIPTION = "Category deleted successfully";
    public static final String DELETE_CATEGORY_RESPONSE_400_DESCRIPTION = "Invalid param";
    public static final String DELETE_CATEGORY_RESPONSE_404_DESCRIPTION = "Category not found";

    public static final String TAG_PRODUCT_NAME = "Product";
    public static final String TAG_PRODUCT_DESCRIPTION = "Product API";
    public static final String SAVE_PRODUCT_SUMMARY = "Save a new product";
    public static final String SAVE_PRODUCT_DESCRIPTION = "Creates a new product in the database";
    public static final String SAVE_PRODUCT_RESPONSE_201_DESCRIPTION = "Product created successfully";
    public static final String SAVE_PRODUCT_RESPONSE_400_DESCRIPTION = "Invalid input";
    public static final String SAVE_PRODUCT_RESPONSE_409_DESCRIPTION = "Product already exists";

    public static final String GET_PRODUCT_SUMMARY = "Get product";
    public static final String GET_PRODUCT_DESCRIPTION = "Recover a product";
    public static final String GET_PRODUCT_RESPONSE_201_DESCRIPTION = "Product retrieved successfully";
    public static final String GET_PRODUCT_RESPONSE_400_DESCRIPTION = "Invalid param";
    public static final String GET_PRODUCT_RESPONSE_404_DESCRIPTION = "Product not found";

    public static final String GET_ALL_PRODUCTS_PAGINATED_SUMMARY = "Get all products paginated";
    public static final String GET_ALL_PRODUCTS_PAGINATED_DESCRIPTION = "Retrieves a paginated list of products";
    public static final String GET_ALL_PRODUCTS_PAGINATED_RESPONSE_200_DESCRIPTION = "Products retrieved successfully";
    public static final String GET_ALL_PRODUCTS_PAGINATED_RESPONSE_400_DESCRIPTION = "Invalid pagination parameters";

    public static final String DELETE_PRODUCT_SUMMARY = "Delete product";
    public static final String DELETE_PRODUCT_DESCRIPTION = "Delete a product";
    public static final String DELETE_PRODUCT_RESPONSE_201_DESCRIPTION = "Product deleted successfully";
    public static final String DELETE_PRODUCT_RESPONSE_400_DESCRIPTION = "Invalid param";
    public static final String DELETE_PRODUCT_RESPONSE_404_DESCRIPTION = "Product not found";

    public static final String UPDATE_PRODUCT_QUANTITY_SUMMARY = "Update product quantity";
    public static final String UPDATE_PRODUCT_QUANTITY_PRODUCT_DESCRIPTION = "Update the quantity of a product";
    public static final String UPDATE_PRODUCT_QUANTITY_PRODUCT_RESPONSE_201_DESCRIPTION = "Quantity Product updated successfully";
    public static final String UPDATE_PRODUCT_QUANTITY_PRODUCT_RESPONSE_400_DESCRIPTION = "Invalid input";
    public static final String UPDATE_PRODUCT_QUANTITY_PRODUCT_RESPONSE_404_DESCRIPTION = "Product not found";
}
