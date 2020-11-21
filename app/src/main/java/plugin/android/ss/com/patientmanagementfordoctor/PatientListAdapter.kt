package plugin.android.ss.com.patientmanagementfordoctor

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class PatientListAdapter(val context: Context, val optionListData: ArrayList<PatientOptionData>) :
    RecyclerView.Adapter<PatientListAdapter.PatientListViewHolder>() {
    var patientName: String? = null
    var infoNumber: String? = "1"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.patient_info_item_layout, parent, false) as LinearLayout
        return PatientListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return optionListData.size
    }

    override fun onBindViewHolder(holder: PatientListViewHolder, position: Int) {
        var data = optionListData.get(position)
        holder.optionNameView.text = data.optionName
        holder.optionSpinnerView.adapter = null

        holder.optionNameView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24f)
        holder.optionNameView.setTypeface(null, Typeface.NORMAL)
        holder.titleDivider.visibility = View.GONE
        if (data.type == OptionType.TITLE) {
            holder.optionNameView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30f)
            holder.optionNameView.setTypeface(null, Typeface.BOLD)
            holder.titleDivider.visibility = View.VISIBLE
            holder.optionEditTextView.visibility = View.GONE
            holder.optionSpinnerView.visibility = View.GONE
        } else if (data.type == OptionType.EDIT || data.type == OptionType.NAME) {
            holder.optionEditTextView.visibility = View.VISIBLE
            holder.optionSpinnerView.visibility = View.GONE
            if (data.optionResult != null) {
                holder.optionEditTextView.setText(data.optionResult)
            } else{
                holder.optionEditTextView.setText(null)
            }
            holder.optionEditTextView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    data.optionResult = s?.toString()
                    if (data.type == OptionType.NAME) {
                        patientName = data.optionResult
                    }
                    if (data.type == OptionType.NUMBER) {
                        infoNumber = data.optionResult
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        } else if (data.type == OptionType.SPINNER && data.optionSpinnerSlections != null) {
            holder.optionEditTextView.visibility = View.GONE
            holder.optionSpinnerView.visibility = View.VISIBLE
            if (data.optionResult != null) {
                holder.optionSpinnerView.setSelection(
                    getIndexOfStr(
                        data.optionResult,
                        data.optionSpinnerSlections
                    )
                )
            }
            val arrayAdapter: ArrayAdapter<String> =
                ArrayAdapter(
                    context,
                    R.layout.option_spinner_item,
                    data.optionSpinnerSlections!!
                )
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.optionSpinnerView.setAdapter(arrayAdapter)
            holder.optionSpinnerView.setOnItemSelectedListener(object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    data.optionResult = data.optionSpinnerSlections?.get(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
        }
    }

    private fun getIndexOfStr(optionResult: String?, optionSpinnerSlections: Array<String>?): Int {
        if (optionSpinnerSlections?.contains(optionResult) == true) {
            optionSpinnerSlections.forEachIndexed { index, element ->
                if (element == optionResult) {
                    return index
                }
            }
        }
        return 0
    }

    class PatientListViewHolder(
        itemView: View
    ) : ViewHolder(itemView) {
        var optionNameView: TextView = itemView.findViewById(R.id.option_name_view)
        var optionEditTextView: EditText = itemView.findViewById(R.id.option_edit_textview)
        var optionSpinnerView: Spinner = itemView.findViewById(R.id.option_spinner_view)
        var titleDivider: View = itemView.findViewById(R.id.title_divider)
    }


}
