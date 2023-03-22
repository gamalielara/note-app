package com.example.noteapplication.data

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.R
import com.example.noteapplication.constants.NoteColorsValue
import com.example.noteapplication.database.Note
import kotlinx.android.synthetic.main.note_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(
    private val noteList: List<Note>
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            noteTitle.text = noteList[position].title
            val epoch = noteList[position].createdTime
            val date = Date(epoch)
            val simpleDateFormat = SimpleDateFormat("EE, MMM dd YYYY")
            noteCreatedAt.text = simpleDateFormat.format(date)


            (noteBox.background as GradientDrawable).setColor(
                ContextCompat.getColor(
                    context,
                    NoteColorsValue::class.java.getDeclaredField(noteList[position].noteColor).get(null) as Int
                )
            )

            val noteId = noteList[position].id ?: 0
            val bundle = Bundle()
            bundle.putInt("noteId", noteId)
            noteBox.setOnClickListener {
                findNavController().navigate(
                    R.id.action_homeFragment_to_noteEditorFragment,
                    bundle
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}