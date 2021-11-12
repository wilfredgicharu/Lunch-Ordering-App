package com.example.lunchordering.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lunchordering.data.DataSource
import java.text.NumberFormat

class OrderViewModel: ViewModel() {

    val menuItem= DataSource.menuItems

    // Default values for item prices
    private var previousEntreePrice = 0.0
    private var previousSidePrice = 0.0
    private var previousAccompanimentPrice = 0.0

    // Default tax rate
    private val taxRate = 0.08

    // Entree for the order
    private val _entree = MutableLiveData<MenuItem?>()
    val entree: LiveData<MenuItem?> = _entree

    // Side for the order
    private val _side = MutableLiveData<MenuItem?>()
    val side: LiveData<MenuItem?> = _side

    // Accompaniment for the order.
    private val _accompaniment = MutableLiveData<MenuItem?>()
    val accompaniment: LiveData<MenuItem?> = _accompaniment

    // Subtotal for the order
    private val _subtotal = MutableLiveData(0.0)
    val subtotal: LiveData<String> = Transformations.map(_subtotal) {
        NumberFormat.getCurrencyInstance().format(it)
    }
    // Total cost of the order
    private val _total = MutableLiveData(0.0)
    val total: LiveData<String> = Transformations.map(_total) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    // Tax for the order
    private val _tax = MutableLiveData(0.0)
    val tax: LiveData<String> = Transformations.map(_tax) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    /**
     * Set the entree for the order.
     */
    fun setEntree(entree: String) {
        if (_entree.value != null){
            previousEntreePrice= _entree.value!!.price
        }
        if (_subtotal.value != null){
            _subtotal.value= _subtotal.value!! - previousEntreePrice
        }
        _entree.value= menuItem[entree]
        updateSubtotal(_entree.value!!.price)

        // TODO: if _entree.value is not null, set the previous entree price to the current
        //  entree price.


        // TODO: if _subtotal.value is not null subtract the previous entree price from the current
        //  subtotal value. This ensures that we only charge for the currently selected entree.

        // TODO: set the current entree value to the menu item corresponding to the passed in string
        // TODO: update the subtotal to reflect the price of the selected entree.
    }

    /**
     * Set the side for the order.
     */
    fun setSide(side: String) {

        if (_side.value != null){
            previousSidePrice= _side.value!!.price
        }
        if (_subtotal.value != null){
            _subtotal.value= _subtotal.value!! - previousSidePrice
        }
        _side.value= menuItem[side]
        updateSubtotal(_side.value!!.price)


        // TODO: if _side.value is not null, set the previous side price to the current side price.

        // TODO: if _subtotal.value is not null subtract the previous side price from the current
        //  subtotal value. This ensures that we only charge for the currently selected side.

        // TODO: set the current side value to the menu item corresponding to the passed in string
        // TODO: update the subtotal to reflect the price of the selected side.
    }

    /**
     * Set the accompaniment for the order.
     */
    fun setAccompaniment(accompaniment: String) {
       if (_accompaniment.value != null){
           previousAccompanimentPrice= _accompaniment.value!!.price
       }
        // TODO: if _accompaniment.value is not null, set the previous accompaniment price to the
        //  current accompaniment price.
        if (_accompaniment.value != null){
            previousAccompanimentPrice= _subtotal.value!! - previousAccompanimentPrice
        }

        // TODO: if _accompaniment.value is not null subtract the previous accompaniment price from
        //  the current subtotal value. This ensures that we only charge for the currently selected
        //  accompaniment.
        _accompaniment.value= menuItem[accompaniment]
        updateSubtotal(_accompaniment.value!!.price)

        // TODO: set the current accompaniment value to the menu item corresponding to the passed in
        //  string
        // TODO: update the subtotal to reflect the price of the selected accompaniment.
    }

    /**
     * Update subtotal value.
     */
    private fun updateSubtotal(itemPrice: Double) {
        if (_subtotal.value != null){
            _subtotal.value= _subtotal.value!! + itemPrice
        } else{
            _subtotal.value= itemPrice
        }
        calculateTaxAndTotal()


    // TODO: if _subtotal.value is not null, update it to reflect the price of the recently
        //  added item.
        //  Otherwise, set _subtotal.value to equal the price of the item.

        // TODO: calculate the tax and resulting total
    }

    /**
     * Calculate tax and update total.
     */
    fun calculateTaxAndTotal() {
        // TODO: set _tax.value based on the subtotal and the tax rate.
        // TODO: set the total based on the subtotal and _tax.value.

        _tax.value= _subtotal.value!! * taxRate
        _total.value= _tax.value!! + _subtotal.value!!
    }

    /**
     * Reset all values pertaining to the order.
     */
    fun resetOrder() {
        previousEntreePrice = 0.0
        previousSidePrice = 0.0
        previousAccompanimentPrice = 0.0
        _entree.value = null
        _side.value = null
        _accompaniment.value = null
        _subtotal.value = 0.0
        _total.value = 0.0
        _tax.value = 0.0

    }
}