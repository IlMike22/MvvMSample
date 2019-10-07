package com.raywenderlich.android.creaturemon.model.room

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.raywenderlich.android.creaturemon.app.CreaturemonApplication
import com.raywenderlich.android.creaturemon.model.Creature
import com.raywenderlich.android.creaturemon.model.CreatureRepository

class RoomRepository : CreatureRepository {

  private val creatureDao: CreatureDao = CreaturemonApplication.database.creatureDao()
  private val allCreatures: LiveData<List<Creature>>

  init {
      allCreatures = creatureDao.getAllCreatures()
  }

  override fun saveCreature(creature: Creature) {
    InsertAsyncTask(creatureDao).execute(creature)
  }

  override fun getAllCreatures(): LiveData<List<Creature>> = allCreatures

  override fun clearAllCreatures() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private class InsertAsyncTask internal constructor(private val dao: CreatureDao) : AsyncTask<Creature, Void, Void>() {
    override fun doInBackground(vararg params: Creature): Void? {
      return null
    }
  }

  private class DeleteAsyncTask internal constructor(private val dao: CreatureDao) : AsyncTask<Creature, Void, Void>() {
    override fun doInBackground(vararg params: Creature): Void? {
      return null
    }
  }
}