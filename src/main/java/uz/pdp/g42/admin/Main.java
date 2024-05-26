package uz.pdp.g42.admin;

import uz.pdp.g42.bot.service.InlineMarkupService;
import uz.pdp.g42.common.dao.CategoryDao;
import uz.pdp.g42.common.dao.ProductDao;
import uz.pdp.g42.common.model.Category;
import uz.pdp.g42.common.model.Product;
import uz.pdp.g42.common.service.CategoryService;
import uz.pdp.g42.common.service.FileService;
import uz.pdp.g42.common.service.ProductService;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scannerStr = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);


        FileService<Category> fileService = new FileService<>();
        CategoryDao categoryDao = new CategoryDao(fileService);
        CategoryService categoryService = new CategoryService(categoryDao);

        FileService<Product> productFileService = new FileService<>();
        ProductDao productDao = new ProductDao(productFileService);
        ProductService productService = new ProductService(productDao);

        int stepCode = 10;
        while (stepCode != 0) {

            System.out.println("1. Add Category 2. Add Product");
            stepCode = scannerInt.nextInt();

            switch (stepCode) {
                case 1 -> {
                    Category category = new Category();
                    System.out.println("enter category name: ");
                    category.setName(scannerStr.nextLine());
                    System.out.println("do you want to set category: ");
                    System.out.println("yes/no");
                    String yes = scannerStr.nextLine();
                    if (yes.equalsIgnoreCase("yes")) {
                        Map<Integer, UUID> categoryMap = categoryService.getCategoryMap(category.getId());
                        UUID uuid = categoryMap.get(scannerInt.nextInt());
                        if (uuid != null) {
                            category.setParentId(uuid);
                        }
                    }
                    categoryService.add(category);
                    System.out.println("Category added");
                }

                case 2 -> {
                    Product product = new Product();
                    System.out.println("enter product name");
                    product.setName(scannerStr.nextLine());

                    System.out.println("enter price");
                    product.setPrice(scannerInt.nextDouble());
                    System.out.println("do you want to set category: ");
                    System.out.println("yes/no");
                    String yes = scannerStr.nextLine();
                    if (yes.equalsIgnoreCase("yes")) {
                        Map<Integer , UUID> categoryMap = categoryService.getCategoryMap(null);
                        UUID uuid = categoryMap.get(scannerInt.nextInt());
                        if (uuid != null) {
                            product.setCategoryId(uuid);
                        }
                    }
                    System.out.println("Product added");
                    productService.create(product);
                }
            }
            System.out.println("test pull request");
        }
    }
}
