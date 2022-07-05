package dev.dlmousey.justalifttracker.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.dlmousey.justalifttracker.LiftTrackerApplication
import dev.dlmousey.justalifttracker.R
import dev.dlmousey.justalifttracker.database.models.Lift
import dev.dlmousey.justalifttracker.viewmodels.LiftViewModel
import dev.dlmousey.justalifttracker.viewmodels.LiftViewModelFactory
import java.time.LocalDate
import java.util.*

private const val NAME_INPUT = "NAME_INPUT"
private const val LIFT_INPUT = "LIFT_INPUT"
private const val DATE_INPUT = "DATE_INPUT"
private const val RPE_INPUT = "RPE_INPUT"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateSetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateSetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var nameInput: String? = null
    private var liftInput: Lift? = null
    private var dateInput: Date? = null
    private var rpeInput: Int? = null

    private lateinit var formSetLiftSpinner: Spinner
    private lateinit var formSetRepsEditText: EditText
    private lateinit var formSetWeightEditText: EditText
    private lateinit var formSetRpeEditText: EditText
    private lateinit var formSetSetNumberEditText: EditText
    private lateinit var formSetTimestampDatepicker: DatePicker
    private lateinit var formSetSubmitButton: Button

    private val liftViewModel: LiftViewModel by viewModels {
        LiftViewModelFactory((activity?.application as LiftTrackerApplication).liftRepository)
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_create_set, null)
        dialog.setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nameInput = it.getString(NAME_INPUT)
            liftInput = it.get(LIFT_INPUT) as Lift
            dateInput = Date(it.getLong(DATE_INPUT))
            rpeInput = it.getInt(RPE_INPUT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_create_set, container, false)

        formSetLiftSpinner = view.findViewById(R.id.form_set_lift_spinner)
        formSetRepsEditText = view.findViewById(R.id.form_set_reps_edittext)
        formSetWeightEditText = view.findViewById(R.id.form_set_weight_edittext)
        formSetRpeEditText = view.findViewById(R.id.form_set_rpe_edittext)
        formSetSetNumberEditText = view.findViewById(R.id.form_set_setnumber_edittext)
        formSetTimestampDatepicker = view.findViewById(R.id.form_set_timestamp_datepicker)
        formSetSubmitButton = view.findViewById(R.id.form_set_submit_button)

        // @TODO - editing support

        formSetSubmitButton.setOnClickListener {
            // HARD CODE LIFT ON FRAGMENT RESULT PROCESSOR
            val reps = formSetRepsEditText.text.toString()
            val weight = formSetWeightEditText.text.toString()
            val rpe = formSetRpeEditText.text.toString()
            val setNumber = formSetSetNumberEditText.text.toString()

            var date = LocalDate.of(
                formSetTimestampDatepicker.year,
                formSetTimestampDatepicker.month,
                formSetTimestampDatepicker.dayOfMonth
            )


            val emptyReps = TextUtils.isEmpty(reps)
            val emptyWeight = TextUtils.isEmpty(weight)
            val emptyRpe = TextUtils.isEmpty(rpe)
            val emptySetNumber = TextUtils.isEmpty(setNumber)

            if (emptyReps || emptyWeight || emptyRpe) {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                setFragmentResult("newSet", bundleOf(
                    "reps" to reps.toInt(),
                    "weight" to weight.toInt(),
                    "rpe" to rpe.toInt(),
                    "setNumber" to setNumber.toInt(),
                    "timestamp" to date.toEpochDay(),
                    "liftId" to 1L
                ))

                formSetRepsEditText.text = null
                formSetWeightEditText.text = null
                formSetRpeEditText.text = null
                formSetSetNumberEditText.text = null
                dialog?.dismiss()
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, lift: Lift, date: Date, rpe: Int) =
            CreateSetFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_INPUT, name)
                    putParcelable(LIFT_INPUT, lift)
                    putLong(DATE_INPUT, date.time)
                    putInt(RPE_INPUT, rpe)
                }
            }
    }
}