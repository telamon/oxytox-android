package se.decentlabs.oxytox.ui.booth

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import se.decentlabs.oxytox.R
import se.decentlabs.oxytox.REQUEST_VIDEO_CAPTURE
import se.decentlabs.oxytox.TAG

private const val PERMISSIONS_REQUEST_CODE = 10
private val PERMISSIONS_REQUIRED = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO)

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
        recordButton.setOnClickListener { ensurePermissions() }
        return root
    }

    // Ref https://github.com/android/camera-samples/blob/main/Camera2Video/app/src/main/java/com/example/android/camera2/video/fragments/PermissionsFragment.kt
    private fun ensurePermissions() {
        if (hasPermissions(requireContext())) dispatchTakeVideoIntent()
        else {
            // Request camera-related permissions
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
        }
    }
    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) dispatchTakeVideoIntent()
            else {
                Toast.makeText(context, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun dispatchTakeVideoIntent() {
        Log.d(TAG, "dispatchTakeVideoIntent: fetching manager")
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { vIntent ->
            Log.d(TAG, "dispatchTakeVideoIntent: also Intent")
            startActivityForResult(vIntent, REQUEST_VIDEO_CAPTURE)
            val pm = activity?.packageManager
            /*
            vIntent.resolveActivity(packageManager)?.also {
                Log.d(TAG, "dispatchTakeVideoIntent: startActivityForResult")
                startActivityForResult(vIntent, REQUEST_VIDEO_CAPTURE)
            }*/
        }
    }
    companion object {
        /** Convenience method used to check if all permissions required by this app are granted */
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}