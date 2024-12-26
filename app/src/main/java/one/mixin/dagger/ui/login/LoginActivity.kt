package one.mixin.dagger.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import one.mixin.dagger.databinding.ActivityLoginBinding
import one.mixin.dagger.ui.main.MainActivity

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        binding.loginBtn.setOnClickListener {
            val identityNumberStr = binding.identityNumber.text.toString()
            if(identityNumberStr.isEmpty()){
                Toast.makeText(this, "id is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val identityNumber = identityNumberStr.toLongOrNull()
            if (identityNumber == null){
                Toast.makeText(this, "id is invalid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
               val result = viewModel.login(identityNumber)
                withContext(Dispatchers.Main) {
                    if (result) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}