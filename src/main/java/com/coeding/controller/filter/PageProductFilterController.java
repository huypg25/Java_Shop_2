package com.coeding.controller.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coeding.entity.Brand;
import com.coeding.entity.Product;
import com.coeding.service.BrandService;
import com.coeding.service.ProductService;

/**
 */
@RestController
@RequestMapping(path = "/filter", produces = "application/json")
@CrossOrigin(origins = "*")

public class PageProductFilterController {

    private ProductService productService;
    private BrandService brandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PageProductFilterController.class);

    @Autowired
    public PageProductFilterController( ProductService productService, BrandService brandService) {
        this.productService = productService;
        this.brandService = brandService;
    }


    @GetMapping("/product")
    public  List<Product> userCheckboxHomePage(
            @RequestParam(name = "category", required = false) Long categoryId,
            @RequestParam(name = "brand", required = false) List<Long> listBrandId,
            @RequestParam(name = "type", required = false) List<String> listType,
            @RequestParam(name = "sort", required = false) String sortCondition,
            @RequestParam(name = "priceFrom",required = false) Double minPrice,
            @RequestParam(name = "priceTo",required = false) Double maxPrice

    ) {
        List<Product> products = new ArrayList<>();
        LOGGER.info("new products: "+products);
        LOGGER.info("get categoryId from request: " +categoryId);
        if (categoryId!=null){

            //filter by price
            if (minPrice!=null&&maxPrice!=null){
                LOGGER.info("list filter by price with  (categoryId!=null,minPrice!=null,maxPrice!=null)");
                products = productService.findByPriceBetween(minPrice,maxPrice).stream()
                .filter(p->p.getCategory().getId() == categoryId)
                .collect(Collectors.toList());

                if (listBrandId !=null && listType !=null){
                    LOGGER.info("list filter by price with  (categoryId!=null,minPrice!=null,maxPrice!=null,listBrandId !=null , listType !=null)");
                    List<Product> listByBrand =  getProductByBrand(products,listBrandId);
                }else if (listType !=null){
                    LOGGER.info("list filter by price with  (categoryId!=null,minPrice!=null,maxPrice!=null,listType !=null)");
                }else if (listBrandId !=null){
                    LOGGER.info("list filter by price with  (categoryId!=null,minPrice!=null,maxPrice!=null,listBrandId !=null)");
                    products = getProductByBrand(products,listBrandId);
                }

            }else
            //filter by Type and Brand
            if (listBrandId !=null && listType !=null){
                LOGGER.info("list filter by Type and Brand with (categoryId!=null,listBrandId !=null, listType !=null)");
                List<Product> listByCategoryAndBrand = getProductByCategoryAndBrand(categoryId,listBrandId);

            }else if (listBrandId != null) {
                LOGGER.info("list filter by Brand with (categoryId!=null,listBrandId != null)");
                products =  getProductByCategoryAndBrand(categoryId,listBrandId);
            }else if (listType!=null){
                LOGGER.info("list filter by Type with (categoryId!=null,listType != null)");
            }else{
                //all
                LOGGER.info("list filter by All with (categoryId!=null,listBrandId==null,listType==null)");
                products =  productService.findByCategoryId(categoryId);
            }

        }else{
            LOGGER.info("minPrice: "+minPrice+"maxPrice: "+maxPrice);
            //filter by price
            if (minPrice!=null&&maxPrice!=null){
                LOGGER.info("list filter by price with  (minPrice!=null,maxPrice!=null)");
                products = productService.findByPriceBetween(minPrice,maxPrice);
                if (listBrandId !=null && listType !=null){
                    LOGGER.info("list filter by price with  (minPrice!=null,maxPrice!=null,listBrandId !=null , listType !=null)");
                    List<Product> listByBrand =  getProductByBrand(products,listBrandId);
                }else if (listType !=null){
                    LOGGER.info("list filter by price with  (minPrice!=null,maxPrice!=null,listType !=null)");
                }else if (listBrandId !=null){
                    LOGGER.info("list filter by price with  (minPrice!=null,maxPrice!=null,listBrandId !=null)");
                    products = getProductByBrand(products,listBrandId);
                }
            }else if (listBrandId !=null && listType !=null){
                LOGGER.info("list filter by Type and Brand with (listBrandId !=null , listType !=null)");
                List<Product> listByBrand =  getProductByBrand(listBrandId);
            }else if (listBrandId != null) {
                LOGGER.info("list filter by Brand with (listBrandId!=null)");
                products =  getProductByBrand(listBrandId);
            }else if (listType!=null){
                LOGGER.info("list filter by Type with (listType!=null)");
            }else{
                LOGGER.info("list filter by All with (listBrandId ==null, listType ==null)");
                products =  productService.findAll();
                 }
        }

        //sort
        if (sortCondition !=null){
            LOGGER.info("list sort with (sortCondition !=null)");
            products = sortByPrice(products,sortCondition);
        }

        LOGGER.info("return products");
        return products;
    }

    @GetMapping("/search")
    public List<Product> searchFilter(
            @RequestParam(name = "categoryId",required = false) Long categoryId,
            @RequestParam(name = "productName",required = false)String keyword){
            if (categoryId !=null){
//                return productService.findByProductNameAndCategoryId(productName, categoryId);
                return productService.findProductContainsByCategoryId(keyword, categoryId);
            }
//            return productService.findByProductName(productName);
        return productService.findProductContains(keyword);
    }



    @GetMapping("/brand")
    public List<Brand> brandFilter(@RequestParam(name = "brandName", required = false) String brandName){
        return brandService.findByNameContains(brandName);
    }
    @GetMapping("/getAllProducts")
    public List<Product> getAllProduct(){
        return productService.findAll();
    }

    public  List<Product> sortByPrice(List<Product>productList,String condition){
        if (condition.equals("lowToHigh")){
            return productList.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
        }else{
            return productList.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .collect(Collectors.toList());
        }
    }

   



    public   List<Product> getProductByCategoryAndBrand(Long categoryId,List<Long> brandIdList){
        List<Product> list = new ArrayList<>();
        brandIdList.forEach(id -> {
            productService.findByCategoryIdAndBrandId(categoryId,id).forEach(p->list.add(p));
        });
        return list;
    }


   
    public  List<Product> getProductByBrand(List<Product>productList,List<Long> brandIdList){
        List<Product> list = new ArrayList<>();
        brandIdList.forEach(brandId -> {
            productList.stream()
                    .filter(p->p.getBrand().getId() == brandId)
                    .forEach(p->list.add(p));
        });
        return list;
    }


    public  List<Product> getProductByBrand(List<Long> brandIdList){
        List<Product> list = new ArrayList<>();
        brandIdList.forEach(id -> {
            productService.findByBrandId(id).forEach(p->list.add(p));
        });
        return list;
    }
}
