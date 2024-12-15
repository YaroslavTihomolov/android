package nsu.fit.tikhomolov.lab3

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Currency(
    val id: String?,
    val name: String,
    val charCode: String,
    var value: String?,
    val image: Int?
) : Parcelable