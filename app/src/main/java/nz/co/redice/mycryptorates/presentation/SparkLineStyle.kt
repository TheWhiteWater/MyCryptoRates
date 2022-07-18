package nz.co.redice.mycryptorates.presentation

import android.app.Application
import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.qualifiers.ApplicationContext
import nz.co.redice.mycryptorates.R
import javax.inject.Inject

class SparkLineStyle @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun styleLineChart(lineChart: LineChart) = lineChart.apply {
        axisLeft.isEnabled = false
        axisRight.isEnabled = true

        xAxis.apply {
            isGranularityEnabled = true
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
        }

        legend.isEnabled = false
        setTouchEnabled(false)
        isDragEnabled = false
        setScaleEnabled(false)
        setPinchZoom(false)
        description.apply {
            isEnabled = false
            text = "Day"
            textSize = 12f
        }

    }

    fun styleDataSet(context: Context, dataSet: LineDataSet) = dataSet.apply {
        setDrawValues(false)
        setDrawCircles(false)
        lineWidth = 2f
        isHighlightEnabled = true
        mode = LineDataSet.Mode.CUBIC_BEZIER
        color = ContextCompat.getColor(context, R.color.RED_LINE)
//        setDrawFilled(true)
//        fillDrawable = ContextCompat.getDrawable(context, R.drawable.red_fade)
    }
}