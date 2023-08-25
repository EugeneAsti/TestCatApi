package ru.aeyu.catapitestapp.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import ru.aeyu.catapitestapp.R
import ru.aeyu.catapitestapp.domain.models.Breed

class BreedsArrayAdapter(
    private val mContext: Context,
    private val viewResourceId: Int,
    private val breeds: List<Breed>,
    private val onItemClick: (breed: Breed?) -> Unit,
) : ArrayAdapter<Breed>(mContext, viewResourceId, breeds) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v: View? = convertView
        if (v == null) {
            val vi = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(viewResourceId, null)
        }
        val breed: Breed = breeds[position]
            val breedName = v?.findViewById(R.id.breedItemName) as TextView?
            breedName?.text = breed.name
        return v!!
    }

    fun onItemClickListener(position: Int){
        onItemClick(breeds[position])
    }

    override fun getFilter(): Filter {
        return nameFilter
    }


    private var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): String {
            return (resultValue as Breed).name
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return FilterResults()
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val listAny = results?.values as ArrayList<*>?
            val filteredList: List<Breed> = listAny?.filterIsInstance<Breed>() ?: emptyList()

            if (results != null && results.count > 0) {
                clear()
                for (c: Breed in filteredList) {
                    add(c)
                }
                notifyDataSetChanged()
            }
        }
    }
}