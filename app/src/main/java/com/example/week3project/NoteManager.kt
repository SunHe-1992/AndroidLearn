package com.example.week3project

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream

public class NoteManager {

    private val dicNotes: MutableMap<Int, Note> = mutableMapOf()
    private val fileName = "user_notes.txt"

    constructor()

    fun SaveNote(context: Context, title: String, content: String) {

        var note = Note(title, content, dicNotes.size)
        dicNotes.put(note.index, note)
        SaveToFile(context)
    }

    fun EditNote(context: Context, title: String, content: String, idx: Int) {
        var note = getNote(idx)
        dicNotes.put(
            idx,
            Note(title, content, idx)
        )
        SaveToFile(context)

    }

    fun DeleteNote(context: Context, idx: Int) {
        var note = getNote(idx)
        note.hidden = true
        SaveToFile(context)

    }

    fun SaveToFile(context: Context) {
        val gson = Gson()
        val jsonString = gson.toJson(getNotes())
        Log.d("NoteManager", "SaveToFile" + jsonString);
        val file = File(context.filesDir, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        val data = jsonString
        val fos = FileOutputStream(file)
        fos.write(data.toByteArray())
        fos.close()
    }

    public fun LoadFromFile(context: Context) {

        val file = File(context!!.filesDir, fileName)
        if (!file.exists()) {

        } else {
            var allText = file.readText()
            Log.d("NoteManager", "LoadFromFile:" + allText);
            //all text to json:
            val gson = Gson()
            val notesArray = gson.fromJson(allText, Array<Note>::class.java)
            dicNotes.values.clear()
            for (note in notesArray) {
                dicNotes.put(note.index, note)
                Log.d("NoteManager", note.toString());
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

    public fun getNotes(): MutableList<Note> {

        return dicNotes.values.filter { note -> !note.hidden }
            .toMutableList()
    }

    public fun deleteAll() {
//        dicNotes.values.clear()
        for (note in dicNotes.values) {
            note.hidden = true
        }
    }


    fun getNote(idx: Int): Note {
        return dicNotes.get(idx)!!
    }
}

// 定义实体类
public data class Note(
    var title: String,
    var content: String,
    var index: Int = 0,
    var hidden: Boolean = false
) {
    constructor(content: String) : this("Default Title", content)
}