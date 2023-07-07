package com.kamp2023.movieapp

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainActivityViewModel by viewModels()

        setContent {
            Column() {
                MovieListScreen(viewModel)
                //MovieAppGrid(viewModel)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppGrid(viewModel: MainActivityViewModel) {
    var trazeniFilm by remember { mutableStateOf("") }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            TextField(
                value = trazeniFilm,
                onValueChange = {
                    trazeniFilm = it
                },
                label = { Text("Pronadji film") },
                singleLine = true,
                modifier = Modifier.wrapContentSize()
            )

            Button(
                onClick = { viewModel.searchMovies(trazeniFilm) },
                modifier = Modifier.wrapContentSize()
            )
            {
                Text("Trazi")
            }

        }

        val movies by viewModel.movies.collectAsState()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(movies.count()) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.White)
                ) {
                    MovieItem(movie = movies.get(index))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(viewModel: MainActivityViewModel) {
    var trazeniFilm by remember { mutableStateOf("") }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            TextField(
                value = trazeniFilm,
                onValueChange = {
                    trazeniFilm = it
                },
                label = { Text("Pronadji film") },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .onKeyEvent {
                        if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER || it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_TAB) {
                            viewModel.searchMovies(trazeniFilm)
                        }
                        false
                    }
            )

            Button(
                onClick = { viewModel.searchMovies(trazeniFilm) },
                modifier = Modifier.wrapContentSize()
            )
            {
                Text("Trazi")
            }

        }

        val movies by viewModel.movies.collectAsState()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(movies) { movie ->
                MovieItem(movie = movie)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
    var isOverviewVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { isOverviewVisible = !isOverviewVisible }
            .animateContentSize()  // add this line
    ) {
        Text(text = movie.title, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    placeholder(R.drawable.ic_launcher_background)
                    error(R.drawable.ic_launcher_background)
                }
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (isOverviewVisible) {
            Text(text = movie.overview)
        }
    }
}
