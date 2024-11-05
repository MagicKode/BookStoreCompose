package com.example.bookstorecompose

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookstorecompose.data.Book
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val fs = Firebase.firestore
    val storage = Firebase.storage.reference.child("images")

    val list = remember {    // объект для обновления состояния списка
        mutableStateOf(emptyList<Book>())
    }

//    fs.collection("books").get().addOnCompleteListener{task ->//сичитываем документы
//        if (task.isSuccessful) {
//            list.value = task.result.toObjects(Book::class.java)  //получаем список из объектов
//        }
//    }

    fs.collection("books")
        .addSnapshotListener() { snapshot, exception ->  //добавляем  документы  при нажатии
            list.value =
                snapshot?.toObjects(Book::class.java) ?: emptyList()  //получаем список из объектов
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            items(list.value) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = book.imageUrl,
                            contentDescription = "",
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                        )
                        Text(
                            text = book.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth()
                                .padding(15.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            onClick = {
                val task = storage.child("book.jpg").putBytes(
                    bitmapToByteArray(context)
                )
                task.addOnSuccessListener { uploadTask ->  //мы загрузили картинку и получили инфу о загруденной картинке на Storage
                    uploadTask.metadata?.reference?.downloadUrl?.addOnCompleteListener { uriTask -> //ждём ссылку на картинку
                        saveBook(fs, uriTask.result.toString())
                    }
                }

            }) {
            Text(
                text = "Add Book"
            )
        }
    }
}

/**
 * ф.ция которая берёт картинку и загружает в Storage
 */
private fun bitmapToByteArray(context: Context): ByteArray {
    val bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.book
    )  //берём картинку из ресурсов
    val baos = ByteArrayOutputStream()          // создаём поток из байт
    bitmap.compress(
        Bitmap.CompressFormat.JPEG,
        100,
        baos
    )  // записываем Bitmap и сжимаем его в формат JPEG
    return baos.toByteArray()  //возвращаем как как массив байт
}

private fun saveBook(fs: FirebaseFirestore, url: String) {
    fs.collection("books").document()
        .set(  //Функция Добавления элементов в коллекцию напрямую в Firestore!!
            Book(
                "My Book",
                "Description",
                "100",
                "fiction",
                url
            )
        )
}
