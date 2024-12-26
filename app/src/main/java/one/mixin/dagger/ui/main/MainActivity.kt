package one.mixin.dagger.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import one.mixin.dagger.databinding.ActivityMainBinding
import one.mixin.dagger.db.entity.Message
import one.mixin.dagger.ui.components.MessageAdapter
import one.mixin.dagger.ui.login.LoginActivity
import one.mixin.dagger.utils.UserComponentManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var messageAdapter: MessageAdapter

    @Inject
    lateinit var userComponentManager: UserComponentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            messageAdapter = MessageAdapter(mutableListOf<Message>())
            messageRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
            messageRecycler.adapter = messageAdapter

            lifecycleScope.launch {
                viewModel.messages.collectLatest {
                    messageAdapter.updateMessages(it)
                }
            }


            btnSend.setOnClickListener {
                val message = messageInput.text.toString()
                if (message.isNotBlank()) {
                    viewModel.insertMessage(message)
                    messageInput.setText("")
                }
            }

            if (!viewModel.isLogin()) {
                Log.d("MainActivity", "请跳转到登陆界面")
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }

            userName.text = "当前用户 ${userComponentManager.getUser()?.name}"

            btnLogout.setOnClickListener {
                userComponentManager.onLogout()
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isLogin()) {
            viewModel.loadMessages()
        }
    }
}