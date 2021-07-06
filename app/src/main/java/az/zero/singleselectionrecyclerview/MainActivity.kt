package az.zero.singleselectionrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import az.zero.singleselectionrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemAdapter = ItemAdapter()
        binding.apply {
            rvMain.apply {
                adapter = itemAdapter
                itemAnimator = null
                layoutManager = GridLayoutManager(this@MainActivity, 2)
            }
        }
        itemAdapter.submitList(list)
    }

    private val list = List(100) {
        Item("Happy")
    }
}