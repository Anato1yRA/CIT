package com.example.cit

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cit.databinding.MissionItemBinding
import com.example.cit.model.Mission

class RVAdapter(private val onItemClicked: (Map<String, String>) -> Unit) :
    RecyclerView.Adapter<RVAdapter.Holder>() {

    private var itemList = ArrayList<Mission>()

    private var swipePosition: Int? = null
    private var swipeMission: Mission? = null

    class Holder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = MissionItemBinding.bind(item)

        @SuppressLint("SetTextI18n", "Range")
        fun bind(itemMission: Mission) = with(binding) {

            when (itemMission.type) {
                "shooting" -> ivType.setImageResource(R.drawable.ic_shooting)
                "tower" -> ivType.setImageResource(R.drawable.ic_tower)
                "flight" -> ivType.setImageResource(R.drawable.ic_flight)
            }

            tvName.text = itemMission.name
            tvInfo.text = itemMission.forestAddress
            tvDate.text = itemMission.date

            when (itemMission.dot) {
                0 -> tvDot.text = "Показывать: все точки"
                1 -> tvDot.text = "Показывать: ключевые точки"
                2 -> tvDot.text = "Показывать: старт и финиш"
                3 -> tvDot.text = "Показывать: без точек"
            }

            seekBar.progress = itemMission.dot

            tvParam1.text = "ППМ: ${itemMission.dotCount}"
            tvParam2.text = itemMission.timeFlight
            tvParam3.text = "${itemMission.length} км"

            if(!itemMission.visible){
                ivVisible.visibility = View.VISIBLE

                ivType.alpha = 0.5F
                tvName.alpha = 0.5F
                tvInfo.alpha = 0.5F
                ivType.alpha = 0.5F
                ivVisible.alpha = 0.5F
                ivUpDown.alpha = 0.5F
                clItemData.alpha = 0.5F
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mission_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position])

        val item = holder.itemView

        val panel = item.findViewById<ConstraintLayout>(R.id.clItemData)

        item.setOnClickListener {
            if (panel.visibility == View.GONE) {
                onItemClicked(mapOf("operation" to "clickItem", "position" to "$position"))

                panel.visibility = View.VISIBLE
                item.findViewById<ImageView>(R.id.ivUpDown)
                    .setImageResource(R.drawable.ic_chevron_up)
            } else {
                panel.visibility = View.GONE
                item.findViewById<ImageView>(R.id.ivUpDown)
                    .setImageResource(R.drawable.ic_chevron_down)
            }
        }

        val sBar = item.findViewById<SeekBar>(R.id.seekBar)
        sBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, currentValue: Int, p2: Boolean) {
                when (currentValue) {
                    0 -> item.findViewById<TextView>(R.id.tvDot).text = "Показывать: все точки"
                    1 -> item.findViewById<TextView>(R.id.tvDot).text = "Показывать: ключевые точки"
                    2 -> item.findViewById<TextView>(R.id.tvDot).text = "Показывать: старт и финиш"
                    3 -> item.findViewById<TextView>(R.id.tvDot).text = "Показывать: без точек"
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        panel.findViewById<ImageView>(R.id.ivEdit).setOnClickListener {
            onItemClicked(mapOf("operation" to "clickEdit"))
        }
        panel.findViewById<ImageView>(R.id.ivGps).setOnClickListener {
            onItemClicked(mapOf("operation" to "clickGps"))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItemList(newItemList: ArrayList<Mission>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    @SuppressLint("ShowToast")
    fun swipeItem(itemPosition: Int) {
        swipePosition = itemPosition
        swipeMission = itemList[itemPosition]

        itemList.removeAt(itemPosition)
        notifyItemRemoved(itemPosition)
    }

    fun swipeUndo() {
        if (swipePosition != null) {
            itemList.add(swipePosition!!, swipeMission!!)
            notifyItemInserted(swipePosition!!)
        }
    }
}