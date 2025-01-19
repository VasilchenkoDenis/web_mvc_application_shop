package com.denisvasilchenko.web_mvc_application.controller;


import com.denisvasilchenko.web_mvc_application.ErrorResponse;
import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.entity.Sale;
import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.service.ProductService;
import com.denisvasilchenko.web_mvc_application.service.ReturnService;
import com.denisvasilchenko.web_mvc_application.service.SaleService;
import com.denisvasilchenko.web_mvc_application.service.UserService;
import com.denisvasilchenko.web_mvc_application.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

//на мой взгляд лучше разнести этот контроллер на несколько более специфичных, например
//UserController , SalesController ProductsController
//тогда у тебя в самом верху может быть @RequestMapping("/api/users") - как пример для UserController
//просто в случае большого проекта ты хочешь найти контроллер для юзера и все эндпоинты спрятаны в ResController
//который непонятно почему так назван

//разделения его будет больше соответствовать Single-responsibility principle (SRP), вот можно почитать:
//https://ru.wikipedia.org/wiki/%D0%9F%D1%80%D0%B8%D0%BD%D1%86%D0%B8%D0%BF_%D0%B5%D0%B4%D0%B8%D0%BD%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D0%BE%D0%B9_%D0%BE%D1%82%D0%B2%D0%B5%D1%82%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D0%BE%D1%81%D1%82%D0%B8
@RestController
@RequestMapping("/api")
public class ResController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private ReturnService returnService;
    @Autowired
    private UserService userService;
    @Autowired
    private Shop shop;

    @GetMapping("/products")
    public ResponseEntity<?> search(@RequestParam String search) {
        List<Product> products = productService.findProductByName(search);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            List<Product> similarProducts = productService.findSimilarProducts(search);
            String message = "Товар не существует. Возможно Вам подойдет: " + similarProducts.get(0).getName();

            ErrorResponse errorResponse = new ErrorResponse(message, similarProducts);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/sales")
    public ResponseEntity<String> addSale(@RequestBody List<Product> products) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByName(username);
        //есть библиотеки Apache Commons и Google Guava которые предоставляют удобные методы для таких вещей
        //например CollectionUtils.isEmpty(products)
        //вижу такое есть еще и в спринге
        //внутри он делает тоже самое, ты можешь даже статически импортировать этот метод
        //import static org.springframework.util.CollectionUtils.isEmpty;
        //и будет еще красивее просто if (isEmpty(products))
        if (isEmpty(products)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect product data.");
        }
        try {
            saleService.addSale(products, user);
            return ResponseEntity.status(HttpStatus.OK).body("Sale added.");
        } catch (Exception e) {
            //попробуй все же добавить Log4j библиотеку чтобы логи более красиво писало
            //в реальности никогда в приложениях System.out.println не используется
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/sales")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<List<Sale>> getSalesByDate(@RequestParam String date) {
        List<Sale> sales = saleService.getSalesPerDay(LocalDate.parse(date));
        return ResponseEntity.ok(sales);
    }

    @PostMapping("/returns")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> addReturn(@RequestBody Sale sale) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByName(username);
        if (sale == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect sale data.");
        }
        try {
            returnService.save(shop.createReturn(sale, user));
            return ResponseEntity.status(HttpStatus.OK).body("Return added.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if(userService.findUserById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + id + " not found.");
        }
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User with id " +id+ " deleted.");
    }
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/users/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        User foundUser = userService.findUserById(user.getId());
        if(foundUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + user.getId() + " not found.");
        }
        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("User with id " + user.getId() + " updated.");
    }

    @PostMapping("/users/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addUser(@RequestBody User user) {

        if(user.getName()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect user data.");
        }
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("User added.");
    }
}


