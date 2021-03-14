package se.decentlabs.oxytox.ui.booth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import se.decentlabs.oxytox.R

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
        return root
    }
}