package com.example.navigation

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        var v: View = inflater.inflate(R.layout.fragment_second, container, false)
        val icanchor: ImageView = v.findViewById(R.id.icanchor)
        val roundingalone = AnimationUtils.loadAnimation(context, R.anim.roundingalone)
        val btnStart: Button = v.findViewById(R.id.button)
        val btnStop: Button = v.findViewById(R.id.buttonstop)
        val timeHere: Chronometer = v.findViewById(R.id.timerhere)
        val btnResume: Button = v.findViewById(R.id.btnResume)
        val textView:TextView=v.findViewById(R.id.textView2)


        val timedifference: Long = 0

        btnStop.alpha = 0F
        btnResume.alpha=0F
        textView.alpha=0F
        btnStart.setOnClickListener {
            textView.animate().alpha(1.0F).translationY(-80F).setDuration(300).start()

            btnResume.alpha=0F
            icanchor.startAnimation(roundingalone)
            btnStop.animate().alpha(1.0F).translationY(-80F).setDuration(300).start()
            btnStart.animate().alpha(0F).setDuration(300).start()
            timeHere.base = SystemClock.elapsedRealtime()
            timeHere.start()
            var timedifference: Long = 0


            btnStop.setOnClickListener {
                btnStart.text="Restart Meditation"
                btnStart.animate().alpha(1.0F).translationY(-80F).setDuration(300).start()
                btnResume.animate().alpha(1.0F).translationY(-80F).setDuration(300).start()
                icanchor.clearAnimation()
                timeHere.stop()
                btnStart.text="Restart Meditation"


                var timedifference: Long = 0
                btnResume.animate().alpha(1.0F).translationY(-80F).setDuration(300).start()
                btnStop.animate().alpha(0F).setDuration(300).start()
                timedifference = timeHere.base - SystemClock.elapsedRealtime()
                btnResume.setOnClickListener {
                    icanchor.startAnimation(roundingalone)
                    btnResume.animate().alpha(0F).setDuration(300).start()
                    btnStart.animate().alpha(0F).setDuration(300).start()
                    btnStop.animate().alpha(1.0F).translationY(-80F).setDuration(300).start()

                    timeHere.base = SystemClock.elapsedRealtime() + (timedifference)
                    timeHere.start()
                }
            }


        }
        return v


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}