package com.example.securenote.presentation.screen.home.components

import android.graphics.Paint
import android.text.Layout
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securenote.R
import com.example.securenote.domain.enum.DateRange
import com.example.securenote.domain.model.LineChartDataPoint
import com.example.securenote.domain.model.PieData
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.common.shape.markerCorneredShape
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.LayeredComponent
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun AnalyticsPage(
    lineData: List<LineChartDataPoint>,
    pieData: List<PieData>,
    onLineChartChangeRange: (DateRange) -> Unit,
    currentDateRange: DateRange,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        LineChartHeader(
            onLineChartChangeRange = { onLineChartChangeRange(it) },
            currentDateRange = currentDateRange
        )
        LineChart(data = lineData)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            stringResource(R.string.home_tags_overview_title),
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary),
            fontWeight = FontWeight.Bold
        )
        PieChart(pieData = pieData)
        Spacer(Modifier.height(100.dp))
    }
}

private val MarkerValueFormatter = DefaultCartesianMarker.ValueFormatter.default()

@Composable
internal fun rememberMarker(
    valueFormatter: DefaultCartesianMarker.ValueFormatter =
        DefaultCartesianMarker.ValueFormatter.default(),
    showIndicator: Boolean = true,
): CartesianMarker {
    val labelBackgroundShape = markerCorneredShape(CorneredShape.Corner.Rounded)
    val labelBackground =
        rememberShapeComponent(
            fill = fill(MaterialTheme.colorScheme.background),
            shape = labelBackgroundShape,
            strokeThickness = 1.dp,
            strokeFill = fill(MaterialTheme.colorScheme.outline),
        )
    val label =
        rememberTextComponent(
            color = MaterialTheme.colorScheme.onSurface,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = insets(8.dp, 4.dp),
            background = labelBackground,
            minWidth = TextComponent.MinWidth.fixed(40f),
        )
    val indicatorFrontComponent =
        rememberShapeComponent(fill(MaterialTheme.colorScheme.surface), CorneredShape.Pill)
    val guideline = rememberAxisGuidelineComponent()
    return rememberDefaultCartesianMarker(
        label = label,
        valueFormatter = valueFormatter,
        indicator =
            if (showIndicator) {
                { color ->
                    LayeredComponent(
                        back = ShapeComponent(fill(color.copy(alpha = 0.15f)), CorneredShape.Pill),
                        front =
                            LayeredComponent(
                                back = ShapeComponent(
                                    fill = fill(color),
                                    shape = CorneredShape.Pill
                                ),
                                front = indicatorFrontComponent,
                                padding = insets(5.dp),
                            ),
                        padding = insets(10.dp),
                    )
                }
            } else {
                null
            },
        indicatorSize = 36.dp,
        guideline = guideline,
    )
}

@Composable
fun LineChart(data: List<LineChartDataPoint>) {
    val labels = data.map { it.label }
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(data) {
        val yValues = data.map { it.value.toFloat() }
        val xValues = data.indices.map { it.toFloat() }

        modelProducer.runTransaction {
            lineSeries {
                series(
                    x = xValues,
                    y = yValues
                )
            }
        }
    }

    val label = rememberAxisLabelComponent(color = MaterialTheme.colorScheme.onPrimary)
    val scrollState = rememberVicoScrollState(initialScroll = Scroll.Absolute.End)
    val zoomState = rememberVicoZoomState()
    val guideLineConfig =
        rememberAxisGuidelineComponent(fill = fill(MaterialTheme.colorScheme.onPrimary))
    val lineConfig = rememberLineComponent(
        fill = fill(MaterialTheme.colorScheme.onPrimary)
    )
    val lineColor = MaterialTheme.colorScheme.primary
    val valueLineProvider = LineCartesianLayer.LineProvider.series(
        LineCartesianLayer.rememberLine(
            fill = LineCartesianLayer.LineFill.single(fill(lineColor)),
        ),
    )


    val bottomAxisFormatter = CartesianValueFormatter { _, value, _ ->
        val index = value.toInt()
        if (index in labels.indices) {
            labels[index]
        } else {
            "-"
        }
    }


    CartesianChartHost(
        chart = rememberCartesianChart(

            rememberLineCartesianLayer(
                lineProvider = valueLineProvider,

                ),
            startAxis = VerticalAxis.rememberStart(
                line = lineConfig,
                label = label,
                guideline = guideLineConfig
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                line = lineConfig,
                label = label,
                guideline = guideLineConfig,
                valueFormatter = bottomAxisFormatter
            ),
            marker = rememberMarker(MarkerValueFormatter),
        ),
        modelProducer = modelProducer,
        scrollState = scrollState,
        zoomState = zoomState,
        placeholder = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chart_holder),
                    contentDescription = null, modifier = Modifier.size(48.dp)
                )
                Text("Content not found!!!")
            }
        },
    )
}


