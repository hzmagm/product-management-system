package com.springdemo.products_management_system.Controller;

import com.springdemo.products_management_system.Model.Product;
import com.springdemo.products_management_system.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    public ProductRepository pRepository;
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        try{
            if(pRepository.existsById(id)){
                return new ResponseEntity<>(pRepository.findById(id).get(), HttpStatus.OK);
            }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts() {
        try{
            ArrayList<Product> products = new ArrayList<>();
            products.addAll(pRepository.findAll());
            if(products.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(pRepository.findAll(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        try{
            if(pRepository.findByName(name).isPresent()){
                return  new ResponseEntity<>(pRepository.findByName(name).get(), HttpStatus.OK);
            }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/product/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try{
            return new ResponseEntity<>(pRepository.save(product), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        try{
        Product oldProduct = pRepository.findById(product.getId()).orElse(null);
        if(oldProduct == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            oldProduct.setId(product.getId());
            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
        return new ResponseEntity<>(pRepository.save(oldProduct), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/product")
    public ResponseEntity<Product> deleteProduct(@RequestBody Product product) {
        try{
            Product oldProduct = pRepository.findById(product.getId()).orElse(null);
            if(oldProduct==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            pRepository.delete(oldProduct);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
