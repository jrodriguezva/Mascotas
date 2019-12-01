package es.architectcoders.mascotas.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import es.architectcoders.mascotas.R
import kotlinx.android.synthetic.main.review_rating.view.*

class ReviewRating : LinearLayout {
    constructor(context: Context) : this(context, null) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
        if (attrs != null) {
            val a = getContext().obtainStyledAttributes(attrs, R.styleable.ReviewRating, 0, 0)
            val numberstars = a.getInt(R.styleable.ReviewRating_numberStars, 1)
            setNumberStars(numberstars)
            a.recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
        if (attrs != null) {
            val a = getContext().obtainStyledAttributes(attrs, R.styleable.ReviewRating, 0, 0)
            val numberstars = a.getInt(R.styleable.ReviewRating_numberStars, 1)
            setNumberStars(numberstars)
            a.recycle()
        }
    }

    private fun init() {
        View.inflate(context, R.layout.review_rating,  this)
    }

    private fun setNumberStars(number:Int){
        when (number) {
            1 -> {
                star1.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star2.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
                star3.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
                star4.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
                star5.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
            }
            2 -> {
                star1.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star2.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star3.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
                star4.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
                star5.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
            }
            3 -> {
                star1.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star2.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star3.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star4.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
                star5.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
            }
            4 -> {
                star1.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star2.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star3.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star4.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star5.setImageDrawable(context.getDrawable(R.drawable.ic_star_disable))
            }
            else -> {
                star1.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star2.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star3.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star3.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
                star3.setImageDrawable(context.getDrawable(R.drawable.ic_star_enable))
            }

        }
    }
}