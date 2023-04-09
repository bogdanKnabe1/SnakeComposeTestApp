package com.test_task.snakecompose.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.get
import com.test_task.snakecompose.domain.utils.DATABASE_OBJECT_TYPE
import com.test_task.snakecompose.domain.utils.DATABASE_TABLE
import com.test_task.snakecompose.domain.utils.DATABASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val GAME_PASS_ENABLED_FRC_KEY = "game_pass"
private const val WEB_LINK_FRC_KEY = "web_link"
private const val TAG = "AppViewModel"

class AppViewModel : ViewModel() {

    private val _gamePassEnabled = MutableStateFlow(false)
    val gamePassEnabled = _gamePassEnabled.asStateFlow()

    private val _webLink = MutableStateFlow("")
    val webLink = _webLink.asStateFlow()

    init {
        loadRemoteGamePassConfigParameters()
    }

    fun loadRemoteGamePassConfigParameters() {
        val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                Log.d(TAG, "Config params $GAME_PASS_ENABLED_FRC_KEY updated: $result}")
                Log.d(TAG, "Config params $WEB_LINK_FRC_KEY updated: $result}")
            } else {
                Log.d(TAG, "Config params update failed")
            }

            _gamePassEnabled.update { remoteConfig[GAME_PASS_ENABLED_FRC_KEY].asBoolean() }
            _webLink.update { remoteConfig[WEB_LINK_FRC_KEY].asString() }
        }
    }

    fun saveLinkToDatabase(link: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = FirebaseDatabase
                .getInstance(DATABASE_URL)
            val myDatabaseReference = database.getReference(DATABASE_TABLE)

            // Generate a unique ID for the new link
            val newRef = myDatabaseReference.push()

            // Save the link with the unique ID
            val linkData = hashMapOf(DATABASE_OBJECT_TYPE to link)
            newRef.setValue(linkData)
        }
    }

}
