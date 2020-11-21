package plugin.android.ss.com.patientmanagementfordoctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class PatientListAdapter(val context: Context, val optionListData: ArrayList<PatientOptionData>) :
    RecyclerView.Adapter<PatientListAdapter.PatientListViewHolder>() {
    var fileName: String? = null

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
        if (data.type == OptionType.EDIT || data.type == OptionType.NAME) {
            holder.optionEditTextView.visibility = View.VISIBLE
            holder.optionSpinnerView.visibility = View.GONE
            holder.optionEditTextView.setOnFocusChangeListener(object : View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if (!hasFocus) {
                        data.optionResult = holder.optionEditTextView.text.toString()
                        if(data.type == OptionType.NAME){
                            fileName = data.optionResult + ".csv"
                        }
                    }
                }
            })
        } else if (data.type == OptionType.SPINNER && data.optionSpinnerSlections != null) {
            holder.optionEditTextView.visibility = View.GONE
            holder.optionSpinnerView.visibility = View.VISIBLE
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

    class PatientListViewHolder(
        itemView: View
    ) : ViewHolder(itemView) {
        var optionNameView: TextView = itemView.findViewById(R.id.option_name_view)
        var optionEditTextView: EditText = itemView.findViewById(R.id.option_edit_textview)
        var optionSpinnerView: Spinner = itemView.findViewById(R.id.option_spinner_view)
    }


}
