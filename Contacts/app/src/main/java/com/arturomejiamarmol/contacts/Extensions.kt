import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast

//ViewGroup
operator fun ViewGroup.get(index: Int) = this.getChildAt(index)
operator fun ViewGroup.plusAssign(child: View) = this.addView(child)
operator fun ViewGroup.minusAssign(child: View) = this.removeView(child)

inline fun ViewGroup.children() = object : Iterable<View> {
    override fun iterator() = object : Iterator<View> {
        var index = 0
        override fun hasNext() = index < childCount
        override fun next() = get(index++)
    }
}

//EditText
fun EditText.isEmptyWithTrim() = text.toString().trim().isEmpty()
fun EditText.getStringValue() = text.toString()
fun EditText.clear() { setText("") }

//Activity
fun Activity.showToast(text: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, duration).show()
}