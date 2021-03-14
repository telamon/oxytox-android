package se.decentlabs.oxytox.ui.party

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import se.decentlabs.oxytox.R

class PartyFragment : Fragment() {

    private lateinit var partyViewModel: PartyViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        partyViewModel =
                ViewModelProvider(this).get(PartyViewModel::class.java)
        val root = inflater.inflate(R.layout.party, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        partyViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}