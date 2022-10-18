package br.com.suacifra.screens.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.models.Cifras
import br.com.suacifra.screens.add.AddFragment
import br.com.suacifra.utils.mutableCollectionToSetOfStringCifras
import br.com.suacifra.utils.mutableCollectionToTextViewString

class CifrasRecyclerViewAdapter(
    private val cifrasList: MutableList<Cifras>,
    val mainActivityContext: MainActivity
) :
    RecyclerView.Adapter<CifrasRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cifraNameItemTextView: TextView
        var cifraToneItemTextView: TextView
        var cifraSingerNameItemTextView: TextView
        var cifraFirstSequenceItemTextView: TextView
        val deleteCifraImageView: ImageView
        val cifraItemCardView: CardView

        init {
            cifraNameItemTextView = view.findViewById(R.id.cifraNameItemTextView)
            cifraToneItemTextView = view.findViewById(R.id.cifraToneItemTextView)
            cifraSingerNameItemTextView = view.findViewById(R.id.cifraSingerNameItemTextView)
            cifraFirstSequenceItemTextView = view.findViewById(R.id.cifraFirstSequenceItemTextView)
            deleteCifraImageView = view.findViewById(R.id.deleteCifraImageButton)
            cifraItemCardView = view.findViewById(R.id.cifraItemCardView)
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
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.cifra_item, parent, false)
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
        val cifraNameTextView = cifrasList[position].name
        val cifraToneTextView =
            mainActivityContext.getString(R.string.cifra_tone_item, cifrasList[position].tone)
        val cifraSingerNameTextView = mainActivityContext.getString(
            R.string.cifra_singer_name_item,
            cifrasList[position].singerName
        )
        val cifraChordsSequence = cifrasList[position].chordsSequence
        val cifraFirstChordsSequenceTextView = mainActivityContext.getString(
            R.string.cifra_first_sequence_item,
            mutableCollectionToTextViewString(cifraChordsSequence[0])
        )

        holder.cifraNameItemTextView.text = cifraNameTextView
        holder.cifraToneItemTextView.text =
            cifraToneTextView
        holder.cifraSingerNameItemTextView.text = cifraSingerNameTextView
        holder.cifraFirstSequenceItemTextView.text = cifraFirstChordsSequenceTextView

        val sharedPref = mainActivityContext.getSharedPreferences(
            mainActivityContext.getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE
        )

        holder.deleteCifraImageView.setOnClickListener {
            // TODO - Delete interact with database
        }

        holder.cifraItemCardView.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putString(
                mainActivityContext.getString(R.string.shared_preference_edit_cifra_mode_name_string_key),
                cifraNameTextView
            )
            sharedPrefEditor.putString(
                mainActivityContext.getString(R.string.shared_preference_edit_cifra_mode_singer_name_string_key),
                cifraSingerNameTextView
            )
            sharedPrefEditor.putString(
                mainActivityContext.getString(R.string.shared_preference_edit_cifra_mode_tone_string_key),
                cifraToneTextView
            )
            sharedPrefEditor.putStringSet(
                mainActivityContext.getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                mutableCollectionToSetOfStringCifras(cifraChordsSequence)
            )

            sharedPrefEditor.putBoolean(
                mainActivityContext.getString(R.string.shared_preference_edit_cifra_mode_boolean_key),
                true
            )
            sharedPrefEditor.apply()
            mainActivityContext.addToBackStackFragment(AddFragment())
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return cifrasList.size
    }

}
