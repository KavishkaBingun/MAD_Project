package com.example.easyworks.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyworks.models.ServiceModel
import com.example.easyworkscrud.R

class ServiceAdapter(private val servicelist:ArrayList<ServiceModel>):
    RecyclerView.Adapter<ServiceAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.service_list_item,parent,false)
       return ViewHolder(itemView,mListener)

   }

    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        val currentservice = servicelist[position]
        holder.tvservicename.text = currentservice.title



    }


    override fun getItemCount(): Int {
       return servicelist.size
    }

    class ViewHolder(itemView: View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val tvservicename : TextView = itemView.findViewById(R.id.tvservicename)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }


}