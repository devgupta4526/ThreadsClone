package com.underground.threadsclone.Utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPref {

    fun storeData(name:String,email:String,username:String,password:String,imageUrl:String, context:Context){
        val sharedPrefence = context.getSharedPreferences("users",MODE_PRIVATE)
        val editor = sharedPrefence.edit()
        editor.putString("name",name)
        editor.putString("email",email)
        editor.putString("username",username)
        editor.putString("password",password)
        editor.putString("imageUrl",imageUrl)
        editor.apply()
    }

    fun getUsername(context:Context): String{
        val sharedPrefence = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPrefence.getString("username", "name")!!
    }

    fun getEmail(context:Context): String{
        val sharedPrefence = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPrefence.getString("email", "")!!
    }

    fun getName(context:Context): String{
        val sharedPrefence = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPrefence.getString("name", "name")!!
    }

    fun getImage(context:Context): String{
        val sharedPrefence = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPrefence.getString("imageUrl", "name")!!
    }
}