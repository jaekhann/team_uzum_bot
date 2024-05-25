package uz.pdp.g42.common.service;

import lombok.RequiredArgsConstructor;
import uz.pdp.g42.common.dao.CategoryDao;
import uz.pdp.g42.common.model.Category;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class CategoryService implements BaseService<Category>{

    private final CategoryDao categoryDao;

    public void add(Category category) throws IOException {
        categoryDao.create(category);
    }

    @Override
    public List<Category> list() throws IOException {
        return categoryDao.list()
                .stream().filter(category -> category.getParentId() == null)
                .toList();
    }

    @Override
    public List<Category> getById(UUID id) throws IOException {
        List<Category> list = categoryDao.list();
        return list.stream()
                .filter(category -> category.getParentId() != null && category.getParentId().equals(id))
                .toList();
    }

    public boolean hasChildCategory(UUID id) throws IOException {
        List<Category> list = categoryDao.list().stream()
                .filter(category -> category.getParentId() != null).toList();

        return list.stream().anyMatch(category -> category.getParentId().equals(id));
    }

    public Map<Integer, UUID> getCategoryMap(UUID uuid) throws IOException {

        List<Category> categoryList = categoryDao.list();
        int key =1;
        Map<Integer, UUID> map = new HashMap<>();
        for (Category category :categoryList){
            if (!category.getId().equals(uuid)){
                System.out.println(key + ". " +category.getName());
                map.put(key++ , category.getId());
            }

        }
        return map;
    }

}
