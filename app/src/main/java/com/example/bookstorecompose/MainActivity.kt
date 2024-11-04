package com.example.bookstorecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fs = Firebase.firestore
        fs.collection("books").document().set(mapOf("name" to "My fav book"))
    }
}