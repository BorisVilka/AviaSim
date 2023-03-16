package com.aviasim.plane.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.aviasim.plane.game.databinding.FragmentGameBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class FragmentGame : Fragment() {

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater,container,false)
        binding.progressBar.max = 100
        binding.game.togglePause()
        binding.game.subject.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
            binding.progressBar.progress = binding.game.progr
        }
        binding.game.setEnd(object : GameView.EndListener{
            override fun end() {
                binding.progressBar.progress = 0
                binding.game.progr = 0
                var count = binding.game.score
                binding.textView4.text = "$count"
                requireActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putInt("count",binding.game.score).apply()
            }

            override fun progr() {

            }

        } )
        binding.imageView10.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
        }
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        var count = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt("count",500)
        binding.textView4.text = "$count"
    }

}