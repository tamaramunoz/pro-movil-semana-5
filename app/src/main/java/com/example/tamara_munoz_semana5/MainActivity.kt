package com.example.tamara_munoz_semana5

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    data class Vendedor(val nombre: String, val area: String, val imagenResId: Int, var imagenBitmap: Bitmap? = null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgAnimacion = findViewById<ImageView>(R.id.imgAnimacion)
        imgAnimacion.setBackgroundResource(R.drawable.animacion_retail)
        val frameAnimation = imgAnimacion.background as AnimationDrawable
        frameAnimation.start()

        val lvVendedores = findViewById<ListView>(R.id.lvVendedores)
        val listaVendedores = mutableListOf(
            Vendedor("Juan Pérez", "Electrónica", R.drawable.juan_perez),
            Vendedor("Ana Gómez", "Línea Blanca", R.drawable.ana_gomez),
            Vendedor("Carla Ruiz", "Deportes", R.drawable.carla_ruiz),
            Vendedor("María López", "Vestuario", R.drawable.maria_lopez),
            Vendedor("Pedro Díaz", "Calzado", R.drawable.pedro_diaz)
        )

        val adapter = object : ArrayAdapter<Vendedor>(this, R.layout.item_vendedor, listaVendedores) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: layoutInflater.inflate(R.layout.item_vendedor, parent, false)
                val vendedor = getItem(position)

                val tvNombre = view.findViewById<TextView>(R.id.tvNombreVendedor)
                val tvArea = view.findViewById<TextView>(R.id.tvAreaVendedor)
                val imgVendedor = view.findViewById<ImageView>(R.id.ivVendedor)

                tvNombre.text = vendedor?.nombre
                tvArea.text = vendedor?.area
                vendedor?.let {
                    if (it.imagenBitmap != null) {
                        imgVendedor.setImageBitmap(it.imagenBitmap)
                    } else {
                        imgVendedor.setImageResource(it.imagenResId)
                    }
                }

                return view
            }
        }
        lvVendedores.adapter = adapter

        mediaPlayer = MediaPlayer.create(this, R.raw.moneda)
        lvVendedores.setOnItemClickListener { _, _, position, _ ->
            if (position == 2) {
                mediaPlayer.seekTo(0)
                mediaPlayer.start()
            }
        }

        val btnTomarFoto = findViewById<Button>(R.id.btnTomarFoto)
        val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                imageBitmap?.let {
                    listaVendedores[2].imagenBitmap = it
                    adapter.notifyDataSetChanged()
                }
            }
        }

        btnTomarFoto.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                takePictureLauncher.launch(cameraIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val btnVerGrafico = findViewById<Button>(R.id.btnVerGrafico)
        val barChart = findViewById<BarChart>(R.id.barChartVentas)

        btnVerGrafico.setOnClickListener {
            if (barChart.visibility == View.GONE) {
                barChart.visibility = View.VISIBLE
                cargarDatosGrafico(barChart)
            } else {
                barChart.visibility = View.GONE
            }
        }
    }

    private fun cargarDatosGrafico(chart: BarChart) {
        val entradas = ArrayList<BarEntry>()
        entradas.add(BarEntry(0f, 150f))
        entradas.add(BarEntry(1f, 120f))
        entradas.add(BarEntry(2f, 200f))
        entradas.add(BarEntry(3f, 90f))
        entradas.add(BarEntry(4f, 140f))

        val dataSet = BarDataSet(entradas, "Rendimiento de Ventas")
        val barData = BarData(dataSet)
        chart.data = barData

        val etiquetas = arrayOf("Pérez", "Gómez", "Ruiz", "López", "Díaz")
        val xAxis = chart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(etiquetas)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        chart.axisRight.isEnabled = false
        chart.description.isEnabled = false
        chart.invalidate()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}