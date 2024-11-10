package com.hesun.comp304lab3_ex1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.hesun.comp304lab3_ex1.Model.Product
import com.hesun.comp304lab3_ex1.Model.ProductRepo
import com.hesun.comp304lab3_ex1.Navigation.MyNavGraph
import com.hesun.comp304lab3_ex1.ViewModel.ProductViewModel
import com.hesun.comp304lab3_ex1.ViewModel.ProductViewModelFactory
import com.hesun.comp304lab3_ex1.Views.MyBottomBar
import com.hesun.comp304lab3_ex1.Views.MyFavButton
import com.hesun.comp304lab3_ex1.Views.MyTopBar
import com.hesun.comp304lab3_ex1.ui.theme.Hesun_COMP304Lab3_Ex1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        var prepo = ProductRepo()
        var pfactory = ProductViewModelFactory(prepo)
        var myProductViewModel = ViewModelProvider(this, pfactory)[ProductViewModel::class.java]

        var mainlist = myProductViewModel.getListOfProducts()

        setContent {
            Hesun_COMP304Lab3_Ex1Theme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }

                MyFirstScaffold(mainlist)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyFirstScaffold(list: ArrayList<Product>) {
    val navController = rememberNavController()
    Scaffold(
        modifier =  Modifier.safeDrawingPadding(),

        bottomBar = { MyBottomBar(navController) },
        topBar = { MyTopBar() },
        floatingActionButton = { MyFavButton() },
        floatingActionButtonPosition = FabPosition.Center
    ) {

            innerPadding ->
        Column {
            MyNavGraph(navController = navController)
        }
    }
}
