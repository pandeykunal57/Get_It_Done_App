package com.example.getitdone.UI.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.getitdone.DATA.GetitDoneDatabase
import com.example.getitdone.DATA.Task
import com.example.getitdone.DATA.TaskDAO
import com.example.getitdone.databinding.FragmentTasksBinding
import kotlin.concurrent.thread

class TaskFragment:Fragment(),TaskAdapter.TaskUpdatedListener {
    private lateinit var binding: FragmentTasksBinding
    private val taskDao:TaskDAO by lazy {
        GetitDoneDatabase.getdatabase(requireContext()).getTaskDao()
    }
    private val  adapter= TaskAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentTasksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.RecyclerView.adapter=adapter
        fetchAllTasks()

    }



     fun fetchAllTasks() {
        thread {
            val task = taskDao.getAllTasks()
            requireActivity().runOnUiThread {
//                binding.RecyclerView.adapter = TaskAdapter(tasks = task, listener = this)
                    adapter.setTasks(task)
            }
        }
    }

     override fun onTaskUpdated(task: Task) {
         thread {
             taskDao.updateTask(task)
             fetchAllTasks()
         }
     }
}
