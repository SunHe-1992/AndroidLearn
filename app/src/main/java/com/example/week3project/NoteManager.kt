package com.example.week3project

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream

public class NoteManager {

    private val notes: MutableList<Note> = mutableListOf()
    public val name = "hello this is note manager"
    private val fileName = "user_notes.txt"
    var uiContext: Context? = null

    constructor()

    fun SaveNote(context: Context, title: String, content: String) {
        uiContext = context
        var note = Note(title, content)
        notes.add(note)
//        notes.add(note)
//        notes.add(note)

        SaveToFile()
    }

    fun SaveToFile() {
        val gson = Gson()
        val jsonString = gson.toJson(notes)
//        Log.d("NoteManager", jsonString);
        val file = File(uiContext!!.filesDir, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        val data = jsonString
        val fos = FileOutputStream(file)
        fos.write(data.toByteArray())
        fos.close()


        //test
        LoadFromFile()
    }

    fun LoadFromFile() {
        val file = File(uiContext!!.filesDir, fileName)
        if (!file.exists()) {

        } else {
            var allText = file.readText()
//            Log.d("NoteManager", "read text: " + allText);
            //all text to json:
            val gson = Gson()
            val notesArray = gson.fromJson(allText, Array<Note>::class.java)
            notes.clear()
            notes.addAll(notesArray)
            for (note in notes) {
                Log.d("NoteManager", "title: " + note.title + ", content: " + note.content);

            }
        }
    }

    companion object {
        private var instance: NoteManager? = null

        fun getInstance(): NoteManager {
            if (instance == null) {
                instance = NoteManager()
            }
            return instance!!
        }
    }

}

// 定义实体类

data class Note(val title: String, val content: String) {
    constructor(content: String) : this("Default Title", content)
}