package com.mocomoco.tradin.presentation.cheat

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.BuildConfig
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.data.common.setDevSocketUserId
import com.mocomoco.tradin.data.common.setProdSocketUserId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(

) : BaseViewModel(), MessageListener {

    private val _errorEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val errorEvent: SharedFlow<Unit> = _errorEvent

    private val _userState: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val userState: StateFlow<Int> = _userState

    private val _roomState: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val roomState: StateFlow<Int> = _roomState

    private val _memberListState: MutableStateFlow<List<Int>> = MutableStateFlow<List<Int>>(emptyList())
    val memberListState: StateFlow<List<Int>> = _memberListState

    private val _receiveMessage: MutableStateFlow<UserMessage?> = MutableStateFlow<UserMessage?>(null)
    val receiveMessage: StateFlow<UserMessage?> = _receiveMessage

    private val _closeEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val closeEvent: SharedFlow<Unit> = _closeEvent

    init {
        val serverUrl =
            if(BuildConfig.DEBUG) setDevSocketUserId(1)
            else setProdSocketUserId(1)
        WebSocketManager.init(serverUrl, this)
    }

    fun onEnter() {
        viewModelScope.launch {
            WebSocketManager.connect()
        }
    }

    fun onReEnter() {
        viewModelScope.launch {
            if(!WebSocketManager.isConnect()) WebSocketManager.reconnect()
        }
    }

    fun onExit() {
        viewModelScope.launch {
            WebSocketManager.close()
        }
    }

    override fun onConnectSuccess() {
        val request = Message(
            method = "enter",
            payload = Payload(
                userId = userState.value,
                room = roomState.value
            )
        )
        sendMessage(request)
    }

    override fun onConnectFailed() {
        viewModelScope.launch {
            _errorEvent.emit(Unit)
        }
    }

    override fun onClose() {
        viewModelScope.launch {
            _closeEvent.emit(Unit)
        }
    }

    override fun onReceiveMessage(text: String?) {
        val response = text?.let {
            it.toJson()
        }
        when (response?.method) {
            "member" -> _memberListState.value = response.payload?.member!!
            "message" -> _receiveMessage.value = response.payload?.toMessage()
        }
    }

    override fun onSendMessage(text: String?) {
        text?.let {
            WebSocketManager.sendMessage(text)
        }
    }

    private fun sendMessage(message: Message) {
        viewModelScope.launch {
            val request = message.toString()
            WebSocketManager.sendMessage(request)
        }
    }
}


data class UserMessage(
    val sender: Int?,
    val receiver: Int?,
    val room: Int?,
    val type: String?,
    val content: String?,
    val time: String? = null
)