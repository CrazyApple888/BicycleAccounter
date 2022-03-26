package ru.nsu.fit.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nsu.fit.databinding.FragmentDetailedBicycleBinding

class DetailedBicycleFragment : Fragment() {

    private var _binding: FragmentDetailedBicycleBinding? = null
    private val binding: FragmentDetailedBicycleBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedBicycleBinding.inflate(inflater, container, false)

        binding.text.text = arguments?.getLong("bikeId").toString()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}