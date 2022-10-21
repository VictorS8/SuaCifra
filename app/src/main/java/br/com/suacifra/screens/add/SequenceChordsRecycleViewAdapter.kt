package br.com.suacifra.screens.add

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.utils.Config
import br.com.suacifra.utils.stringToTextViewString

class SequenceChordsRecyclerViewAdapter(
    private val sequenceChordsList: MutableSet<String>,
    val mainActivityContext: MainActivity
) : RecyclerView.Adapter<SequenceChordsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var sequenceItemTitleTextView: TextView
        var sequenceItemBodyTextView: TextView
        var sequenceItemCardView: CardView

        init {
            sequenceItemTitleTextView = view.findViewById(R.id.sequenceItemTitleTextView)
            sequenceItemBodyTextView = view.findViewById(R.id.sequenceItemBodyTextView)
            sequenceItemCardView = view.findViewById(R.id.sequenceItemCardView)
        }
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sequence_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sequenceTitleTextView =
            mainActivityContext.getString(R.string.sequence_item_title, (position + 1))
        val sequenceBodyTextView =
            stringToTextViewString(sequenceChordsList.toMutableList()[position]).removePrefix("[")
                .removeSuffix("]")
        val sequenceToEdit = sequenceChordsList.toMutableList()[position]

        holder.sequenceItemTitleTextView.text = sequenceTitleTextView
        holder.sequenceItemBodyTextView.text = sequenceBodyTextView

        val sharedPref = mainActivityContext.getSharedPreferences(
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )

        holder.sequenceItemCardView.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putBoolean(
                Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_BOOLEAN_KEY,
                true
            )
            sharedPrefEditor.putString(
                Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_SEQUENCE_STRING_KEY,
                sequenceToEdit
            )
            sharedPrefEditor.putInt(
                Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_SEQUENCE_INDEX_KEY,
                position
            )
            sharedPrefEditor.apply()
            mainActivityContext.addToBackStackFragment(AddSequenceFragment())
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        val sharedPref = mainActivityContext.getSharedPreferences(
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )
        var deleteSequence: MutableSet<String>? = mutableSetOf()
        deleteSequence = sharedPref.getStringSet(
            Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
            deleteSequence
        )
        if (deleteSequence != null) {
            return deleteSequence.size
        }
        return sequenceChordsList.size
    }

}
