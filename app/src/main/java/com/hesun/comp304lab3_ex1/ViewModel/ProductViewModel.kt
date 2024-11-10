package com.hesun.comp304lab3_ex1.ViewModel

import androidx.lifecycle.ViewModel
import com.hesun.comp304lab3_ex1.Model.Product
import com.hesun.comp304lab3_ex1.Model.ProductRepo

class ProductViewModel(private val productRepo: ProductRepo) : ViewModel() {


    fun getListOfProducts(): ArrayList<Product>{
        return productRepo.getProducts()
    }


}