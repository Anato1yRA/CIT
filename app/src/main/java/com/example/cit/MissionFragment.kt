package com.example.cit

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cit.databinding.MissionFragmentBinding
import com.example.cit.model.Mission

class MissionFragment : Fragment() {

    private lateinit var binding: MissionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = MissionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MissionFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val missionListLocal = ArrayList<Mission>()
        missionListLocal.add(
            Mission(
                1,
                "shooting",
                "Мониторинг лесоизменений",
                "Кодинское / Тагаринское",
                "01.02.2022",
                0,
                10,
                "00:00:00",
                10.5F,
                true
            )
        )
        missionListLocal.add(
            Mission(
                2,
                "tower",
                "Поиск очагов пожара",
                "Кодинское / Тагаринское / кв. 12 / в. 2, 3",
                "02.02.2022",
                1,
                20,
                "20:00:00",
                20.5F,
                false
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
                true
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
                false
            )
        )

        val missionListServer = ArrayList<Mission>()
        missionListServer.add(
            Mission(
                5,
                "shooting",
                "Мониторинг лесоизменений",
                "Кодинское / Тагаринское",
                "01.02.2022",
                0,
                10,
                "00:00:00",
                10.5F,
                true
            )
        )
        missionListServer.add(
            Mission(
                6,
                "tower",
                "Поиск очагов пожара",
                "Кодинское / Тагаринское / кв. 12 / в. 2, 3",
                "02.02.2022",
                1,
                20,
                "20:00:00",
                20.5F,
                false
            )
        )
        missionListServer.add(
            Mission(
                7,
                "flight",
                "Пролёт по границе выделов",
                "Кодинское / Тагаринское / 12 / 2, 3-5, 11",
                "03.02.2022",
                2,
                30,
                "30:00:00",
                30.5F,
                true
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

        val adapter1 = RVAdapter {
            when (it["operation"]) {
                "clickItem" -> {
                    for (i in 0 until missionListLocal.count()) {
                        val item = localMission[i]
                        val panel = item.findViewById<ConstraintLayout>(R.id.clItemData)
                        if (panel.visibility == View.VISIBLE) {
                            panel.visibility = View.GONE
                            item.findViewById<ImageView>(R.id.ivUpDown)
                                .setImageResource(R.drawable.ic_chevron_up)
                        }
                    }

                    for (i in 0 until missionListServer.count()) {
                        val item = serverMission[i]
                        val panel = item.findViewById<ConstraintLayout>(R.id.clItemData)
                        if (panel.visibility == View.VISIBLE) {
                            panel.visibility = View.GONE
                            item.findViewById<ImageView>(R.id.ivUpDown)
                                .setImageResource(R.drawable.ic_chevron_up)
                        }
                    }
                }
                "clickEdit" -> toast("Нажата кнопка Edit")
                "clickGps" -> toast("Нажата кнопка Gps")
            }
        }

        val adapter2 = RVAdapter {
            when (it["operation"]) {
                "clickItem" -> {
                    for (i in 0 until missionListLocal.count()) {
                        val item = localMission[i]
                        val panel = item.findViewById<ConstraintLayout>(R.id.clItemData)
                        if (panel.visibility == View.VISIBLE) {
                            panel.visibility = View.GONE
                            item.findViewById<ImageView>(R.id.ivUpDown)
                                .setImageResource(R.drawable.ic_chevron_up)
                        }
                    }

                    for (i in 0 until missionListServer.count()) {
                        val item = serverMission[i]
                        val panel = item.findViewById<ConstraintLayout>(R.id.clItemData)
                        if (panel.visibility == View.VISIBLE) {
                            panel.visibility = View.GONE
                            item.findViewById<ImageView>(R.id.ivUpDown)
                                .setImageResource(R.drawable.ic_chevron_up)
                        }
                    }
                }
                "clickEdit" -> toast("Нажата кнопка Edit")
                "clickGps" -> toast("Нажата кнопка Gps")
            }
        }

        adapter1.setItemList(missionListLocal)
        adapter2.setItemList(missionListServer)

        val swipeGesture1 = object : SwipeGesture() {
            @SuppressLint("ShowToast")
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


        binding.toolbar1.apply {
            val avSearch = menu.findItem(R.id.search).actionView
            avSearch.setOnClickListener {
                if (binding.clSearch.visibility == View.GONE) {
                    binding.clSearch.visibility = View.VISIBLE
                    avSearch.background = ColorDrawable(resources.getColor(R.color.slider_on, null))
                } else {
                    binding.clSearch.visibility = View.GONE
                    avSearch.background = ColorDrawable(resources.getColor(R.color.opasity0, null))
                    binding.etSearch.setText("")
                }
            }

            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.add -> {
                        openFragment(MissionAddFragment.newInstance())
                    }

                    R.id.del -> {
                        var isDel = false
                        for (i in 0 until missionListLocal.count()) {
                            val item = localMission[i]
                            val panel = item.findViewById<ConstraintLayout>(R.id.clItemData)
                            if (panel.visibility == View.VISIBLE) {
                                adapter1.swipeItem(i)

                                isDel = true

                                binding.clDialog.visibility = View.VISIBLE
                                binding.clDialog.findViewById<Button>(R.id.bNo).setOnClickListener {
                                    adapter1.swipeUndo()
                                    binding.clDialog.visibility = View.GONE
                                }
                                binding.clDialog.findViewById<Button>(R.id.bYes)
                                    .setOnClickListener {
                                        binding.clDialog.visibility = View.GONE
                                    }
                                break
                            }
                        }

                        for (i in 0 until missionListServer.count()) {
                            val item = serverMission[i]
                            val panel = item.findViewById<ConstraintLayout>(R.id.clItemData)
                            if (panel.visibility == View.VISIBLE) {
                                adapter2.swipeItem(i)

                                isDel = true

                                binding.clDialog.visibility = View.VISIBLE
                                binding.clDialog.findViewById<Button>(R.id.bNo).setOnClickListener {
                                    adapter2.swipeUndo()
                                    binding.clDialog.visibility = View.GONE
                                }
                                binding.clDialog.findViewById<Button>(R.id.bYes)
                                    .setOnClickListener {
                                        binding.clDialog.visibility = View.GONE
                                    }
                                break
                            }
                        }

                        if (!isDel) {
                            toast("Выберите миссию для удаления")
                        }
                    }
                }

                super.onOptionsItemSelected(menuItem)
            }
        }

        binding.toolbar2.apply {
            val onColor = ColorDrawable(resources.getColor(R.color.slider_on, null))

            val menuVisible = menu.findItem(R.id.visibleItem).actionView
            menuVisible.setOnClickListener {
                if (menuVisible.background == onColor) {
                    menuVisible.background =
                        ColorDrawable(resources.getColor(R.color.opasity0, null))
                } else {
                    menuVisible.background = onColor

                    toast("Нажата кнопка фильтрации видимых на карте элементов списка")
                }
            }

            val menuFilter = menu.findItem(R.id.filterItem).actionView
            menuFilter.setOnClickListener {
                if (menuFilter.background == onColor) {
                    menuFilter.background =
                        ColorDrawable(resources.getColor(R.color.opasity0, null))
                } else {
                    menuFilter.background = onColor

                    toast("Нажата кнопка включения режима «показывать на карте только миссию под пальцем при проведении пальцем (вверх-вниз) по списку»")
                }
            }

            findViewById<Switch>(R.id.mySwitch)
                .setOnCheckedChangeListener { _, b ->
                    if (b)
                        toast("Переключатель видимости всех миссий включен")
                    else
                        toast("Переключатель видимости всех миссий выключен")
                }
        }

        binding.etSearch.setOnClickListener {
            Log.d("MyTest", "-")
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
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}