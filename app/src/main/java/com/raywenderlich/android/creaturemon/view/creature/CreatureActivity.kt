package com.raywenderlich.android.creaturemon.view.creature

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.raywenderlich.android.creaturemon.R
import com.raywenderlich.android.creaturemon.model.AttributeStore
import com.raywenderlich.android.creaturemon.model.AttributeType
import com.raywenderlich.android.creaturemon.model.AttributeValue
import com.raywenderlich.android.creaturemon.model.Avatar
import com.raywenderlich.android.creaturemon.view.avatars.AvatarAdapter
import com.raywenderlich.android.creaturemon.view.avatars.AvatarBottomDialogFragment
import com.raywenderlich.android.creaturemon.viewModel.CreatureViewModel
import kotlinx.android.synthetic.main.activity_creature.*


class CreatureActivity : AppCompatActivity(), AvatarAdapter.AvatarListener {

    private lateinit var viewModel: CreatureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creature)

        // pass the viewmodel the the value (jetpack architecture component)
        viewModel = ViewModelProviders.of(this).get(CreatureViewModel::class.java)

        configureUI()
        configureSpinnerAdapters()
        configureSpinnerListeners()
        configureEditText()
        configureClickListeners()
        configureLiveDataObservers()
    }

    private fun configureUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.add_creature)
        if (viewModel.drawable != 0) hideTapLabel()
    }

    private fun configureSpinnerAdapters() {
        intelligence.adapter = ArrayAdapter<AttributeValue>(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.INTELLIGENCE)
        strength.adapter = ArrayAdapter<AttributeValue>(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.STRENGTH)
        endurance.adapter = ArrayAdapter<AttributeValue>(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.ENDURANCE)
    }

    private fun configureSpinnerListeners() {
        intelligence.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.INTELLIGENCE, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        strength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.STRENGTH, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        endurance.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.ENDURANCE, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun configureEditText() {
        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.name = s.toString() //pass current typed name to vm
            }
        })
    }

    private fun configureClickListeners() {
        avatarImageView.setOnClickListener {
            val bottomDialogFragment = AvatarBottomDialogFragment.newInstance()
            bottomDialogFragment.show(supportFragmentManager, "AvatarBottomDialogFragment")
        }

        saveButton.setOnClickListener {
            if (viewModel.saveCreature()) {
                Toast.makeText(this, getString(R.string.creature_saved), Toast.LENGTH_SHORT).show()
                finish()
            } else
                Toast.makeText(this, getString(R.string.error_saving_creature), Toast.LENGTH_SHORT).show()
        }
    }

    private fun configureLiveDataObservers() {
        viewModel.getCreatureLiveData().observe(this, Observer { creature ->
            //pass the creature data to the view if something changed (via observer)
            creature?.let {
                hitPoints.text = creature.hitpoints.toString()
                avatarImageView.setImageResource(creature.drawable)
                nameEditText.setText(creature.name)
            }

        })
    }

    override fun avatarClicked(avatar: Avatar) {
        viewModel.drawableSelected(avatar.drawable) // pass current selected drawable to vm
        hideTapLabel()
    }

    private fun hideTapLabel() {
        tapLabel.visibility = View.INVISIBLE
    }
}
