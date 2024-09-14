package model

import android.media.audiofx.AudioEffect.Descriptor
import java.util.Date

data class item(

    val id: Int,
    val name:String,
    val description:String,
    val date: Date,
    val price: Float,
    val money: Float

    )