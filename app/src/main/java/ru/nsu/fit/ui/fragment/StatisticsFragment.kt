package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentStatisticsBinding
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.presentation.viewmodel.StatisticViewModel
import javax.inject.Inject

class StatisticsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: StatisticViewModel

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[StatisticViewModel::class.java]

        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.statistics.collect { stats ->
                binding.dirtyProfitValue.text =
                    String.format(binding.dirtyProfitValue.text.toString(), stats.profitDirty)
                binding.issuesLossesValue.text =
                    String.format(binding.issuesLossesValue.text.toString(), stats.issuesLosses)
                binding.moneySpentValue.text =
                    String.format(binding.moneySpentValue.text.toString(), stats.moneySpent)
                binding.profitValue.text =
                    String.format(binding.profitValue.text.toString(), stats.profitClear)
                binding.bicyclesCountValue.text = stats.bicyclesSold.toString()
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.messages.collect {
                if (it is Result.Failure) {
                    showToastShort(getString(R.string.stats_loading_failed))
                }
            }
        }
    }

    private fun showToastShort(hint: String) {
        Toast.makeText(
            requireContext(),
            hint,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}