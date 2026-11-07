package com.example.mylesson2.database

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylesson2.R
import com.example.mylesson2.databinding.AddContactLayoutBinding
import com.example.mylesson2.databinding.MysqlMainActivityBinding

class MySQLMainActivity : AppCompatActivity() {

    val contactList = mutableListOf<ContactModel>()
    lateinit var contactAdapter: ContactAdapter
    lateinit var dbManager: MyDbManager
    var changedPosition: Int? = null

    private lateinit var binding: MysqlMainActivityBinding

    private val requestCallPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            if (isGranted) {
                //make call
                makeCall("+99890213364")
            } else {
                //show worning message
            }
        }


    private val requestSmsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            if (isGranted) {
                //make call
                makeCall("+99890213364")
            } else {
                //show worning message
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MysqlMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbManager = MyDbManager(this)
        dbManager.onCreate()
//        dbManager.insert(
//            ContactModel(
//                name = "Ali",
//                phoneNumber = "+998902158530",
//                address = "Namangan",
//                age = 19
//            )
//        )
        contactAdapter = ContactAdapter()

        dbManager.delete(1)

        fetchContactData()
        contactAdapter.submitList(contactList)
        val myLayouManager = LinearLayoutManager(this)
        binding.contactView.apply {
            adapter = contactAdapter
            layoutManager = myLayouManager
            addItemDecoration(
                DividerItemDecoration(
                    this@MySQLMainActivity,
                    myLayouManager.orientation
                )
            )
        }

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                changedPosition = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.RIGHT) {
                    //call
                    checkCallPermission(contactList[viewHolder.adapterPosition].phoneNumber)
                } else {
                    //sms
                    checkSmsPermission(contactList[viewHolder.adapterPosition].phoneNumber)
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.contactView)

        binding.addContactFab.setOnClickListener {
            showAddContactDialog()

        }
    }

    private fun checkSmsPermission(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            makeSms(phoneNumber)
        } else {
            requestSmsPermission.launch(android.Manifest.permission.SEND_SMS)
        }
    }

    private fun makeSms(phoneNumber: String) {

        val smsManager = getSystemService(SmsManager::class.java)
        smsManager
            .sendTextMessage(phoneNumber,
                null, "Assalomu alaykum", null, null)
        changedPosition?.apply {
            contactAdapter.notifyItemChanged(this
            )
        }
    }


    private fun checkCallPermission(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            makeCall(phoneNumber)

        } else {
            requestCallPermission.launch(android.Manifest.permission.CALL_PHONE)
        }
    }

    private fun makeCall(phoneNumber: String) {
        Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber")).apply {
            startActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()
        changedPosition?.apply {
            contactAdapter.notifyItemChanged(this)
        }

    }

    private fun showAddContactDialog() {
        val dialogBinding = AddContactLayoutBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Add contact")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                val name = dialogBinding.nameEt.text.toString()
                val age = dialogBinding.ageEt.text.toString().toInt()
                val phone = dialogBinding.phoneEt.text.toString()
                val address = dialogBinding.addressEt.text.toString()
                dbManager.insert(ContactModel(0, name, phone, address, age))
                contactAdapter.submitList(fetchContactData())
                contactAdapter.notifyItemInserted(contactList.size)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()

        dialog.show()
    }

    private fun fetchContactData(): List<ContactModel> {
        val cursor = dbManager.fetch()
        contactList.clear()
        if (cursor != null) {
            val idIndex = cursor.getColumnIndex(MySQLiteHelper.id)
            val nameIndex = cursor.getColumnIndex(MySQLiteHelper.name)
            val addressIndex = cursor.getColumnIndex(MySQLiteHelper.address)
            val ageIndex = cursor.getColumnIndex(MySQLiteHelper.age)
            val phoneIndex = cursor.getColumnIndex(MySQLiteHelper.phone)
            do {
                val id = cursor.getInt(idIndex)
                val name = cursor.getString(nameIndex)
                val age = cursor.getInt(ageIndex)
                val address = cursor.getString(addressIndex)
                val phone = cursor.getString(phoneIndex)
                val contactModel = ContactModel(id, name, phone, address, age)
                contactList.add(contactModel)
            } while (cursor.moveToNext())

        }
        return contactList
    }
}