package com.ido.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditActivity : AppCompatActivity() {

        private lateinit var editNote: EditText
        private lateinit var buttonUpdate: MaterialButton
        private val api by lazy { ApiRetrofit().endpoint }
        private val note by lazy { intent.getSerializableExtra("note") as NoteModel.Data }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar!!.title = "Edit"
        setupView()
        setupListener()

    }

    private fun setupView() {
        editNote = findViewById(R.id.edit_note)
        buttonUpdate = findViewById(R.id.button_update)
        editNote.setText( note.note )
    }

    private fun setupListener() {
        buttonUpdate.setOnClickListener {
            api.update( note.id!!, editNote.text.toString() )
                .enqueue(object : Callback<SubmitModel> {
                    override fun onResponse(
                        call: Call<SubmitModel>,
                        response: Response<SubmitModel>
                    ) {
                        if (response.isSuccessful){
                            val submit = response.body()
                            Toast.makeText(applicationContext,
                                submit!!.message
                                , Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<SubmitModel>, t: Throwable) {

                    }

                })
        }
    }
}