package com.aviasim.plane.game

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.aviasim.plane.game.databinding.FragmentStartBinding
import java.io.IOException


class FragmentStart : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private var ind = 0;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater,container,false)
        ind = requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getInt("sounds",1)
        try {
            // get input stream
            val ims = requireContext().assets.open("bg.png")
            // load image as Drawable
            val d = Drawable.createFromStream(ims, null)
            // set image to ImageView
            binding.bg.setImageDrawable(d)
            ims.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        change()
        binding.imageView5.setOnClickListener {
            ind  = 0
            change()
            requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putInt("sounds",ind).apply()
        }
        binding.imageView4.setOnClickListener {
            ind = 1
            change()
            requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putInt("sounds",ind).apply()
        }
        binding.imageView6.setOnClickListener {
            ind = 2
            change()
            requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putInt("sounds",ind).apply()
        }
        binding.imageView.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.navigate(R.id.fragmentGame)
        }
        binding.button.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.navigate(R.id.shopFragment)
        }
        return binding.root
    }

    fun change() {
        when(ind) {
            0 -> {
                binding.imageView5.setColorFilter(Color.BLACK)
                binding.imageView5.setBackgroundResource(R.drawable.bg_red)

                binding.imageView4.setColorFilter(requireContext().getColor(R.color.bg))
                binding.imageView4.setBackgroundResource(R.drawable.bg)

                binding.imageView6.setColorFilter(requireContext().getColor(R.color.bg))
                binding.imageView6.setBackgroundResource(R.drawable.bg)
            }
            1 -> {
                binding.imageView4.setColorFilter(Color.BLACK)
                binding.imageView4.setBackgroundResource(R.drawable.bg_red)

                binding.imageView5.setColorFilter(requireContext().getColor(R.color.bg))
                binding.imageView5.setBackgroundResource(R.drawable.bg)

                binding.imageView6.setColorFilter(requireContext().getColor(R.color.bg))
                binding.imageView6.setBackgroundResource(R.drawable.bg)
            }
            2 -> {
                binding.imageView6.setColorFilter(Color.BLACK)
                binding.imageView6.setBackgroundResource(R.drawable.bg_red)

                binding.imageView5.setColorFilter(requireContext().getColor(R.color.bg))
                binding.imageView5.setBackgroundResource(R.drawable.bg)

                binding.imageView4.setColorFilter(requireContext().getColor(R.color.bg))
                binding.imageView4.setBackgroundResource(R.drawable.bg)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        var count = requireActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE).getInt("count",500)
        binding.textView2.text = "$count"
        binding.imageView.setImageResource(
            requireContext().getSharedPreferences(
                "prefs",
                Context.MODE_PRIVATE
            ).getInt("id", R.drawable.avia1))
    }
}