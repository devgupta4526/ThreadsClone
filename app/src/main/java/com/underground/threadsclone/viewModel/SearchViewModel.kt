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
import com.underground.threadsclone.models.ThreadModel
import com.underground.threadsclone.models.UserModel
import java.util.UUID

class SearchViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val usersRef = db.getReference("users")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("threads/${UUID.randomUUID()}.jpg")


    private var _user = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>> = _user

    init {
        fetchUser {
            _user.value = it
        }
    }



    private fun fetchUser(onResult: (List<UserModel>) -> Unit){

        usersRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                 val result = mutableListOf<UserModel>()
                for(threadSnapshot in snapshot.children){
                    val thread = threadSnapshot.getValue(UserModel::class.java)
                    result.add(thread!!)

                }
                onResult(result)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    fun fetchUserFromThread(threadModel: ThreadModel, onResult:(UserModel) -> Unit){
        db.getReference("users").child(threadModel.userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                user?.let(onResult)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }




}