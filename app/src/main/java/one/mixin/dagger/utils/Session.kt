package one.mixin.dagger.utils

object Session {

    private var account: UserInfo? = null
    private var isLogin = false
    fun login(identityNumber: Long){
        account = UserInfo(identityNumber)
        isLogin = true
    }
    fun getAccount(): UserInfo? {
        return account
    }
    fun isLoggedIn():Boolean{
        return isLogin
    }
    fun logout(){
        account = null
        isLogin = false
    }
    data class UserInfo(val identityNumber :Long)
}
