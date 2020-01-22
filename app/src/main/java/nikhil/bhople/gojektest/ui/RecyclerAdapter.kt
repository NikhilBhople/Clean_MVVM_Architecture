package nikhil.bhople.gojektest.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recycler_item.view.*
import nikhil.bhople.gojektest.R
import nikhil.bhople.gojektest.data.model.RepoResponse


class RecyclerAdapter(
    val list: ArrayList<RepoResponse>,
    val context: Context
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var mExpandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
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

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(list[position], context)

        val isExpanded = position === mExpandedPosition
        holder.itemView.group.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.itemView.isActivated = isExpanded

        holder.itemView.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            notifyDataSetChanged()
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            data: RepoResponse,
            context: Context
        ) {
            itemView.txt_name.text = data.author
            itemView.txt_heading.text = data.name
            itemView.txt_desc.text = data.description
            itemView.txt_star.text = data.stars.toString()
            itemView.txt_forks.text = data.forks.toString()
            itemView.txt_lang.text = data.language

            Glide.with(context).load(data.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.img_avatar)
        }

    }
}