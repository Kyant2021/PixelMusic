package com.kyant.pixelmusic.ui.visualizer

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.kyant.pixelmusic.data.Media
import com.kyant.pixelmusic.media.FFTAudioProcessor
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.pow

@Composable
fun FrequencyVisualizer(modifier: Modifier = Modifier) {
    // Taken from: https://en.wikipedia.org/wiki/Preferred_number#Audio_frequencies
    val frequencyBandLimits = arrayOf(
        20, 25, 32, 40, 50, 63, 80, 100, 125, 160, 200, 250, 315, 400, 500, 630,
        800, 1000, 1250, 1600, 2000, 2500, 3150, 4000, 5000, 6300, 8000, 10000,
        12500, 16000, 20000
    )

    val bands = frequencyBandLimits.size
    val size = FFTAudioProcessor.SAMPLE_SIZE / 2
    val maxConst = 25_000 // Reference max value for accum magnitude

    var finalFft by remember { mutableStateOf(FloatArray(size)) }
    Media.fftAudioProcessor.listener = object : FFTAudioProcessor.FFTListener {
        override fun onFFTReady(sampleRateHz: Int, channelCount: Int, fft: FloatArray) {
            synchronized(finalFft) {
                fft.copyInto(finalFft, 0, 2, size)
                finalFft += floatArrayOf(0f, 0f)
            }
        }
    }

    // We average out the values over 3 occurences (plus the current one), so big jumps are smoothed out
    val smoothingFactor = 3
    val previousValues = FloatArray(bands * smoothingFactor)

    val fftPath = Path()

    Canvas(modifier) {
        val (width, height) = this.size
        // Clear the previous drawing on the screen

        // Set up counters and widgets
        var currentFftPosition = 0
        var currentFrequencyBandLimitIndex = 0
        fftPath.reset()
        fftPath.moveTo(0f, height)
        var currentAverage = 0f

        // Iterate over the entire FFT result array
        while (currentFftPosition < size) {
            var accum = 0f

            // We divide the bands by frequency.
            // Check until which index we need to stop for the current band
            val nextLimitAtPosition =
                floor(frequencyBandLimits[currentFrequencyBandLimitIndex] / 20_000.toFloat() * size).toInt()

            synchronized(finalFft) {
                // Here we iterate within this single band
                for (j in 0 until (nextLimitAtPosition - currentFftPosition) step 2) {
                    // Convert real and imaginary part to get energy
                    val raw = (finalFft[currentFftPosition + j].toDouble().pow(2.0) +
                            finalFft[currentFftPosition + j + 1].toDouble().pow(2.0)).toFloat()

                    // Hamming window (by frequency band instead of frequency, otherwise it would prefer 10kHz, which is too high)
                    // The window mutes down the very high and the very low frequencies, usually not hearable by the human ear
                    val m = bands / 2
                    val windowed =
                        raw * (0.54f + 0.46f * cos((currentFrequencyBandLimitIndex - m) * Math.PI / (m + 1))).toFloat()
                    accum += windowed
                }
            }
            // A window might be empty which would result in a 0 division
            if (nextLimitAtPosition - currentFftPosition != 0) {
                accum /= (nextLimitAtPosition - currentFftPosition)
            } else {
                accum = 0.0f
            }
            currentFftPosition = nextLimitAtPosition

            // Here we do the smoothing
            // If you increase the smoothing factor, the high shoots will be toned down, but the
            // 'movement' in general will decrease too
            var smoothedAccum = accum
            for (i in 0 until smoothingFactor) {
                smoothedAccum += previousValues[i * bands + currentFrequencyBandLimitIndex]
                if (i != smoothingFactor - 1) {
                    previousValues[i * bands + currentFrequencyBandLimitIndex] =
                        previousValues[(i + 1) * bands + currentFrequencyBandLimitIndex]
                } else {
                    previousValues[i * bands + currentFrequencyBandLimitIndex] = accum
                }
            }
            smoothedAccum /= (smoothingFactor + 1) // +1 because it also includes the current value

            // We display the average amplitude with a vertical line
            currentAverage += smoothedAccum / bands


            val leftX = width * (currentFrequencyBandLimitIndex / bands.toFloat())
            val rightX = leftX + width / bands.toFloat()

            val barHeight =
                (height * (smoothedAccum / maxConst.toDouble()).coerceAtMost(1.0).toFloat())
            val top = height - barHeight

            drawRect(
                Color(0x20000000),
                Offset(leftX, top),
                Size(rightX - leftX + 100f, height + 100f),
                style = Fill
            )
            drawRect(
                Color(0x60000000),
                Offset(leftX, top),
                Size(rightX - leftX, height),
                style = Stroke(1f)
            )

            fftPath.lineTo(
                (leftX + rightX) / 2,
                top
            )

            currentFrequencyBandLimitIndex++
        }

        drawPath(fftPath, Color.Blue)

        drawLine(
            Color(0x1976D2),
            Offset(0f, height * (1 - (currentAverage / maxConst))),
            Offset(width, height * (1 - (currentAverage / maxConst))),
            2f
        )
    }
}