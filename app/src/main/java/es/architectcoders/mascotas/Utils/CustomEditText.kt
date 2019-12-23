package es.architectcoders.mascotas.Utils

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class CustomEditText(context: Context, attrs: AttributeSet?) : TextView(context, attrs) {}

fun customClick(view:CustomEditText){
    view.setOnTouchListener(view,{view.setOnTouchListener { view, event ->true  }})
}

private fun CustomEditText.setOnTouchListener(view: CustomEditText, function: () -> Unit) {
    view.setOnTouchListener { v, event ->
        val inType: Int = view.getInputType() // backup the input type
        view.setInputType(InputType.TYPE_NULL) // disable soft input
        view.onTouchEvent(event) // call native handler
        view.setInputType(inType) // restore input type
        true // consume touch even

    }
}
