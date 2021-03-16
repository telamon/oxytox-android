package se.decentlabs.oxytox.ui.booth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BoothViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Photobooth"
    }
    val text: LiveData<String> = _text

    private val _videoData = MutableLiveData<ByteArray?>().apply { value = null }
    val videoData: LiveData<ByteArray?> = _videoData
}