package com.example.dreamlog

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var editTextReflection: EditText
    private lateinit var spinnerEmotion: Spinner
    private lateinit var buttonSave : Button
    var selectedEmotion : String? = null

    private val dreamViewModel : DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editTextTitle = findViewById(R.id.edit_title)
        editTextContent = findViewById(R.id.edit_content)
        editTextReflection = findViewById(R.id.edit_reflection)
        spinnerEmotion = findViewById(R.id.spinner)
        buttonSave = findViewById(R.id.button_save)

        val dream = intent.getStringArrayExtra("dream")
        val id : Int? = dream?.get(0)?.toInt()
        Log.e("content", id.toString())
        editTextTitle.setText(dream?.get(1))
        editTextContent.setText(dream?.get(2))
        editTextReflection.setText(dream?.get(3))

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
            spinnerEmotion.setSelection(adapter.getPosition(dream?.get(4)))
        }
        spinnerEmotion.onItemSelectedListener = this



        // catch user input error in front end
        buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(editTextTitle.text) || TextUtils.isEmpty(editTextContent.text) ||
                TextUtils.isEmpty(editTextReflection.text) || selectedEmotion == null){
                toastError("Missing fields")
            }
            else {
                if (id != null) {
                    dreamViewModel.update(id, editTextTitle.text.toString(), editTextContent.text.toString(),
                        editTextReflection.text.toString(), selectedEmotion!!
                    )
                    finish() // go back automatically
                }else {
                    toastError("Dream does not exist")
                }
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