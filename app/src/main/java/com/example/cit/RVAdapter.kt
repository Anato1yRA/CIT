package com.example.cit

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.cit.databinding.MissionItemBinding
import com.example.cit.model.Mission
import java.util.*

class RVAdapter(private val onItemClicked: (Map<String, String>) -> Unit) :
    RecyclerView.Adapter<RVAdapter.Holder>() {

    private var itemList = ArrayList<Mission>()

    private var swipePosition: Int? = null
    private var swipeMission: Mission? = null

    class Holder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = MissionItemBinding.bind(item)

        fun bind(itemMission: Mission) = with(binding) {

            tvID.text = "${itemMission.id}"

            when (itemMission.type) {
                "shooting" -> ivType.setImageResource(R.drawable.ic_shooting)
                "tower" -> ivType.setImageResource(R.drawable.ic_tower)
                "flight" -> ivType.setImageResource(R.drawable.ic_flight)
            }

            tvName.text = itemMission.nameMission
            tvInfo.text = itemMission.forestAddress
            tvDate.text = itemMission.date

            tvLatitude.text = itemMission.latitude.toString()
            tvLongitude.text = itemMission.longitude.toString()

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

            if (!itemMission.visibleItem) {
                ivVisible.visibility = View.VISIBLE

                ivType.alpha = 0.5F
                tvName.alpha = 0.5F
                tvInfo.alpha = 0.5F
                ivType.alpha = 0.5F
                ivVisible.alpha = 0.5F
                ivUpDown.alpha = 0.5F
                clItemData.alpha = 0.5F
            } else {
                ivVisible.visibility = View.GONE

                ivType.alpha = 1F
                tvName.alpha = 1F
                tvInfo.alpha = 1F
                ivType.alpha = 1F
                ivVisible.alpha = 1F
                ivUpDown.alpha = 1F
                clItemData.alpha = 1F
            }

            if (itemMission.openPanel) {
                ivUpDown.setImageResource(R.drawable.ic_chevron_up)
                clItemData.visibility = View.VISIBLE
            } else {
                ivUpDown.setImageResource(R.drawable.ic_chevron_down)
                clItemData.visibility = View.GONE
            }

            if (itemMission.searchStr != "") {
                val greenColorValue: Int = Color.parseColor("#67E4A1")

                setHighLightedText(tvName, itemMission.searchStr, greenColorValue)
            }
        }

        @SuppressLint("ResourceAsColor")
        private fun setHighLightedText(tv: TextView, textTo: String, colorSearch: Int) {
            val textFrom = tv.text.toString()

            val textFromLowercase = textFrom.lowercase(Locale.getDefault())

            val textToPosition = textFromLowercase.indexOf(textTo, 0)

            val spannable = SpannableString(textFrom)
            spannable.removeSpan(textFrom)

            if (textTo != "" && textToPosition >= 0) {
                spannable.setSpan(
                    ForegroundColorSpan(colorSearch),
                    textToPosition,
                    textToPosition + textTo.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    textToPosition,
                    textToPosition + textTo.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            tv.text = spannable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mission_item, parent, false)
        return Holder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position])

        val item = holder.itemView

        val panel = item.findViewById<ConstraintLayout>(R.id.clItemData)

        item.setOnClickListener {
            for (i in 0 until itemList.count()) {
                if (i != position && itemList[i].openPanel) {
                    itemList[i].openPanel = false
                    notifyItemChanged(i)
                }
            }

            if (panel.visibility == View.GONE) {
                onItemClicked(mapOf("operation" to "clickItem"))

                panel.visibility = View.VISIBLE
                item.findViewById<ImageView>(R.id.ivUpDown)
                    .setImageResource(R.drawable.ic_chevron_up)

                itemList[position].openPanel = true
            } else {
                panel.visibility = View.GONE
                item.findViewById<ImageView>(R.id.ivUpDown)
                    .setImageResource(R.drawable.ic_chevron_down)

                itemList[position].openPanel = false
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
            onItemClicked(
                mapOf(
                    "operation" to "clickGps",
                    "nameMission" to itemList[position].nameMission,
                    "latitude" to "${itemList[position].latitude}",
                    "longitude" to "${itemList[position].longitude}"
                )
            )
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

    fun swipeItem(itemPosition: Int) {
        swipePosition = itemPosition
        swipeMission = itemList[itemPosition]

        itemList.removeAt(itemPosition)
        notifyItemRemoved(itemPosition)
        notifyItemChanged(itemPosition)
    }

    fun swipeUndo() {
        if (swipePosition != null) {
            itemList.add(swipePosition!!, swipeMission!!)
            notifyItemInserted(swipePosition!!)
        }
    }

    fun closeAllPanels() {
        for (i in 0 until itemList.count()) {
            if (itemList[i].openPanel) {
                itemList[i].openPanel = false
                notifyItemChanged(i)
            }
        }
    }

    fun searchStr(searchText: String) {
        for (i in 0 until itemList.count()) {
            itemList[i].searchStr = searchText
            notifyItemChanged(i)
        }
    }

    fun delItem() {
        for (i in 0 until itemList.count()) {
            if (itemList[i].openPanel) {
                swipePosition = i
                swipeMission = itemList[i]

                itemList.removeAt(i)
                notifyItemRemoved(i)
                notifyItemChanged(i)
                onItemClicked(mapOf("operation" to "delItem"))
                break
            }
        }
    }
}