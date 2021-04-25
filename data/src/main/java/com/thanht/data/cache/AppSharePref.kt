package com.thanht.data.cache

interface AppSharePref {
    var token: String
}
private const val TOKEN = "SP_TOKEN"

class AppSharePrefImpl(
    private val pref: CoreSharedPref
) : AppSharePref {

    override var token: String
        get() = pref.getString(TOKEN) ?: "OAUTH-TOKEN"
        set(value) = pref.saveString(TOKEN, value)
}
