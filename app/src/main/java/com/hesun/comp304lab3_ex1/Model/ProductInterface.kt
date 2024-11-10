package com.hesun.comp304lab3_ex1.Model

interface ProductInterface {
    fun getProducts () : ArrayList<Product>
    fun addNewProduct(p: Product)
    fun deleteOneProduct (p: Product)
    fun updateProduct()
}