package es.upsa.mimo.mimo18_androidarch.list

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import es.upsa.mimo.mimo18_androidarch.R
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.Character

class CharacterListAdapter(private val onClickListener: ImageOnClick?)
    : RecyclerView.Adapter<CharacterListAdapter.CharacterVH>() {

    private var characters: List<Character> = emptyList()

    interface ImageOnClick {

        fun imageClicked(view: ImageView, url: String)
    }

    class CharacterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIv: ImageView = itemView.findViewById(R.id.character_image)
        val textView: TextView = itemView.findViewById(R.id.character_name)
    }

    fun characters(characters: List<Character>) {
        this.characters = characters
        notifyDataSetChanged() // This is bad. Used for simplicity.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.character_list_list_item, parent, false)
        return CharacterVH(view)
    }

    override fun onBindViewHolder(holder: CharacterVH, position: Int) {

        val imageUrl = getCharacterImageUrl(position)

        Log.d(TAG, "Binding: $imageUrl")

        Picasso.Builder(holder.imageIv.context)
                .indicatorsEnabled(true)
                .loggingEnabled(true)
                .build()
                .load(imageUrl)
                .into(holder.imageIv)

        holder.textView.text = characters[position].name

        if (onClickListener != null) {
            holder.imageIv.setOnClickListener {
                val selectedPosition = holder.adapterPosition
                val charId = getCharacterId(selectedPosition)
                onClickListener.imageClicked(holder.imageIv, charId)
            }
        }
    }

    private fun getCharacterImageUrl(position: Int): String {
        val (_, _, _, thumbnail) = characters[position]
        return thumbnail!!.path + "." + thumbnail.extension
    }


    private fun getCharacterId(position: Int): String {
        return characters[position].let { it.id!! }
    }

    override fun getItemCount(): Int = characters.size

    companion object {
        private val TAG = CharacterListAdapter::class.java.canonicalName
    }


}
