package com.example.notess

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.*


class MainActivity : AppCompatActivity(), NoteAdapter.INotesAdapter {

    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]
        recycle.layoutManager = GridLayoutManager(this, 2)

        showNotes()
    }

    @SuppressLint("ResourceType")

    fun openDialog(view: View) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog)
        dialog.show()

        val addBtn = dialog.findViewById<Button>(R.id.addButton)
        val editContent = dialog.findViewById<EditText>(R.id.editContent)
        val editTitle = dialog.findViewById<EditText>(R.id.editTitle)

        addBtn.setOnClickListener {

            val noteText = editContent.text.toString()
            val noteTitle = editTitle.text.toString()

            if (noteText!="") {
                viewModel.insertNote(Note(noteTitle, noteText))
                Toast.makeText(this, "Note Inserted ", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                showNotes()
            }
            else{
                Toast.makeText(this, "Please Enter Something",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "Note  Deleted", Toast.LENGTH_SHORT).show()
        showNotes()
    }


    private fun showNotes() {
            val adapter = NoteAdapter(this, this)
            recycle.adapter = adapter

            viewModel.allNotes.observe(this) { list ->
                if (viewModel.allNotes.value.isNullOrEmpty())
                {
                    llNotes.visibility = View.VISIBLE
                    recycle.visibility = View.GONE
                }
                else
                list?.let {
                    llNotes.visibility = View.GONE
                    recycle.visibility = View.VISIBLE
                    adapter.updateList(it)
                }

            }
    }
}
