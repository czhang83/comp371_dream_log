package com.example.dreamlog

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class DetailActivity : AppCompatActivity() {

    private lateinit var textView_detail_title : TextView
    private lateinit var textView_detail_content : TextView
    private lateinit var textView_detail_reflection : TextView
    private lateinit var textView_detail_emotion : TextView
    private lateinit var button_update: Button
    private lateinit var button_delete: Button

    private lateinit var dreamInfo : Array<String>

    private val dreamViewModel : DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        textView_detail_title = findViewById(R.id.textView_detail_title)
        textView_detail_content = findViewById(R.id.textView_detail_content)
        textView_detail_reflection = findViewById(R.id.textView_detail_reflection)
        textView_detail_emotion = findViewById(R.id.textView_detail_emotion)
        button_update = findViewById(R.id.button_update)
        button_delete = findViewById(R.id.button_delete)

        val id : Int = intent.getIntExtra("id", -1)

        dreamViewModel.getDream(id).observe(this, Observer {
            // dream object could be null after it is deleted
            if (it != null){
                textView_detail_title.text = "Title: " + it.title
                textView_detail_content.text = "Content: " + it.content
                textView_detail_reflection.text = "Reflection: " + it.reflection
                textView_detail_emotion.text = "Emotion: " + it.emotion

                // viewModel gives a Flow Live Data so it does not need another thread
                // keep track of the dream content when observing
                // pass the dream content in the moment when update is clicked
                // so that the editText in Update page would not change while user is editing
                dreamInfo = arrayOf(it.id.toString(), it.title, it.content, it.reflection, it.emotion)
                // could not save it - Dream object, null
            }
        })


        button_delete.setOnClickListener {
            val popup = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.item_popup_delete, null)
            popup.contentView = view
            val button_yes : Button = view.findViewById(R.id.button_yes)
            val button_no : Button = view.findViewById(R.id.button_no)
            button_yes.setOnClickListener {
                dreamViewModel.delete(id)
                finish()
            }
            button_no.setOnClickListener {
                popup.dismiss()
            }
            popup.showAtLocation(it, Gravity.CENTER, 0, 0)
        }

        button_update.setOnClickListener {
            val intent = Intent(this@DetailActivity, UpdateActivity::class.java)
            intent.putExtra("dream", dreamInfo)
            startActivity(intent)
        }

    }
}