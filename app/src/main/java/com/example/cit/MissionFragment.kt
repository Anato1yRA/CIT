package com.example.cit

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cit.databinding.MissionFragmentBinding
import com.example.cit.model.Mission


class MissionFragment : Fragment() {

    private lateinit var binding: MissionFragmentBinding

    private lateinit var adapter1: RVAdapter
    private lateinit var adapter2: RVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = MissionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MissionFragment()
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val missionListLocal = ArrayList<Mission>()
        missionListLocal.add(
            Mission(
                1,
                "shooting",
                "Мониторинг лесоизменений a b c d e f g h i j k l m n o p q r s t u v w x y z",
                "Кодинское / Тагаринское a b c d e f g h i j k l m n o p q r s t u v w x y z",
                "01.02.2022",
                0,
                10,
                "00:00:00",
                10.5F,
                true,
                55.753695452960244,
                37.62014698237181
            )
        )
        missionListLocal.add(
            Mission(
                2,
                "tower",
                "Поиск очагов пожара a b c d e f g h i j k l m n o p q r s t u v w x y z",
                "Кодинское / Тагаринское / кв. 12 / в. 2, 3 a b c d e f g h i j k l m n o p q r s t u v w x y z",
                "02.02.2022",
                1,
                20,
                "20:00:00",
                20.5F,
                false,
                59.95114441821376,
                30.368098206818104
            )
        )
        missionListLocal.add(
            Mission(
                3,
                "flight",
                "Пролёт по границе выделов",
                "Кодинское / Тагаринское / 12 / 2, 3-5, 11",
                "03.02.2022",
                2,
                30,
                "30:00:00",
                30.5F,
                true,
                54.61169880579972,
                39.711167961359024
            )
        )
        missionListLocal.add(
            Mission(
                4,
                "flight",
                "Пролёт по границе выделов",
                "Кодинское / Тагаринское / 12 / 2, 3-5, 11",
                "04.02.2022",
                3,
                40,
                "40:00:00",
                40.5F,
                false,
                56.33975221903344,
                43.97157669067383
            )
        )
        missionListLocal.add(
            Mission(
                5,
                "flight",
                "Пролёт по границе выделов",
                "Кодинское / Тагаринское / 12 / 2, 3-5, 11",
                "04.02.2022",
                3,
                40,
                "40:00:00",
                40.5F,
                false,
                54.21076260273506,
                37.64130458235741
            )
        )

        val missionListServer = ArrayList<Mission>()
        missionListServer.add(
            Mission(
                6,
                "shooting",
                "Мониторинг лесоизменений",
                "Кодинское / Тагаринское",
                "01.02.2022",
                0,
                10,
                "00:00:00",
                10.5F,
                true,
                51.682426262454705,
                39.18545667082071
            )
        )
        missionListServer.add(
            Mission(
                7,
                "tower",
                "Поиск очагов пожара",
                "Кодинское / Тагаринское / кв. 12 / в. 2, 3",
                "02.02.2022",
                1,
                20,
                "20:00:00",
                20.5F,
                false,
                53.26786088249154,
                34.4036466255784
            )
        )
        missionListServer.add(
            Mission(
                8,
                "flight",
                "Пролёт по границе выделов",
                "Кодинское / Тагаринское / 12 / 2, 3-5, 11",
                "03.02.2022",
                2,
                30,
                "30:00:00",
                30.5F,
                true,
                54.79108794690934,
                32.064585350453854
            )
        )

        binding.clDialog.visibility = View.GONE

        binding.tvLocalName.text = "Локальные миссии (${missionListLocal.count()})"

        val localMission = binding.rvLocalList
        localMission.layoutManager = LinearLayoutManager(context)

        binding.tvServerName.text = "Загружено с сервера (${missionListServer.count()})"
        binding.tvServerDate.text = "11.03.2022"

        val serverMission = binding.rvServerList
        serverMission.layoutManager = LinearLayoutManager(context)

        adapter1 = RVAdapter {
            when (it["operation"]) {
                "clickItem" -> {
                    adapter2.closeAllPanels()
                }

                "clickEdit" -> toast("Нажата кнопка Edit")

                "clickGps" -> {
                    setMarker(it)
                }

                "delItem" -> {
                    binding.clDialog.visibility = View.VISIBLE
                    binding.clDialog.findViewById<Button>(R.id.bNo).setOnClickListener {
                        adapter1.swipeUndo()
                        binding.clDialog.visibility = View.GONE
                    }
                    binding.clDialog.findViewById<Button>(R.id.bYes).setOnClickListener {
                        binding.clDialog.visibility = View.GONE
                    }
                }
            }
        }

        adapter2 = RVAdapter {
            when (it["operation"]) {
                "clickItem" -> {
                    adapter1.closeAllPanels()
                }

                "clickEdit" -> toast("Нажата кнопка Edit")

                "clickGps" -> {
                    setMarker(it)
                }

                "delItem" -> {
                    binding.clDialog.visibility = View.VISIBLE
                    binding.clDialog.findViewById<Button>(R.id.bNo).setOnClickListener {
                        adapter2.swipeUndo()
                        binding.clDialog.visibility = View.GONE
                    }
                    binding.clDialog.findViewById<Button>(R.id.bYes).setOnClickListener {
                        binding.clDialog.visibility = View.GONE
                    }
                }
            }
        }

        adapter1.setItemList(missionListLocal)
        adapter2.setItemList(missionListServer)

        val swipeGesture1 = object : SwipeGesture() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        adapter1.swipeItem(viewHolder.adapterPosition)

