package dev.dlmousey.justalifttracker.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.dlmousey.justalifttracker.R

private const val NAME_INPUT: String = "NAME_INPUT"
private const val TYPE_INPUT: String = "TYPE_INPUT"

class CreateLiftFragment : BottomSheetDialogFragment() {

    private var isEditing: Boolean = false
    private var nameInput: String? = null
    private var typeInput: String? = null

    private lateinit var formLiftNameEditText: EditText
    private lateinit var formLiftTypeSpinner: Spinner
    private lateinit var formLiftSubmitButton: Button

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
         super.setupDialog(dialog, style)

        val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_create_lift, null)
        dialog.setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Add a lift"

        arguments?.let {
            isEditing = it.getString(NAME_INPUT) !== "NAME_INPUT" && it.getString(TYPE_INPUT) !== "TYPE_INPUT"
            nameInput = it.getString(NAME_INPUT)
            typeInput = it.getString(TYPE_INPUT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_create_lift, container, false)

        formLiftNameEditText = view.findViewById(R.id.form_lift_name_edittext)
        formLiftTypeSpinner = view.findViewById(R.id.form_lift_type_spinner)
        formLiftSubmitButton = view.findViewById(R.id.form_lift_submit_button)

        if (isEditing) {
            formLiftNameEditText.setText(nameInput)

            val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.lift_types,
                android.R.layout.simple_spinner_item)

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            formLiftTypeSpinner.adapter = adapter

            val position: Int = adapter.getPosition(typeInput)
            formLiftTypeSpinner.setSelection(position)
        }

        formLiftSubmitButton.setOnClickListener {
            val name = formLiftNameEditText.text.toString()
            val type = formLiftTypeSpinner.selectedItem.toString()

            val emptyName = TextUtils.isEmpty(name)

            if (emptyName) {
                Toast.makeText(context, "Name is required", Toast.LENGTH_SHORT).show()
            } else {
                setFragmentResult("newLift", bundleOf(
                    "name" to name,
                    "type" to type
                ))

                formLiftNameEditText.text = null
                dialog?.dismiss()
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, type: String) =
            CreateLiftFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_INPUT, name)
                    putString(TYPE_INPUT, type)
                }
            }
    }
}