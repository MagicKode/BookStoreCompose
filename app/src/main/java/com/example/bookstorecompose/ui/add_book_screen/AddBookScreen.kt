package com.example.bookstorecompose.ui.add_book_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.bookstorecompose.R
import com.example.bookstorecompose.data.Book
import com.example.bookstorecompose.ui.login.LoginButton
import com.example.bookstorecompose.ui.login.RoundedCornerTextField
import com.example.bookstorecompose.ui.theme.BoxFilterColor
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

@Preview(showBackground = true)
@Composable
fun AddBookScreen(
    onSaved: () -> Unit = {}
) {
    var selectedCategory = "Bestsellers"

    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val price = remember {
        mutableStateOf("")
    }

    val selectedImageUri = remember {     //для смены картинки фона , при выборе книги
        mutableStateOf<Uri?>(null)
    }

    val fireStore = remember {
        Firebase.firestore
    }

    val storage = remember {
        Firebase.storage
    }

    /**
     * лаунчер для выбора картинок, который будет со-здавать наш URI
     */
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),  //что мы хотим вызвать , какой контент
    ) { uri ->
        selectedImageUri.value = uri   //передаём ссылку на картинку
    }

    Image(
        painter = rememberAsyncImagePainter(
            model = selectedImageUri.value
        ),
        contentDescription = "bg",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.4f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoxFilterColor)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 40.dp, end = 40.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.books_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(90.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Add new book",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(15.dp))
        RoundedCornerDropDownMenu { selectedItem ->
            selectedCategory = selectedItem
        }
        Spacer(modifier = Modifier.height(15.dp))
        RoundedCornerTextField(
            text = title.value,
            label = "Title"
        ) {
            title.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            singleLine = false,
            maxLines = 5,
            text = description.value,
            label = "Description"
        ) {
            description.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = price.value,
            label = "Price"
        ) {
            price.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        LoginButton(text = "Select image") {
            imageLauncher.launch("image/*")   //выбираем картинку с телефона
        }
        LoginButton(text = "Save") {
            saveBookImage(
                selectedImageUri.value!!,
                storage,
                fireStore,
                Book(
                    title = title.value,
                    description = description.value,
                    price = price.value,
                    category = selectedCategory
                ),
                onSaved = {
                    onSaved()
                },
                onError = {

                }
            )
        }
    }
}

/**
 * при нажатии на SAVE, мы сначала
 *  - проверяем загружаем картинку
 *   - и после того загрузить уже текстовая часть.
 *
 *   Сначала нужна ссылка на картинку, и потом уже текстовуь часть, чтобы не сохранять и не обнавлять ещё раз.
 */
private fun saveBookImage(
    uri: Uri,
    storage: FirebaseStorage,
    fireStore: FirebaseFirestore,
    book: Book,
    onSaved: () -> Unit,
    onError: () -> Unit
) {
    val timeStamp = System.currentTimeMillis()
    val storageRef = storage.reference    //путь, где мы будем сохранять картинку
        .child("book_images")
        .child("image_$timeStamp.jpg")  //нужны разные названия, чтобыч картинки не перезаписывали друг друга
    val uploadTask = storageRef.putFile(uri)
    uploadTask.addOnSuccessListener {
        storageRef.downloadUrl.addOnSuccessListener { url ->
            //сохранение текстовой части, после того, как получили ссылку
            saveBookToFireStore(
                fireStore,
                url.toString(),
                book,
                onSaved = {
                    onSaved()
                },
                onError = {
                    onError()
                }
            )
        }
    }
}

private fun saveBookToFireStore(
    firestore: FirebaseFirestore,
    url: String,
    book: Book,
    onSaved: () -> Unit,
    onError: () -> Unit
) {
    val db = firestore.collection("books")
    val key = db.document().id
    db.document(key)
        .set(
            book.copy(
                key = key,
                imageUrl = url
            )
        )
        .addOnSuccessListener {
            onSaved()
        }
        .addOnFailureListener {
            onError()
        }
}
