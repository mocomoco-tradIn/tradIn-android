package com.mocomoco.tradin.presentation.cheat

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("method") val method: String? = null,
    @SerializedName("payload") val payload: Payload? = null
)
data class Payload(
    @SerializedName("userId") val userId: Int? = null,
    @SerializedName("room") val room: Int? = null,
    @SerializedName("member") val member: List<Int>? = null,
    @SerializedName("sender") val sender: Int? = null,
    @SerializedName("receiver") val receiver: Int? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("time") val time: String? = null
)

fun Payload.toMessage(): UserMessage {
    return UserMessage(
        sender = this.sender,
        receiver = this.receiver,
        room = this.room,
        type = this.type,
        content = this.content,
    )
}

fun String.toJson(): Message? =
    try {
        Gson().newBuilder().create().getAdapter(Message::class.java).fromJson(this)
    } catch (e: Exception) {
        null
    }


fun Message.toString(): String? =
    try {
        this.toString()
    } catch (e: Exception) {
        null
    }
