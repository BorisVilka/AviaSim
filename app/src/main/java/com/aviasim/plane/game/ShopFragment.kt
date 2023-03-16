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
import com.aviasim.plane.game.databinding.FragmentShopBinding
import java.io.IOException


class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    private var ind = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopBinding.inflate(inflater,container,false)
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
        ind = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("id", R.drawable.avia1)
        change()
        binding.imageView8.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
        }
        binding.imageView11.setOnClickListener {
            ind = R.drawable.avia1
            change()
        }
        if(!requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("2",false)) {
            binding.imageView13.setColorFilter(requireContext().getColor(R.color.grey))
            binding.textView3.visibility = View.VISIBLE
            binding.imageView21.visibility = View.VISIBLE
        } else {
            binding.textView3.visibility = View.INVISIBLE
            binding.imageView21.visibility = View.INVISIBLE
        }
        binding.imageView13.setOnClickListener {
            if(!requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("2",false)) {
                if(requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)>=500) {
                    requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putBoolean("2",true).apply()
                    binding.textView3.visibility = View.INVISIBLE
                    binding.imageView21.visibility = View.INVISIBLE
                    binding.imageView13.setColorFilter(Color.TRANSPARENT)
                    requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                        .putInt("count",requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)-500).apply()
                    price()
                }
            } else {
              ind = R.drawable.avia2
                change()
            }
        }
        if(!requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("3",false)) {
            binding.imageView15.setColorFilter(requireContext().getColor(R.color.grey))
            binding.textView5.visibility = View.VISIBLE
            binding.imageView22.visibility = View.VISIBLE
        } else {
            binding.textView5.visibility = View.INVISIBLE
            binding.imageView22.visibility = View.INVISIBLE
        }
        binding.imageView15.setOnClickListener {
            if(!requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("3",false)) {
                if(requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)>=500) {
                    requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putBoolean("3",true).apply()
                    binding.textView5.visibility = View.INVISIBLE
                    binding.imageView22.visibility = View.INVISIBLE
                    binding.imageView15.setColorFilter(Color.TRANSPARENT)
                    requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                        .putInt("count",requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)-500).apply()
                    price()
                }
            } else {
                ind = R.drawable.avia3
                change()
            }
        }
        if(!requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("4",false)) {
            binding.imageView17.setColorFilter(requireContext().getColor(R.color.grey))
            binding.textView6.visibility = View.VISIBLE
            binding.imageView23.visibility = View.VISIBLE
        } else {
            binding.textView6.visibility = View.INVISIBLE
            binding.imageView23.visibility = View.INVISIBLE
        }
        binding.imageView17.setOnClickListener {
            if(!requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("4",false)) {
                if(requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)>=500) {
                    requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putBoolean("4",true).apply()
                    binding.textView6.visibility = View.INVISIBLE
                    binding.imageView23.visibility = View.INVISIBLE
                    binding.imageView17.setColorFilter(Color.TRANSPARENT)
                    requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                        .putInt("count",requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)-500).apply()
                    price()
                }
            } else {
                ind = R.drawable.avia4
                change()
            }
        }
        if(!requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("5",false)) {
            binding.imageView19.setColorFilter(requireContext().getColor(R.color.grey))
            binding.textView7.visibility = View.VISIBLE
            binding.imageView24.visibility = View.VISIBLE
        } else {
            binding.textView7.visibility = View.INVISIBLE
            binding.imageView24.visibility = View.INVISIBLE
        }
        binding.imageView19.setOnClickListener {
            if(!requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("5",false)) {
                if(requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)>=500) {
                    requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putBoolean("5",true).apply()
                    binding.textView7.visibility = View.INVISIBLE
                    binding.imageView24.visibility = View.INVISIBLE
                    binding.imageView19.setColorFilter(Color.TRANSPARENT)
                    requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                        .putInt("count",requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)-500).apply()
                    price()
                }
            } else {
                ind = R.drawable.avia5
                change()
            }
        }
        return binding.root
    }
    fun price() {
        var count = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)
        binding.textView4.text = "$count"
    }
    override fun onResume() {
        super.onResume()
        price()
    }
    fun change() {
        requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putInt("id",ind).apply()
        when(ind) {
            R.drawable.avia1 -> {
                binding.imageView12.visibility = View.VISIBLE
                binding.imageView14.visibility = View.INVISIBLE
                binding.imageView16.visibility = View.INVISIBLE
                binding.imageView18.visibility = View.INVISIBLE
                binding.imageView20.visibility = View.INVISIBLE
            }
            R.drawable.avia2 -> {
                binding.imageView12.visibility = View.INVISIBLE
                binding.imageView14.visibility = View.VISIBLE
                binding.imageView16.visibility = View.INVISIBLE
                binding.imageView18.visibility = View.INVISIBLE
                binding.imageView20.visibility = View.INVISIBLE
            }
            R.drawable.avia3 -> {
                binding.imageView12.visibility = View.INVISIBLE
                binding.imageView14.visibility = View.INVISIBLE
                binding.imageView16.visibility = View.VISIBLE
                binding.imageView18.visibility = View.INVISIBLE
                binding.imageView20.visibility = View.INVISIBLE
            }
            R.drawable.avia4 -> {
                binding.imageView12.visibility = View.INVISIBLE
                binding.imageView14.visibility = View.INVISIBLE
                binding.imageView16.visibility = View.INVISIBLE
                binding.imageView18.visibility = View.VISIBLE
                binding.imageView20.visibility = View.INVISIBLE
            }
            R.drawable.avia5 -> {
                binding.imageView12.visibility = View.INVISIBLE
                binding.imageView14.visibility = View.INVISIBLE
                binding.imageView16.visibility = View.INVISIBLE
                binding.imageView18.visibility = View.INVISIBLE
                binding.imageView20.visibility = View.VISIBLE
            }
        }
    }
}