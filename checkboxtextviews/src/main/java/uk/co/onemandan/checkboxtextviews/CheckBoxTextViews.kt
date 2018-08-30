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
import android.view.LayoutInflater

/*
 * Converts a String array via an @array resource into selectable/deselectable views that act as a
 * CheckBox.  Selected views are returned via a String ArrayList, either via the listener or public
 * function.
 * @param Context
 * @param AttributeSet
 */
class CheckBoxTextViews(context: Context, attrs: AttributeSet): LinearLayout(context, attrs),
    View.OnClickListener {

    init {
        init(attrs)
    }

    private var _flexbox: FlexboxLayout?    = null  //CheckBox item host layout.  Flexbox is set up
                                                    //so that items flow to next line and are
                                                    //centered

    private var _defaultBackgroundColour    = 0     //Deselected CheckBox item background colour
    private var _defaultTextColour          = 0     //Deselected CheckBox item text colour
    private var _selectedBackgroundColour   = 0     //Selected CheckBox item background colour
    private var _selectedTextColour         = 0     //Selected CheckBox item text clour

    private var _defaultSelected            = false //Whether or not the CheckBox items are
                                                    //initially selected

    private var _selectedItems: ArrayList<String>? = null
    private var _selectedItemListener: CheckBoxTextViewsListener? = null

    override fun onClick(v: View?) {
        selectView(v)
    }

    /*
     * Inflate and retrieve the CheckBox item host layout.  Handle attribute resources.
     * @param AttributeSet passed to HandleAttributes
     */
    private fun init(attrs: AttributeSet){
        val root        = LayoutInflater.from(context).inflate(R.layout.checkbox_item_host,
                this, true)

        _flexbox        = root.rootFlexboxLayout
        _selectedItems  = ArrayList()

        handleDefaultAttributes()
        handleAttributes(attrs)
    }

    /*
     * Set attributes to initial default values.  These are the values that are used if none are
     * supplied via the View.
     */
    private fun handleDefaultAttributes(){
        val themeAttrs  = intArrayOf(R.attr.colorAccent)
        val ta          = context.theme.obtainStyledAttributes(themeAttrs)

        _selectedBackgroundColour   = ta.getColor(0, 0)
        _selectedTextColour         = ContextCompat.getColor(context, R.color.colorTextAccent)
        _defaultBackgroundColour    = ContextCompat.getColor(context, R.color.TintBlack10)
        _defaultTextColour          = ContextCompat.getColor(context, R.color.colorTextPrimary)

        ta.recycle()
    }

    /*
     * Attempt to obtain the supplied resources via the View.  The resources that the user can
     * provide are the CheckBox items deselected/selected background colour and text colour, whether
     * the items are initially selected and the String array to create the CheckBox items from.
     * @param AttributeSet used to obtain the styleable attributes from the View
     */
    private fun handleAttributes(attrs: AttributeSet){
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

        //Retrieve the items via the provided String array and create the CheckBox items
        val itemsId = ta.getResourceId(R.styleable.CheckBoxTextViews_cbtv_items, 0)
        if(itemsId != 0){
            val items: Array<String> = resources.getStringArray(itemsId)
            for(item in items){
                createCheckBoxItem(item)
            }
        }
    }

    /*
     * A CheckBox item is a rounded rectangle view with centered text, that is selectable.  Inflate
     * the CheckBox item layout and set the title and and root Views corresponding to the String and
     * attribute resources.
     * @param String the CheckBox item text
     */
    private fun createCheckBoxItem(item: String){
        val checkBoxItem: View  = LayoutInflater.from(context).inflate(R.layout.checkbox_item,
                _flexbox, false)

        val root: ClipView      = checkBoxItem.rootClipView
        val title: TextView     = checkBoxItem.itemTextView

        title.text = item
        title.setTextColor(_defaultTextColour)

        setBackgroundColour(root, _defaultBackgroundColour)
        checkBoxItem.setOnClickListener(this)

        //During inflation, 'attachToRoot' can be specified, however, Views need to be added to the
        //FlexBox individually so that the specified rules are used (flex to new line, centered)
        _flexbox!!.addView(checkBoxItem)

        //Users can specify whether or not the CheckBox items should initially be selected
        if(_defaultSelected){
            selectView(checkBoxItem)
        }
    }

    /*
     * Sets the CheckBox items tag to reflect whether or not it has been selected.  Set the CheckBox
     * items background colour and text colour corresponding to its selection status.
     * @param View? the CheckBox items root layout
     */
    private fun selectView(v: View?){
        //Update whether or not the item has been selected via the tag
        if(v!!.tag == null){
            //If first time setting the tag, it must have been selected, so it must be true
            v.tag = true
        } else {
            v.tag = !(v.tag as Boolean)
        }

        val title   = v.itemTextView //Used to change text colour
        val root    = v.rootClipView //Used to change background colour

        //Update text and background colour of the CheckBox item depending on whether or not it has
        //been selected
        if(v.tag as Boolean){
            title.setTextColor(_selectedTextColour)
            setBackgroundColour(root, _selectedBackgroundColour)

            _selectedItems!!.add(title.text.toString())
        } else {
            title.setTextColor(_defaultTextColour)
            setBackgroundColour(root, _defaultBackgroundColour)

            _selectedItems!!.remove(title.text.toString())
        }

        //Call the listener
        _selectedItemListener?.onItemSelected(_selectedItems!!)
    }

    //HELPERS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun setBackgroundColour(view: View, colour: Int){
        view.background.colorFilter = PorterDuffColorFilter(colour,
                PorterDuff.Mode.SRC_IN)
    }

    private fun dpToPx(dp: Float): Int {
        return Math.round(dp * (Resources.getSystem().displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    //ACCESSORS
    ////////////////////////////////////////////////////////////////////////////////////////////////

    fun getSelectedItems(): ArrayList<String> {
        return _selectedItems!!
    }

    fun setSelectedItemListener(listener: CheckBoxTextViewsListener){
        _selectedItemListener = listener
    }
}