                        binding.clDialog.visibility = View.VISIBLE
                        binding.clDialog.findViewById<Button>(R.id.bNo).setOnClickListener {
                            adapter1.swipeUndo()
                            binding.clDialog.visibility = View.GONE
                        }
                        binding.clDialog.findViewById<Button>(R.id.bYes).setOnClickListener {
                            binding.clDialog.visibility = View.GONE
                        }
                    }
                }
            }
        }

        val swipeGesture2 = object : SwipeGesture() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        adapter2.swipeItem(viewHolder.adapterPosition)

                        binding.clDialog.visibility = View.VISIBLE
                        binding.clDialog.findViewById<Button>(R.id.bNo).setOnClickListener {
                            adapter2.swipeUndo()
                            binding.clDialog.visibility = View.GONE
                        }
                        binding.clDialog.findViewById<Button>(R.id.bYes).setOnClickListener {
                            binding.clDialog.visibility = View.GONE
                        }
                    }
                }
            }
        }

        localMission.adapter = adapter1
        serverMission.adapter = adapter2

        val touchLocalMission = ItemTouchHelper(swipeGesture1)
        touchLocalMission.attachToRecyclerView(localMission)

        val touchServerMission = ItemTouchHelper(swipeGesture2)
        touchServerMission.attachToRecyclerView(serverMission)

        binding.incToolbar.ivAdd.setOnClickListener {
            openFragment(MissionAddFragment.newInstance())
        }

        val ivSearch = binding.incToolbar.ivSearch
        ivSearch.setOnClickListener {
            if (binding.clSearch.visibility == View.GONE) {
                binding.clSearch.visibility = View.VISIBLE
                ivSearch.background = ColorDrawable(resources.getColor(R.color.slider_on, null))
            } else {
                binding.clSearch.visibility = View.GONE
                ivSearch.background = ColorDrawable(resources.getColor(R.color.opasity0, null))
                binding.etSearch.setText("")
            }
        }

        binding.etSearch.addTextChangedListener(object :
            TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(editText: Editable) {
                val searchText = editText.toString().lowercase().trim()

                adapter1.searchStr(searchText)
                adapter2.searchStr(searchText)
            }
        })

        binding.incToolbar.ivDell.setOnClickListener {
            adapter1.delItem()
            adapter2.delItem()
        }

        val onColor = ColorDrawable(resources.getColor(R.color.slider_on, null))

        val ivVisible = binding.incToolbar.ivVisible
        ivVisible.setOnClickListener {
            if (ivVisible.background == onColor) {
                ivVisible.background =
                    ColorDrawable(resources.getColor(R.color.opasity0, null))
            } else {
                ivVisible.background = onColor

                toast("Нажата кнопка фильтрации видимых на карте элементов списка")
            }
        }

        val ivFilter = binding.incToolbar.ivFilter
        ivFilter.setOnClickListener {
            if (ivFilter.background == onColor) {
                ivFilter.background = ColorDrawable(resources.getColor(R.color.opasity0, null))

                binding.scrollView2.setScrolling(true)
            } else {
                ivFilter.background = onColor

                binding.scrollView2.setScrolling(false)

                toast("Проведите по списку для отображения точек на карте")
            }
        }

        binding.incToolbar.tbSwitch.setOnCheckedChangeListener { _, b ->
            if (b)
                toast("Переключатель видимости всех миссий включен")
            else
                toast("Переключатель видимости всех миссий выключен")
        }

        var oldName = ""
        var oldLatitude = ""
        var oldLongitude = ""

        binding.rvLocalList.setOnTouchListener { _, motionEvent ->
            val el = binding.rvLocalList.findChildViewUnder(motionEvent.x, motionEvent.y)

            if (el != null) {
                val mName = el.findViewById<TextView>(R.id.tvName).text.toString()
                val mLatitude = el.findViewById<TextView>(R.id.tvLatitude).text.toString()
                val mLongitude = el.findViewById<TextView>(R.id.tvLongitude).text.toString()

                if(mLatitude != oldLatitude && mLongitude != oldLongitude && mName != oldName){
                    oldLatitude = mLatitude
                    oldLongitude = mLongitude
                    oldName = mName

                    setMarker(mapOf(
                        "nameMission" to mName,
                        "latitude" to mLatitude,
                        "longitude" to mLongitude
                    ))
                }
            }

            false
        }

        binding.rvServerList.setOnTouchListener { _, motionEvent ->
            val el = binding.rvLocalList.findChildViewUnder(motionEvent.x, motionEvent.y)

            if (el != null) {
                val mName = el.findViewById<TextView>(R.id.tvName).text.toString()
                val mLatitude = el.findViewById<TextView>(R.id.tvLatitude).text.toString()
                val mLongitude = el.findViewById<TextView>(R.id.tvLongitude).text.toString()

                if(mLatitude != oldLatitude && mLongitude != oldLongitude && mName != oldName){
                    oldLatitude = mLatitude
                    oldLongitude = mLongitude
                    oldName = mName

                    setMarker(mapOf(
                        "nameMission" to mName,
                        "latitude" to mLatitude,
                        "longitude" to mLongitude
                    ))
                }
            }

            false
        }
    }

    private fun setMarker(params: Map<String, String>) {
        val nameMission = params["nameMission"].toString()
        val latitude = params["latitude"]!!.toDouble()
        val longitude = params["longitude"]!!.toDouble()

        val frag = activity?.supportFragmentManager?.findFragmentByTag("MapsFragment")
        if (frag != null) {
            (frag as MapsFragment).setMarkerToMap(latitude, longitude, nameMission)
        }
    }

    private fun openFragment(fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragmentContainerViewMain, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}