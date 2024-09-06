package com.underground.threadsclone.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.underground.threadsclone.Utils.SharedPref
import com.underground.threadsclone.models.ThreadModel
import com.underground.threadsclone.models.UserModel
import java.util.UUID

class AddThreadsViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("threads")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("threads/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> = _firebaseUser

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted



   fun saveImage(
        thread: String,
        userId: String,
        imageUri: Uri,
    ) {

        val uploadTask = imageRef.putFile(imageUri!!)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnCompleteListener {
                saveData(thread,userId, it.toString())
            }
        }


    }

   fun saveData(
        thread: String,
        userId: String,
        imageUri: String,
    ) {
        val threadData  = ThreadModel(thread,imageUri,userId,System.currentTimeMillis().toString())

        userRef.child(userRef.push().key!!).setValue(threadData).addOnSuccessListener {

           _isPosted.postValue(true)

        }.addOnFailureListener {
            _isPosted.postValue(false)

        }

    }




}