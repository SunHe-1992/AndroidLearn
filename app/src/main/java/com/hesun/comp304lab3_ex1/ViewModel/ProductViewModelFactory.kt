package com.hesun.comp304lab3_ex1.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hesun.comp304lab3_ex1.Model.ProductRepo


@Suppress("UNCHECKED_CAST")
class ProductViewModelFactory(private val repo: ProductRepo) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }

}

