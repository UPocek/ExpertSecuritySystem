package com.ftn.sbnz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.PeopleReportResult;
import com.ftn.sbnz.service.ProductDetectionService;

@RestController
@RequestMapping("/api/product_detection")
public class ProductDetectionController {

    @Autowired
    private ProductDetectionService productDetectionService;

    @PostMapping()
    public void addProductDetection(@RequestParam Long productId, @RequestParam String act,
            @RequestParam String customerId, @RequestParam double price) {
        productDetectionService.insertDetection(productId, act, customerId, price);

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
    public List<PeopleReportResult> mostReturnedInDayTime(@RequestParam String product,
            @RequestParam String startDate,
            @RequestParam String endDate, @RequestParam String partOfDay) {

        return productDetectionService.mostReturnedInDayTime(product, startDate, endDate, partOfDay);
    }

    @GetMapping("/selling_trend")
    public List<PeopleReportResult> sellingTrend(@RequestParam String product,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return productDetectionService.sellingTrend(product, startDate, endDate);
    }

    @GetMapping("/take_return_rate")
    public List<PeopleReportResult> takeReturnRate(@RequestParam String product,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return productDetectionService.takeReturnRate(product, startDate, endDate);
    }

    @GetMapping("/money_return_loss")
    public List<PeopleReportResult> moneyReturnLoss(@RequestParam String product,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return productDetectionService.moneyReturnLoss(product, startDate, endDate);
    }

    @GetMapping("/most_profitable_product")
    public List<PeopleReportResult> mostProfitableProduct(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return productDetectionService.mostProfitableProduct(startDate, endDate);
    }
}
