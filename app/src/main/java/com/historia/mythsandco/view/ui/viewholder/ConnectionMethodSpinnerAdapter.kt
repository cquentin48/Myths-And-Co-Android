package com.historia.mythsandco.view.ui.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.historia.mythsandco.R
import com.historia.mythsandco.view.model.ConnectionSpinnerModel
import com.squareup.picasso.Picasso

class ConnectionMethodSpinnerAdapter(private val context: Context, private val listItemText: ArrayList<ConnectionSpinnerModel>):BaseAdapter() {
    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if(convertView == null){
            view = inflater.inflate(R.layout.connection_method_spinner_layout,parent,false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        }else{
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        vh.methodName.text = listItemText[position].methodValue
        vh.methodIcon.setImageDrawable(context.getDrawable(listItemText[position].methodIconResId))
        //Picasso.get().load(listItemText[position].methodIconURL).into(vh.methodIcon)
        return view
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItem(position: Int): ConnectionSpinnerModel = listItemText[position]

    override fun getCount(): Int = listItemText.size

    private class ItemRowHolder(row: View?){
        val methodName: TextView = row?.findViewById(R.id.connection_method_spinner_custom_layout_value) as TextView
        val methodIcon : ImageView = row?.findViewById(R.id.connection_method_spinner_custom_layout_icon) as ImageView
    }
}