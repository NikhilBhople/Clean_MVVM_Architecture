package nikhil.bhople.mvvm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recycler_item.view.*
import nikhil.bhople.mvvm.R
import nikhil.bhople.mvvm.data.model.RepoResponse


class RecyclerAdapter(
    private val list: ArrayList<RepoResponse>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var mExpandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

        val isExpanded = position === mExpandedPosition
        holder.itemView.group.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.itemView.isActivated = isExpanded

        holder.itemView.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            notifyDataSetChanged()
        }
    }

    fun resetData(data: List<RepoResponse>) {
        list.clear()
        list.addAll(data)
        mExpandedPosition = -1
        notifyDataSetChanged()
    }

    fun getList(): List<RepoResponse> {
        return list
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            data: RepoResponse
        ) {
            itemView.txt_name.text = data.author
            itemView.txt_heading.text = data.name
            itemView.txt_desc.text = data.description
            itemView.txt_star.text = data.stars.toString()
            itemView.txt_forks.text = data.forks.toString()
            itemView.txt_lang.text = data.language

            Glide.with(itemView.context).load(data.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.img_avatar)
        }

    }
}