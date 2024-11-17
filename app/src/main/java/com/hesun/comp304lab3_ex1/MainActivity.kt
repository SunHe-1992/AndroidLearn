package com.hesun.comp304lab3_ex1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.hesun.comp304lab3_ex1.Navigation.MyNavGraph
import com.hesun.comp304lab3_ex1.RoomDB.CityDataBase
import com.hesun.comp304lab3_ex1.ViewModel.AppRepository
import com.hesun.comp304lab3_ex1.ViewModel.ViewModelFactory
import com.hesun.comp304lab3_ex1.ViewModel.WeatherViewModel
import com.hesun.comp304lab3_ex1.ViewModel.WeatherViewModelFactory
import com.hesun.comp304lab3_ex1.ViewModel.citiesViewModel
import com.hesun.comp304lab3_ex1.Views.MyBottomBar
import com.hesun.comp304lab3_ex1.Views.MyTopBar
import com.hesun.comp304lab3_ex1.ui.theme.Hesun_COMP304Lab3_Ex1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //create db and vm
        val database = CityDataBase.getInstance(applicationContext)
        val repository = AppRepository(database.getCityDao())
        val cityVMFactory = ViewModelFactory(repository)
        val cityVM = ViewModelProvider(this, cityVMFactory)[citiesViewModel::class.java]

        val weatherVMFactory = WeatherViewModelFactory(repository)
        val weatherVM = ViewModelProvider(this, weatherVMFactory)[WeatherViewModel::class.java]
        setContent {
            Hesun_COMP304Lab3_Ex1Theme {
                MyFirstScaffold(cityVM, weatherVM)
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyFirstScaffold(cityVM: citiesViewModel, weatherVM: WeatherViewModel) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        bottomBar = { MyBottomBar(navController, cityVM) },
        topBar = { MyTopBar() }

//        floatingActionButton = { MyFavButton() },
//        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Column {

            MyNavGraph(innerPadding, navController = navController, cityVM, weatherVM)
        }

    }
}
