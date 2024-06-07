package com.ftn.sbnz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.model.models.Product;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.repository.IProductRepository;
import com.ftn.sbnz.repository.IRoomRepository;

@Service
public class ProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IRoomRepository roomRepository;

    public Product addProduct(String name, Long productGroupId, Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No room with that id."));

        Product newProduct = new Product(name, room);
        if (productGroupId != 0L) {
            Product productGroup = productRepository.findById(productGroupId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product with that id."));
            newProduct.setIsContainedIn(productGroup);

        }

        productRepository.save(newProduct);
        productRepository.flush();
        return newProduct;

    }

}
