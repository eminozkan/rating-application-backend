package dev.ozkan.ratingapp.app.category;

import dev.ozkan.ratingapp.core.model.product.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    @GetMapping("/categories")
    ResponseEntity<?> getCategories(){
        return ResponseEntity.ok(Category.values());
    }
}
