package uk.co.onemandan.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.onemandan.checkboxtextviews.CheckBoxTextViewsListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsCheckBoxTextViews.setSelectedItemListener(object: CheckBoxTextViewsListener{
            override fun onItemSelected(items: List<String>) {
                selectedItemsTextView.text = items.joinToString(", ")
            }
        })
    }
}
