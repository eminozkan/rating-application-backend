package dev.ozkan.ratingapp.app.product;

import dev.ozkan.ratingapp.business.core.ResponseMessage;
import dev.ozkan.ratingapp.business.core.resulthandler.BusinessResultHandler;
import dev.ozkan.ratingapp.config.UserSession;
import dev.ozkan.ratingapp.config.handler.exception.WrongCategoryNameException;
import dev.ozkan.ratingapp.core.model.user.UserRole;
import dev.ozkan.ratingapp.core.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PutMapping("/admin/products")
    ResponseEntity<?> addProduct(UserSession userSession,@RequestBody SaveProductRequest request) throws WrongCategoryNameException {
        if (userSession.role() != UserRole.ADMIN){
            return new ResponseEntity<>(new ResponseMessage().setMessage("Unauthorized user"),HttpStatus.UNAUTHORIZED);
        }
        var result = productService.saveProduct(request.toServiceRequest());
        if (!result.isSuccess()){
            return BusinessResultHandler.handleFailureReason(result.getReason(),result.getMessage());
        }
        return new ResponseEntity<>(new ResponseMessage().setMessage("successfully created"),HttpStatus.CREATED);
    }


    @GetMapping("/products")
    ResponseEntity<?> getProducts(@RequestParam(required = false) String category) throws WrongCategoryNameException {
        var result = productService.getProducts(category);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/products/{productId}")
    ResponseEntity<?> getProduct(@PathVariable String productId){
        var result = productService.getProduct(productId);
        if (result.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage().setMessage("Product not found."));
        }
        return ResponseEntity.ok(result.get());
    }
}
