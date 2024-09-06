package com.underground.threadsclone.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import com.underground.threadsclone.Utils.SharedPref
import com.underground.threadsclone.models.UserModel
import java.util.UUID

class AuthViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _firebaseUser.postValue(auth.currentUser)
                getData(auth.currentUser?.uid!!, context)
            } else {
                _error.postValue(it.exception!!.message)
            }
        }
    }

    private fun getData(uid: String, context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                SharedPref.storeData(
                    userData!!.name,
                    userData!!.email,
                    userData!!.username,
                    userData!!.password,
                    userData!!.imageUri,
                    context
                )

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun register(
        email: String,
        password: String,
        username: String,
        imageUri: Uri?,
        name: String,
        context: Context
    ) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _firebaseUser.postValue(auth.currentUser)
                saveImage(auth.currentUser?.uid, name, email, password, username, imageUri, context)
            } else {
                _error.postValue("Something Went Wrong")
            }
        }

    }

    private fun saveImage(
        uid: String?,
        name: String,
        email: String,
        password: String,
        username: String,
        imageUri: Uri?,
        context: Context
    ) {

        val uploadTask = imageRef.putFile(imageUri!!)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnCompleteListener {
                saveData(uid, name, email, password, username, it.toString(), context)
            }
        }


    }

    private fun saveData(
        uid: String?,
        name: String,
        email: String,
        password: String,
        username: String,
        imageUri: String,
        context: Context
    ) {
        val userData = UserModel(uid!!, name, email, password, username, imageUri)

        userRef.child(uid).setValue(userData).addOnSuccessListener {

            SharedPref.storeData(name, email, username, password, imageUri, context)

        }.addOnFailureListener {

        }

    }

    fun logout() {
        auth.signOut()
        _firebaseUser.postValue(null)
    }


}