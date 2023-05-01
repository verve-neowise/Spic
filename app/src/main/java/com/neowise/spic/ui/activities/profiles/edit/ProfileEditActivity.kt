package com.neowise.spic.ui.activities.profiles.edit

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.neowise.spic.model.Gender
import com.neowise.spic.databinding.ActivityProfileEditBinding
import com.neowise.spic.model.Profile
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.util.RunIO
import com.squareup.picasso.Picasso
import com.neowise.spic.Const
import com.neowise.spic.util.*

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding

    private lateinit var firebase: FirebaseStorage
    private lateinit var storageReference: StorageReference

    private lateinit var profile: Profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profilePhoto.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .compress(512)
                .start()
        }

        binding.saveChangesButton.setOnClickListener {
            postChanges(build())
        }

        firebase = FirebaseStorage.getInstance()
        storageReference = firebase.reference
        binding.birthDate.readOnly()
        binding.birthDate.setOnClickListener {
            DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    binding.birthDate.setText(SimpleDate(year, month+1, dayOfMonth).toString())
                },
                profile.birthDay.year, profile.birthDay.month, profile.birthDay.day).show()
        }

        loadData()
    }

    private fun loadData() {
        val token = Preferences.instance(this).token

        RunIO<Profile>()
            .run {
                Services.profileService.getProfile(token)
            }
            .onSuccess(::fillEntries)
            .onFailure { errorToast(applicationContext, it) }
            .execute()
    }

    private fun postChanges(profile: Profile) {

        val token = Preferences.instance(this).token

        if (profile == Profile.Empty) return

        RunIO<Unit>()
            .run {
                println("url: ${profile.birthDay}")
                Services.profileService.updateProfile(token, profile)
            }
            .onSuccess {
                Toast.makeText(this, "Changes saved!", Toast.LENGTH_SHORT).show()
                setResult(Const.UPDATE_DATA)
                finish()
            }
            .onFailure { errorToast(this, it) }
            .execute()
    }

    private fun uploadProfilePhoto(fileUri: Uri) {
        val ref = storageReference.child("avatars/${profile.id}")

        ref.putFile(fileUri).addOnSuccessListener {
            Toast.makeText(this, "profile photo update", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillEntries(profile: Profile) {

        binding.nameEdit.setText(profile.name)
        binding.birthDate.setText(profile.birthDay.toString())
        binding.genderSpinner.setSelection(if (profile.gender == Gender.MALE) 0 else 1)
        binding.phoneEdit.setText(profile.phoneNumber)
        binding.emailEdit.setText(profile.emailAddress)

        this.profile = profile

        loadImage(this, profile.id, binding.profilePhoto)
    }

    private fun build() : Profile {

        return buildModel(this, Profile.Empty) {

            val name = binding.nameEdit.validateValue("name")
            val birthDate = binding.birthDate.validateValue("birth Day")
            val gender = if (binding.genderSpinner.selectedItemPosition == 0) Gender.MALE else Gender.FEMALE
            val phone = binding.phoneEdit.validateValue("phone number")
            val email = binding.emailEdit.validateValue("email")

            Profile(-1, name, SimpleDate.parse(birthDate), gender, phone, email, "", "", 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val fileUri = data!!.data

            Picasso.get().load(fileUri).into(binding.profilePhoto)

            uploadProfilePhoto(fileUri!!)
        }
    }
}