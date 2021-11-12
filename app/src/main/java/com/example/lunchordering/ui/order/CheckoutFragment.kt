package com.example.lunchordering.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lunchordering.R
import com.example.lunchordering.databinding.FragmentCheckoutBinding
import com.example.lunchordering.model.OrderViewModel
import com.google.android.material.snackbar.Snackbar


class CheckoutFragment : Fragment() {


    // Binding object instance corresponding to the fragment_start_order.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var _binding: FragmentCheckoutBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        val root = binding.root

        // Calculate tax and total upon creating the CheckoutFragment view
        sharedViewModel.calculateTaxAndTotal()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel= sharedViewModel
            checkoutFragment= this@CheckoutFragment

        }
    }

    /**
     * Cancel the order and start over.
     */
    fun cancelOrder() {
        sharedViewModel.resetOrder()

        findNavController().navigate(R.id.action_checkoutFragment_to_startOrderFragment)
    }

    /**
     * Submit order and navigate to home screen.
     */
    fun submitOrder() {

        Snackbar.make(binding.root, R.string.submit_order, Snackbar.LENGTH_SHORT).show()
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_checkoutFragment_to_startOrderFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}