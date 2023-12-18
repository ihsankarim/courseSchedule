package com.dicoding.courseschedule.paging

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber
import android.widget.*

class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    //TODO 7 : Complete ViewHolder to show item
    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course
        val tvTime = itemView.findViewById<TextView>(R.id.tv_time)
        val tvCourse = itemView.findViewById<TextView>(R.id.tv_course)
        val tvLecturer = itemView.findViewById<TextView>(R.id.tv_lecturer)

        course.apply {
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)
            tvTime.text = timeFormat
            tvCourse.text = courseName
            tvLecturer.text = lecturer
        }

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun getCourse(): Course = course
}