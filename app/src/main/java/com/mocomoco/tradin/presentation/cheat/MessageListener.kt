package com.mocomoco.tradin.presentation.cheat

interface MessageListener {
    fun onConnectSuccess()
    fun onConnectFailed()
    fun onClose()
    fun onReceiveMessage(text: String?)
    fun onSendMessage(text: String?)
}