package br.iesb.songs.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.iesb.songs.R
import kotlinx.android.synthetic.main.message_received_layout.view.*
import kotlinx.android.synthetic.main.message_send_layout.view.*

private const val VIEW_TYPE_USER_MESSAGE = 1
private const val VIEW_TYPE_ASSISTANT_MESSAGE = 2

class MessageAdapter(val context: Context) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val allMessages = mutableListOf<Array<String>>()

    fun addMessage(message: String, type: String) {
        val m = arrayOf(message, type)
        allMessages.add(m)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == VIEW_TYPE_USER_MESSAGE) {
            UserMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.message_send_layout, parent, false)
            )
        } else {
            AssistantMessageViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.message_received_layout, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val type = allMessages[position][1]

        return if(type == "GEO"){
            VIEW_TYPE_ASSISTANT_MESSAGE
        } else {
            VIEW_TYPE_USER_MESSAGE
        }
    }

    override fun getItemCount(): Int {
        return allMessages.size
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder, position: Int) {
        val message = allMessages[position][0]

        holder.bind(message)
    }

    inner class UserMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.textMessageSend

        override fun bind(message: String) {
            messageText.text = message
        }
    }

    inner class AssistantMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.assistantMessage

        override fun bind(message: String) {
            messageText.text = message
        }
    }

    open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Discover import for message
        open fun bind(message: String) {}
    }
}