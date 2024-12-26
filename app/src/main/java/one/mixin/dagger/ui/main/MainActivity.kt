package one.mixin.dagger.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import one.mixin.dagger.databinding.ActivityMainBinding
import one.mixin.dagger.ui.components.MessageAdapter
import one.mixin.dagger.ui.login.LoginActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        messageAdapter = MessageAdapter(mutableListOf())
        binding.messageList.layoutManager = LinearLayoutManager(this)
        binding.messageList.adapter = messageAdapter
    }

    private fun initData() {
        lifecycleScope.launch {
            val isLoggedIn = viewModel.isLoggedIn()
            if (!isLoggedIn) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
                return@launch
            }
            val messages = withContext(Dispatchers.IO) {
                viewModel.loadMessages()
            }
            messageAdapter.updateData(messages)
            viewModel.addMessages()
        }
    }

    private fun initListener() {
        binding.logoutBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                withContext(Dispatchers.Main) {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }

        binding.fetchBtn.setOnClickListener {
            lifecycleScope.launch {
                val result = viewModel.addMessages()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}