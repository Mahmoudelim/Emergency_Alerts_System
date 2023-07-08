package com.example.emergency_alert_system.Dialogesandmaps

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.emergency_alert_system.user.AlertMaking.MakeAlert
import com.example.emergencyalertsystem.R
import kotlinx.android.synthetic.main.fragment_emergency_dialog.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmergencyDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class EmergencyDialogFragment : DialogFragment() {

    private lateinit var countDownTimer: CountDownTimer
 var alert:MakeAlert= MakeAlert()
    companion object {
        fun newInstance(): EmergencyDialogFragment {
            return EmergencyDialogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_emergency_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the button click listeners
        confirm_button.setOnClickListener {
            // Handle positive button click
            alert.AlertToEp()
            alert.AlertToEp()
            dismiss()
        }

        cancel_button.setOnClickListener {
            // Handle negative button click
            dismiss()
        }

        // Set up the countdown timer
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the dialog message with the remaining time
                val remainingTime = millisUntilFinished / 1000
                message_textview.text = "Confirm as an emergency situation or cancel? it will be considerd as an emergency situation  in $remainingTime seconds."
            }

            override fun onFinish() {

                // Dismiss the dialog when the countdown is finished
                alert.AlertToEp()
                alert.AlertToEp()
                dismiss()
            }
        }

        // Start the countdown timer
        countDownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Cancel the countdown timer if the dialog is destroyed
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }

}