package com.example.easyworks.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.easyworks.models.CustomerModel
import com.example.easyworkscrud.R


class CusAdapter (private val cusList : ArrayList<CustomerModel>) : RecyclerView.Adapter<CusAdapter.ViewHolder>(){
    @SuppressLint("SuspiciousIndentation")

    private lateinit var  mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener= clickListener
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cus_list_item,parent, false)
        return ViewHolder(itemView,mListener)
    }


    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val curruntCus = cusList[position]
        holder.tvCusName.text = curruntCus.cusName
        holder.tvCusR.text = curruntCus.cusReview
    }


    override fun getItemCount(): Int {
      return cusList.size
    }

    class ViewHolder(itemView : View, clickListener:onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvCusName : TextView = itemView.findViewById(R.id.tvCusName)
        val tvCusR : TextView = itemView.findViewById(R.id.tvCusR)

        init{
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }


}