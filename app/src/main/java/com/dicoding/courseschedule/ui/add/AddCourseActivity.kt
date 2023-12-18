package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val factory = AddCourseViewModelFactory.createFactory(this@AddCourseActivity)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        viewModel.saved.observe(this) { isSaved ->
            if (isSaved.getContentIfNotHandled() == true) {
                onBackPressed()
            } else {
                val message = getString(R.string.input_empty_message)
                Toast.makeText(this@AddCourseActivity, message, Toast.LENGTH_SHORT).show()
                Log.e("ADD DATA FAILED", "$isSaved")
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = findViewById<EditText>(R.id.ed_course_name).text.toString().trim()
                val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
                val startTime = findViewById<TextView>(R.id.tv_start_time).text.toString().trim()
                val endTime = findViewById<TextView>(R.id.tv_end_time).text.toString().trim()
                val lecturer = findViewById<EditText>(R.id.ed_lecturer).text.toString().trim()
                val note = findViewById<EditText>(R.id.ed_note).text.toString().trim()

                viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }


    fun showTimePickerFortStartTime(view: View) {
        TimePickerFragment().show(
            supportFragmentManager, "startDialog"
        )
    }

    fun showTimePickerForEndTime(view: View) {
        TimePickerFragment().show(supportFragmentManager, "endDialog")
    }


    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calender = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            "startDialog" -> {
                findViewById<TextView>(R.id.tv_start_time).text =
                    timeFormat.format(calender.time)
            }

            "endDialog" -> {
                findViewById<TextView>(R.id.tv_end_time).text =
                    timeFormat.format(calender.time)

            }
        }
    }
}