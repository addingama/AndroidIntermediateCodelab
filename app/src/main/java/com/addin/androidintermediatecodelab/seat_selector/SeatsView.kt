package com.addin.androidintermediatecodelab.seat_selector

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.addin.androidintermediatecodelab.R

class SeatsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val seats: ArrayList<Seat> = arrayListOf(
        Seat(id = 1, name = "A1", isBooked = false),
        Seat(id = 2, name = "A2", isBooked = false),
        Seat(id = 3, name = "B1", isBooked = false),
        Seat(id = 4, name = "A4", isBooked = false),
        Seat(id = 5, name = "C1", isBooked = false),
        Seat(id = 6, name = "C2", isBooked = false),
        Seat(id = 7, name = "D1", isBooked = false),
        Seat(id = 8, name = "D2", isBooked = false),
    )

    var seat: Seat? = null

    private val backgroundPaint = Paint()
    private val armRestPaint = Paint()
    private val bottomSeatPaint = Paint()
    private val mBounds = Rect()
    private val numberSeatPaint = Paint(Paint.FAKE_BOLD_TEXT_FLAG)
    private val titlePaint = Paint(Paint.FAKE_BOLD_TEXT_FLAG)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (item in seats) {
            drawSeat(canvas, item)
        }

        val text = "Silakan Pilih Kursi"
        titlePaint.apply {
            textSize = 50F
        }
        canvas?.drawText(text, (width / 2F) - 197F, 100F, titlePaint)
    }

    private fun drawSeat(canvas: Canvas?, seat: Seat) {
        // Mengatur warna ketika sudah di booking
        if (seat.isBooked) {
            backgroundPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            armRestPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            bottomSeatPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            numberSeatPaint.color = ResourcesCompat.getColor(resources, R.color.black, null)
        } else {
            backgroundPaint.color = ResourcesCompat.getColor(resources, R.color.blue_500, null)
            armRestPaint.color = ResourcesCompat.getColor(resources, R.color.blue_700, null)
            bottomSeatPaint.color = ResourcesCompat.getColor(resources, R.color.blue_200, null)
            numberSeatPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
        }

        canvas?.save()

        // Background
        canvas?.translate(seat.x as Float, seat.y as Float)

        val backgroundPath = Path()
        // buat gambar kotak dulu 200x200
        backgroundPath.addRect(0F, 0F, 200F, 200F, Path.Direction.CCW)
        // buat lengkungan dengan radius 75, mulai dari tengah kotak melengkung berlawanan jarum jam
        backgroundPath.addCircle(100F, 50F, 75F, Path.Direction.CCW)
        canvas?.drawPath(backgroundPath, backgroundPaint)

        // Sandaran Tangan
        val armrestPath = Path()
        // buat kotak mulai dari pojok kiri atas, ke kanan 50 dan ke bawah 200
        armrestPath.addRect(0F, 0F, 50F, 200F, Path.Direction.CCW)
        canvas?.drawPath(armrestPath, armRestPaint)
        // set titik awal canvas mulai 150 dari kiri
        canvas?.translate(150F, 0F)
        // buat kotak yg sama, left ttp 0 karena sudah di translate
        armrestPath.addRect(0F, 0F, 50F, 200F, Path.Direction.CCW)
        canvas?.drawPath(armrestPath, armRestPaint)

        // Bagian Bawah Kursi
        // kembalikan lagi titik awal kanvas di sisi x, td +150, sekarang di -150
        canvas?.translate(-150F, 175F)
        val bottomSeatPath = Path()
        // gambar balok dengan lebar 200 dan tinggi 25
        bottomSeatPath.addRect(0F, 0F, 200F, 25F, Path.Direction.CCW)
        canvas?.drawPath(bottomSeatPath, bottomSeatPaint)

        // Menulis Nomor Kursi
        // geser titik mulainya dari paling kiri dan y nya di tarik ke titik 0 lagi (+175-175)
        canvas?.translate(0F, -175F)
        numberSeatPaint.apply {
            textSize = 50F
            numberSeatPaint.getTextBounds(seat.name, 0, seat.name.length, mBounds)
        }
        // Posisikan text di tengah2 di sumbu x biar rata kiri kanan dgn cara 100 - mBounds.centerX()
        canvas?.drawText(seat.name, 100F - mBounds.centerX(), 100F, numberSeatPaint)


        canvas?.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)

        val halfOfHeight = height / 2
        val halfOfWidth = width / 2
        var value = -600F


        for (i in 0..7) {
            if (i.mod(2) == 0) {
                seats[i].apply {
                    x = halfOfWidth - 300F // geser ke kiri 300 dari titik tengah
                    y = halfOfHeight + value // geser ke atas 600 dari titik tengah
                }
            } else {
                seats[i].apply {
                    x = halfOfWidth + 100F // geser ke kanan 100 dari titik tengah
                    y = halfOfHeight + value // geser ke atas 600 dari titik tengah
                }
                value += 300F
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val halfOfHeight = height / 2
        val halfOfWidth = width / 2

        // lebar kolom adalah 200F dengan jarak 100F dari titik tengah
        val widthColumnOne = (halfOfWidth - 300F)..(halfOfWidth - 100F)
        val widthColumnTwo = (halfOfWidth + 100F)..(halfOfWidth + 300F)

        val heightRowOne = (halfOfHeight - 600F)..(halfOfHeight - 400F)
        val heightRowTwo = (halfOfHeight - 300F)..(halfOfHeight - 100F)
        val heightRowTree = (halfOfHeight + 0F)..(halfOfHeight + 200F)
        val heightRowFour =(halfOfHeight + 300F)..(halfOfHeight + 500F)

        if (event?.action == MotionEvent.ACTION_DOWN) {
            when {
                event.x in widthColumnOne && event.y in heightRowOne -> booking(0)
                event.x in widthColumnTwo && event.y in heightRowOne -> booking(1)
                event.x in widthColumnOne && event.y in heightRowTwo -> booking(2)
                event.x in widthColumnTwo && event.y in heightRowTwo -> booking(3)
                event.x in widthColumnOne && event.y in heightRowTree -> booking(4)
                event.x in widthColumnTwo && event.y in heightRowTree -> booking(5)
                event.x in widthColumnOne && event.y in heightRowFour -> booking(6)
                event.x in widthColumnTwo && event.y in heightRowFour -> booking(7)
            }
        }
        return true
    }

    private fun booking(position: Int) {
        for (seat in seats) {
            seat.isBooked = false
        }
        seats[position].apply {
            seat = this
            isBooked = true
        }
        invalidate()
    }
}