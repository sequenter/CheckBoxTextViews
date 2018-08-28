package uk.co.onemandan.checkboxtextviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class ClipView : FrameLayout {

    private var _context        : Context?  = null
    private var _cornerRadius   : Float     = 0f

    private val _rect       = Rect()
    private val _rectF      = RectF()
    private val _clipPath   = Path()

    constructor(context: Context) : super(context) {
        _context = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        _context = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        _context = context
        init()
    }

    private fun init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        _cornerRadius = _context!!.resources.getDimension(R.dimen.corner_radius)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.getClipBounds(_rect)

        _rectF.set(_rect)
        _clipPath.reset()
        _clipPath.addRoundRect(_rectF, _cornerRadius, _cornerRadius, Path.Direction.CW)

        canvas.clipPath(_clipPath)

        super.onDraw(canvas)
    }
}
