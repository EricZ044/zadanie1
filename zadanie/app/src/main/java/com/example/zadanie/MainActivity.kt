package com.example.zadanie

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.zadanie.Dog

class MainActivity : AppCompatActivity() {

    private lateinit var editTextDogName: EditText
    private lateinit var buttonAddDog: Button
    private lateinit var buttonRemoveDog: Button
    private lateinit var listViewDogs: ListView

    private val dogList = mutableListOf<Dog>()
    private lateinit var adapter: ArrayAdapter<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextDogName = findViewById(R.id.editTextDogName)
        buttonAddDog = findViewById(R.id.buttonAddDog)
        buttonRemoveDog = findViewById(R.id.buttonRemoveDog)
        listViewDogs = findViewById(R.id.listViewDogs)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dogList.map { it.name })
        listViewDogs.adapter = adapter

        buttonAddDog.setOnClickListener {
            val name = editTextDogName.text.toString()
            if (name.isNotBlank() && dogList.none { it.name == name }) {
                dogList.add(Dog(name))
                updateList()
            } else {
                Toast.makeText(this, "Pies o takim imieniu już istnieje lub nazwa jest pusta", Toast.LENGTH_SHORT).show()
            }
            editTextDogName.text.clear()
        }

        buttonRemoveDog.setOnClickListener {
            val name = editTextDogName.text.toString()
            val dog = dogList.find { it.name == name }
            if (dog != null) {
                dogList.remove(dog)
                updateList()
            } else {
                Toast.makeText(this, "Nie znaleziono psa o takim imieniu", Toast.LENGTH_SHORT).show()
            }
            editTextDogName.text.clear()
        }

        listViewDogs.setOnItemClickListener { _, _, position, _ ->
            val dog = dogList[position]
            dog.isFavorite = !dog.isFavorite
            updateList()
        }
    }

    private fun updateList() {
        adapter.clear()
        adapter.addAll(dogList.map { if (it.isFavorite) "${it.name} ★" else it.name })
        adapter.notifyDataSetChanged()
    }
}
