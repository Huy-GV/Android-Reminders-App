  package com.reminders

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.room.Room
import com.reminders.data.dao.*
import com.reminders.data.database.AppDatabase
import com.reminders.data.model.*
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AppViewModel(
    private val topicDao: TopicDao,
    private val reminderDao: ReminderDao

) : ViewModel() {

    var dateString: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")

    fun getTopics() : LiveData<List<Topic>> {
        return topicDao.getAll().asLiveData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createTopic(name: String, creationDate: LocalDate) {
        viewModelScope.launch {
            topicDao.create(Topic(name = name, creationDate = creationDate))
        }
    }

    fun getReminders(topicId: Int) : LiveData<List<Reminder>> {
        return reminderDao.getAll(topicId).asLiveData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createReminder(content: String, rawDeadline: String, priority: Int, topicId: Int) {
        var deadline: LocalDate? = null
        if (rawDeadline.isNotEmpty())
            deadline = LocalDate.parse(rawDeadline, dateFormatter)
        viewModelScope.launch {
            reminderDao.create(Reminder(
                content = content,
                deadline = deadline,
                priority = priority,
                topicId = topicId
            ))
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderDao.delete(reminder)
        }
    }

    fun deleteTopic(topicId: Int) {
        viewModelScope.launch {
            topicDao.delete(topicId)
        }
    }

    fun updateTopic(topicId: Int, name: String) {
        viewModelScope.launch {
            topicDao.update(topicId, name)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderDao.update(reminder)
        }
    }

    class Factory(
        private val reminderDao: ReminderDao,
        private val topicDao: TopicDao
        ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(topicDao, reminderDao) as T
        }
    }

}