package one.mixin.dagger.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import one.mixin.dagger.databinding.ActivityLoginBinding
import one.mixin.dagger.db.entity.User
import one.mixin.dagger.ui.main.MainActivity
import one.mixin.dagger.utils.UserComponentManager
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var userComponentManager: UserComponentManager
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnLogin.setOnClickListener {
                val userName = etUserName.text.toString()
                // 模拟登录逻辑
                if (userName.isNotBlank() ) {
                    val user = User(
                        id = UUID.nameUUIDFromBytes(userName.toByteArray()).toString(),
                        name = userName
                    )
                    userComponentManager.onLogin(user)
                    Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                    Intent(this@LoginActivity, MainActivity::class.java).also {
                        startActivity(it)
                    }
                    finish() // 关闭登录页面
                } else {
                    Toast.makeText(this@LoginActivity, "用户名或密码不能为空", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}