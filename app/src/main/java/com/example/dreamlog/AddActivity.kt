package com.example.dreamlog

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var editTextReflection: EditText
    private lateinit var spinnerEmotion: Spinner
    private lateinit var buttonSave : Button
    var selectedEmotion : String? = null

    private val dreamViewModel : DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    } // call the one and only repository we created in the Task Application class
    // so not creating multiple instances of the repository in our app

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editTextTitle = findViewById(R.id.edit_title)
        editTextContent = findViewById(R.id.edit_content)
        editTextReflection = findViewById(R.id.edit_reflection)
        spinnerEmotion = findViewById(R.id.spinner)
        buttonSave = findViewById(R.id.button_save)

        // Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.emotions,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerEmotion.adapter = adapter
        }
        spinnerEmotion.onItemSelectedListener = this

        // catch user input error in front end
        buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(editTextTitle.text) || TextUtils.isEmpty(editTextContent.text) ||
                TextUtils.isEmpty(editTextReflection.text) || selectedEmotion == null){
                toastError("Missing fields")
            }
            else {
                val task = Dream(editTextTitle.text.toString(), editTextContent.text.toString(),
                editTextReflection.text.toString(), selectedEmotion!!
                )
                dreamViewModel.insert(task)
                finish() // go back automatically
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        selectedEmotion = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        selectedEmotion = null
    }

    private fun toastError(text:String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}