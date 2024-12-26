package one.mixin.dagger.utils

object Session {

    private var account: UserInfo? = null


    fun login(identityNumber: Long){
        account = UserInfo(identityNumber)
    }

    fun getAccount(): UserInfo? {
        return account
    }

    fun logout(){
        account = null
    }

    data class UserInfo(val identityNumber :Long)
}