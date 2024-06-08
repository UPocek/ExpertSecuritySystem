package com.ftn.sbnz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.dtos.ReturnSellRateDTO;
import com.ftn.sbnz.model.models.PeopleReportResult;
import com.ftn.sbnz.model.models.ProductReportResult;
import com.ftn.sbnz.service.ProductDetectionService;

@RestController
@RequestMapping("/api/product_detection")
public class ProductDetectionController {

    @Autowired
    private ProductDetectionService productDetectionService;

    @PostMapping()
    public void addProductDetection(@RequestParam String productGroup, @RequestParam String act,
            @RequestParam String customerId, @RequestParam double price) {
        productDetectionService.insertDetection(productGroup, act, customerId, price);

    }
    // @GetMapping("/most_bought")
    // public List<PeopleReportResult> mostBoughtProducts(@RequestParam String
    // product,
    // @RequestParam String startDate,
    // @RequestParam String endDate) {

    // return productDetectionService.mostBoughtProducts(product, startDate,
    // endDate);
    // }

    @GetMapping("/most_return")
    public List<ProductReportResult> mostReturned() {

        return productDetectionService.mostReturned();
    }

    @GetMapping("/selling_trend")
    public List<PeopleReportResult> sellingTrend(@RequestParam String product,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return productDetectionService.sellingTrend(product, startDate, endDate);
    }

    @GetMapping("/take_return_rate")
    public ReturnSellRateDTO takeReturnRate(@RequestParam String product,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return productDetectionService.takeReturnRate(product, startDate, endDate);
    }

    @GetMapping("/money_return_loss")
    public List<ProductReportResult> moneyReturnLoss(@RequestParam String product,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return productDetectionService.moneyReturnLoss(product, startDate, endDate);
    }

    @GetMapping("/most_profitable_product")
    public List<ProductReportResult> mostProfitableProduct() {

        return productDetectionService.mostProfitableProduct();
    }
}
