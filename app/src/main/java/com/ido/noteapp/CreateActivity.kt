package com.ido.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : AppCompatActivity() {

    private lateinit var editNote: EditText
    private lateinit var buttonCreate: MaterialButton
    private val api by lazy { ApiRetrofit().endpoint }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        supportActionBar!!.title = "Catatan Baru"
        setupView()
        setupListener()
    }

    private fun setupView(){
        editNote = findViewById(R.id.edit_note)
        buttonCreate = findViewById(R.id.button_create)
    }

    private fun setupListener(){
        buttonCreate.setOnClickListener {
            if (editNote.text.toString().isNotEmpty()) {
                Log.e("CreateActivity", editNote.text.toString())
                api.create( editNote.text.toString() )
                    .enqueue( object : Callback<SubmitModel>{
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
            } else {
                Toast.makeText(applicationContext, "Note Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT).show()
            }

        }
        }
    }
