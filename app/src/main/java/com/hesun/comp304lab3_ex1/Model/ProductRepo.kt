package com.hesun.comp304lab3_ex1.Model

class ProductRepo :ProductInterface{
    override fun getProducts(): ArrayList<Product> {
        var list: ArrayList<Product> = ArrayList<Product>()
        list.add(Product(1,"Hats",4,9.9))
        list.add(Product(1,"Tshirts",40,20.9))
        list.add(Product(1,"Shoes",11,50.9))
        return list
    }

    override fun addNewProduct(p: Product) {

    }

    override fun deleteOneProduct(p: Product) {

    }

    override fun updateProduct() {

    }
}