package se.decentlabs.oxytox.ui.booth

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import se.decentlabs.oxytox.R
import se.decentlabs.oxytox.TAG

class BoothFragment : Fragment() {

    private lateinit var boothViewModel: BoothViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        boothViewModel =
                ViewModelProvider(this).get(BoothViewModel::class.java)
        val root = inflater.inflate(R.layout.booth, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        boothViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val recordButton: Button = root.findViewById(R.id.button_record)
        recordButton.setOnClickListener { dispatchTakeVideoIntent() }
        return root
    }

    private fun dispatchTakeVideoIntent() {
        Log.d(TAG, "dispatchTakeVideoIntent: fetching manager")
        val packageManager = activity?.packageManager ?: return
        Log.d(TAG, "dispatchTakeVideoIntent: starting intent")
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, Companion.REQUEST_VIDEO_CAPTURE)
            }
        }
    }
    companion object {
        const val REQUEST_VIDEO_CAPTURE = 1

    }
}