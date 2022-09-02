package com.shaluambasta.googlelogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {


    private lateinit var textWelcomeUser: MaterialTextView
    private lateinit var imgUserProfile: ShapeableImageView
    private lateinit var btnLogout: MaterialButton

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textWelcomeUser = findViewById(R.id.text_welcome_user)
        imgUserProfile = findViewById(R.id.img_user_profile)
        btnLogout = findViewById(R.id.btn_logout)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser


        ("Welcome\n" + currentUser?.displayName + " !").also { textWelcomeUser.text = it }
        Picasso.get()
            .load(currentUser?.photoUrl)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .error(R.drawable.ic_baseline_account_circle_24)
            .into(imgUserProfile)


        btnLogout.setOnClickListener {

            val dialog = MaterialAlertDialogBuilder(this)
            dialog.setTitle("LogOut")
            dialog.setMessage("Do you really want to Logout?")
            dialog.setIcon(R.drawable.ic_baseline_login_24)

            dialog.setPositiveButton("Yeah") { _, _ ->

                auth.signOut()
                val intent = Intent(this, GoogleLoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show()
                finish()
            }

            dialog.setNeutralButton("Cancel") { _, _ ->
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()

            }

            dialog.create()
            dialog.setCancelable(false)
            dialog.show()

        }

    }
}