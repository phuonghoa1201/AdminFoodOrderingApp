package com.example.adminfoodorderingapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodorderingapp.databinding.ActivityAddItemBinding
import com.example.adminfoodorderingapp.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {
//    Food item detail
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredients: String
    private var foodImageUri: Uri? = null
//    Firebase
    private lateinit var auth :FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private val binding: ActivityAddItemBinding by lazy{
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        initialize firebase authentication istance
        auth = FirebaseAuth.getInstance()
//        intialize firebase database Instance
        database = FirebaseDatabase.getInstance()

        binding.addItemBtn.setOnClickListener {
//            get data from fields
            foodName = binding.foodName.text.toString().trim() // trim()=loai bo khoang trang
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredients = binding.ingredient.text.toString().trim()

            if(!(foodName.isBlank() || foodPrice.isBlank()|| foodDescription.isBlank()||foodIngredients.isBlank()))
            {
                uploadData()
                Toast.makeText(this,"Item Add Successfully",Toast.LENGTH_SHORT).show()
                finish()
            } else  {
                Toast.makeText(this,"Fill all the detail",Toast.LENGTH_SHORT).show()
            }
        }
        binding.selectImg.setOnClickListener {
            pickImage.launch("image/*")
        }


        binding.backBtn.setOnClickListener {
            finish()
        }

//        binding.selectImg.setOnClickListener {
//            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) // chon hinh anh
//        }
    }

    private fun uploadData() {
//        get a reference to the "menu" node in the database
        val menuRef = database.getReference("menu")
//        generate a unique key for the new menu item
        val newItemKey = menuRef.push().key

        if(foodImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!) // khi upload hinh len thi !! ko bao gio null

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl ->
//                    create a new menu item
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice=foodPrice,
                        foodDescription=foodDescription,
                        foodIngredient = foodIngredients,
                        foodImage = downloadUrl.toString()
                    )
//                    let thuc hien cac thao tac khi newItemKey not null
                    newItemKey?.let{
                        key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"data uploaded successfully",Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener {
                            Toast.makeText(this,"data uploaded fail",Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }.addOnFailureListener {
                Toast.makeText(this,"Image uploaded fail",Toast.LENGTH_SHORT).show()
            }

        }else {
                Toast.makeText(this,"Please select an image",Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if(uri != null) {
            binding.selectedImg.setImageURI(uri) // dat hinh anh vao ImageView
            foodImageUri = uri // gán uri của hinh anh vào biến foodImageUri
        }
    }
}