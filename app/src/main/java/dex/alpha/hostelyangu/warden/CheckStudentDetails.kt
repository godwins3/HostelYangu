package dex.alpha.hostelyangu.warden

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dex.alpha.hostelyangu.R
import dex.alpha.hostelyangu.db.BookingDetails
import dex.alpha.hostelyangu.db.BookingDetailsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [BookingDetails::class], version = 1)
abstract class BookingDatabase : RoomDatabase() {
    abstract fun bookingDetailsDao(): BookingDetailsDao
}

class CheckStudentDetails : AppCompatActivity() {

    private lateinit var res: CardView

    private lateinit var deleteBtn: Button
    private lateinit var nameTextView: TextView
    private lateinit var dropdownIndicator: ImageView
    private lateinit var fullDetailsLayout: LinearLayout
    private var isFullDetailsVisible = false
    private var collapsedCardHeight = 0


    // Define the database variable
    private lateinit var db: BookingDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_student_details)

        // Initialize views
        nameTextView = findViewById(R.id.nameTextView)
        dropdownIndicator = findViewById(R.id.dropdownIndicator)
        fullDetailsLayout = findViewById(R.id.fullDetailsLayout)
        res = findViewById(R.id.cardView)
        deleteBtn = findViewById(R.id.delete_button)

        // Initialize database
        db = Room.databaseBuilder(
            applicationContext,
            BookingDatabase::class.java,
            "booking-database"
        ).build()

        // Calculate the collapsed height of the card
        res.post {
            collapsedCardHeight = res.height
        }


        // Set initial name
        appendDetails()

        // Dropdown Indicator click listener
        dropdownIndicator.setOnClickListener {
            isFullDetailsVisible = !isFullDetailsVisible
            if (isFullDetailsVisible) {
                fullDetailsLayout.visibility = View.VISIBLE
                nameTextView.visibility = View.VISIBLE // Show the name when expanding
                dropdownIndicator.setImageResource(R.drawable.ic_arrow_up) // Change to your drawable resource for up arrow
                appendFullDetails()
            } else {
                fullDetailsLayout.visibility = View.GONE
                nameTextView.visibility = View.VISIBLE // Show the name when collapsing
                clearFullDetails()
                appendDetails()
                dropdownIndicator.setImageResource(R.drawable.ic_dropdown) // Change to your drawable resource for dropdown arrow
            }
        }






        // Other code...
    }

    // Append student name to the TextView
    private fun appendDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            val studentName = db.bookingDetailsDao().getStudentName() // Assuming you have a method to fetch the student name from the database
            launch(Dispatchers.Main) {
                val name = "Name: $studentName\n\n"
                nameTextView.text = name
            }
        }
    }

    // Append full student details to the LinearLayout
    // Append full student details to the LinearLayout
    private fun appendFullDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            val studentDetails = db.bookingDetailsDao().getAllBookingDetails() // Assuming you have a method to fetch all student details from the database
            launch(Dispatchers.Main) {
                // Clear previous details
                clearFullDetails()

                // Append new details
                var details = ""
                for (booking in studentDetails) {
                    details += "Name: ${booking.nameOfStudent}\n" +
                            "Reg. No.: ${booking.registrationNumber}\n" +
                            "Email: ${booking.emailOfStudent}\n" +
                            "Gender: ${booking.gender}\n" +
                            "Hostel: ${booking.hostel}\n" +
                            "Room Type: ${booking.roomType}\n" +
                            "Seater: ${booking.seater}\n" +
                            "Room: ${booking.room}\n" +
                            "Bed: ${booking.bed}\n\n"
                }

                // Assuming studentDetailsTextView is the id of your TextView in the layout
                findViewById<TextView>(R.id.nameTextView).text = details
            }
        }
    }

    // Clear full student details from the LinearLayout
    private fun clearFullDetails() {
        fullDetailsLayout.removeAllViews()
    }

    // Other methods...
}
