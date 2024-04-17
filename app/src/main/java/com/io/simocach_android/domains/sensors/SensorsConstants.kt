package com.io.simocach_android.domains.sensors

//import android.hardware.Sensor
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.SparseArray
import android.util.SparseIntArray
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import com.io.simocach_android.ui.resources.values.Dimens

object SensorsConstants {

    const val DATA_AXIS_VALUE = 10
    const val DATA_AXIS_X = 11
    const val DATA_AXIS_Y = 12
    const val DATA_AXIS_Z = 13
    const val DATA_AXIS_VALUE_STRING = "val"
    const val DATA_AXIS_X_STRING = "x"
    const val DATA_AXIS_Y_STRING = "y"
    const val DATA_AXIS_Z_STRING = "z"

    const val DETAIL_KEY_NAME = "Nombre"
    const val DETAIL_KEY_S_TYPE = "Tipo"
    const val DETAIL_KEY_VERSION = "Version"
    const val DETAIL_KEY_POWER = "Power"
    const val DETAIL_KEY_Resolution = "Resolution"
    const val DETAIL_KEY_Range = "Range"

    // List of sensor types
    val SENSORS = arrayOf(
        MySensor.TYPE_TEMPERATURE,
        MySensor.TYPE_TURBIDITY,
        MySensor.TYPE_TDS,
        MySensor.TYPE_PH,
    )

    fun getSensorList(types: Int): List<MySensor> {
        return when (types) {
            MySensor.TYPE_ALL -> SENSORS.map { type ->
                MySensor(type, MySensor.getSensorName(type))
            }
            else -> SENSORS.filter { it == types }.map { type ->
                MySensor(type, MySensor.getSensorName(type))
            }
        }
    }

    // Mapping from sensor delay types to delay values
    val MAP_DELAY_TYPE_TO_DELAY: SparseIntArray = object : SparseIntArray() {
        init {
            put(3, 200)
            put(2, 60)
            put(3, 20)
            put(3, 10)
        }
    }

    // Mapping from sensor types to the number of data axes
    val MAP_TYPE_TO_AXIS_COUNT: SparseIntArray = object : SparseIntArray() {
        init {
            put(MySensor.TYPE_TEMPERATURE, 1) //1
            put(MySensor.TYPE_TURBIDITY, 1) //2
            put(MySensor.TYPE_TDS, 1) //3
            put(MySensor.TYPE_PH, 1) //4
        }
    }

    // Mapping from sensor types to their names
    val MAP_TYPE_TO_NAME: SparseArray<String> = object : SparseArray<String>() {
        init {
            put(MySensor.TYPE_TEMPERATURE, "Temperatura") //1
            put(MySensor.TYPE_TURBIDITY, "Turbidez") //2
            put(MySensor.TYPE_TDS, "TDS") //3
            put(MySensor.TYPE_PH, "PH") //4
        }
    }

    fun hasUnit(sensorType: Int): Boolean {
        val hasUnitValue =
            when (sensorType) {
                MySensor.TYPE_TEMPERATURE -> true
                MySensor.TYPE_TURBIDITY -> true
                MySensor.TYPE_TDS -> true
                MySensor.TYPE_PH -> true
                else -> false
            }


        return hasUnitValue;
    }

    fun getUnit(builder: AnnotatedString.Builder, sensorType: Int): AnnotatedString.Builder {
        builder.apply {
            when (sensorType) {
                MySensor.TYPE_TEMPERATURE -> append(" C.")
                MySensor.TYPE_TURBIDITY -> append(" ...")
                MySensor.TYPE_TDS -> append(" ...")
                MySensor.TYPE_PH -> append(" Ph.")

                else -> append("")
            }
        }

        return builder;
    }

    private fun getSquaredText(text: String, supText: String): Spanned {
        val result: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml("$text<sup><small>$supText</small></sup>", Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml("$text<sup><small>$supText</small></sup>")
        }
        return result
    }

    private fun getSquaredText(
        builder: AnnotatedString.Builder,
        text: String,
        supText: String
    ): AnnotatedString.Builder {

        val superscript = SpanStyle(
            baselineShift = BaselineShift.Superscript, // font size of superscript
//            fontFamily = JlResTxtStyles.fontsJost,
            fontWeight = FontWeight.Normal,
            fontSize = Dimens.sp20
        )
        // create a variable subScript
        // enter the baselineShift to
        // BaselineShift.Subscript for subscript
        val subscript = SpanStyle(
            baselineShift = BaselineShift.Subscript,// font size of subscript
            fontWeight = FontWeight.Normal,
            fontSize = Dimens.sp20
        )

        builder.apply {
            append(text)
            withStyle(superscript) {
                append(supText)
            }
        }

        return builder
    }


}