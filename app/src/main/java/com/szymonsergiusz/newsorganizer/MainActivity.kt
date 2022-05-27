package com.szymonsergiusz.newsorganizer

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.szymonsergiusz.newsorganizer.model.News
import com.szymonsergiusz.newsorganizer.ui.theme.NewsOrganizerTheme
import com.szymonsergiusz.newsorganizer.viewmodel.MainScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.logging.Logger


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsOrganizerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()
                    var text by rememberSaveable { mutableStateOf("")}
                    val viewModel = MainScreenViewModel(LocalContext.current.applicationContext as Application)
                    var isSearching by remember {
                        mutableStateOf(false)
                    }
                    Scaffold(
                        scaffoldState = scaffoldState,

                        topBar = {
                            TopAppBar(
                            navigationIcon = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(imageVector = Icons.Filled.Menu,
                                        contentDescription = "Menu")
                                }

                            },
                                title = { Text("News Organizer")},
                                actions = {
                                    Row {
                                        Icon(imageVector = Icons.Filled.Search,
                                            contentDescription = "Search", modifier = Modifier.fillMaxHeight())
                                        TextField(value = text, onValueChange = {
                                                text = it
                                            isSearching = if (text.isNotEmpty()) {
                                                viewModel.search(text)
                                                true
                                            } else false
                                                                                },

                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .padding(3.dp),
                                            textStyle = TextStyle(color = White),
                                            label = { Text(text = "Search", color = White)},
                                        )
                                        IconButton(onClick = {
                                            scope.launch(Dispatchers.IO) {
                                                viewModel.downloadNews()
                                            }
                                        }) {
                                            Icon(imageVector = Icons.Filled.Refresh, contentDescription = "refresh news")
                                        }
                                    }


                            }
                                )
                        },
                        
                        content = {
                            ScreenSetup(viewModel = viewModel, isSearching)

                        }
                                
                    )




//        Icon(imageVector = Icons.Filled.Search, contentDescription = "search field")

                    }
                }
            }
        }
    }

@Composable
fun ScreenSetup(viewModel: MainScreenViewModel, isSearching: Boolean) {
    val list by viewModel.searchResults.observeAsState(listOf())
    val list2 by viewModel.allNews.observeAsState(listOf())

    //TODO ZNALEŹĆ LEPSZY SPOSÓB

    if (isSearching) {
        Log.i("WYSZUKANE", list.toString())

        LazyColumn {
            items(items = list) {
                NewsCard(it)

            }
    } } else {
            LazyColumn {
                items(items = list2) {
                    NewsCard(it)

                }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsOrganizerTheme {
//        ScreenSetup(MockViewModel())
    }
}