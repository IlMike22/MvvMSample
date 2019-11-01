package com.raywenderlich.android.creaturemon.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.raywenderlich.android.creaturemon.model.Creature

class AllCreaturesViewModel: ViewModel() {
    private val allCreaturesLiveData = MutableLiveData<List<Creature>>()
    fun getAllCreaturesLiveData():LiveData<List<Creature>> = allCreaturesLiveData



}