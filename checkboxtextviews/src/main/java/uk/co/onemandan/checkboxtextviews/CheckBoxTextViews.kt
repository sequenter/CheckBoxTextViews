package uk.co.onemandan.checkboxtextviews

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.widget.LinearLayout
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.checkbox_item_host.view.*
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.checkbox_item.view.*
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics

class CheckBoxTextViews(context: Context, attrs: AttributeSet): LinearLayout(context, attrs),
    View.OnClickListener {

    init {
        init(context, attrs)
    }

    private var _flexbox: FlexboxLayout?    = null

    private var _defaultBackgroundColour    = 0
    private var _defaultTextColour          = 0
    private var _selectedBackgroundColour   = 0
    private var _selectedTextColour         = 0

    private var _defaultSelected            = false

    private var _selectedItems: ArrayList<String>? = null
    private var _selectedItemListener: CheckBoxTextViewsListener? = null

    override fun onClick(v: View?) {
        selectView(v)
    }

    private fun init(context: Context, attrs: AttributeSet){
        val root = inflate(context, R.layout.checkbox_item_host, this)
        _flexbox = root.rootFlexboxLayout

        _selectedItems = ArrayList()

        handleDefaultAttributes(context)
        handleAttributes(context, attrs)
    }

    private fun handleDefaultAttributes(context: Context){
        val themeAttrs  = intArrayOf(R.attr.colorAccent)
        val ta          = context.theme.obtainStyledAttributes(themeAttrs)

        _selectedBackgroundColour   = ta.getColor(0, 0)
        _selectedTextColour         = ContextCompat.getColor(context, R.color.colorTextAccent)
        _defaultBackgroundColour    = ContextCompat.getColor(context, R.color.TintBlack10)
        _defaultTextColour          = ContextCompat.getColor(context, R.color.colorTextPrimary)

        ta.recycle()
    }

    private fun handleAttributes(context: Context, attrs: AttributeSet){
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.CheckBoxTextViews,
                0, 0)

        _defaultBackgroundColour    = ta.getColor(
                R.styleable.CheckBoxTextViews_cbtv_default_background_color, _defaultBackgroundColour)
        _defaultTextColour          = ta.getColor(
                R.styleable.CheckBoxTextViews_cbtv_default_text_color, _defaultTextColour)
        _selectedBackgroundColour   = ta.getColor(
                R.styleable.CheckBoxTextViews_cbtv_selected_background_color, _selectedBackgroundColour)
        _selectedTextColour         = ta.getColor(
                R.styleable.CheckBoxTextViews_cbtv_selected_text_color, _selectedTextColour)
        _defaultSelected            = ta.getBoolean(
                R.styleable.CheckBoxTextViews_cbtv_selected, false)

        val itemsId = ta.getResourceId(R.styleable.CheckBoxTextViews_cbtv_items, 0)
        if(itemsId != 0){
            val items: Array<String> = resources.getStringArray(itemsId)
            for(item in items){
                createCheckBoxItem(item)
            }
        }
    }

    private fun createCheckBoxItem(item: String){
        val checkBoxItem: View  = inflate(context, R.layout.checkbox_item, null)
        val root: ClipView      = checkBoxItem.rootClipView
        val title: TextView     = checkBoxItem.itemTextView

        title.text = item
        title.setTextColor(_defaultTextColour)

        setBackgroundColour(root, _defaultBackgroundColour)

        val layoutParams = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT)

        layoutParams.setMargins(dpToPx(8f), dpToPx(8f), dpToPx(8f), 0)
        checkBoxItem.layoutParams = layoutParams
        checkBoxItem.setOnClickListener(this)

        _flexbox!!.addView(checkBoxItem)

        if(_defaultSelected){
            selectView(checkBoxItem)
        }
    }

    private fun setBackgroundColour(view: View, colour: Int){
        view.background.colorFilter = PorterDuffColorFilter(colour,
                PorterDuff.Mode.SRC_IN)
    }

    private fun selectView(v: View?){
        //Update whether or not the item has been selected via the tag
        if(v!!.tag == null){
            v.tag = true
        } else {
            v.tag = !(v.tag as Boolean)
        }

        val title   = v.itemTextView
        val root    = v.rootClipView

        if(v.tag as Boolean){
            title.setTextColor(_selectedTextColour)
            setBackgroundColour(root, _selectedBackgroundColour)

            _selectedItems!!.add(title.text.toString())
        } else {
            title.setTextColor(_defaultTextColour)
            setBackgroundColour(root, _defaultBackgroundColour)

            _selectedItems!!.remove(title.text.toString())
        }

        _selectedItemListener?.onItemSelected(_selectedItems!!)
    }

    fun getSelectedItems(): ArrayList<String> {
        return _selectedItems!!
    }

    fun setSelectedItemListener(listener: CheckBoxTextViewsListener){
        _selectedItemListener = listener
    }

    private fun dpToPx(dp: Float): Int {
        return Math.round(dp * (Resources.getSystem().displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
}
