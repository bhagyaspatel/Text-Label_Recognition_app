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
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.synthetic.main.fragment_text_recognition.*

class TextRecognitionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_recognition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recognizeBtn.setOnClickListener{
            recognizeText ((sampleImg.drawable as BitmapDrawable).bitmap)
        }
    }

    private fun recognizeText(bitmap : Bitmap) {
        recognizeBtn.isEnabled = false
        recognizedTextArea.text = ""

        val image = InputImage.fromBitmap(bitmap, 0)
        val detector = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        detector.process(image)
            .addOnSuccessListener { text ->
                parseRecognizedText(text)
            }
            .addOnFailureListener{ ex ->
                Toast.makeText(requireActivity(), "Error Occurred: ${ex.message} ", Toast.LENGTH_SHORT).show()
            }
        recognizeBtn.isEnabled = true
    }

    private fun parseRecognizedText(result: Text) {
//        recognizedTextArea.text = result.text ..one way to do
        for (block in result.textBlocks){
            recognizedTextArea.append("${block.text}\n")

//            for (line in block.lines){
//                recognizedTextArea.append(line.text)
//
////                for (element in line.elements)
////                    recognizedTextArea.append(element.text)
//            }
        }
    }
}