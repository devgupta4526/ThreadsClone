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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import com.underground.threadsclone.Utils.SharedPref
import com.underground.threadsclone.models.ThreadModel
import com.underground.threadsclone.models.UserModel
import java.util.UUID

//class HomeViewModel : ViewModel() {
//
//    private val db = FirebaseDatabase.getInstance()
//    val thread = db.getReference("threads")
//
//    private val storageRef = Firebase.storage.reference
//    private val imageRef = storageRef.child("threads/${UUID.randomUUID()}.jpg")
//
//
//    private var _threadsAndUser = MutableLiveData<List<Pair<ThreadModel,UserModel>>>()
//    val threadsAndUsers: LiveData<List<Pair<ThreadModel,UserModel>>> = _threadsAndUser
//
//    init {
//        fetchThreadsAndUser {
//            _threadsAndUser.value = it
//        }
//    }
//
//
//
//    private fun fetchThreadsAndUser(onResult: (List<Pair<ThreadModel,UserModel>>) -> Unit){
//
//        thread.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                 val result = mutableListOf<Pair<ThreadModel,UserModel>>()
//                for(threadSnapshot in snapshot.children){
//                    val thread = threadSnapshot.getValue(ThreadModel::class.java)
//                    thread.let {
//                        fetchUserFromThread(it!!){
//                            user ->
//                            result.add(0, it to user)
//
//                            if(result.size == snapshot.childrenCount.toInt()){
//                                onResult(result)
//                            }
//                        }
//                    }
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })
//
//    }
//
//    fun fetchUserFromThread(threadModel: ThreadModel, onResult:(UserModel) -> Unit){
//        db.getReference("users").child(threadModel.userId).addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val user = snapshot.getValue(UserModel::class.java)
//                user?.let(onResult)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })
//    }
//
//
//
//
//}


class HomeViewModel:ViewModel() {


    private val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    val thread: DatabaseReference = db.getReference("threads")





    private var _threadsAndUsers = MutableLiveData<List<Pair<ThreadModel,UserModel>>>()
    val threadsAndUsers: LiveData<List<Pair<ThreadModel,UserModel>>> = _threadsAndUsers



    init {
        fetchThreadsAndUser {
            _threadsAndUsers .value= it
        }
    }





    private fun fetchThreadsAndUser(onResult: (List<Pair<ThreadModel,UserModel>>)->Unit){
        thread.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                val result = mutableListOf<Pair<ThreadModel,UserModel>>()
                for (threadSnapshot in snapshot.children){
                    val thread = threadSnapshot.getValue(ThreadModel::class.java)
                    thread.let {
                        fetchUSerFromThread(it!!){
                                user ->

                            result.add(0, it to user)

                            if (result.size == snapshot.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    fun fetchUSerFromThread(thread: ThreadModel, onResult:(UserModel)->Unit){
        db.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }


}