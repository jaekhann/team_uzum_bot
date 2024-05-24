package uz.pdp.g42.common.dao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.ws.rs.GET;

@AllArgsConstructor
@Getter
public enum FilePath {
    CATEGORY("/Users/macbookpro/Desktop/g42_uzum_uz/json/category.json"),
    PRODUCT("/Users/macbookpro/Desktop/g42_uzum_uz/json/product.json"),
    USER("/Users/macbookpro/Desktop/g42_uzum_uz/json/user.json");
    private String path;
}
