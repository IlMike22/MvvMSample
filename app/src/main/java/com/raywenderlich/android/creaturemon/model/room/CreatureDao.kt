package com.raywenderlich.android.creaturemon.model.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.raywenderlich.android.creaturemon.model.Creature

@Dao
interface CreatureDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createCreature(creature: Creature)

    @Delete
    fun removeCreature(vararg creature:Creature)

    @Query("SELECT * FROM creature_table ORDER BY name ASC")
    fun getAllCreatures(): LiveData<List<Creature>>
}