@Composable
fun AnimatedPieChart(
    pieData: List<PieData>,
    modifier: Modifier = Modifier,
) {
    val data = pieData.map { it.value }
    val colors = pieData.map { it.noteType.color }
    val total = data.sum()
    val sweepPercentages = data.map { it / total }
    val animatedSweepAngles = remember {
        sweepPercentages.map { Animatable(0f) }
    }
    val density = LocalDensity.current
    var showLabels by remember { mutableStateOf(false) }

    LaunchedEffect(pieData) {
        animatedSweepAngles.forEachIndexed { index, anim ->
            anim.animateTo(
                targetValue = sweepPercentages[index] * 360f,
                animationSpec = tween(
                    durationMillis = 1200,
                    easing = FastOutSlowInEasing
                )
            )
        }
        showLabels = true
    }

    var selectedIndex by remember { mutableIntStateOf(-1) }

    Canvas(
        modifier = modifier
            .padding(top = 12.dp, bottom = 24.dp)
            .size(250.dp),
    ) {
        var startAngle = -90f
        val radius = size.minDimension / 2
        val center = size.center

        for (i in data.indices) {
            val sweep = animatedSweepAngles[i].value
            drawArc(
                color = if (i == selectedIndex) colors[i].copy(alpha = 0.7f) else colors[i],
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = true
            )

            val angleRad = Math.toRadians((startAngle + sweep / 2).toDouble())
            val labelX = center.x + cos(angleRad).toFloat() * radius * 0.6f
            val labelY = center.y + sin(angleRad).toFloat() * radius * 0.6f
            val value = (sweep / 360f * 100).roundToInt()
            if (showLabels && value >= 5) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${value}%",
                    labelX,
                    labelY,
                    Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = with(density) { 14.sp.toPx() }
                        textAlign = Paint.Align.CENTER
                    }
                )
            }

            startAngle += sweep
        }
    }

    if (selectedIndex >= 0) {
        Text(
            text = "Selected: ${(data[selectedIndex] / total * 100).roundToInt()}%",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 16.dp)
        )
    }
}


@Composable
fun PieChart(pieData: List<PieData>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedPieChart(pieData = pieData)
        PieChartTagLabels(pieData = pieData)
    }
}

@Composable
fun PieChartTagLabels(
    pieData: List<PieData>,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        pieData.forEachIndexed { index, value ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            pieData[index % pieData.size].noteType.color,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                )
                Text(text = "${pieData[index].noteType.typeName}: ${pieData[index].value.roundToInt()}%")
            }
        }
    }
}

@Composable
fun LineChartHeader(
    onLineChartChangeRange: (DateRange) -> Unit,
    currentDateRange: DateRange,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.home_quantity_analysis_title),
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary),
            fontWeight = FontWeight.Bold
        )
        Box {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DateRange.entries.forEachIndexed { index, value ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                value.label,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxSize(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            expanded = false
                            onLineChartChangeRange(value)
                        },
                        colors = if (value == currentDateRange) MenuDefaults.itemColors()
                            .copy(textColor = MaterialTheme.colorScheme.primary) else MenuDefaults.itemColors()
                    )
                }
            }
            Icon(
                painter = painterResource(R.drawable.ic_document_filter),
                contentDescription = null,
                modifier = Modifier.clickable {
                    expanded = true
                },
            )
        }
    }
}