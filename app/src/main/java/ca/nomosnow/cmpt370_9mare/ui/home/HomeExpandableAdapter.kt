package ca.nomosnow.cmpt370_9mare.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.nomosnow.cmpt370_9mare.databinding.EventItemBinding
import ca.nomosnow.cmpt370_9mare.databinding.EventViewBinding
import ca.nomosnow.cmpt370_9mare.databinding.GroupEventBinding

class HomeExpandableAdapter  internal constructor(
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var groupBinding: GroupEventBinding
    private lateinit var itemBinding: EventItemBinding


    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }

    override fun getChild(listPosition: Int, expandableListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition]]!![expandableListPosition]
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getChildId(listPosition: Int, expandableListPosition: Int): Long {
        return expandableListPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, view: View?, parent: ViewGroup?): View {
        var convertView = view
        val holder: GroupViewHolder
        if (convertView == null) {
            groupBinding = GroupEventBinding.inflate(inflater,parent,false)
            convertView = groupBinding.root
            holder = GroupViewHolder()
            holder.label = groupBinding.listEvent
            convertView.tag = holder
        } else {
            holder = convertView.tag as GroupViewHolder
        }
        val listTitle = getGroup(listPosition) as String
        holder.label!!.text = listTitle
        return convertView

    }

    override fun getChildView(listPosition: Int, expandableListPosition: Int, isLastChild: Boolean, view: View?, parent: ViewGroup?): View {
        var convertView = view
        val holder: ItemViewHolder
        if (convertView == null){
            itemBinding = EventItemBinding.inflate(inflater,parent,false)
            convertView = itemBinding.root
            holder = ItemViewHolder()
            holder.label = itemBinding.expandedListItem
            convertView.tag = holder
        }else{
            holder = convertView.tag as ItemViewHolder
        }

        val expandableListText = getChild(listPosition, expandableListPosition) as String
        holder.label!!.text = expandableListText
        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    inner class ItemViewHolder {
        internal var label: TextView? = null
    }

    inner class GroupViewHolder {
        internal var label: TextView? = null
    }


}