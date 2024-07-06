package com.example.getitdone.UI

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getitdone.DATA.GetitDoneDatabase
import com.example.getitdone.DATA.Task
import com.example.getitdone.DATA.TaskDAO
import com.example.getitdone.UI.task.TaskFragment
import com.example.getitdone.databinding.ActivityMainBinding
import com.example.getitdone.databinding.DialogAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: GetitDoneDatabase
    private val taskDao: TaskDAO by lazy { database.getTaskDao() }
    private val taskFragment:TaskFragment=TaskFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.pager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.pager) { tab, _ ->
            tab.text = "Tasks"
        }.attach()

        binding.fab.setOnClickListener { showAddTaskDialog() }


        database = GetitDoneDatabase.getdatabase(this)


        thread {
            taskDao.createTask(Task(title = "Some TAsk"))
            taskDao.getAllTasks()

        }


    }

    private fun showAddTaskDialog() {
            val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)

            val dialog = BottomSheetDialog(this)
            dialog.setContentView(dialogBinding.root)

        dialogBinding.buttonShowDetails.setOnClickListener {
           dialogBinding.editTextTaskDetails.visibility =if (dialogBinding.editTextTaskDetails.visibility==View.VISIBLE)View.GONE else View.VISIBLE
        }
        dialogBinding.SaveButton.setOnClickListener {
            val task =Task(
                title = dialogBinding.editTextTaskTitle.text.toString(),
                description = dialogBinding.editTextTaskDetails.text.toString()

            )
            thread {
                taskDao.createTask(task)
            }
            dialog.dismiss()
            taskFragment.fetchAllTasks()

        }
            dialog.show()


    }


    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {

            return taskFragment
        }


    }
}