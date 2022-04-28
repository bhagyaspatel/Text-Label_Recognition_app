package com.example.ml_kit_text_recognition

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.android.synthetic.main.fragment_label_recognition.*
import kotlinx.android.synthetic.main.fragment_main.*

class LabelRecognitionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_label_recognition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recognizeLabelBtn.setOnClickListener{
            recognizeLabels ((sampleImgLabel.drawable as BitmapDrawable).bitmap)
        }
    }

    private fun recognizeLabels(bitmap: Bitmap) {
        recognizeLabelBtn.isEnabled = false
        recognizedLabelArea.text = ""

        val image = InputImage.fromBitmap(bitmap, 0)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        labeler.process(image)
            .addOnSuccessListener { list ->
                parseRecognizedLabels(list)
                recognizeLabelBtn.isEnabled = true ;
            }
            .addOnFailureListener{ ex ->
                Toast.makeText(requireActivity(), "Error Occurred: ${ex.message} ", Toast.LENGTH_SHORT).show()
            }
    }

    private fun parseRecognizedLabels(labels : List<ImageLabel>) {
        for (label in labels){
            val text = label.text
            val confidence = label.confidence

            recognizedLabelArea.append("Item: $text Confidence: $confidence\n")
        }
    }

}