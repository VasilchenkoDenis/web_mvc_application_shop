package com.denisvasilchenko.web_mvc_application.service;

import com.denisvasilchenko.web_mvc_application.entity.Product;
import com.denisvasilchenko.web_mvc_application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAllByAvailableTrue();
    }

    public boolean saveProduct(Product product) {
        Product savedProduct = productRepository.findByNameAndPurchasePrice(product.getName(), product.getPurchasePrice());
        if (savedProduct != null) {
            return false;
        }
        productRepository.save(product);
        return true;
    }

    public Product findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        //здесь и везде в других местах, нельзя никогда возвращать null из метода, для такого Optional и существует
        //лучше используй productRepository.getById(id)
        return product.orElse(null);
    }

    public List<Product> findProductByName(String name) {
        List<Product> foundProducts = new ArrayList<>();
        //find означает что метод возвращает Optional, хотя это не так
        //попробуй поменять чтобы возвралось Optional
        Product product = productRepository.findByName(name);
        if (product != null) {
            foundProducts.add(product);
        }

        return foundProducts;
    }

    //лучшее место чтобы написать junit тест, потому что не совсем ясно как работает такой метод
    //он возвращает любой товар у которого хотя бы 1 буква совпадает?
    public List<Product> findSimilarProducts(String name) {
        List<Product> similarProducts = new ArrayList<>();
        //советую попробовать переписать и возвращать только имена товаров, а не полностью всю таблицу
        //в будущем можно будет повесить index на колонку name чтобы операция была довольно быстрой
        List<Product> products = productRepository.findAll();
        Product suitableProduct = null;
        int minDistance = Integer.MAX_VALUE;
        for (Product p : products) {
            String str = p.getName();
            LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();
            int distance = levenshteinDistance.apply(str, name);
            //тут и везде в остальныъ местах, удали System.out.println
            //если хочется вывод в консоль, почитал про Log4j и как его конфигурировать
            System.out.println(distance);
            if (distance < minDistance) {
                minDistance = distance;
                suitableProduct = p;
            }
        }
        //правильно ли я понимаю что вернет только 1 элемент всегда? зачем тогда лист?
        similarProducts.add(suitableProduct);
        return similarProducts;
    }
}
