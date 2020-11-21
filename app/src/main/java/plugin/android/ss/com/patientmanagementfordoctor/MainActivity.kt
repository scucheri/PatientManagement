package plugin.android.ss.com.patientmanagementfordoctor

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var theAdapter: PatientListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListView()
        initSaveBtn()
    }

    private fun initSaveBtn() {
        save_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                if (!hasPermission) {
                    ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
                } else {
                    CsvUtil.save(this@MainActivity, theAdapter?.patientName, theAdapter?.infoNumber?:"1",  theAdapter?.optionListData)
                    Toast.makeText(applicationContext, "保存成功", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initListView() {
        var data: ArrayList<PatientOptionData> = ArrayList()
        data.add(PatientOptionData(OptionType.TITLE, "生命体征及体格检查", null, null))
        data.add(PatientOptionData(OptionType.NAME, "姓名:", null, null))
        data.add(PatientOptionData(OptionType.NUMBER, "登记编号:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "出生日期:", null, null))
        data.add(PatientOptionData(OptionType.SPINNER, "性别:", null, arrayOf("男", "女")))
        data.add(PatientOptionData(OptionType.EDIT, "详细吸烟史:", null, null))

        data.add(PatientOptionData(OptionType.EDIT, "病理诊断:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "确诊日期:", null, null))
        data.add(PatientOptionData(OptionType.SPINNER, "手术:", null, arrayOf("活检", "穿刺")))
        data.add(
            PatientOptionData(
                OptionType.SPINNER,
                "基因检测标本类型:",
                null,
                arrayOf("病理组织", "胸腹水")
            )
        )


        data.add(PatientOptionData(OptionType.EDIT, "检测结果:", null, null))


        data.add(PatientOptionData(OptionType.EDIT, "手术史:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "靶向药物等治疗史:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "化疗史无:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "放疗史无:", null, null))


        data.add(PatientOptionData(OptionType.EDIT, "体重:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "身高:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "体质指数BMI:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "ECOG评分:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "腰围:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "心率:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "血压:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "呼吸:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "体温:", null, null))


        data.add(PatientOptionData(OptionType.TITLE, "检查项目", null, null))

        data.add(PatientOptionData(OptionType.EDIT, "精神状况:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "皮肤:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "呼吸系统:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "循环系统:", null, null))

        data.add(PatientOptionData(OptionType.EDIT, "消化系统:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "神经系统:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "心血管系统:", null, null))
        data.add(PatientOptionData(OptionType.EDIT, "其他:", null, null))

        val layoutManager = LinearLayoutManager(this)
        patient_info_list.setLayoutManager(layoutManager)
        theAdapter = PatientListAdapter(this, data)
        patient_info_list.adapter = theAdapter


    }
}
