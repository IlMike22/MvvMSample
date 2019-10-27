package com.raywenderlich.android.creaturemon.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.provider.Telephony
import com.raywenderlich.android.creaturemon.model.*

class CreatureViewModel(private val generator: CreatureGenerator = CreatureGenerator()) : ViewModel() {
    private val creatureLiveData = MutableLiveData<Creature>()
    fun getCreatureLiveData(): LiveData<Creature> = creatureLiveData

    var name = ""
    var strength = 0
    var endurance = 0
    var intelligence = 0
    var drawable = 0

    lateinit var creature: Creature

    fun updateCreature() {
        val attibutes = CreatureAttributes(intelligence, strength, endurance)
        creature = generator.generateCreature(attibutes, name, drawable)
        creatureLiveData.postValue(creature)
    }

    fun attributeSelected(attributeType: AttributeType, position: Int) {
        when (attributeType) {
            AttributeType.INTELLIGENCE ->
                intelligence = AttributeStore.INTELLIGENCE[position].value
            AttributeType.ENDURANCE ->
                endurance = AttributeStore.ENDURANCE[position].value
            AttributeType.STRENGTH ->
                strength = AttributeStore.STRENGTH[position].value
        }

        //call the updateCreature method to pass the new data to the view (live-data)
        updateCreature()
    }

    fun drawableSelected(drawable:Int)
    {
        this.drawable = drawable
        updateCreature()
    }

}