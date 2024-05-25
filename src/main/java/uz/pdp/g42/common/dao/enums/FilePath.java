package uz.pdp.g42.common.dao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.ws.rs.GET;

@AllArgsConstructor
@Getter
public enum FilePath {
    CATEGORY("/Users/macbookair/Desktop/projectUzum/team_uzum_bot/json/category.json"),
    PRODUCT("/Users/macbookair/Desktop/projectUzum/team_uzum_bot/json/product.json"),
    USER("/Users/macbookair/Desktop/projectUzum/team_uzum_bot/json/user.json");
    private String path;
}
